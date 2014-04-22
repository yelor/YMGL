package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Zichandengjitb {
    private String yuandanId;

    private Integer gdzcId;

    private Date gouzhiDate;

    private Integer dengjirenId;

    private Integer quantity;

    private Date baoxiuqi;

    private String pihao;

    public String getYuandanId() {
        return yuandanId;
    }

    public void setYuandanId(String yuandanId) {
        this.yuandanId = yuandanId == null ? null : yuandanId.trim();
    }

    public Integer getGdzcId() {
        return gdzcId;
    }

    public void setGdzcId(Integer gdzcId) {
        this.gdzcId = gdzcId;
    }

    public Date getGouzhiDate() {
        return gouzhiDate;
    }

    public void setGouzhiDate(Date gouzhiDate) {
        this.gouzhiDate = gouzhiDate;
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
}