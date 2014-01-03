/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class YimiaodengjitbFindEntity extends Yimiaodengjitb{
     private int count;
    
    private  List<Yimiaodengjitb> yimiaodengjis;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Yimiaodengjitb> getYimiaodengjis() {
        return yimiaodengjis;
    }

    public void setYimiaodengjis(List<Yimiaodengjitb> yimiaodengjis) {
        this.yimiaodengjis = yimiaodengjis;
    }
    
    
    
}
