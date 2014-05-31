package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Backsaletb {
    private String backsaleId;

    private Date backsaleDate;

    private Integer deportId;

    private Integer supplierId;

    private Integer customerId;

    private Integer zhidanrenId;

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

    public Integer getDeportId() {
        return deportId;
    }

    public void setDeportId(Integer deportId) {
        this.deportId = deportId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getZhidanrenId() {
        return zhidanrenId;
    }

    public void setZhidanrenId(Integer zhidanrenId) {
        this.zhidanrenId = zhidanrenId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}