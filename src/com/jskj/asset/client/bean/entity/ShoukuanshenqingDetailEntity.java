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
public class ShoukuanshenqingDetailEntity extends Shoukuandantb{
    
    private String shenqingren;
    
    private String supplier;
    
    private String accountNum;
    
    List<Yingfukuandanjutb> ysklist;
    
    List<Qitashoukuanliebiaotb> qtlist;

    public String getShenqingren() {
        return shenqingren;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public List<Yingfukuandanjutb> getYsklist() {
        return ysklist;
    }

    public List<Qitashoukuanliebiaotb> getQtlist() {
        return qtlist;
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

    public void setYsklist(List<Yingfukuandanjutb> ysklist) {
        this.ysklist = ysklist;
    }

    public void setQtlist(List<Qitashoukuanliebiaotb> qtlist) {
        this.qtlist = qtlist;
    }

}
