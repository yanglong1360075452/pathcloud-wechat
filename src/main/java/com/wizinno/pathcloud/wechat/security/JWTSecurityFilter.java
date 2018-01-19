package com.wizinno.pathcloud.wechat.security;

import com.google.gson.Gson;
import com.wizinno.pathcloud.wechat.Exception.CustomException;
import com.wizinno.pathcloud.wechat.Exception.CustomExceptionCode;
import com.wizinno.pathcloud.wechat.data.attr.PathcloudApiUrl;
import com.wizinno.pathcloud.wechat.data.response.ResponseVO;
import com.wizinno.pathcloud.wechat.domain.UserMapper;
import com.wizinno.pathcloud.wechat.domain.model.User;
import com.wizinno.pathcloud.wechat.util.HttpClientUtil;
import com.wizinno.pathcloud.wechat.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by LiuMei on 2017-05-23.
 */
public class JWTSecurityFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(JWTSecurityFilter.class);
    private Gson gson = new Gson();

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpClientUtil httpClientUtil;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getServletPath();
        String method = httpRequest.getMethod();
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ResponseVO responseVO;

        if (!requestURI.equals("/api/wechat/login") && requestURI.indexOf("/api/wechat/auth") == -1 && !method.equals("OPTIONS")) {
            String auth = httpRequest.getHeader("Authorization");
            if (auth == null) {
                responseVO = new ResponseVO(CustomExceptionCode.AuthFailed, "认证失败");
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response(httpResponse, responseVO);
            }

            Claims claims = null;
            try {
                claims = jwtUtil.parseJWT(auth);
            } catch (Exception e) {
                responseVO = new ResponseVO(CustomExceptionCode.AuthFailed, "认证失败");
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response(httpResponse, responseVO);
            }
            if (claims != null) {
                String subject = claims.getSubject();
                Gson gson = new Gson();
                User user = gson.fromJson(subject, User.class);
                String openId = user.getOpenId();
                request.setAttribute("openId", openId);
                request.setAttribute("username", user.getUsername());

                /**
                 * 检查用户cookie是否timeout
                 * 如果是，调用login接口重新获取cookie
                 */
                User user1 = userMapper.selectByOpenId(openId);
                if (user1 != null) {
                    String cookie = user1.getCookie();
                    try {
                        httpClientUtil.doGet(PathcloudApiUrl.checkCookie.toString(), null, cookie);
                    } catch (Exception e) {
                        Map<String, Object> login = null;
                        try {
                            login = httpClientUtil.login(user1.getUsername(), user1.getPassword());
                            user1.setCookie(login.get("cookie").toString());
                            userMapper.update(user1);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                    try {
                        chain.doFilter(request, response);
                        return;
                    } catch (Exception e) {
                        Throwable cause = e.getCause();
                        Class<? extends Throwable> exception = cause.getClass();
                        if (exception == CustomException.class) {
                            CustomException customException = (CustomException) cause;
                            Integer code = customException.getCode();
                            if (code.equals(CustomExceptionCode.AccessDenied)) {
                                responseVO = new ResponseVO(code,CustomExceptionCode.getReasonByCode(code,null));
                                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                response(httpResponse, responseVO);
                            } else {
                                responseVO = new ResponseVO(code,CustomExceptionCode.getReasonByCode(code,"未知错误"));
                                response(httpResponse, responseVO);
                            }
                        }
                    }
                } else {
                    responseVO = new ResponseVO(CustomExceptionCode.AuthFailed, "认证失败");
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response(httpResponse, responseVO);
                }
            } else {
                responseVO = new ResponseVO(CustomExceptionCode.AuthFailed, "认证失败");
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response(httpResponse, responseVO);
            }

        } else {
            chain.doFilter(request, response);
            return;
        }
    }

    private void response(ServletResponse response, ResponseVO responseVO) {
        try {
            response.getOutputStream().write(gson.toJson(responseVO).toString().getBytes("UTF-8"));
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void destroy() {
    }
}
