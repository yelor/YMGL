package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Churukudantb {
    private String churukuId;

    private Date zhidandate;

    private Integer yimiaoId;

    private String source;

    private String tongguandanno;

    private Integer quantity;

    private String pihao;

    private Date youxiaoqi;

    private String piqianfahegeno;

    private Integer gongyingdanwei;

    private Integer jingbanren;

    private Integer zhidanren;

    public String getChurukuId() {
        return churukuId;
    }

    public void setChurukuId(String churukuId) {
        this.churukuId = churukuId == null ? null : churukuId.trim();
    }

    public Date getZhidandate() {
        return zhidandate;
    }

    public void setZhidandate(Date zhidandate) {
        this.zhidandate = zhidandate;
    }

    public Integer getYimiaoId() {
        return yimiaoId;
    }

    public void setYimiaoId(Integer yimiaoId) {
        this.yimiaoId = yimiaoId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public String getPiqianfahegeno() {
        return piqianfahegeno;
    }

    public void setPiqianfahegeno(String piqianfahegeno) {
        this.piqianfahegeno = piqianfahegeno == null ? null : piqianfahegeno.trim();
    }

    public Integer getGongyingdanwei() {
        return gongyingdanwei;
    }

    public void setGongyingdanwei(Integer gongyingdanwei) {
        this.gongyingdanwei = gongyingdanwei;
    }

    public Integer getJingbanren() {
        return jingbanren;
    }

    public void setJingbanren(Integer jingbanren) {
        this.jingbanren = jingbanren;
    }

    public Integer getZhidanren() {
        return zhidanren;
    }

    public void setZhidanren(Integer zhidanren) {
        this.zhidanren = zhidanren;
    }
}