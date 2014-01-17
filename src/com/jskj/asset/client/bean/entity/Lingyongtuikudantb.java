package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Lingyongtuikudantb {
    private String lytkId;

    private Date lytkDate;

    private Integer zhidanrenId;

    private Integer shenqingrenId;

    private String lytkRemark;

    public String getLytkId() {
        return lytkId;
    }

    public void setLytkId(String lytkId) {
        this.lytkId = lytkId == null ? null : lytkId.trim();
    }

    public Date getLytkDate() {
        return lytkDate;
    }

    public void setLytkDate(Date lytkDate) {
        this.lytkDate = lytkDate;
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

    public String getLytkRemark() {
        return lytkRemark;
    }

    public void setLytkRemark(String lytkRemark) {
        this.lytkRemark = lytkRemark == null ? null : lytkRemark.trim();
    }
}