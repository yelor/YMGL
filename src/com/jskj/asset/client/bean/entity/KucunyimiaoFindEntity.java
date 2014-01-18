/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author tt
 */
public class KucunyimiaoFindEntity {
    
    private int count;
    
    private  List<KucunyimiaoEntity> result;

    public int getCount() {
        return count;
    }

    public List<KucunyimiaoEntity> getResult() {
        return result;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setResult(List<KucunyimiaoEntity> result) {
        this.result = result;
    }
        
}
