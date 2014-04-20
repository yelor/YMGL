/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 *
 * @author tt
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class KucunyimiaoEntity {
    private Integer yimiaoId;

    private String yimiaoName;

    private String yimiaoGuige;

    private String yimiaoJixing;

    private String shengchanqiye;
    
    private String unit;
    
    private Date youxiaoqi;

    private Float price;
    
    private Float saleprice;
    
    private Integer kucunQuantiy;

    public Integer getYimiaoId() {
        return yimiaoId;
    }

    public void setYimiaoId(Integer yimiaoId) {
        this.yimiaoId = yimiaoId;
    }

    public String getYimiaoName() {
        return yimiaoName;
    }

    public void setYimiaoName(String yimiaoName) {
        this.yimiaoName = yimiaoName;
    }

    public String getYimiaoGuige() {
        return yimiaoGuige;
    }

    public void setYimiaoGuige(String yimiaoGuige) {
        this.yimiaoGuige = yimiaoGuige;
    }

    public String getYimiaoJixing() {
        return yimiaoJixing;
    }

    public void setYimiaoJixing(String yimiaoJixing) {
        this.yimiaoJixing = yimiaoJixing;
    }

    public String getShengchanqiye() {
        return shengchanqiye;
    }

    public void setShengchanqiye(String shengchanqiye) {
        this.shengchanqiye = shengchanqiye;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getYouxiaoqi() {
        return youxiaoqi;
    }

    public void setYouxiaoqi(Date youxiaoqi) {
        this.youxiaoqi = youxiaoqi;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(Float saleprice) {
        this.saleprice = saleprice;
    }

    public Integer getKucunQuantiy() {
        return kucunQuantiy;
    }

    public void setKucunQuantiy(Integer kucunQuantiy) {
        this.kucunQuantiy = kucunQuantiy;
    }

   

 
        
}
