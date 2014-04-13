package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Shoukuandantb {
    private String shoukuandanId;

    private Date shoukuandanDate;

    private Integer kehudanweiId;

    private Float yingshou;

    private Float shoukuan;

    private Float youhui;

    private String shenqingdanRemark;

    private Integer zhidanrenId;

    private Integer danjuleixingId;

    private Integer isCompleted;

    private Integer isPaid;

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

    public Integer getKehudanweiId() {
        return kehudanweiId;
    }

    public void setKehudanweiId(Integer kehudanweiId) {
        this.kehudanweiId = kehudanweiId;
    }

    public Float getYingshou() {
        return yingshou;
    }

    public void setYingshou(Float yingshou) {
        this.yingshou = yingshou;
    }

    public Float getShoukuan() {
        return shoukuan;
    }

    public void setShoukuan(Float shoukuan) {
        this.shoukuan = shoukuan;
    }

    public Float getYouhui() {
        return youhui;
    }

    public void setYouhui(Float youhui) {
        this.youhui = youhui;
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

    public Integer getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
    }
}