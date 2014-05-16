package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Zhijielingyongtb {
    private String yuandanId;

    private Integer yhpId;

    private Date gouzhiDate;

    private Date baoxiuDate;

    private Integer dengjirenId;

    private Integer quantity;

    private Float price;

    private String barcode;

    public String getYuandanId() {
        return yuandanId;
    }

    public void setYuandanId(String yuandanId) {
        this.yuandanId = yuandanId == null ? null : yuandanId.trim();
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

    public Date getBaoxiuDate() {
        return baoxiuDate;
    }

    public void setBaoxiuDate(Date baoxiuDate) {
        this.baoxiuDate = baoxiuDate;
    }

    public Integer getDengjirenId() {
        return dengjirenId;
    }

    public void setDengjirenId(Integer dengjirenId) {
        this.dengjirenId = dengjirenId;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }
}