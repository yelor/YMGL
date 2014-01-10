package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Shenqingdantb {
    private String shenqingdanId;

    private Date shenqingdanDate;

    private Integer supplierId;

    private String shenqingdanRemark;

    private Integer jingbanrenId;

    private Integer zhidanrenId;

    private Integer danjuleixingId;

    public String getShenqingdanId() {
        return shenqingdanId;
    }

    public void setShenqingdanId(String shenqingdanId) {
        this.shenqingdanId = shenqingdanId == null ? null : shenqingdanId.trim();
    }

    public Date getShenqingdanDate() {
        return shenqingdanDate;
    }

    public void setShenqingdanDate(Date shenqingdanDate) {
        this.shenqingdanDate = shenqingdanDate;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getShenqingdanRemark() {
        return shenqingdanRemark;
    }

    public void setShenqingdanRemark(String shenqingdanRemark) {
        this.shenqingdanRemark = shenqingdanRemark == null ? null : shenqingdanRemark.trim();
    }

    public Integer getJingbanrenId() {
        return jingbanrenId;
    }

    public void setJingbanrenId(Integer jingbanrenId) {
        this.jingbanrenId = jingbanrenId;
    }

    public Integer getZhidanrenId() {
        return zhidanrenId;
    }

    public void setZhidanrenId(Integer zhidanrenId) {
        this.zhidanrenId = zhidanrenId;
    }

    public Integer getDanjuleixingId() {
        return danjuleixingId;
    }

    public void setDanjuleixingId(Integer danjuleixingId) {
        this.danjuleixingId = danjuleixingId;
    }
}