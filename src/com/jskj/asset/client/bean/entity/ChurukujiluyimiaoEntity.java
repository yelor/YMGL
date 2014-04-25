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
public class ChurukujiluyimiaoEntity {

    private YiMiaotb yimiao;

    private Churukudantb churukudan;

    private Churukudanyimiaoliebiaotb chukuyimiao;

    public YiMiaotb getYimiao() {
        return yimiao;
    }

    public void setYimiao(YiMiaotb yimiao) {
        this.yimiao = yimiao;
    }

    public Churukudantb getChurukudan() {
        return churukudan;
    }

    public void setChurukudan(Churukudantb churukudan) {
        this.churukudan = churukudan;
    }

    public Churukudanyimiaoliebiaotb getChukuyimiao() {
        return chukuyimiao;
    }

    public void setChukuyimiao(Churukudanyimiaoliebiaotb chukuyimiao) {
        this.chukuyimiao = chukuyimiao;
    }

}
