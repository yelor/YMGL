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
public class ShenbaoyimiaoFindEntity {

    private int count;

    private List<ShenbaoyimiaoEntity> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ShenbaoyimiaoEntity> getResult() {
        return result;
    }

    public void setResult(List<ShenbaoyimiaoEntity> result) {
        this.result = result;
    }
    
}
