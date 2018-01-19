package com.wizinno.pathcloud.wechat.controller;

import com.google.gson.Gson;
import com.wizinno.pathcloud.wechat.Exception.CustomException;
import com.wizinno.pathcloud.wechat.Exception.CustomExceptionCode;
import com.wizinno.pathcloud.wechat.data.attr.PathcloudApiUrl;
import com.wizinno.pathcloud.wechat.data.dto.DyeDto;
import com.wizinno.pathcloud.wechat.data.dto.IhcBlockDto;
import com.wizinno.pathcloud.wechat.data.vo.DyeVO;
import com.wizinno.pathcloud.wechat.data.vo.IhcBlockVO;
import com.wizinno.pathcloud.wechat.domain.ApplicantMapper;
import com.wizinno.pathcloud.wechat.domain.model.Applicant;
import com.wizinno.pathcloud.wechat.util.CookieUtil;
import com.wizinno.pathcloud.wechat.util.HttpClientUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by LiuMei on 2017-05-27.
 */
@RestController
@RequestMapping("/api/wechat/dye")
public class DyeController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    HttpClientUtil httpClientUtil;

    @Autowired
    ApplicantMapper applicantMapper;

    private Gson gson = new Gson();

    public String getOpenId() {
        Object openId = httpServletRequest.getAttribute("openId");
        return openId.toString();
    }

    /**
     * 获取染色类别
     *
     * @return
     */
    @RequestMapping(value = "/type", method = RequestMethod.GET)
    public String getType() throws CustomException {
        String cookie = cookieUtil.getCookieByOpenId(getOpenId());
        return httpClientUtil.doGet(PathcloudApiUrl.getDyeType.toString(), null, cookie);
    }

    /**
     * 获取当前用户可申请染色的蜡块
     * @param page
     * @param length
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     */
    @RequestMapping(value = "/blocks",method = RequestMethod.GET)
    public String getBlocks(@RequestParam(value = "page",defaultValue = "1") int page,
                            @RequestParam(value = "length",defaultValue = "10") int length,
                            @RequestParam(value = "createTimeStart",required = false)Long createTimeStart,
                            @RequestParam(value = "createTimeEnd",required = false) Long createTimeEnd) throws CustomException {
        String cookie = cookieUtil.getCookieByOpenId(getOpenId());
        Map<String,Object> params = new HashMap<>();
        params.put("page",page);
        params.put("length",length);
        params.put("createTimeStart",createTimeStart);
        params.put("createTimeEnd",createTimeEnd);
        return httpClientUtil.doGet(PathcloudApiUrl.getDyeBlocks.toString(),params,cookie);
    }

    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String create(@RequestBody DyeVO dyeVO) throws CustomException {
        Long applicantId = dyeVO.getApplicantId();
        List<IhcBlockVO> ihcBlocks = dyeVO.getIhcBlocks();
        if(applicantId == null || ihcBlocks == null || ihcBlocks.size() <= 0){
            throw new CustomException(CustomExceptionCode.ParamError);
        }
        List<IhcBlockDto> ihcBlockDtos = new ArrayList<>();
        IhcBlockDto ihcBlockDto = null;
        for(IhcBlockVO ihcBlockVO : ihcBlocks){
            String pathNo = ihcBlockVO.getSerialNumber();
            String subId = ihcBlockVO.getSubId();
            Integer specialDye = ihcBlockVO.getSpecialDye();
            List<String> ihcMarker = ihcBlockVO.getIhcMarker();
            if(pathNo == null || "".equals(pathNo) || subId == null || "".equals(subId) || specialDye == null || ihcMarker == null){
                throw new CustomException(CustomExceptionCode.ParamError);
            }
            ihcBlockDto = new IhcBlockDto();
            BeanUtils.copyProperties(ihcBlockVO,ihcBlockDto);
            ihcBlockDtos.add(ihcBlockDto);
        }
        Applicant applicant = applicantMapper.selectByPrimaryKey(applicantId);
        if(applicant == null){
            throw new CustomException(CustomExceptionCode.ParamError);
        }
        DyeDto dyeDto = new DyeDto();
        dyeDto.setApplyTel(applicant.getPhone());
        dyeDto.setApplyUser(applicant.getFirstName());
        dyeDto.setDepartments(applicant.getDepartments());
        dyeDto.setIhcBlocks(ihcBlockDtos);
        String cookie = cookieUtil.getCookieByOpenId(getOpenId());
        return httpClientUtil.doPost(PathcloudApiUrl.createDye.toString(),gson.toJson(dyeDto),cookie);
    }

    @RequestMapping(value = "/cancel/{id}",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String cancelDye(@PathVariable("id") long id) throws CustomException {
        String cookie = cookieUtil.getCookieByOpenId(getOpenId());
        return httpClientUtil.doPost(PathcloudApiUrl.cancelDye.toString()+"/"+id,null,cookie);
    }
}
