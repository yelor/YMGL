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
public class FukuanshenqingDetailEntity extends Fukuandantb{
    
    private String shenqingren;
    
    private String supplier;
    
    private String accountNum;
    
    List<Yingfukuandanjutb> yfklist;
    
    List<Qitafukuanliebiaotb> qtlist;

    public String getShenqingren() {
        return shenqingren;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public List<Yingfukuandanjutb> getYfklist() {
        return yfklist;
    }

    public List<Qitafukuanliebiaotb> getQtlist() {
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

    public void setYfklist(List<Yingfukuandanjutb> yfklist) {
        this.yfklist = yfklist;
    }

    public void setQtlist(List<Qitafukuanliebiaotb> qtlist) {
        this.qtlist = qtlist;
    }

}
