package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Shoukuandantb {
    private String shoukuandanId;

    private Date shoukuandanDate;

    private Integer supplierId;

    private String jiesuanId;

    private Integer zhekou;

    private Float totalprice;

    private String payType;

    private String shenqingdanRemark;

    private Integer zhidanrenId;

    private Integer danjuleixingId;

    private Integer isCompleted;

    public String getShoukuandanId() {
        return shoukuandanId;
    }

    public void setShoukuandanId(String shoukuandanId) {
        this.shoukuandanId = shoukuandanId == null ? null : shoukuandanId.trim();
    }

    public Date getShoukuandanDate() {
        return shoukuandanDate;
    }

    public void setShoukuandanDate(Date shoukuandanDate) {
        this.shoukuandanDate = shoukuandanDate;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getJiesuanId() {
        return jiesuanId;
    }

    public void setJiesuanId(String jiesuanId) {
        this.jiesuanId = jiesuanId == null ? null : jiesuanId.trim();
    }

    public Integer getZhekou() {
        return zhekou;
    }

    public void setZhekou(Integer zhekou) {
        this.zhekou = zhekou;
    }

    public Float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Float totalprice) {
        this.totalprice = totalprice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
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
}