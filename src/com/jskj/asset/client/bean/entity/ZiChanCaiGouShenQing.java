package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class ZiChanCaiGouShenQing {
    private String cgsqId;

    private String processId;

    private String checkId1;

    private String checkId2;

    private String checkId3;

    private Date checkTime1;

    private Date checkTime2;

    private Date checkTime3;

    private String rejectReason;

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

    public Date getCheckTime1() {
        return checkTime1;
    }

    public void setCheckTime1(Date checkTime1) {
        this.checkTime1 = checkTime1;
    }

    public Date getCheckTime2() {
        return checkTime2;
    }

    public void setCheckTime2(Date checkTime2) {
        this.checkTime2 = checkTime2;
    }

    public Date getCheckTime3() {
        return checkTime3;
    }

    public void setCheckTime3(Date checkTime3) {
        this.checkTime3 = checkTime3;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason == null ? null : rejectReason.trim();
    }
}