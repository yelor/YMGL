/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class YimiaoyunshuEntity {

    private Yimiaoyunshutb yimiaoyunshu;

    private int count;

    private List<Yimiaoyunshu_detail_tb> result;

    public Yimiaoyunshutb getYimiaoyunshu() {
        return yimiaoyunshu;
    }

    public void setYimiaoyunshu(Yimiaoyunshutb yimiaoyunshu) {
        this.yimiaoyunshu = yimiaoyunshu;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Yimiaoyunshu_detail_tb> getResult() {
        return result;
    }

    public void setResult(List<Yimiaoyunshu_detail_tb> result) {
        this.result = result;
    }

}
