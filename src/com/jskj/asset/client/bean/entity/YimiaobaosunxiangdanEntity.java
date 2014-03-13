/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class YimiaobaosunxiangdanEntity {
    
    private UsertbAll userAll;

    private Baosuntb baosuntb;

    private List<YimiaobaosunDetailEntity> result;

    public UsertbAll getUserAll() {
        return userAll;
    }

    public void setUserAll(UsertbAll userAll) {
        this.userAll = userAll;
    }

    public Baosuntb getBaosuntb() {
        return baosuntb;
    }

    public void setBaosuntb(Baosuntb baosuntb) {
        this.baosuntb = baosuntb;
    }

    public List<YimiaobaosunDetailEntity> getResult() {
        return result;
    }

    public void setResult(List<YimiaobaosunDetailEntity> result) {
        this.result = result;
    }    

}
