package com.jskj.asset.client.bean.entity;

public class Yimiaoshenpiliucheng {
    private String danjuId;

    private String processId;

    private String checkId;

    public String getDanjuId() {
        return danjuId;
    }

    public void setDanjuId(String danjuId) {
        this.danjuId = danjuId == null ? null : danjuId.trim();
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId == null ? null : checkId.trim();
    }
}