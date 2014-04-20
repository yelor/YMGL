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
public class YimiaochurukuEntity {

    private Churukudantb churukutb;
    
    private List<Churukudanyimiaoliebiaotb> result;

    public Churukudantb getChurukutb() {
        return churukutb;
    }

    public void setChurukutb(Churukudantb churukutb) {
        this.churukutb = churukutb;
    }

    public List<Churukudanyimiaoliebiaotb> getResult() {
        return result;
    }

    public void setResult(List<Churukudanyimiaoliebiaotb> result) {
        this.result = result;
    }
    
    

}
