package com.jskj.asset.client.bean.entity;

public class Yimiaotiaojia_detail_tb {
    private Integer detailId;

    private String tiaojiaId;

    private Integer kucunyimiaoId;

    private Float beforeprice;

    private Float lastprice;

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public String getTiaojiaId() {
        return tiaojiaId;
    }

    public void setTiaojiaId(String tiaojiaId) {
        this.tiaojiaId = tiaojiaId == null ? null : tiaojiaId.trim();
    }

    public Integer getKucunyimiaoId() {
        return kucunyimiaoId;
    }

    public void setKucunyimiaoId(Integer kucunyimiaoId) {
        this.kucunyimiaoId = kucunyimiaoId;
    }

    public Float getBeforeprice() {
        return beforeprice;
    }

    public void setBeforeprice(Float beforeprice) {
        this.beforeprice = beforeprice;
    }

    public Float getLastprice() {
        return lastprice;
    }

    public void setLastprice(Float lastprice) {
        this.lastprice = lastprice;
    }
}