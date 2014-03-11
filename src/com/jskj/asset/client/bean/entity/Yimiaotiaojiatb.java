package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Yimiaotiaojiatb {
    private String tiaojiaId;

    private Date zhidandate;

    private Integer zhidanrenId;

    private String tiaojiakemu;

    private Float tiaojiajine;

    private Integer jingbanrenId;

    private String tioajiaremark;

    private Integer isCompleted;

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

    public String getTioajiaremark() {
        return tioajiaremark;
    }

    public void setTioajiaremark(String tioajiaremark) {
        this.tioajiaremark = tioajiaremark == null ? null : tioajiaremark.trim();
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }
}