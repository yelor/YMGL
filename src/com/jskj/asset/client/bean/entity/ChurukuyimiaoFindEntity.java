/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author tt
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChurukuyimiaoFindEntity {
    
    private int count;
    
    private  List<ChurukuyimiaoEntity> result;

    public int getCount() {
        return count;
    }

    public List<ChurukuyimiaoEntity> getResult() {
        return result;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setResult(List<ChurukuyimiaoEntity> result) {
        this.result = result;
    }
        
}
