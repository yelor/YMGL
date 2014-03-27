/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class XiaoshoutuihuoEntity {

    private Backsaletb backsaletb;

    private List<Backsale_detail_tb> backsale_details;

    public Backsaletb getBacksaletb() {
        return backsaletb;
    }

    public void setBacksaletb(Backsaletb backsaletb) {
        this.backsaletb = backsaletb;
    }

    public List<Backsale_detail_tb> getBacksales() {
        return backsale_details;
    }

    public void setBacksale_details(List<Backsale_detail_tb> backsale_details) {
        this.backsale_details = backsale_details;
    }

}
