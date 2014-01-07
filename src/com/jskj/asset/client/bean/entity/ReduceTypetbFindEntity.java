/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class ReduceTypetbFindEntity extends ReduceTypetb{
     private int count;
    
    private  List<ReduceTypetb> reduceTypes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ReduceTypetb> getReduceTypes() {
        return reduceTypes;
    }

    public void setReduceTypes(List<ReduceTypetb> reduceTypes) {
        this.reduceTypes = reduceTypes;
    }
    
    
    
}
