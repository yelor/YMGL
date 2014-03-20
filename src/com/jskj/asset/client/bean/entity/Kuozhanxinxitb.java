package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Kuozhanxinxitb {
    private Integer kzId;

    private Integer zhuzcId;

    private String kzName;

    private String kzXinghao;

    private String kzPinpai;

    private String kzXuliehao;

    private String kzQita;

    public Integer getKzId() {
        return kzId;
    }

    public void setKzId(Integer kzId) {
        this.kzId = kzId;
    }

    public Integer getZhuzcId() {
        return zhuzcId;
    }

    public void setZhuzcId(Integer zhuzcId) {
        this.zhuzcId = zhuzcId;
    }

    public String getKzName() {
        return kzName;
    }

    public void setKzName(String kzName) {
        this.kzName = kzName == null ? null : kzName.trim();
    }

    public String getKzXinghao() {
        return kzXinghao;
    }

    public void setKzXinghao(String kzXinghao) {
        this.kzXinghao = kzXinghao == null ? null : kzXinghao.trim();
    }

    public String getKzPinpai() {
        return kzPinpai;
    }

    public void setKzPinpai(String kzPinpai) {
        this.kzPinpai = kzPinpai == null ? null : kzPinpai.trim();
    }

    public String getKzXuliehao() {
        return kzXuliehao;
    }

    public void setKzXuliehao(String kzXuliehao) {
        this.kzXuliehao = kzXuliehao == null ? null : kzXuliehao.trim();
    }

    public String getKzQita() {
        return kzQita;
    }

    public void setKzQita(String kzQita) {
        this.kzQita = kzQita == null ? null : kzQita.trim();
    }
}