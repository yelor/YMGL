/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

/**
 *
 * @author 305027939
 */
public class GudingzichanAll extends Gudingzichantb{
    private Depot depottb;
    
    private Supplier suppliertb;
    
    private int count;

    /**
     * @return the depottb
     */
    public Depot getDepottb() {
        return depottb;
    }

    /**
     * @param depottb the depottb to set
     */
    public void setDepottb(Depot depottb) {
        this.depottb = depottb;
    }

    /**
     * @return the suppliertb
     */
    public Supplier getSuppliertb() {
        return suppliertb;
    }

    /**
     * @param suppliertb the suppliertb to set
     */
    public void setSuppliertb(Supplier suppliertb) {
        this.suppliertb = suppliertb;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
}
