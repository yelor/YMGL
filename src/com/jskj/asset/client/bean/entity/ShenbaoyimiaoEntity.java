/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author tt
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShenbaoyimiaoEntity {    
    
    private YimiaoAll yimiaoAll;

    private Shenqingdantb shenqingdan;

    private Yimiaoshenqingdantb yimiaoshenqingtb;

    public YimiaoAll getYimiaoAll() {

        return yimiaoAll;
    }

    public void setYimiaoAll(YimiaoAll yimiaoAll) {

        this.yimiaoAll = yimiaoAll;
    }

    public Shenqingdantb getShenqingdan() {

        return shenqingdan;
    }

    public void setShenqingdan(Shenqingdantb shenqingdan) {
        
        this.shenqingdan = shenqingdan;
    }

    public Yimiaoshenqingdantb getYimiaoshenqingtb() {

        return yimiaoshenqingtb;
    }

    public void setYimiaoshenqingtb(Yimiaoshenqingdantb yimiaoshenqingtb) {
        
        this.yimiaoshenqingtb = yimiaoshenqingtb;
    }

}
