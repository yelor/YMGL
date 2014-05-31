package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Yimiaobaosuntb {
    private Integer id;

    private String baosunId;

    private Date baosunDate;

    private Integer yimiaoId;

    private Integer kucunId;

    private Integer quantity;

    private Float price;

    private Float totalprice;

    private Integer leijikucun;

    private String xiaohuiaddr;

    private Date xiaohuidate;

    private String xiaohuitype;

    private String xiaohuireason;

    private Integer isCompleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaosunId() {
        return baosunId;
    }

    public void setBaosunId(String baosunId) {
        this.baosunId = baosunId == null ? null : baosunId.trim();
    }

    public Date getBaosunDate() {
        return baosunDate;
    }

    public void setBaosunDate(Date baosunDate) {
        this.baosunDate = baosunDate;
    }

    public Integer getYimiaoId() {
        return yimiaoId;
    }

    public void setYimiaoId(Integer yimiaoId) {
        this.yimiaoId = yimiaoId;
    }

    public Integer getKucunId() {
        return kucunId;
    }

    public void setKucunId(Integer kucunId) {
        this.kucunId = kucunId;
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

    public Float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Float totalprice) {
        this.totalprice = totalprice;
    }

    public Integer getLeijikucun() {
        return leijikucun;
    }

    public void setLeijikucun(Integer leijikucun) {
        this.leijikucun = leijikucun;
    }

    public String getXiaohuiaddr() {
        return xiaohuiaddr;
    }

    public void setXiaohuiaddr(String xiaohuiaddr) {
        this.xiaohuiaddr = xiaohuiaddr == null ? null : xiaohuiaddr.trim();
    }

    public Date getXiaohuidate() {
        return xiaohuidate;
    }

    public void setXiaohuidate(Date xiaohuidate) {
        this.xiaohuidate = xiaohuidate;
    }

    public String getXiaohuitype() {
        return xiaohuitype;
    }

    public void setXiaohuitype(String xiaohuitype) {
        this.xiaohuitype = xiaohuitype == null ? null : xiaohuitype.trim();
    }

    public String getXiaohuireason() {
        return xiaohuireason;
    }

    public void setXiaohuireason(String xiaohuireason) {
        this.xiaohuireason = xiaohuireason == null ? null : xiaohuireason.trim();
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }
}