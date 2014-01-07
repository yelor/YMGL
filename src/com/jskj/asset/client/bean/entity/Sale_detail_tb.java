package com.jskj.asset.client.bean.entity;

public class Sale_detail_tb {
    private Integer saleDetailId;

    private Integer saleId;

    private Integer yimiaoId;

    private Integer quantity;

    private Float price;

    private Float totalprice;

    public Integer getSaleDetailId() {
        return saleDetailId;
    }

    public void setSaleDetailId(Integer saleDetailId) {
        this.saleDetailId = saleDetailId;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
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
