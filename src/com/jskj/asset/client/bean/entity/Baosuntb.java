package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Baosuntb {
    private String baosunId;

    private Date baosunDate;

    private Integer jingbanren;

    private String deport;

    private Integer zhidanren;

    private Float danjujine;

    private Integer isCompleted;

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

    public String getDeport() {
        return deport;
    }

    public void setDeport(String deport) {
        this.deport = deport == null ? null : deport.trim();
    }

    public Integer getZhidanren() {
        return zhidanren;
    }

    public void setZhidanren(Integer zhidanren) {
        this.zhidanren = zhidanren;
    }

    public Float getDanjujine() {
        return danjujine;
    }

    public void setDanjujine(Float danjujine) {
        this.danjujine = danjujine;
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }
}