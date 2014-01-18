package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Lingyongshenqingdantb {
    private String lysqId;

    private Date lysqDate;

    private Integer zhidanrenId;

    private Integer shenqingrenId;

    private String lysqRemark;

    public String getLysqId() {
        return lysqId;
    }

    public void setLysqId(String lysqId) {
        this.lysqId = lysqId == null ? null : lysqId.trim();
    }

    public Date getLysqDate() {
        return lysqDate;
    }

    public void setLysqDate(Date lysqDate) {
        this.lysqDate = lysqDate;
    }

    public Integer getZhidanrenId() {
        return zhidanrenId;
    }

    public void setZhidanrenId(Integer zhidanrenId) {
        this.zhidanrenId = zhidanrenId;
    }

    public Integer getShenqingrenId() {
        return shenqingrenId;
    }

    public void setShenqingrenId(Integer shenqingrenId) {
        this.shenqingrenId = shenqingrenId;
    }

    public String getLysqRemark() {
        return lysqRemark;
    }

    public void setLysqRemark(String lysqRemark) {
        this.lysqRemark = lysqRemark == null ? null : lysqRemark.trim();
    }
}