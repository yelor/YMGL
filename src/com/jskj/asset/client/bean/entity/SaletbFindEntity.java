/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class SaletbFindEntity extends Saletb{
     private int count;
    
    private  List<Saletb> sales;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Saletb> getSales() {
        return sales;
    }

    public void setSales(List<Saletb> sales) {
        this.sales = sales;
    }
    
    
    
}
