package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Backsaletb {
    private String backsaleId;

    private Date backsaleDate;

    private Integer departmentId;

    private Integer deportId;

    private Integer customerId;

    private Integer checkId;

    private String remark;

    public String getBacksaleId() {
        return backsaleId;
    }

    public void setBacksaleId(String backsaleId) {
        this.backsaleId = backsaleId == null ? null : backsaleId.trim();
    }

    public Date getBacksaleDate() {
        return backsaleDate;
    }

    public void setBacksaleDate(Date backsaleDate) {
        this.backsaleDate = backsaleDate;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getDeportId() {
        return deportId;
    }

    public void setDeportId(Integer deportId) {
        this.deportId = deportId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}