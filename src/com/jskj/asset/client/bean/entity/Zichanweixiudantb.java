package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Zichanweixiudantb {
    private String wxdId;

    private Integer wxzcId;

    private Double zcValue;

    private Double weixiufeiyong;

    private Date yujiquhuiDate;

    private Date songxiuDate;

    private Date quhuiDate;

    private String peijianName;

    private Integer songxiurenId;

    private Integer pizhunrenId;

    private String songxiuReason;

    private String weixiuState;

    private String returnState;

    public String getWxdId() {
        return wxdId;
    }

    public void setWxdId(String wxdId) {
        this.wxdId = wxdId == null ? null : wxdId.trim();
    }

    public Integer getWxzcId() {
        return wxzcId;
    }

    public void setWxzcId(Integer wxzcId) {
        this.wxzcId = wxzcId;
    }

    public Double getZcValue() {
        return zcValue;
    }

    public void setZcValue(Double zcValue) {
        this.zcValue = zcValue;
    }

    public Double getWeixiufeiyong() {
        return weixiufeiyong;
    }

    public void setWeixiufeiyong(Double weixiufeiyong) {
        this.weixiufeiyong = weixiufeiyong;
    }

    public Date getYujiquhuiDate() {
        return yujiquhuiDate;
    }

    public void setYujiquhuiDate(Date yujiquhuiDate) {
        this.yujiquhuiDate = yujiquhuiDate;
    }

    public Date getSongxiuDate() {
        return songxiuDate;
    }

    public void setSongxiuDate(Date songxiuDate) {
        this.songxiuDate = songxiuDate;
    }

    public Date getQuhuiDate() {
        return quhuiDate;
    }

    public void setQuhuiDate(Date quhuiDate) {
        this.quhuiDate = quhuiDate;
    }

    public String getPeijianName() {
        return peijianName;
    }

    public void setPeijianName(String peijianName) {
        this.peijianName = peijianName == null ? null : peijianName.trim();
    }

    public Integer getSongxiurenId() {
        return songxiurenId;
    }

    public void setSongxiurenId(Integer songxiurenId) {
        this.songxiurenId = songxiurenId;
    }

    public Integer getPizhunrenId() {
        return pizhunrenId;
    }

    public void setPizhunrenId(Integer pizhunrenId) {
        this.pizhunrenId = pizhunrenId;
    }

    public String getSongxiuReason() {
        return songxiuReason;
    }

    public void setSongxiuReason(String songxiuReason) {
        this.songxiuReason = songxiuReason == null ? null : songxiuReason.trim();
    }

    public String getWeixiuState() {
        return weixiuState;
    }

    public void setWeixiuState(String weixiuState) {
        this.weixiuState = weixiuState == null ? null : weixiuState.trim();
    }

    public String getReturnState() {
        return returnState;
    }

    public void setReturnState(String returnState) {
        this.returnState = returnState == null ? null : returnState.trim();
    }
}