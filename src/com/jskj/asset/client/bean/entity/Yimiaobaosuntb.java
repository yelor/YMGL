package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Yimiaobaosuntb {
    private Integer id;

    private String baosunId;

    private Integer kucunId;

    private Integer quantity;

    private String xiaohuiaddr;

    private Date xiaohuidate;

    private String xiaohuitype;

    private String xiaohuireason;

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
}