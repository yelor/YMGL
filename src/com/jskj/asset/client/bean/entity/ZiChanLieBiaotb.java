package com.jskj.asset.client.bean.entity;

public class ZiChanLieBiaotb {
    private String cgsqId;

    private Integer gdzcId;

    private Integer quantity;

    public String getCgsqId() {
        return cgsqId;
    }

    public void setCgsqId(String cgsqId) {
        this.cgsqId = cgsqId == null ? null : cgsqId.trim();
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
}