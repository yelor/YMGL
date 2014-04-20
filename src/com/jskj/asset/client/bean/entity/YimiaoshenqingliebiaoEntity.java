/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author huiqi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YimiaoshenqingliebiaoEntity {

    private Yimiaoshenqingdantb yimiaoshenqingdan;

    private YiMiaotb yimiao;

    public Yimiaoshenqingdantb getYimiaoshenqingdan() {
        return yimiaoshenqingdan;
    }

    public void setYimiaoshenqingdan(Yimiaoshenqingdantb yimiaoshenqingdan) {
        this.yimiaoshenqingdan = yimiaoshenqingdan;
    }

    public YiMiaotb getYimiao() {
        return yimiao;
    }

    public void setYimiao(YiMiaotb yimiao) {
        this.yimiao = yimiao;
    }

}
