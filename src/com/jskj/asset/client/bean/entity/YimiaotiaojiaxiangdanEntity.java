/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class YimiaotiaojiaxiangdanEntity {
    
    private UsertbAll userAll;

    private Yimiaotiaojiatb yimiaotiaojiatb;

    private List<YimiaotiaojiaDetailEntity> result;

    public UsertbAll getUserAll() {
        return userAll;
    }

    public void setUserAll(UsertbAll userAll) {
        this.userAll = userAll;
    }

    public Yimiaotiaojiatb getYimiaotiaojiatb() {
        return yimiaotiaojiatb;
    }

    public void setYimiaotiaojiatb(Yimiaotiaojiatb yimiaotiaojiatb) {
        this.yimiaotiaojiatb = yimiaotiaojiatb;
    }

    public List<YimiaotiaojiaDetailEntity> getResult() {
        return result;
    }

    public void setResult(List<YimiaotiaojiaDetailEntity> result) {
        this.result = result;
    }
    
    
    

}
