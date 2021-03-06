package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Depot {
    private Integer depotId;

    private String depotName;

    private String depotArea;

    private String depotAddr;

    private String depotRemark;

    private String zujima;

    public Integer getDepotId() {
        return depotId;
    }

    public void setDepotId(Integer depotId) {
        this.depotId = depotId;
    }

    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName == null ? null : depotName.trim();
    }

    public String getDepotArea() {
        return depotArea;
    }

    public void setDepotArea(String depotArea) {
        this.depotArea = depotArea == null ? null : depotArea.trim();
    }

    public String getDepotAddr() {
        return depotAddr;
    }

    public void setDepotAddr(String depotAddr) {
        this.depotAddr = depotAddr == null ? null : depotAddr.trim();
    }

    public String getDepotRemark() {
        return depotRemark;
    }

    public void setDepotRemark(String depotRemark) {
        this.depotRemark = depotRemark == null ? null : depotRemark.trim();
    }

    public String getZujima() {
        return zujima;
    }

    public void setZujima(String zujima) {
        this.zujima = zujima == null ? null : zujima.trim();
    }
}