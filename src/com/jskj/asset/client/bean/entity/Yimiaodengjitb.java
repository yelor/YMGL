package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Yimiaodengjitb {
    private Integer ymdjId;

    private Integer yimiaoId;

    private String pihao;

    private Date youxiaoqi;

    private Integer quantity;

    private Float jinjia;

    private String piqianfahegezhenno;

    private String source;

    private String tongguandanno;

    private Integer xiangdanId;

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

    public String getPihao() {
        return pihao;
    }

    public void setPihao(String pihao) {
        this.pihao = pihao == null ? null : pihao.trim();
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

    public Float getJinjia() {
        return jinjia;
    }

    public void setJinjia(Float jinjia) {
        this.jinjia = jinjia;
    }

    public String getPiqianfahegezhenno() {
        return piqianfahegezhenno;
    }

    public void setPiqianfahegezhenno(String piqianfahegezhenno) {
        this.piqianfahegezhenno = piqianfahegezhenno == null ? null : piqianfahegezhenno.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getTongguandanno() {
        return tongguandanno;
    }

    public void setTongguandanno(String tongguandanno) {
        this.tongguandanno = tongguandanno == null ? null : tongguandanno.trim();
    }

    public Integer getXiangdanId() {
        return xiangdanId;
    }

    public void setXiangdanId(Integer xiangdanId) {
        this.xiangdanId = xiangdanId;
    }
}