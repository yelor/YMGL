/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class Sale_detail_tbFindEntity extends Sale_detail_tb{
     private int count;
    
    private  List<Sale_detail_tb> sale_details;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Sale_detail_tb> getSale_details() {
        return sale_details;
    }

    public void setSale_details(List<Sale_detail_tb> sale_details) {
        this.sale_details = sale_details;
    }
    
    
    
}
