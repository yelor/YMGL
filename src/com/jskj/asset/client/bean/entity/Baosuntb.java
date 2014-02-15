package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Baosuntb {
    private String baosunId;

    private Date baosunDate;

    private String jingbanren;

    private String deport;

    private String zhidanren;

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

    public String getJingbanren() {
        return jingbanren;
    }

    public void setJingbanren(String jingbanren) {
        this.jingbanren = jingbanren == null ? null : jingbanren.trim();
    }

    public String getDeport() {
        return deport;
    }

    public void setDeport(String deport) {
        this.deport = deport == null ? null : deport.trim();
    }

    public String getZhidanren() {
        return zhidanren;
    }

    public void setZhidanren(String zhidanren) {
        this.zhidanren = zhidanren == null ? null : zhidanren.trim();
    }
}