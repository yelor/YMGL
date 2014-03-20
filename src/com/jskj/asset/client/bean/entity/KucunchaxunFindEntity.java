/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author haitao
 */
public class KucunchaxunFindEntity {
    
    private int count;
    
    private List<KucunchaxunEntity> result;

    public int getCount() {
        return count;
    }

    public List<KucunchaxunEntity> getResult() {
        return result;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setResult(List<KucunchaxunEntity> result) {
        this.result = result;
    }
    
}
