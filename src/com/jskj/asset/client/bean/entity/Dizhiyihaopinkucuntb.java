package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Dizhiyihaopinkucuntb {
    private Integer kucunId;

    private Integer yhpId;

    private Date gouzhiDate;

    private Integer quantity;

    private String kufang;

    private Date baoxiuqi;

    private String pihao;

    private Float price;

    private Float totalPrice;

    public Integer getKucunId() {
        return kucunId;
    }

    public void setKucunId(Integer kucunId) {
        this.kucunId = kucunId;
    }

    public Integer getYhpId() {
        return yhpId;
    }

    public void setYhpId(Integer yhpId) {
        this.yhpId = yhpId;
    }

    public Date getGouzhiDate() {
        return gouzhiDate;
    }

    public void setGouzhiDate(Date gouzhiDate) {
        this.gouzhiDate = gouzhiDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getKufang() {
        return kufang;
    }

    public void setKufang(String kufang) {
        this.kufang = kufang == null ? null : kufang.trim();
    }

    public Date getBaoxiuqi() {
        return baoxiuqi;
    }

    public void setBaoxiuqi(Date baoxiuqi) {
        this.baoxiuqi = baoxiuqi;
    }

    public String getPihao() {
        return pihao;
    }

    public void setPihao(String pihao) {
        this.pihao = pihao == null ? null : pihao.trim();
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}