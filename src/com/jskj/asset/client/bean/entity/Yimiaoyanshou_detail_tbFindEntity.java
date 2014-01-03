/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class Yimiaoyanshou_detail_tbFindEntity extends Yimiaoyanshou_detail_tb{
     private int count;
    
    private  List<Yimiaoyanshou_detail_tb> yimiaoyanshou_details;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Yimiaoyanshou_detail_tb> getYimiaoyanshou_details() {
        return yimiaoyanshou_details;
    }

    public void setYimiaoyanshou_details(List<Yimiaoyanshou_detail_tb> yimiaoyanshou_details) {
        this.yimiaoyanshou_details = yimiaoyanshou_details;
    }
    
    
    
}
