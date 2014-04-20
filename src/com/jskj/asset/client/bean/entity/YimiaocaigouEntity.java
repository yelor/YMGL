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
public class YimiaocaigouEntity {

    private YimiaoAll yimiaoAll;

    private Yimiaoshenqingdantb yimiaoshenqingdantb;

    public YimiaoAll getYimiaoAll() {
        return yimiaoAll;
    }

    public void setYimiaoAll(YimiaoAll yimiaoAll) {
        this.yimiaoAll = yimiaoAll;
    }

    public Yimiaoshenqingdantb getYimiaoshenqingdantb() {
        return yimiaoshenqingdantb;
    }

    public void setYimiaoshenqingdantb(Yimiaoshenqingdantb yimiaoshenqingdantb) {
        this.yimiaoshenqingdantb = yimiaoshenqingdantb;
    }

}
