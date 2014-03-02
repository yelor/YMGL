/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class SaleyimiaoFindEntity {
    private int count;
    
    private  List<SaleyimiaoEntity> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SaleyimiaoEntity> getResult() {
        return result;
    }

    public void setResult(List<SaleyimiaoEntity> result) {
        this.result = result;
    }
    
    
}
