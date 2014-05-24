package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Backsale_detail_tb {
    private Integer backDetailId;

    private String backsaleId;

    private Integer stockpileId;

    private Date youxiaoqi;

    private Integer quantity;

    private Float buyprice;

    private Float saleprice;

    private Float totalprice;

    public Integer getBackDetailId() {
        return backDetailId;
    }

    public void setBackDetailId(Integer backDetailId) {
        this.backDetailId = backDetailId;
    }

    public String getBacksaleId() {
        return backsaleId;
    }

    public void setBacksaleId(String backsaleId) {
        this.backsaleId = backsaleId == null ? null : backsaleId.trim();
    }

    public Integer getStockpileId() {
        return stockpileId;
    }

    public void setStockpileId(Integer stockpileId) {
        this.stockpileId = stockpileId;
    }

    public Date getYouxiaoqi() {
        return youxiaoqi;
    }

    public void setYouxiaoqi(Date youxiaoqi) {
        this.youxiaoqi = youxiaoqi;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(Float buyprice) {
        this.buyprice = buyprice;
    }

    public Float getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(Float saleprice) {
        this.saleprice = saleprice;
    }

    public Float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Float totalprice) {
        this.totalprice = totalprice;
    }
}