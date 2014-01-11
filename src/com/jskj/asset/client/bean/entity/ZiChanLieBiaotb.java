package com.jskj.asset.client.bean.entity;

public class ZiChanLieBiaotb {
    private String cgsqId;

    private Integer cgzcId;

    private Integer quantity;

    public String getCgsqId() {
        return cgsqId;
    }

    public void setCgsqId(String cgsqId) {
        this.cgsqId = cgsqId == null ? null : cgsqId.trim();
    }

    public Integer getCgzcId() {
        return cgzcId;
    }

    public void setCgzcId(Integer cgzcId) {
        this.cgzcId = cgzcId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}