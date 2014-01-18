/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author 305027939
 */
public class DepartmentFindEntity {
    private int count;
    private List<DepartmenttbAll> result;

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
    public List<DepartmenttbAll> getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(List<DepartmenttbAll> result) {
        this.result = result;
    }
    
}
