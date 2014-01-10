package com.jskj.asset.client.bean.entity;

public class ZiChanCaiGouShenQing {
    private String cgsqId;

    private String processId;

    private String checkId1;

    private String checkId2;

    private String checkId3;

    public String getCgsqId() {
        return cgsqId;
    }

    public void setCgsqId(String cgsqId) {
        this.cgsqId = cgsqId == null ? null : cgsqId.trim();
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getCheckId1() {
        return checkId1;
    }

    public void setCheckId1(String checkId1) {
        this.checkId1 = checkId1 == null ? null : checkId1.trim();
    }

    public String getCheckId2() {
        return checkId2;
    }

    public void setCheckId2(String checkId2) {
        this.checkId2 = checkId2 == null ? null : checkId2.trim();
    }

    public String getCheckId3() {
        return checkId3;
    }

    public void setCheckId3(String checkId3) {
        this.checkId3 = checkId3 == null ? null : checkId3.trim();
    }
}