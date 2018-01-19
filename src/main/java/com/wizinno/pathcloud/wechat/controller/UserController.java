package com.wizinno.pathcloud.wechat.controller;

import com.google.gson.Gson;
import com.wizinno.pathcloud.wechat.Exception.CustomException;
import com.wizinno.pathcloud.wechat.Exception.CustomExceptionCode;
import com.wizinno.pathcloud.wechat.data.Config;
import com.wizinno.pathcloud.wechat.data.attr.PathcloudApiUrl;
import com.wizinno.pathcloud.wechat.data.dto.UserDto;
import com.wizinno.pathcloud.wechat.data.response.ResponseVO;
import com.wizinno.pathcloud.wechat.data.vo.ApplicantVO;
import com.wizinno.pathcloud.wechat.data.vo.FeedbackVO;
import com.wizinno.pathcloud.wechat.domain.ApplicantMapper;
import com.wizinno.pathcloud.wechat.domain.UserMapper;
import com.wizinno.pathcloud.wechat.domain.model.Applicant;
import com.wizinno.pathcloud.wechat.domain.model.User;
import com.wizinno.pathcloud.wechat.util.CookieUtil;
import com.wizinno.pathcloud.wechat.util.EmailMessage;
import com.wizinno.pathcloud.wechat.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by LiuMei on 2017-06-05.
 */
@RestController
@RequestMapping("/api/wechat/user")
public class UserController extends BaseController {

    Logger logger =  LoggerFactory.getLogger(UserController.class);

    @Autowired
    HttpClientUtil httpClientUtil;

    @Autowired
    UserMapper userMapper;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    ApplicantMapper applicantMapper;

    private Gson gson = new Gson();

    public String getOpenId() {
        Object openId = httpServletRequest.getAttribute("openId");
        return openId.toString();
    }

    /**
     * 获取个人信息
     *
     * @return
     * @throws CustomException
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getUser() throws CustomException {
        String cookie = cookieUtil.getCookieByOpenId(getOpenId());
        return httpClientUtil.doGet(PathcloudApiUrl.getUserInfo.toString(), null, cookie);
    }

    /**
     * 修改个人信息
     *
     * @param applicantVO
     * @return
     * @throws CustomException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateUser(@RequestBody ApplicantVO applicantVO) throws CustomException {
        String openId = getOpenId();
        String firstName = applicantVO.getFirstName();
        Integer departments = applicantVO.getDepartments();
        String phone = applicantVO.getPhone();
        Integer identity = applicantVO.getIdentity();
        String projectCode = applicantVO.getProjectCode();
        if (firstName == null || departments == null || phone == null || identity == null || projectCode == null) {
            throw new CustomException(CustomExceptionCode.ParamError);
        }
        User user = userMapper.selectByOpenId(openId);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(applicantVO, userDto);
        userDto.setUsername(user.getUsername());
        Applicant applicant = new Applicant();
        BeanUtils.copyProperties(userDto, applicant);
        applicant.setOpenId(openId);
        applicantMapper.updateUserInfo(applicant);
        return httpClientUtil.doPost(PathcloudApiUrl.updateUser.toString(), gson.toJson(userDto), user.getCookie());
    }

    /**
     * 修改是否接收通知
     *
     * @return
     * @throws CustomException
     */
    @RequestMapping(value = "/received", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO received() throws CustomException {
        Object openId = httpServletRequest.getAttribute("openId");
        User user = userMapper.selectByOpenId(openId.toString());
        if (user != null) {
            user.setReceived(!user.getReceived());
            userMapper.update(user);
        }
        return new ResponseVO();
    }

    /**
     * 修改是否接收通知
     *
     * @return
     * @throws CustomException
     */
    @RequestMapping(value = "/received", method = RequestMethod.GET)
    public ResponseVO getReceived() throws CustomException {
        Object openId = httpServletRequest.getAttribute("openId");
        User user = userMapper.selectByOpenId(openId.toString());
        return new ResponseVO(user.getReceived());
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO sendFeedback(@RequestBody FeedbackVO feedbackVO) {

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setTo(Config.emailToUsername);
        emailMessage.setSubject("病理追踪微信平台意见反馈");

        String content = "收到用户反馈："
                +"<br/>"
                +"联系方式："+feedbackVO.getContactWay()
                +"<br/>"
                +"意见反馈："+feedbackVO.getFeedback()
                +"<br/><br/>"
                +"----------------------------------------------------------------------------------------------<br/>"
                +"<br/><br/>"
                +"病理追踪微信平台";
        emailMessage.setBody(content);

        emailMessage.setUsername(Config.emailUsername);
        emailMessage.setPassword(Config.emailPassword);
        emailMessage.setSmtpHost(Config.emailSmtp);
        emailMessage.setFrom(Config.emailUsername);
        logger.info("to:" + emailMessage.getTo() + "\nsubject:" + emailMessage.getSubject());
        logger.info("body:" + emailMessage.getBody());
        emailMessage.init();
        boolean sended = emailMessage.sendOut();
        for (int i = 0; !sended && (i < 2); i++) {
            sended = emailMessage.sendOut();
        }
        return new ResponseVO();
    }
}
