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
public class ZichanliebiaoDetailEntity extends Gudingzichantb{
    
    private int count;
    
    private float saleprice;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(float saleprice) {
        this.saleprice = saleprice;
    }
    
}
