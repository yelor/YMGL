/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.assets.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 *
 * @author tt
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JingbanrenEntity {

    private Integer userId;

    private String userName;

    private String userPassword;

    private String userRoles;

    private String userIdentitycard;

    private Date userBirthday;

    private String userSex;

    private Date userEntrydate;

    private String userPhone;

    private Integer departmentId;

    private String userEmail;

    private String userPosition;

    private String userRemark;

    private String departmentName;

    private String tel;

    private String fax;

    private String departmentRemark;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    }

    public String getUserIdentitycard() {
        return userIdentitycard;
    }

    public void setUserIdentitycard(String userIdentitycard) {
        this.userIdentitycard = userIdentitycard;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Date getUserEntrydate() {
        return userEntrydate;
    }

    public void setUserEntrydate(Date userEntrydate) {
        this.userEntrydate = userEntrydate;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDepartmentRemark() {
        return departmentRemark;
    }

    public void setDepartmentRemark(String departmentRemark) {
        this.departmentRemark = departmentRemark;
    }
    
    
}
