package com.wizinno.pathcloud.wechat.controller;

import com.wizinno.pathcloud.wechat.Exception.CustomException;
import com.wizinno.pathcloud.wechat.Exception.CustomExceptionCode;
import com.wizinno.pathcloud.wechat.data.Config;
import com.wizinno.pathcloud.wechat.data.attr.PathcloudApiUrl;
import com.wizinno.pathcloud.wechat.data.condition.ApplicantCondition;
import com.wizinno.pathcloud.wechat.data.response.ResponseVO;
import com.wizinno.pathcloud.wechat.data.vo.ApplicantVO;
import com.wizinno.pathcloud.wechat.domain.ApplicantMapper;
import com.wizinno.pathcloud.wechat.domain.UserMapper;
import com.wizinno.pathcloud.wechat.domain.model.Applicant;
import com.wizinno.pathcloud.wechat.domain.model.LoginRequest;
import com.wizinno.pathcloud.wechat.domain.model.User;
import com.wizinno.pathcloud.wechat.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by LiuMei on 2017-05-19.
 */
@RestController
@RequestMapping("/api/wechat")
public class AuthController extends BaseController {

    @Autowired
    HttpClientUtil httpClientUtil;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    HttpServletRequest httpRequest;

    @Autowired
    ApplicantMapper applicantMapper;

    /**
     * 网页授权
     *
     * 已绑定自动登录返回token
     * @param code
     * @return
     */
    @RequestMapping(value = "/auth/{code}")
    public ResponseVO getAccessToken(@PathVariable("code") String code) throws CustomException {
        AccessToken accessToken = WechatUtil.getWebAuthAccessToken(code);
        String openId = accessToken.getOpenId();
        User user = userMapper.selectByOpenId(openId);
        Map<String, String> result = new HashedMap();
        if (user != null) {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setOpenId(openId);
            loginRequest.setUsername(user.getUsername());
            loginRequest.setPassword(user.getPassword());
            return login(loginRequest);
        } else {
            result.put("openId", openId);
            return new ResponseVO(result);
        }
    }

    /**
     * 登录
     *
     * @param loginRequest
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO login(@RequestBody LoginRequest loginRequest) throws CustomException {
        String password = loginRequest.getPassword();
        String username = loginRequest.getUsername();
        String openId = loginRequest.getOpenId();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new CustomException(CustomExceptionCode.ParamError);
        }
        if(StringUtils.isBlank(openId)){
            //如果openId为空,解除用户与微信绑定关系
            userMapper.deleteByUsername(username);
            throw new CustomException(CustomExceptionCode.ParamError);
        }
        Map<String, Object> loginInfo = httpClientUtil.login(username, password);
        if (loginInfo == null) {
            throw new CustomException(CustomExceptionCode.UnNormalError);
        }
        if (loginInfo.get("cookie") == null) {
            throw new CustomException(CustomExceptionCode.UsernameOrPasswordError);
        }
        long userId = Long.valueOf(loginInfo.get("userId").toString());
        String cookie = (String) loginInfo.get("cookie");
        User user = userMapper.selectByOpenId(openId);
        if (user == null) {
            user = new User();
            user.setUserId(userId);
            user.setCookie(cookie);
            user.setUsername(username);
            user.setPassword(password);//todo 待改成加密保存
            user.setOpenId(openId);
            user.setReceived(true);
            userMapper.insert(user);
        } else {
            user.setUserId(userId);
            user.setCookie(cookie);
            user.setUsername(username);
            user.setPassword(password);//todo 待改成加密保存
            user.setOpenId(openId);
            user.setReceived(true);
            userMapper.update(user);
        }

        String s = httpClientUtil.doGet(PathcloudApiUrl.getUserInfo.toString(), null, cookie);
        Applicant data = DataTransUtil.transObject(s, Applicant.class);
        if (data != null) {
            Applicant applicant = new Applicant();
            BeanUtils.copyProperties(data, applicant);
            applicant.setSelf(true);
            applicant.setOpenId(openId);

            ApplicantCondition applicantCondition = new ApplicantCondition();
            applicantCondition.setOpenId(openId);
            applicantCondition.setSelf(true);
            Long total = applicantMapper.countByCondition(applicantCondition);
            if (total != null && total > 0) {
                applicantMapper.updateUserInfo(applicant);
            } else {
                applicantMapper.insert(applicant);
            }
        }
        Map<String, String> result = new HashedMap();
        String subject = JwtUtil.generalSubject(user);
        String token = "";
        try {
            token = jwtUtil.createJWT(Config.jwtId, subject, Config.jwtExpire);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("token", token);
        return new ResponseVO(result);
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO logout() throws CustomException {
        Object openId = httpRequest.getAttribute("openId");
        User user = userMapper.selectByOpenId(openId.toString());
        if (user != null) {
            userMapper.deleteByOpenId(openId.toString());
            httpClientUtil.doPost(PathcloudApiUrl.logout.toString(), null, user.getCookie());
        }
        return new ResponseVO();
    }
}
