package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZiChanCaiGouShenQing {
    private String cgsqId;

    private String processId;

    private String checkId1;

    private String checkId2;

    private String checkId3;

    private String checkId4;

    private Date checkTime1;

    private Date checkTime2;

    private Date checkTime3;

    private Date checkTime4;

    private String checkUser1;

    private String checkUser2;

    private String checkUser3;

    private String checkUser4;

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

    public String getCheckId4() {
        return checkId4;
    }

    public void setCheckId4(String checkId4) {
        this.checkId4 = checkId4 == null ? null : checkId4.trim();
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

    public Date getCheckTime4() {
        return checkTime4;
    }

    public void setCheckTime4(Date checkTime4) {
        this.checkTime4 = checkTime4;
    }

    public String getCheckUser1() {
        return checkUser1;
    }

    public void setCheckUser1(String checkUser1) {
        this.checkUser1 = checkUser1 == null ? null : checkUser1.trim();
    }

    public String getCheckUser2() {
        return checkUser2;
    }

    public void setCheckUser2(String checkUser2) {
        this.checkUser2 = checkUser2 == null ? null : checkUser2.trim();
    }

    public String getCheckUser3() {
        return checkUser3;
    }

    public void setCheckUser3(String checkUser3) {
        this.checkUser3 = checkUser3 == null ? null : checkUser3.trim();
    }

    public String getCheckUser4() {
        return checkUser4;
    }

    public void setCheckUser4(String checkUser4) {
        this.checkUser4 = checkUser4 == null ? null : checkUser4.trim();
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason == null ? null : rejectReason.trim();
    }
}