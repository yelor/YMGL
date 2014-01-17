package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class ZichanYanshoutb {
    private String zcysId;

    private Date zcysDate;

    private Date zcysDaohuodate;

    private Integer gdzcId;

    private Integer caigourenId;

    private Integer zhidanrenId;

    private Integer yanshourenId;

    private Integer jiancerenId;

    private String zcysJielun;

    private String zcysZhiliangjiance;

    private String zcysShicexixngneng;

    private String imgUri;

    public String getZcysId() {
        return zcysId;
    }

    public void setZcysId(String zcysId) {
        this.zcysId = zcysId == null ? null : zcysId.trim();
    }

    public Date getZcysDate() {
        return zcysDate;
    }

    public void setZcysDate(Date zcysDate) {
        this.zcysDate = zcysDate;
    }

    public Date getZcysDaohuodate() {
        return zcysDaohuodate;
    }

    public void setZcysDaohuodate(Date zcysDaohuodate) {
        this.zcysDaohuodate = zcysDaohuodate;
    }

    public Integer getGdzcId() {
        return gdzcId;
    }

    public void setGdzcId(Integer gdzcId) {
        this.gdzcId = gdzcId;
    }

    public Integer getCaigourenId() {
        return caigourenId;
    }

    public void setCaigourenId(Integer caigourenId) {
        this.caigourenId = caigourenId;
    }

    public Integer getZhidanrenId() {
        return zhidanrenId;
    }

    public void setZhidanrenId(Integer zhidanrenId) {
        this.zhidanrenId = zhidanrenId;
    }

    public Integer getYanshourenId() {
        return yanshourenId;
    }

    public void setYanshourenId(Integer yanshourenId) {
        this.yanshourenId = yanshourenId;
    }

    public Integer getJiancerenId() {
        return jiancerenId;
    }

    public void setJiancerenId(Integer jiancerenId) {
        this.jiancerenId = jiancerenId;
    }

    public String getZcysJielun() {
        return zcysJielun;
    }

    public void setZcysJielun(String zcysJielun) {
        this.zcysJielun = zcysJielun == null ? null : zcysJielun.trim();
    }

    public String getZcysZhiliangjiance() {
        return zcysZhiliangjiance;
    }

    public void setZcysZhiliangjiance(String zcysZhiliangjiance) {
        this.zcysZhiliangjiance = zcysZhiliangjiance == null ? null : zcysZhiliangjiance.trim();
    }

    public String getZcysShicexixngneng() {
        return zcysShicexixngneng;
    }

    public void setZcysShicexixngneng(String zcysShicexixngneng) {
        this.zcysShicexixngneng = zcysShicexixngneng == null ? null : zcysShicexixngneng.trim();
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri == null ? null : imgUri.trim();
    }
}