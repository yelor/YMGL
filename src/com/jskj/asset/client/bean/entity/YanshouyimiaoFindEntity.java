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
public class YanshouyimiaoFindEntity {
    private int count;
    
    private  List<YanshouyimiaoEntity> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<YanshouyimiaoEntity> getResult() {
        return result;
    }

    public void setResult(List<YanshouyimiaoEntity> result) {
        this.result = result;
    }
    
    
}
