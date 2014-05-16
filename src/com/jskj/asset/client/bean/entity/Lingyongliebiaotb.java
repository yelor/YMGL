package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Lingyongliebiaotb {
    private Integer lylbId;

    private String yuandanId;

    private Integer lyzcId;

    private String pihao;

    private Float price;

    private String fuzeren;

    private String depart;

    private String didian;

    private String xuliehao;

    private String barcode;

    public Integer getLylbId() {
        return lylbId;
    }

    public void setLylbId(Integer lylbId) {
        this.lylbId = lylbId;
    }

    public String getYuandanId() {
        return yuandanId;
    }

    public void setYuandanId(String yuandanId) {
        this.yuandanId = yuandanId == null ? null : yuandanId.trim();
    }

    public Integer getLyzcId() {
        return lyzcId;
    }

    public void setLyzcId(Integer lyzcId) {
        this.lyzcId = lyzcId;
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

    public String getFuzeren() {
        return fuzeren;
    }

    public void setFuzeren(String fuzeren) {
        this.fuzeren = fuzeren == null ? null : fuzeren.trim();
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart == null ? null : depart.trim();
    }

    public String getDidian() {
        return didian;
    }

    public void setDidian(String didian) {
        this.didian = didian == null ? null : didian.trim();
    }

    public String getXuliehao() {
        return xuliehao;
    }

    public void setXuliehao(String xuliehao) {
        this.xuliehao = xuliehao == null ? null : xuliehao.trim();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }
}