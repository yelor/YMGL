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
public class YimiaocaigoujihuaEntity {
    private Integer yimiaoId;

    private String yimiaoName;

    private String yimiaoGuige;

    private String yimiaoJixing;

    private String unit;

    private Integer kucunQuantity;

    private Integer kucunXiaxian;
    
    private Integer kucunShangxian;
    
    private Integer jihuaQuantity;
    
    private Integer tuijianQuantity;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getKucunQuantity() {
        return kucunQuantity;
    }

    public void setKucunQuantity(Integer kucunQuantity) {
        this.kucunQuantity = kucunQuantity;
    }

    public Integer getKucunXiaxian() {
        return kucunXiaxian;
    }

    public void setKucunXiaxian(Integer kucunXiaxian) {
        this.kucunXiaxian = kucunXiaxian;
    }

    public Integer getKucunShangxian() {
        return kucunShangxian;
    }

    public void setKucunShangxian(Integer kucunShangxian) {
        this.kucunShangxian = kucunShangxian;
    }

    public Integer getJihuaQuantity() {
        return jihuaQuantity;
    }

    public void setJihuaQuantity(Integer jihuaQuantity) {
        this.jihuaQuantity = jihuaQuantity;
    }

    public Integer getTuijianQuantity() {
        return tuijianQuantity;
    }

    public void setTuijianQuantity(Integer tuijianQuantity) {
        this.tuijianQuantity = tuijianQuantity;
    }

 
        
}
