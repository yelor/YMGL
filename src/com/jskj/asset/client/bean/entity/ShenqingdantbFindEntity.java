/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class ShenqingdantbFindEntity extends Shenqingdantb{
     private int count;
    
    private  List<Shenqingdantb> shenqingdans;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Shenqingdantb> getYimiaoshenqingdans() {
        return shenqingdans;
    }

    public void setYimiaoshenqingdans(List<Shenqingdantb> shenqingdans) {
        this.shenqingdans = shenqingdans;
    }
    
    
    
}
