/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class Backsale_detail_tbFindEntity extends Backsale_detail_tb{
     private int count;
    
    private  List<Backsale_detail_tb> backsale_details;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Backsale_detail_tb> getBacksales() {
        return backsale_details;
    }

    public void setBacksale_details(List<Backsale_detail_tb> backsale_details) {
        this.backsale_details = backsale_details;
    }
    
    
    
}
