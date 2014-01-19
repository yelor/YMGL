package com.jskj.asset.client.bean.entity;

public class Appparam {
    private Integer appparamId;

    private String appparamType;

    private String appparamName;

    private Integer appparamPid;

    private String appparamDesc;

    private Integer systemparam;

    public Integer getAppparamId() {
        return appparamId;
    }

    public void setAppparamId(Integer appparamId) {
        this.appparamId = appparamId;
    }

    public String getAppparamType() {
        return appparamType;
    }

    public void setAppparamType(String appparamType) {
        this.appparamType = appparamType == null ? null : appparamType.trim();
    }

    public String getAppparamName() {
        return appparamName;
    }

    public void setAppparamName(String appparamName) {
        this.appparamName = appparamName == null ? null : appparamName.trim();
    }

    public Integer getAppparamPid() {
        return appparamPid;
    }

    public void setAppparamPid(Integer appparamPid) {
        this.appparamPid = appparamPid;
    }

    public String getAppparamDesc() {
        return appparamDesc;
    }

    public void setAppparamDesc(String appparamDesc) {
        this.appparamDesc = appparamDesc == null ? null : appparamDesc.trim();
    }

    public Integer getSystemparam() {
        return systemparam;
    }

    public void setSystemparam(Integer systemparam) {
        this.systemparam = systemparam;
    }
}