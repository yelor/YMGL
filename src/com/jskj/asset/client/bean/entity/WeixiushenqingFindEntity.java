/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author tt
 */
public class WeixiushenqingFindEntity {
    
    private int count;
    
    private  List<WeixiuzichanDetailEntity> result;

    public int getCount() {
        return count;
    }

    public List<WeixiuzichanDetailEntity> getResult() {
        return result;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setResult(List<WeixiuzichanDetailEntity> result) {
        this.result = result;
    }
    
}
