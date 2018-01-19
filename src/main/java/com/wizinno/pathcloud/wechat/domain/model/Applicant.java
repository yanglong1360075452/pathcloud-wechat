package com.wizinno.pathcloud.wechat.domain.model;

import java.io.Serializable;
import java.util.Date;

public class Applicant implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.id
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.open_id
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private String openId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.first_name
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private String firstName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.phone
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.identity
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private Integer identity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.tutor
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private String tutor;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.faculty
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private String faculty;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.student_number
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private String studentNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.unit
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private String unit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.wno
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private String wno;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.departments
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private Integer departments;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.task_type
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private Integer taskType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.task_name
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private String taskName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.specialty
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private String specialty;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.project_code
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private String projectCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.create_time
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column applicant.update_time
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private Date updateTime;

    private Boolean isSelf;

    public Boolean getSelf() {
        return isSelf;
    }

    public void setSelf(Boolean self) {
        isSelf = self;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table applicant
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.id
     *
     * @return the value of applicant.id
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.id
     *
     * @param id the value for applicant.id
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.open_id
     *
     * @return the value of applicant.open_id
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.open_id
     *
     * @param openId the value for applicant.open_id
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.first_name
     *
     * @return the value of applicant.first_name
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.first_name
     *
     * @param firstName the value for applicant.first_name
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? null : firstName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.phone
     *
     * @return the value of applicant.phone
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.phone
     *
     * @param phone the value for applicant.phone
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.identity
     *
     * @return the value of applicant.identity
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public Integer getIdentity() {
        return identity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.identity
     *
     * @param identity the value for applicant.identity
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.tutor
     *
     * @return the value of applicant.tutor
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public String getTutor() {
        return tutor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.tutor
     *
     * @param tutor the value for applicant.tutor
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setTutor(String tutor) {
        this.tutor = tutor == null ? null : tutor.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.faculty
     *
     * @return the value of applicant.faculty
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.faculty
     *
     * @param faculty the value for applicant.faculty
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setFaculty(String faculty) {
        this.faculty = faculty == null ? null : faculty.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.student_number
     *
     * @return the value of applicant.student_number
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public String getStudentNumber() {
        return studentNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.student_number
     *
     * @param studentNumber the value for applicant.student_number
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber == null ? null : studentNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.unit
     *
     * @return the value of applicant.unit
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.unit
     *
     * @param unit the value for applicant.unit
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.wno
     *
     * @return the value of applicant.wno
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public String getWno() {
        return wno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.wno
     *
     * @param wno the value for applicant.wno
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setWno(String wno) {
        this.wno = wno == null ? null : wno.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.departments
     *
     * @return the value of applicant.departments
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public Integer getDepartments() {
        return departments;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.departments
     *
     * @param departments the value for applicant.departments
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.task_type
     *
     * @return the value of applicant.task_type
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public Integer getTaskType() {
        return taskType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.task_type
     *
     * @param taskType the value for applicant.task_type
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.task_name
     *
     * @return the value of applicant.task_name
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.task_name
     *
     * @param taskName the value for applicant.task_name
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.specialty
     *
     * @return the value of applicant.specialty
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.specialty
     *
     * @param specialty the value for applicant.specialty
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty == null ? null : specialty.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.project_code
     *
     * @return the value of applicant.project_code
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.project_code
     *
     * @param projectCode the value for applicant.project_code
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode == null ? null : projectCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.create_time
     *
     * @return the value of applicant.create_time
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.create_time
     *
     * @param createTime the value for applicant.create_time
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column applicant.update_time
     *
     * @return the value of applicant.update_time
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column applicant.update_time
     *
     * @param updateTime the value for applicant.update_time
     *
     * @mbggenerated Thu May 25 10:52:48 CST 2017
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}