package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Weixiushenqingdantb {
    private String wxsqId;

    private Date wxsqDate;

    private Integer zhidanrenId;

    private Integer shenqingrenId;

    private String wxsqRemark;

    private Double weixiufeiyong;

    private Integer danjuleixingId;

    private String processId;

    private String checkId1;

    private String checkId2;

    private String checkId3;

    public String getWxsqId() {
        return wxsqId;
    }

    public void setWxsqId(String wxsqId) {
        this.wxsqId = wxsqId == null ? null : wxsqId.trim();
    }

    public Date getWxsqDate() {
        return wxsqDate;
    }

    public void setWxsqDate(Date wxsqDate) {
        this.wxsqDate = wxsqDate;
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

    public String getWxsqRemark() {
        return wxsqRemark;
    }

    public void setWxsqRemark(String wxsqRemark) {
        this.wxsqRemark = wxsqRemark == null ? null : wxsqRemark.trim();
    }

    public Double getWeixiufeiyong() {
        return weixiufeiyong;
    }

    public void setWeixiufeiyong(Double weixiufeiyong) {
        this.weixiufeiyong = weixiufeiyong;
    }

    public Integer getDanjuleixingId() {
        return danjuleixingId;
    }

    public void setDanjuleixingId(Integer danjuleixingId) {
        this.danjuleixingId = danjuleixingId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getCheckId1() {
        return checkId1;
    }

    public void setCheckId1(String checkId1) {
        this.checkId1 = checkId1 == null ? null : checkId1.trim();
    }

    public String getCheckId2() {
        return checkId2;
    }

    public void setCheckId2(String checkId2) {
        this.checkId2 = checkId2 == null ? null : checkId2.trim();
    }

    public String getCheckId3() {
        return checkId3;
    }

    public void setCheckId3(String checkId3) {
        this.checkId3 = checkId3 == null ? null : checkId3.trim();
    }
}