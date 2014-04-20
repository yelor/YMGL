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
public class YimiaocaigouxiangdanEntity {
    
    private UsertbAll userAll;
    
    private Supplier supplier;

    private Shenqingdantb shenqingdantb;

    private List<YimiaocaigouEntity> result;

    public UsertbAll getUserAll() {
        return userAll;
    }

    public void setUserAll(UsertbAll userAll) {
        this.userAll = userAll;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Shenqingdantb getShenqingdantb() {
        return shenqingdantb;
    }

    public void setShenqingdantb(Shenqingdantb shenqingdantb) {
        this.shenqingdantb = shenqingdantb;
    }

    public List<YimiaocaigouEntity> getResult() {
        return result;
    }

    public void setResult(List<YimiaocaigouEntity> result) {
        this.result = result;
    }

    

}
