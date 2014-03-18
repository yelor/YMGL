package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Yimiaotiaojia_detail_tb {
    private Integer detailId;

    private String tiaojiaId;

    private Integer kucunyimiaoId;

    private Float beforebuyprice;

    private Float lastbuyprice;

    private Float beforesaleprice;

    private Float lastsaleprice;

    private Integer isCompleted;

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

    public Float getBeforebuyprice() {
        return beforebuyprice;
    }

    public void setBeforebuyprice(Float beforebuyprice) {
        this.beforebuyprice = beforebuyprice;
    }

    public Float getLastbuyprice() {
        return lastbuyprice;
    }

    public void setLastbuyprice(Float lastbuyprice) {
        this.lastbuyprice = lastbuyprice;
    }

    public Float getBeforesaleprice() {
        return beforesaleprice;
    }

    public void setBeforesaleprice(Float beforesaleprice) {
        this.beforesaleprice = beforesaleprice;
    }

    public Float getLastsaleprice() {
        return lastsaleprice;
    }

    public void setLastsaleprice(Float lastsaleprice) {
        this.lastsaleprice = lastsaleprice;
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }
}