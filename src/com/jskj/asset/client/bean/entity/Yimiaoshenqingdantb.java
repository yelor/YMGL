package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Yimiaoshenqingdantb {
    private Integer xiangdanId;

    private String shenqingdanId;

    private Integer danjuleixingId;

    private Integer yimiaoId;

    private Integer quantity;

    private Float buyprice;

    private Float totalprice;

    private Integer isCompleted;

    private Integer status;

    public Integer getXiangdanId() {
        return xiangdanId;
    }

    public void setXiangdanId(Integer xiangdanId) {
        this.xiangdanId = xiangdanId;
    }

    public String getShenqingdanId() {
        return shenqingdanId;
    }

    public void setShenqingdanId(String shenqingdanId) {
        this.shenqingdanId = shenqingdanId == null ? null : shenqingdanId.trim();
    }

    public Integer getDanjuleixingId() {
        return danjuleixingId;
    }

    public void setDanjuleixingId(Integer danjuleixingId) {
        this.danjuleixingId = danjuleixingId;
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

    public Float getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(Float buyprice) {
        this.buyprice = buyprice;
    }

    public Float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Float totalprice) {
        this.totalprice = totalprice;
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}