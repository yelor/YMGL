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
public class DanjuleixingtbFindEntity extends Danjuleixingtb{
     private int count;
    
    private  List<Danjuleixingtb> danjuleixings;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Danjuleixingtb> getDanjuleixings() {
        return danjuleixings;
    }

    public void setDanjuleixings(List<Danjuleixingtb> danjuleixings) {
        this.danjuleixings = danjuleixings;
    }
    
    
    
}
