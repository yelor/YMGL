/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author huiqi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaletbFindEntity{
     private int count;
    
    private  List<SaletbAll> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SaletbAll> getResult() {
        return result;
    }

    public void setResult(List<SaletbAll> result) {
        this.result = result;
    }
    
    
    
}
