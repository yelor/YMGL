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
public class CaigoujihuaChaxunEntity {
    private Integer gdzcId;

    private String gdzcName;

    private String gdzcType;

    private String gdzcGuige;

    private String gdzcXinghao;

    private String unitId;

    private String gdzcPinpai;
    
    private Integer quantity;
    
    private Integer jihuaQuantity;
    
    private Integer tuijianQuantity;

    public Integer getGdzcId() {
        return gdzcId;
    }

    public String getGdzcName() {
        return gdzcName;
    }

    public String getGdzcType() {
        return gdzcType;
    }

    public String getGdzcGuige() {
        return gdzcGuige;
    }

    public String getGdzcXinghao() {
        return gdzcXinghao;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getGdzcPinpai() {
        return gdzcPinpai;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getJihuaQuantity() {
        return jihuaQuantity;
    }

    public Integer getTuijianQuantity() {
        return tuijianQuantity;
    }

    public void setGdzcId(Integer gdzcId) {
        this.gdzcId = gdzcId;
    }

    public void setGdzcName(String gdzcName) {
        this.gdzcName = gdzcName;
    }

    public void setGdzcType(String gdzcType) {
        this.gdzcType = gdzcType;
    }

    public void setGdzcGuige(String gdzcGuige) {
        this.gdzcGuige = gdzcGuige;
    }

    public void setGdzcXinghao(String gdzcXinghao) {
        this.gdzcXinghao = gdzcXinghao;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public void setGdzcPinpai(String gdzcPinpai) {
        this.gdzcPinpai = gdzcPinpai;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setJihuaQuantity(Integer jihuaQuantity) {
        this.jihuaQuantity = jihuaQuantity;
    }

    public void setTuijianQuantity(Integer tuijianQuantity) {
        this.tuijianQuantity = tuijianQuantity;
    }
        
}
