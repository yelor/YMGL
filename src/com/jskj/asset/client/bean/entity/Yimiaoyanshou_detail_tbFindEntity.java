/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class Yimiaoyanshou_detail_tbFindEntity {
    private Yimiaoyanshoutb yimiaoyanshou;
    
     private int count;
    
    private  List<Yimiaoyanshou_detail_tb> result;

    public Yimiaoyanshoutb getYimiaoyanshou() {
        return yimiaoyanshou;
    }

    public void setYimiaoyanshou(Yimiaoyanshoutb yimiaoyanshou) {
        this.yimiaoyanshou = yimiaoyanshou;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Yimiaoyanshou_detail_tb> getResult() {
        return result;
    }

    public void setResult(List<Yimiaoyanshou_detail_tb> result) {
        this.result = result;
    }
    
    
    
}
