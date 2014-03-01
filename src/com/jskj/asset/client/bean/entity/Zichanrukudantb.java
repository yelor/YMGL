package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Zichanrukudantb {
    private String rukudanId;

    private Date rukudanDate;

    private String shenqingdanRemark;

    private Integer zhidanrenId;

    private Integer danjuleixingId;

    public String getRukudanId() {
        return rukudanId;
    }

    public void setRukudanId(String rukudanId) {
        this.rukudanId = rukudanId == null ? null : rukudanId.trim();
    }

    public Date getRukudanDate() {
        return rukudanDate;
    }

    public void setRukudanDate(Date rukudanDate) {
        this.rukudanDate = rukudanDate;
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
}