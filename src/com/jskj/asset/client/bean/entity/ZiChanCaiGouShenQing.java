package com.jskj.asset.client.bean.entity;

public class ZiChanCaiGouShenQing {
    private Integer cgsqId;

    private Integer gdzcId;

    private Integer quantity;

    private Float price;

    private String processId;

    public Integer getCgsqId() {
        return cgsqId;
    }

    public void setCgsqId(Integer cgsqId) {
        this.cgsqId = cgsqId;
    }

    public Integer getGdzcId() {
        return gdzcId;
    }

    public void setGdzcId(Integer gdzcId) {
        this.gdzcId = gdzcId;
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

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }
}