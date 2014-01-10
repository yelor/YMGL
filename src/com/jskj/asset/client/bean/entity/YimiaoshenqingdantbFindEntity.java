/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class YimiaoshenqingdantbFindEntity{

    private Shenqingdantb shenqingdan;

    private int count;

    private List<Yimiaoshenqingdantb> yimiaoshenqingdans;

    public Shenqingdantb getShenqingdan() {
        return shenqingdan;
    }

    public void setShenqingdan(Shenqingdantb shenqingdan) {
        this.shenqingdan = shenqingdan;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Yimiaoshenqingdantb> getYimiaoshenqingdans() {
        return yimiaoshenqingdans;
    }

    public void setYimiaoshenqingdans(List<Yimiaoshenqingdantb> yimiaoshenqingdans) {
        this.yimiaoshenqingdans = yimiaoshenqingdans;
    }

}
