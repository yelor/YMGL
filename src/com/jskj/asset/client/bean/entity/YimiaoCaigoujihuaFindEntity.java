/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author tt
 */
public class YimiaoCaigoujihuaFindEntity {
    
    private int count;
    
    private  List<YimiaocaigoujihuaEntity> result;

    public int getCount() {
        return count;
    }

    public List<YimiaocaigoujihuaEntity> getResult() {
        return result;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setResult(List<YimiaocaigoujihuaEntity> result) {
        this.result = result;
    }
        
}
