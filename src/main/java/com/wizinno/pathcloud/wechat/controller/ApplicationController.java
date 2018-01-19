package com.wizinno.pathcloud.wechat.controller;

import com.google.gson.Gson;
import com.wizinno.pathcloud.wechat.Exception.CustomException;
import com.wizinno.pathcloud.wechat.Exception.CustomExceptionCode;
import com.wizinno.pathcloud.wechat.data.attr.PathcloudApiUrl;
import com.wizinno.pathcloud.wechat.data.attr.ResearchType;
import com.wizinno.pathcloud.wechat.data.dto.ApplicationDto;
import com.wizinno.pathcloud.wechat.data.dto.BookDto;
import com.wizinno.pathcloud.wechat.data.dto.SampleDto;
import com.wizinno.pathcloud.wechat.data.vo.ApplicationVO;
import com.wizinno.pathcloud.wechat.data.vo.BookVO;
import com.wizinno.pathcloud.wechat.data.vo.SampleVO;
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
 * Created by LiuMei on 2017-05-26.
 */
@RestController
@RequestMapping("/api/wechat/application")
public class ApplicationController {

    @Autowired
    private ApplicantMapper applicantMapper;

    @Autowired
    private HttpClientUtil httpClientUtil;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private Gson gson = new Gson();

    public String getOpenId() {
        Object openId = httpServletRequest.getAttribute("openId");
        return openId.toString();
    }

    /**
     * 获取预约信息
     * @param timeStart
     * @param timeEnd
     * @return
     * @throws CustomException
     */
    @RequestMapping(value = "/booking",method = RequestMethod.GET)
    public String getBooking(@RequestParam("timeStart") Long timeStart,@RequestParam("timeEnd") Long timeEnd) throws CustomException {
        String cookie = cookieUtil.getCookieByOpenId(getOpenId());
        Map<String,Object> param = new HashMap<>();
        param.put("timeStart",timeStart);
        param.put("timeEnd",timeEnd);
        return httpClientUtil.doGet(PathcloudApiUrl.getBooking.toString(),param,cookie);
    }

    /**
     * 创建申请
     * @param applicationVO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String create(@RequestBody ApplicationVO applicationVO) throws CustomException {
        Long id = applicationVO.getApplicantId();
        List<SampleVO> samples = applicationVO.getSamples();
        Integer researchType = applicationVO.getResearchType();
        List<BookVO> books = applicationVO.getBooks();
        if(id == null  || samples == null || samples.size() <= 0 || researchType == null){
            throw new CustomException(CustomExceptionCode.ParamError);
        }
        String researchTypeDesc = ResearchType.getNameByCode(researchType);
        if(researchTypeDesc == null){
            throw new CustomException(CustomExceptionCode.ParamError);
        }
        List<SampleDto> sampleDtos = new ArrayList<>();
        SampleDto sampleDto = null;
        for(SampleVO sampleVO : samples){
            String name = sampleVO.getName();
            Integer category = sampleVO.getCategory();
            if(name==null || category == null){
                throw new CustomException(CustomExceptionCode.ParamError);
            }
            sampleDto = new SampleDto();
            sampleDto.setCategory(sampleVO.getCategory());
            sampleDto.setName(sampleVO.getName());
            sampleDtos.add(sampleDto);
        }
        List<BookDto> bookDtos = new ArrayList<>();
        if(researchType.equals(ResearchType.Freeze.toCode())){
            if(books == null || books.size() <= 0){
                throw new CustomException(CustomExceptionCode.ParamError);
            }
            BookDto bookDto = null;
            for(BookVO bookVO : books){
                bookDto = new BookDto();
                BeanUtils.copyProperties(bookVO,bookDto);
                bookDto.setFreezeStartTime(bookVO.getFreezeStartTime().getTime());
                bookDtos.add(bookDto);
            }
        }
        Applicant applicant = applicantMapper.selectByPrimaryKey(id);
        if(applicant == null){
            throw new CustomException(CustomExceptionCode.ParamError);
        }
        String projectCode = applicant.getProjectCode();
        if(projectCode == null || "".equals(projectCode)){
            throw new CustomException(CustomExceptionCode.ProjectCode);
        }
        String cookie = cookieUtil.getCookieByOpenId(getOpenId());
        ApplicationDto applicationDto = new ApplicationDto();
        BeanUtils.copyProperties(applicant,applicationDto);
        applicationDto.setApplicant(applicant.getFirstName());
        applicationDto.setFunds(projectCode);
        applicationDto.setResearchType(researchType);
        applicationDto.setBooks(bookDtos);
        applicationDto.setSamples(sampleDtos);
        return httpClientUtil.doPost(PathcloudApiUrl.createApplication.toString(),gson.toJson(applicationDto),cookie);
    }

    /**
     * 撤销申请
     * @param id
     * @return
     */
    @RequestMapping(value = "/cancel/{id}",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String cancel(@PathVariable("id") long id) throws CustomException {
        String cookie = cookieUtil.getCookieByOpenId(getOpenId());
        return httpClientUtil.doPost(PathcloudApiUrl.cancelApplication.toString()+"/"+id,null,cookie);
    }

    /**
     * 获取申请列表
     * @param length
     * @param page
     * @param createTimeStart
     * @param createTimeEnd
     * @param status
     * @param type
     * @param filter
     * @param sort
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getList(@RequestParam(value = "length", defaultValue = "10") int length,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "createTimeStart") Long createTimeStart,
                              @RequestParam(value = "createTimeEnd") Long createTimeEnd,
                              @RequestParam(value = "status",required = false) Integer status,
                              @RequestParam(value = "type",required = false) Integer type,
                              @RequestParam(value = "filter",required = false) String filter,
                              @RequestParam(value = "sort",required = false) Integer sort
                                  ) throws CustomException {
            String cookie = cookieUtil.getCookieByOpenId(getOpenId());
            Map<String,Object> params = new HashMap<>();
            params.put("length",length);
            params.put("page",page);
            params.put("createTimeStart",createTimeStart);
            params.put("createTimeEnd",createTimeEnd);
            params.put("status",status);
            params.put("type",type);
            params.put("filter",filter);
            params.put("sort",sort);
            return httpClientUtil.doGet(PathcloudApiUrl.getApplications.toString(),params,cookie);
    }

    /**
     * 获取申请详情
     * @param id
     * @param type
     * @return
     * @throws CustomException
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public String getDetail(@RequestParam(value = "id") long id,@RequestParam(value = "type") int type ) throws CustomException{
        String cookie = cookieUtil.getCookieByOpenId(getOpenId());
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);
        params.put("type",type);
        return httpClientUtil.doGet(PathcloudApiUrl.getApplicationDetail.toString(),params,cookie);
    }
}
