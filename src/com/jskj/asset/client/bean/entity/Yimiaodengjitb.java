package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Yimiaodengjitb {
    private Integer ymdjId;

    private Integer yimiaoId;

    private String pizhunwenhao;

    private Date youxiaoqi;

    private Integer quantity;

    private Double jinjia;

    private Integer piqianfahegezhenno;

    private String source;

    private Integer tongguandanno;

    public Integer getYmdjId() {
        return ymdjId;
    }

    public void setYmdjId(Integer ymdjId) {
        this.ymdjId = ymdjId;
    }

    public Integer getYimiaoId() {
        return yimiaoId;
    }

    public void setYimiaoId(Integer yimiaoId) {
        this.yimiaoId = yimiaoId;
    }

    public String getPizhunwenhao() {
        return pizhunwenhao;
    }

    public void setPizhunwenhao(String pizhunwenhao) {
        this.pizhunwenhao = pizhunwenhao == null ? null : pizhunwenhao.trim();
    }

    public Date getYouxiaoqi() {
        return youxiaoqi;
    }

    public void setYouxiaoqi(Date youxiaoqi) {
        this.youxiaoqi = youxiaoqi;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getJinjia() {
        return jinjia;
    }

    public void setJinjia(Double jinjia) {
        this.jinjia = jinjia;
    }

    public Integer getPiqianfahegezhenno() {
        return piqianfahegezhenno;
    }

    public void setPiqianfahegezhenno(Integer piqianfahegezhenno) {
        this.piqianfahegezhenno = piqianfahegezhenno;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Integer getTongguandanno() {
        return tongguandanno;
    }

    public void setTongguandanno(Integer tongguandanno) {
        this.tongguandanno = tongguandanno;
    }
}