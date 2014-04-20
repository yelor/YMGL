/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author huiqi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DanweiyingshouyingfuEntity {
    
    private Integer danweiId;

    private String danweiName;

    private String fuzeren;

    private String telephone;

    private String danweiAddr;

    private Float yingshoujine;
    
    private Float yingfujine;

    public Integer getDanweiId() {
        return danweiId;
    }

    public void setDanweiId(Integer danweiId) {
        this.danweiId = danweiId;
    }

    public String getDanweiName() {
        return danweiName;
    }

    public void setDanweiName(String danweiName) {
        this.danweiName = danweiName;
    }

    public String getFuzeren() {
        return fuzeren;
    }

    public void setFuzeren(String fuzeren) {
        this.fuzeren = fuzeren;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDanweiAddr() {
        return danweiAddr;
    }

    public void setDanweiAddr(String danweiAddr) {
        this.danweiAddr = danweiAddr;
    }

    public Float getYingshoujine() {
        return yingshoujine;
    }

    public void setYingshoujine(Float yingshoujine) {
        this.yingshoujine = yingshoujine;
    }

    public Float getYingfujine() {
        return yingfujine;
    }

    public void setYingfujine(Float yingfujine) {
        this.yingfujine = yingfujine;
    }
    
    
    
}
