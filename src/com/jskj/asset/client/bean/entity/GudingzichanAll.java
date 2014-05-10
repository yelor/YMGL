/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author 305027939
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GudingzichanAll extends Gudingzichantb{
    private Depot depottb;
    
    private Supplier suppliertb;
    
    private ShenqingdanAll shenqingdan;
    
    private ZiChanLieBiaotb liebiao;
    
    private Gudingzichankucuntb kucun;
    
    private Lingyongliebiaotb lylb;
    
    private int count;
    
    private float saleprice;

    /**
     * @return the depottb
     */
    public Depot getDepottb() {
        return depottb;
    }

    /**
     * @param depottb the depottb to set
     */
    public void setDepottb(Depot depottb) {
        this.depottb = depottb;
    }

    /**
     * @return the suppliertb
     */
    public Supplier getSuppliertb() {
        return suppliertb;
    }

    /**
     * @param suppliertb the suppliertb to set
     */
    public void setSuppliertb(Supplier suppliertb) {
        this.suppliertb = suppliertb;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ShenqingdanAll getShenqingdan() {
        return shenqingdan;
    }

    public void setShenqingdan(ShenqingdanAll shenqingdan) {
        this.shenqingdan = shenqingdan;
    }

    public float getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(float saleprice) {
        this.saleprice = saleprice;
    }

    public ZiChanLieBiaotb getLiebiao() {
        return liebiao;
    }

    public void setLiebiao(ZiChanLieBiaotb liebiao) {
        this.liebiao = liebiao;
    }

    public Gudingzichankucuntb getKucun() {
        return kucun;
    }

    public void setKucun(Gudingzichankucuntb kucun) {
        this.kucun = kucun;
    }

    public Lingyongliebiaotb getLylb() {
        return lylb;
    }

    public void setLylb(Lingyongliebiaotb lylb) {
        this.lylb = lylb;
    }

}
