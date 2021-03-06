/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author haitao
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QitashoukuanshenqingDetailEntity extends Qitashoukuandantb{
    
    private String shenqingren;
    
    private String supplier;
    
    private String accountNum;
    
    private String checkId1;
    
    private String checkId2;
    
    private String checkId3;

    private String rejectReason;
    
    private List<Qitashoukuanliebiaotb> list;

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

    public List<Qitashoukuanliebiaotb> getList() {
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

    public void setList(List<Qitashoukuanliebiaotb> list) {
        this.list = list;
    }
    
    public String getCheckId3() {
        return checkId3;
    }

    public void setCheckId3(String checkId3) {
        this.checkId3 = checkId3;
    }
    
}
