/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author haitao
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YihaopinliebiaoEntity extends Dizhiyihaopin{
    
    private int count;
    
    private float saleprice;

    public int getCount() {
        return count;
    }

    public float getSaleprice() {
        return saleprice;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSaleprice(float saleprice) {
        this.saleprice = saleprice;
    }

}
