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
public class YimiaotiaojiaxiangdanEntity {

    private Yimiaotiaojiatb yimiaotiaojiatb;

    private UsertbAll userAll;

    private List<YimiaotiaojiaDetailEntity> result;
                           
    public Yimiaotiaojiatb getYimiaotiaojiatb() {
        return yimiaotiaojiatb;
    }

    public void setYimiaotiaojiatb(Yimiaotiaojiatb yimiaotiaojiatb) {
        this.yimiaotiaojiatb = yimiaotiaojiatb;
    }
    
    

    public UsertbAll getUserAll() {
        return userAll;
    }

    public void setUserAll(UsertbAll userAll) {
        this.userAll = userAll;
    }

    public List<YimiaotiaojiaDetailEntity> getResult() {
        return result;
    }

    public void setResult(List<YimiaotiaojiaDetailEntity> result) {
        this.result = result;
    }

}
