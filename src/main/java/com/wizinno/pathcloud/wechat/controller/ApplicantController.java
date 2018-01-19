package com.wizinno.pathcloud.wechat.controller;

import com.wizinno.pathcloud.wechat.Exception.CustomException;
import com.wizinno.pathcloud.wechat.Exception.CustomExceptionCode;
import com.wizinno.pathcloud.wechat.data.attr.Identity;
import com.wizinno.pathcloud.wechat.data.attr.PathcloudApiUrl;
import com.wizinno.pathcloud.wechat.data.attr.TaskType;
import com.wizinno.pathcloud.wechat.data.condition.ApplicantCondition;
import com.wizinno.pathcloud.wechat.data.response.PageDataVO;
import com.wizinno.pathcloud.wechat.data.response.ResponseVO;
import com.wizinno.pathcloud.wechat.data.vo.ApplicantSimpleVO;
import com.wizinno.pathcloud.wechat.data.vo.ApplicantVO;
import com.wizinno.pathcloud.wechat.domain.ApplicantMapper;
import com.wizinno.pathcloud.wechat.domain.model.Applicant;
import com.wizinno.pathcloud.wechat.util.CookieUtil;
import com.wizinno.pathcloud.wechat.util.DataTransUtil;
import com.wizinno.pathcloud.wechat.util.HttpClientUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMei on 2017-05-16.
 */
@RestController
@RequestMapping("/api/wechat/applicant")
public class ApplicantController extends BaseController {

    @Autowired
    private ApplicantMapper applicantMapper;

    @Autowired
    private HttpClientUtil httpClientUtil;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private HttpServletRequest httpServletRequest;

    public String getOpenId() {
        Object openId = httpServletRequest.getAttribute("openId");
        return openId.toString();
    }

    /**
     * 获取科室列表
     *
     * @return
     */
    @RequestMapping(value = "/department", method = RequestMethod.GET)
    public String getDepartment() throws CustomException {
        String cookie = cookieUtil.getCookieByOpenId(getOpenId());
        String s = httpClientUtil.doGet(PathcloudApiUrl.getDepartment.toString(), null, cookie);
        return s;
    }

    /**
     * 添加申请人
     *
     * @param applicantVO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO add(@RequestBody ApplicantVO applicantVO) throws CustomException {
        String firstName = applicantVO.getFirstName();
        Integer departments = applicantVO.getDepartments();
        String phone = applicantVO.getPhone();
        Integer identity = applicantVO.getIdentity();
        String projectCode = applicantVO.getProjectCode();
        if (firstName == null || departments == null || phone == null || identity == null || projectCode == null) {
            throw new CustomException(CustomExceptionCode.ParamError);
        }
        String identityDesc = Identity.getNameByCode(identity);
        if(identityDesc == null){
            throw new CustomException(CustomExceptionCode.ParamError);
        }
        String departmentDesc = getDepartmentDesc(departments);
        if(departmentDesc == null){
            throw new CustomException(CustomExceptionCode.ParamError);
        }
        Integer taskType = applicantVO.getTaskType();
        if (taskType != null) {
            String taskTypeDesc = TaskType.getNameByCode(taskType);
            if(taskTypeDesc == null){
                throw new CustomException(CustomExceptionCode.ParamError);
            }
        }
        Applicant applicant = new Applicant();
        BeanUtils.copyProperties(applicantVO, applicant);
        applicant.setOpenId(getOpenId());
        applicant.setSelf(false);
        applicantMapper.insert(applicant);
        return new ResponseVO();
    }

    /**
     * 获取申请人列表
     *
     * @param length
     * @param page
     * @param isSelf
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseVO getList(@RequestParam(value = "length", defaultValue = "10") int length,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "isSelf", required = false) Boolean isSelf) throws CustomException {
        ApplicantCondition condition = new ApplicantCondition();
        condition.setOrder("id desc");
        condition.setSize(length);
        condition.setStart((page - 1) * length);
        condition.setOpenId(getOpenId());
        condition.setSelf(isSelf);
        List<Applicant> applicants = applicantMapper.selectByCondition(condition);
        List<ApplicantSimpleVO> applicantSimpleVOS = new ArrayList<>();
        if (applicants != null && applicants.size() > 0) {
            ApplicantSimpleVO simpleVO = null;
            for (Applicant applicant : applicants) {
                simpleVO = new ApplicantSimpleVO();
                simpleVO.setId(applicant.getId());
                simpleVO.setFirstName(applicant.getFirstName());
                Integer departments = applicant.getDepartments();
                simpleVO.setDepartments(departments);
                simpleVO.setDepartmentsDesc(getDepartmentDesc(departments));
                Integer identity = applicant.getIdentity();
                simpleVO.setIdentity(identity);
                simpleVO.setIdentityDesc(Identity.getNameByCode(identity));
                simpleVO.setIsSelf(applicant.getSelf());
                applicantSimpleVOS.add(simpleVO);
            }
        }

        Long total = applicantMapper.countByCondition(condition);
        return new PageDataVO(page, length, total, applicantSimpleVOS);
    }

    /**
     * 获取申请人详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseVO getApplicant(@PathVariable("id") long id) throws CustomException {
        Applicant applicant = applicantMapper.selectByPrimaryKey(id);
        ApplicantVO applicantVO = new ApplicantVO();
        if (applicant != null) {
            BeanUtils.copyProperties(applicant, applicantVO);
            applicantVO.setDepartmentsDesc(getDepartmentDesc(applicant.getDepartments()));
            applicantVO.setIdentityDesc(Identity.getNameByCode(applicant.getIdentity()));
            applicantVO.setTaskTypeDesc(TaskType.getNameByCode(applicant.getTaskType()));
        }
        return new ResponseVO(applicantVO);
    }

    /**
     * 修改申请人信息
     *
     * @param applicantVO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO update(@RequestBody ApplicantVO applicantVO) throws CustomException {
        Long id = applicantVO.getId();
        if (id == null) {
            throw new CustomException(CustomExceptionCode.ParamError);
        }
        String firstName = applicantVO.getFirstName();
        Integer departments = applicantVO.getDepartments();
        String phone = applicantVO.getPhone();
        Integer identity = applicantVO.getIdentity();
        String projectCode = applicantVO.getProjectCode();
        if (firstName == null || departments == null || phone == null || identity == null || projectCode == null) {
            throw new CustomException(CustomExceptionCode.ParamError);
        }
        Applicant applicant = applicantMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(applicantVO, applicant);
        applicant.setOpenId(getOpenId());
        applicantMapper.updateByPrimaryKey(applicant);
        return new ResponseVO();
    }

    /**
     * 删除申请人信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseVO delete(@PathVariable("id") long id) throws CustomException {
        applicantMapper.deleteByPrimaryKey(id);
        return new ResponseVO();
    }

    /**
     * 获取科室描述
     *
     * @param code
     * @return
     * @throws CustomException
     */
    public String getDepartmentDesc(int code) throws CustomException {
        String s = httpClientUtil.doGet(PathcloudApiUrl.getDepartment + "/" + code, null, cookieUtil.getCookieByOpenId(getOpenId()));
        return DataTransUtil.transObject(s, String.class).toString();
    }

}
