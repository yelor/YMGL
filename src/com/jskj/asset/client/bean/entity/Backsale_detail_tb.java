package com.jskj.asset.client.bean.entity;

public class Backsale_detail_tb {
    private Integer backDetailId;

    private String backsaleId;

    private Integer yimiaoId;

    private Integer quantity;

    private Float price;

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

    public Integer getYimiaoId() {
        return yimiaoId;
    }

    public void setYimiaoId(Integer yimiaoId) {
        this.yimiaoId = yimiaoId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Float totalprice) {
        this.totalprice = totalprice;
    }
}