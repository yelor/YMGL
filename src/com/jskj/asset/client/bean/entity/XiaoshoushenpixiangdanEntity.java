/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class XiaoshoushenpixiangdanEntity {

    private Saletb saletb;

    private Kehudanweitb kehudanwei;

    private UsertbAll userAll;

    private List<SaleyimiaoEntity> result;

    public Saletb getSaletb() {
        return saletb;
    }

    public void setSaletb(Saletb saletb) {
        this.saletb = saletb;
    }

    public Kehudanweitb getKehudanwei() {
        return kehudanwei;
    }

    public void setKehudanwei(Kehudanweitb kehudanwei) {
        this.kehudanwei = kehudanwei;
    }

    public UsertbAll getUserAll() {
        return userAll;
    }

    public void setUserAll(UsertbAll userAll) {
        this.userAll = userAll;
    }

    public List<SaleyimiaoEntity> getResult() {
        return result;
    }

    public void setResult(List<SaleyimiaoEntity> result) {
        this.result = result;
    }


    
}
