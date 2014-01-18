/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class YiMiaotbFindEntity {
    private int count;
    
    private  List<YiMiaotb> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<YiMiaotb> getResult() {
        return result;
    }

    public void setResult(List<YiMiaotb> result) {
        this.result = result;
    }
    
}
