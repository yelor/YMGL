/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author 305027939
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockpiletbFindEntity{
    
    private int count;
    
    private  List<StockpiletbAll> result;

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the users
     */
    public List<StockpiletbAll> getResult() {
        return result;
    }

    /**
     * @param result the users to set
     */
    public void setResult(List<StockpiletbAll> result) {
        this.result = result;
    }
    
}
