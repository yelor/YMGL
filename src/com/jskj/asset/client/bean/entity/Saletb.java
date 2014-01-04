package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Saletb {
    private Integer saleId;

    private Date saleDate;

    private Integer departmentId;

    private Integer customerId;

    private String gongyingtype;

    private String fahuotype;

    private Integer zhidanrenId;

    private String shoumiaopeople;

    private Integer deportId;

    private Integer checkId;

    private String remark;

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getGongyingtype() {
        return gongyingtype;
    }

    public void setGongyingtype(String gongyingtype) {
        this.gongyingtype = gongyingtype == null ? null : gongyingtype.trim();
    }

    public String getFahuotype() {
        return fahuotype;
    }

    public void setFahuotype(String fahuotype) {
        this.fahuotype = fahuotype == null ? null : fahuotype.trim();
    }

    public Integer getZhidanrenId() {
        return zhidanrenId;
    }

    public void setZhidanrenId(Integer zhidanrenId) {
        this.zhidanrenId = zhidanrenId;
    }

    public String getShoumiaopeople() {
        return shoumiaopeople;
    }

    public void setShoumiaopeople(String shoumiaopeople) {
        this.shoumiaopeople = shoumiaopeople == null ? null : shoumiaopeople.trim();
    }

    public Integer getDeportId() {
        return deportId;
    }

    public void setDeportId(Integer deportId) {
        this.deportId = deportId;
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