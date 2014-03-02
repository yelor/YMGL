package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Lingyongshenqingdantb {
    private String lysqId;

    private Date lysqDate;

    private Integer zhidanrenId;

    private Integer shenqingrenId;

    private String lysqRemark;

    private Integer danjuleixingId;

    private String processId;

    private String checkId1;

    private String checkId2;

    private Date checkTime1;

    private Date checkTime2;

    private String checkUser1;

    private String checkUser2;

    private String rejectReason;

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

    public Date getCheckTime1() {
        return checkTime1;
    }

    public void setCheckTime1(Date checkTime1) {
        this.checkTime1 = checkTime1;
    }

    public Date getCheckTime2() {
        return checkTime2;
    }

    public void setCheckTime2(Date checkTime2) {
        this.checkTime2 = checkTime2;
    }

    public String getCheckUser1() {
        return checkUser1;
    }

    public void setCheckUser1(String checkUser1) {
        this.checkUser1 = checkUser1 == null ? null : checkUser1.trim();
    }

    public String getCheckUser2() {
        return checkUser2;
    }

    public void setCheckUser2(String checkUser2) {
        this.checkUser2 = checkUser2 == null ? null : checkUser2.trim();
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason == null ? null : rejectReason.trim();
    }
}