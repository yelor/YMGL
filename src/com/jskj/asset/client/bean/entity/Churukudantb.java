package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Churukudantb {
    private String churukuId;

    private Date zhidandate;

    private Integer gongyingdanwei;

    private String kufang;

    private Integer zhidanren;

    private String remark;

    private Integer xiangdanId;

    public String getChurukuId() {
        return churukuId;
    }

    public void setChurukuId(String churukuId) {
        this.churukuId = churukuId == null ? null : churukuId.trim();
    }

    public Date getZhidandate() {
        return zhidandate;
    }

    public void setZhidandate(Date zhidandate) {
        this.zhidandate = zhidandate;
    }

    public Integer getGongyingdanwei() {
        return gongyingdanwei;
    }

    public void setGongyingdanwei(Integer gongyingdanwei) {
        this.gongyingdanwei = gongyingdanwei;
    }

    public String getKufang() {
        return kufang;
    }

    public void setKufang(String kufang) {
        this.kufang = kufang == null ? null : kufang.trim();
    }

    public Integer getZhidanren() {
        return zhidanren;
    }

    public void setZhidanren(Integer zhidanren) {
        this.zhidanren = zhidanren;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getXiangdanId() {
        return xiangdanId;
    }

    public void setXiangdanId(Integer xiangdanId) {
        this.xiangdanId = xiangdanId;
    }
}