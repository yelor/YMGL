/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

/**
 *
 * @author haitao
 */
public class ShenqingdanAll extends Shenqingdantb{
    
    private String danjuType;
    
    private float totalPrice;
    
    private String zhidanren;

    public String getDanjuType() {
        return danjuType;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public String getZhidanren() {
        return zhidanren;
    }

    public void setDanjuType(String danjuType) {
        this.danjuType = danjuType;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setZhidanren(String zhidanren) {
        this.zhidanren = zhidanren;
    }
    
}
