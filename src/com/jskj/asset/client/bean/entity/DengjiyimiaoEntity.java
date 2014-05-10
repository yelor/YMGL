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
public class DengjiyimiaoEntity extends ShenbaoyimiaoEntity {

    private Supplier gongyingdanwei;

    private Yimiaodengjitb yimiaodengji;

    public Supplier getGongyingdanwei() {
        return gongyingdanwei;
    }

    public void setGongyingdanwei(Supplier gongyingdanwei) {
        this.gongyingdanwei = gongyingdanwei;
    }

    public Yimiaodengjitb getYimiaodengji() {

        return yimiaodengji;
    }

    public void setYimiaodengji(Yimiaodengjitb yimiaodengji) {

        this.yimiaodengji = yimiaodengji;
    }

}
