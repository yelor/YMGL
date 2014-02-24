package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Qitashoukuanliebiaotb {
    private String shoukuandanId;

    private String zhichuType;

    private String jingbanren;

    private Float price;

    private String remark;

    public String getShoukuandanId() {
        return shoukuandanId;
    }

    public void setShoukuandanId(String shoukuandanId) {
        this.shoukuandanId = shoukuandanId == null ? null : shoukuandanId.trim();
    }

    public String getZhichuType() {
        return zhichuType;
    }

    public void setZhichuType(String zhichuType) {
        this.zhichuType = zhichuType == null ? null : zhichuType.trim();
    }

    public String getJingbanren() {
        return jingbanren;
    }

    public void setJingbanren(String jingbanren) {
        this.jingbanren = jingbanren == null ? null : jingbanren.trim();
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}