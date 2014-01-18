package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Yimiaotiaojiatb {
    private String tiaojiaId;

    private Date zhidandate;

    private Integer zhidanrenId;

    private String tiaojiakemu;

    private Float tiaojiajine;

    private Integer jingbanrenId;

    public String getTiaojiaId() {
        return tiaojiaId;
    }

    public void setTiaojiaId(String tiaojiaId) {
        this.tiaojiaId = tiaojiaId == null ? null : tiaojiaId.trim();
    }

    public Date getZhidandate() {
        return zhidandate;
    }

    public void setZhidandate(Date zhidandate) {
        this.zhidandate = zhidandate;
    }

    public Integer getZhidanrenId() {
        return zhidanrenId;
    }

    public void setZhidanrenId(Integer zhidanrenId) {
        this.zhidanrenId = zhidanrenId;
    }

    public String getTiaojiakemu() {
        return tiaojiakemu;
    }

    public void setTiaojiakemu(String tiaojiakemu) {
        this.tiaojiakemu = tiaojiakemu == null ? null : tiaojiakemu.trim();
    }

    public Float getTiaojiajine() {
        return tiaojiajine;
    }

    public void setTiaojiajine(Float tiaojiajine) {
        this.tiaojiajine = tiaojiajine;
    }

    public Integer getJingbanrenId() {
        return jingbanrenId;
    }

    public void setJingbanrenId(Integer jingbanrenId) {
        this.jingbanrenId = jingbanrenId;
    }
}