package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Yuandanliebiaotb {
    private String fukuandanId;

    private String yuandanId;

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
}