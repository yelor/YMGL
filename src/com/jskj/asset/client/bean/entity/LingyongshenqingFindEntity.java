/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author tt
 */
public class LingyongshenqingFindEntity {
    
    private int count;
    
    private  List<LingyongzichanDetailEntity> result;

    public int getCount() {
        return count;
    }

    public List<LingyongzichanDetailEntity> getResult() {
        return result;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setResult(List<LingyongzichanDetailEntity> result) {
        this.result = result;
    }
    
}
