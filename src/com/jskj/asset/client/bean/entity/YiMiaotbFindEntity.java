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
public class YiMiaotbFindEntity {
    private int count;
    
    private  List<YimiaoAll> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<YimiaoAll> getResult() {
        return result;
    }

    public void setResult(List<YimiaoAll> result) {
        this.result = result;
    }
    
}
