/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.layout.ws;

import java.util.List;

/**
 *
 * @author 305027939
 */
public class CommFindEntity<T> {

    private int count;
    private List<T> result;

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
     * @return the result
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(List<T> result) {
        this.result = result;
    }
    
    
}
