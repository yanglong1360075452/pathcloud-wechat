package com.wizinno.pathcloud.wechat.data.vo;

/**
 * Created by LiuMei on 2017-05-26.
 */
public class ApplicantSimpleVO {

    private Long id;

    private String firstName;

    private Integer departments;
    private String departmentsDesc;

    private Integer identity;
    private String identityDesc;

    private Boolean isSelf;

    public Boolean getIsSelf() {
        return this.isSelf;
    }

    public void setIsSelf(Boolean isSelf) {
        this.isSelf = isSelf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public String getDepartmentsDesc() {
        return departmentsDesc;
    }

    public void setDepartmentsDesc(String departmentsDesc) {
        this.departmentsDesc = departmentsDesc;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public String getIdentityDesc() {
        return identityDesc;
    }

    public void setIdentityDesc(String identityDesc) {
        this.identityDesc = identityDesc;
    }
}
