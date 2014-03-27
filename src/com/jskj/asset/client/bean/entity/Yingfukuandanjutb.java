package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Yingfukuandanjutb {
    private String fukuandanId;

    private String yuandanId;

    private Date zhidandate;

    private String yuandantype;

    private Float danjujine;

    private Float increase;

    private Float decrease;

    private Float yingfu;

    private String remark;

    public String getFukuandanId() {
        return fukuandanId;
    }

    public void setFukuandanId(String fukuandanId) {
        this.fukuandanId = fukuandanId == null ? null : fukuandanId.trim();
    }

    public String getYuandanId() {
        return yuandanId;
    }

    public void setYuandanId(String yuandanId) {
        this.yuandanId = yuandanId == null ? null : yuandanId.trim();
    }

    public Date getZhidandate() {
        return zhidandate;
    }

    public void setZhidandate(Date zhidandate) {
        this.zhidandate = zhidandate;
    }

    public String getYuandantype() {
        return yuandantype;
    }

    public void setYuandantype(String yuandantype) {
        this.yuandantype = yuandantype == null ? null : yuandantype.trim();
    }

    public Float getDanjujine() {
        return danjujine;
    }

    public void setDanjujine(Float danjujine) {
        this.danjujine = danjujine;
    }

    public Float getIncrease() {
        return increase;
    }

    public void setIncrease(Float increase) {
        this.increase = increase;
    }

    public Float getDecrease() {
        return decrease;
    }

    public void setDecrease(Float decrease) {
        this.decrease = decrease;
    }

    public Float getYingfu() {
        return yingfu;
    }

    public void setYingfu(Float yingfu) {
        this.yingfu = yingfu;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}