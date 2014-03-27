package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Qitashoukuandantb {
    private String shoukuandanId;

    private Date fukuandanDate;

    private Integer supplierId;

    private Float totalprice;

    private String shenqingdanRemark;

    private Integer zhidanrenId;

    private Integer danjuleixingId;

    public String getShoukuandanId() {
        return shoukuandanId;
    }

    public void setShoukuandanId(String shoukuandanId) {
        this.shoukuandanId = shoukuandanId == null ? null : shoukuandanId.trim();
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

    public Float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Float totalprice) {
        this.totalprice = totalprice;
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