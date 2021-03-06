package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Yimiaozuzhuangchaixie {
    private Integer id;

    private String zuzhuangchaixieId;

    private Date zhidandate;

    private Integer jingbanrenId;

    private Double zuzhuangfeiyong;

    private String feiyongtype;

    private String remark;

    private Integer yimiao1Id;

    private String cangku1;

    private Integer quantity1;

    private Integer yimiao2Id;

    private String cangku2;

    private Integer quantity2;

    private Integer danjuleixingId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZuzhuangchaixieId() {
        return zuzhuangchaixieId;
    }

    public void setZuzhuangchaixieId(String zuzhuangchaixieId) {
        this.zuzhuangchaixieId = zuzhuangchaixieId == null ? null : zuzhuangchaixieId.trim();
    }

    public Date getZhidandate() {
        return zhidandate;
    }

    public void setZhidandate(Date zhidandate) {
        this.zhidandate = zhidandate;
    }

    public Integer getJingbanrenId() {
        return jingbanrenId;
    }

    public void setJingbanrenId(Integer jingbanrenId) {
        this.jingbanrenId = jingbanrenId;
    }

    public Double getZuzhuangfeiyong() {
        return zuzhuangfeiyong;
    }

    public void setZuzhuangfeiyong(Double zuzhuangfeiyong) {
        this.zuzhuangfeiyong = zuzhuangfeiyong;
    }

    public String getFeiyongtype() {
        return feiyongtype;
    }

    public void setFeiyongtype(String feiyongtype) {
        this.feiyongtype = feiyongtype == null ? null : feiyongtype.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getYimiao1Id() {
        return yimiao1Id;
    }

    public void setYimiao1Id(Integer yimiao1Id) {
        this.yimiao1Id = yimiao1Id;
    }

    public String getCangku1() {
        return cangku1;
    }

    public void setCangku1(String cangku1) {
        this.cangku1 = cangku1 == null ? null : cangku1.trim();
    }

    public Integer getQuantity1() {
        return quantity1;
    }

    public void setQuantity1(Integer quantity1) {
        this.quantity1 = quantity1;
    }

    public Integer getYimiao2Id() {
        return yimiao2Id;
    }

    public void setYimiao2Id(Integer yimiao2Id) {
        this.yimiao2Id = yimiao2Id;
    }

    public String getCangku2() {
        return cangku2;
    }

    public void setCangku2(String cangku2) {
        this.cangku2 = cangku2 == null ? null : cangku2.trim();
    }

    public Integer getQuantity2() {
        return quantity2;
    }

    public void setQuantity2(Integer quantity2) {
        this.quantity2 = quantity2;
    }

    public Integer getDanjuleixingId() {
        return danjuleixingId;
    }

    public void setDanjuleixingId(Integer danjuleixingId) {
        this.danjuleixingId = danjuleixingId;
    }
}