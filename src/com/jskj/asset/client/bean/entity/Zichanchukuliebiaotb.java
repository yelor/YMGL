package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Zichanchukuliebiaotb {
    private String chukudanId;

    private Integer ckzcId;

    private Integer quantity;

    private Float saleprice;

    private Float totalprice;

    public String getChukudanId() {
        return chukudanId;
    }

    public void setChukudanId(String chukudanId) {
        this.chukudanId = chukudanId == null ? null : chukudanId.trim();
    }

    public Integer getCkzcId() {
        return ckzcId;
    }

    public void setCkzcId(Integer ckzcId) {
        this.ckzcId = ckzcId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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