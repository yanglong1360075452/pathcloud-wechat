package com.wizinno.pathcloud.wechat.data.vo;

import java.util.List;

/**
 * Created by LiuMei on 2017-05-26.
 */
public class ApplicationVO {

    private Long applicantId;

    private Integer researchType;

    private List<SampleVO> samples;

    private List<BookVO> books;

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public Integer getResearchType() {
        return researchType;
    }

    public void setResearchType(Integer researchType) {
        this.researchType = researchType;
    }

    public List<SampleVO> getSamples() {
        return samples;
    }

    public void setSamples(List<SampleVO> samples) {
        this.samples = samples;
    }

    public List<BookVO> getBooks() {
        return books;
    }

    public void setBooks(List<BookVO> books) {
        this.books = books;
    }
}
