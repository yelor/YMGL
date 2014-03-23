package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Fukuandantb {
    private String fukuandanId;

    private Date fukuandanDate;

    private Integer supplierId;

    private Float yingfu;

    private Float fukuan;

    private Float youhui;

    private String shenqingdanRemark;

    private Integer zhidanrenId;

    private Integer danjuleixingId;

    private Integer isCompleted;

    private Integer isPaid;

    public String getFukuandanId() {
        return fukuandanId;
    }

    public void setFukuandanId(String fukuandanId) {
        this.fukuandanId = fukuandanId == null ? null : fukuandanId.trim();
    }

    public Date getFukuandanDate() {
        return fukuandanDate;
    }

    public void setFukuandanDate(Date fukuandanDate) {
        this.fukuandanDate = fukuandanDate;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Float getYingfu() {
        return yingfu;
    }

    public void setYingfu(Float yingfu) {
        this.yingfu = yingfu;
    }

    public Float getFukuan() {
        return fukuan;
    }

    public void setFukuan(Float fukuan) {
        this.fukuan = fukuan;
    }

    public Float getYouhui() {
        return youhui;
    }

    public void setYouhui(Float youhui) {
        this.youhui = youhui;
    }

    public String getShenqingdanRemark() {
        return shenqingdanRemark;
    }

    public void setShenqingdanRemark(String shenqingdanRemark) {
        this.shenqingdanRemark = shenqingdanRemark == null ? null : shenqingdanRemark.trim();
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

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
    }
}