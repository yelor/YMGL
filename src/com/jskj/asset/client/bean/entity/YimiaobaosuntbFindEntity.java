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
public class YimiaobaosuntbFindEntity {
    
    private Baosuntb baosun;

    private int count;

    private List<Yimiaobaosuntb> result;

    public Baosuntb getBaosun() {
        return baosun;
    }

    public void setBaosun(Baosuntb baosun) {
        this.baosun = baosun;
    } 
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Yimiaobaosuntb> getResult() {
        return result;
    }

    public void setResult(List<Yimiaobaosuntb> result) {
        this.result = result;
    }

}
