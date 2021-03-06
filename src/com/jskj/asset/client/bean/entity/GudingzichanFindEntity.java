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
public class GudingzichanFindEntity {
    
    private int count;
    
    private  List<GudingzichanAll> result;

    public int getCount() {
        return count;
    }

    public List<GudingzichanAll> getResult() {
        return result;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setResult(List<GudingzichanAll> result) {
        this.result = result;
    }
    
}
