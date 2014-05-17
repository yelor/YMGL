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

    private String wanglaidanwei;

    private String duifangjingbanren;

    private Usertb zhidanren;

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

    public String getWanglaidanwei() {
        return wanglaidanwei;
    }

    public void setWanglaidanwei(String wanglaidanwei) {
        this.wanglaidanwei = wanglaidanwei;
    }

    public String getDuifangjingbanren() {
        return duifangjingbanren;
    }

    public void setDuifangjingbanren(String duifangjingbanren) {
        this.duifangjingbanren = duifangjingbanren;
    }

    public Usertb getZhidanren() {
        return zhidanren;
    }

    public void setZhidanren(Usertb zhidanren) {
        this.zhidanren = zhidanren;
    }

}
