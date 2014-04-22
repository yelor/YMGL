package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Zichanchukudantb {
    private String chukudanId;

    private Date chukudanDate;

    private String shenqingdanRemark;

    private Integer zhidanrenId;

    private Integer danjuleixingId;

    private String kufang;

    public String getChukudanId() {
        return chukudanId;
    }

    public void setChukudanId(String chukudanId) {
        this.chukudanId = chukudanId == null ? null : chukudanId.trim();
    }

    public Date getChukudanDate() {
        return chukudanDate;
    }

    public void setChukudanDate(Date chukudanDate) {
        this.chukudanDate = chukudanDate;
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

    public String getKufang() {
        return kufang;
    }

    public void setKufang(String kufang) {
        this.kufang = kufang == null ? null : kufang.trim();
    }
}