/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class BacksaletbFindEntity extends Backsaletb{
     private int count;
    
    private  List<Backsaletb> backsales;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Backsaletb> getBacksales() {
        return backsales;
    }

    public void setBacksales(List<Backsaletb> backsales) {
        this.backsales = backsales;
    }
    
    
    
}
