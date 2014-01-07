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
    
    private  List<YiMiaotb> yimiaos;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<YiMiaotb> getYimiaos() {
        return yimiaos;
    }

    public void setYimiaos(List<YiMiaotb> yimiaos) {
        this.yimiaos = yimiaos;
    }
    
}
