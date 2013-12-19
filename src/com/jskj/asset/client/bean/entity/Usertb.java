package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Usertb {
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
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles == null ? null : userRoles.trim();
    }

    public String getUserIdentitycard() {
        return userIdentitycard;
    }

    public void setUserIdentitycard(String userIdentitycard) {
        this.userIdentitycard = userIdentitycard == null ? null : userIdentitycard.trim();
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
        this.userSex = userSex == null ? null : userSex.trim();
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
        this.userPhone = userPhone == null ? null : userPhone.trim();
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
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition == null ? null : userPosition.trim();
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark == null ? null : userRemark.trim();
    }
}