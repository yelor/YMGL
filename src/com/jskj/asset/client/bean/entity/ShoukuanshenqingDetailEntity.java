/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author haitao
 */
public class ShoukuanshenqingDetailEntity extends Shoukuandantb{
    
    private String shenqingren;
    
    private String supplier;
    
    private String accountNum;
    
    private String checkId1;
    
    private String checkId2;
    
    private String rejectReason;
    
    private List<SaletbAll> list;

    public String getShenqingren() {
        return shenqingren;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public String getCheckId1() {
        return checkId1;
    }

    public String getCheckId2() {
        return checkId2;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public List<SaletbAll> getList() {
        return list;
    }

    public void setShenqingren(String shenqingren) {
        this.shenqingren = shenqingren;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public void setCheckId1(String checkId1) {
        this.checkId1 = checkId1;
    }

    public void setCheckId2(String checkId2) {
        this.checkId2 = checkId2;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public void setList(List<SaletbAll> list) {
        this.list = list;
    }
    
}
