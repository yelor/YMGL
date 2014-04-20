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
public class YimiaoyanshoutbFindEntity extends Yimiaoyanshoutb{
     private int count;
    
    private  List<Yimiaoyanshoutb> yimiaoyanshous;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Yimiaoyanshoutb> getYimiaoyanshous() {
        return yimiaoyanshous;
    }

    public void setYimiaoyanshous(List<Yimiaoyanshoutb> yimiaoyanshous) {
        this.yimiaoyanshous = yimiaoyanshous;
    }
    
    
    
}
