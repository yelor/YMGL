/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class ShenqingdantbFindEntity{
     private int count;
    
    private  List<ShenqingdanAll> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ShenqingdanAll> getResult() {
        return result;
    }

    public void setResult(List<ShenqingdanAll> result) {
        this.result = result;
    }
    
}
