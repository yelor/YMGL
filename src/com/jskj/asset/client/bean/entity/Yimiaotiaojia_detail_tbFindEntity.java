/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author huiqi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Yimiaotiaojia_detail_tbFindEntity{

    private Yimiaotiaojiatb yimiaotiaojia;

    private int count;

    private List<Yimiaotiaojia_detail_tb> result;

    public Yimiaotiaojiatb getYimiaotiaojia() {
        return yimiaotiaojia;
    }

    public void setYimiaotiaojia(Yimiaotiaojiatb yimiaotiaojia) {
        this.yimiaotiaojia = yimiaotiaojia;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Yimiaotiaojia_detail_tb> getResult() {
        return result;
    }

    public void setResult(List<Yimiaotiaojia_detail_tb> result) {
        this.result = result;
    }

}
