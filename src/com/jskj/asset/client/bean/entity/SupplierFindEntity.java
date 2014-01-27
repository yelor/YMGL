/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class SupplierFindEntity extends Supplier{
     private int count;
    
    private  List<Supplier> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Supplier> getResult() {
        return result;
    }

    public void setResult(List<Supplier> result) {
        this.result = result;
    }
    
    
    
}
