package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Baosuntb {
    private String baosunId;

    private Date baosunDate;

    private Integer jingbanren;

    private Integer deport;

    private Integer zhidanren;

    public String getBaosunId() {
        return baosunId;
    }

    public void setBaosunId(String baosunId) {
        this.baosunId = baosunId == null ? null : baosunId.trim();
    }

    public Date getBaosunDate() {
        return baosunDate;
    }

    public void setBaosunDate(Date baosunDate) {
        this.baosunDate = baosunDate;
    }

    public Integer getJingbanren() {
        return jingbanren;
    }

    public void setJingbanren(Integer jingbanren) {
        this.jingbanren = jingbanren;
    }

    public Integer getDeport() {
        return deport;
    }

    public void setDeport(Integer deport) {
        this.deport = deport;
    }

    public Integer getZhidanren() {
        return zhidanren;
    }

    public void setZhidanren(Integer zhidanren) {
        this.zhidanren = zhidanren;
    }
}