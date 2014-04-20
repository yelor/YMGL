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
public class ChukuyimiaoEntity {

    private YiMiaotb yimiao;

    private Churukudanyimiaoliebiaotb chukuyimiao;

    public YiMiaotb getYimiao() {
        return yimiao;
    }

    public void setYimiao(YiMiaotb yimiao) {
        this.yimiao = yimiao;
    }

    public Churukudanyimiaoliebiaotb getChukuyimiao() {
        return chukuyimiao;
    }

    public void setChukuyimiao(Churukudanyimiaoliebiaotb chukuyimiao) {
        this.chukuyimiao = chukuyimiao;
    }

}
