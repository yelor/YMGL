package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Depot {
    private Integer depotId;

    private String depotName;

    private String depotArea;

    private String depotAddr;

    private Integer userId;

    private Date depotCreatedate;

    private Date depotCanceldate;

    private String depotRemark;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDepotCreatedate() {
        return depotCreatedate;
    }

    public void setDepotCreatedate(Date depotCreatedate) {
        this.depotCreatedate = depotCreatedate;
    }

    public Date getDepotCanceldate() {
        return depotCanceldate;
    }

    public void setDepotCanceldate(Date depotCanceldate) {
        this.depotCanceldate = depotCanceldate;
    }

    public String getDepotRemark() {
        return depotRemark;
    }

    public void setDepotRemark(String depotRemark) {
        this.depotRemark = depotRemark == null ? null : depotRemark.trim();
    }
}