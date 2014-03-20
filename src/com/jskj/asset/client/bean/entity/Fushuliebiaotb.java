package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Fushuliebiaotb {
    private Integer fsId;

    private Integer zhuzcId;

    private String fszcName;

    private String fszcXinghao;

    private String fszcPinpai;

    private String fszcXuliehao;

    private String fszcQita;

    public Integer getFsId() {
        return fsId;
    }

    public void setFsId(Integer fsId) {
        this.fsId = fsId;
    }

    public Integer getZhuzcId() {
        return zhuzcId;
    }

    public void setZhuzcId(Integer zhuzcId) {
        this.zhuzcId = zhuzcId;
    }

    public String getFszcName() {
        return fszcName;
    }

    public void setFszcName(String fszcName) {
        this.fszcName = fszcName == null ? null : fszcName.trim();
    }

    public String getFszcXinghao() {
        return fszcXinghao;
    }

    public void setFszcXinghao(String fszcXinghao) {
        this.fszcXinghao = fszcXinghao == null ? null : fszcXinghao.trim();
    }

    public String getFszcPinpai() {
        return fszcPinpai;
    }

    public void setFszcPinpai(String fszcPinpai) {
        this.fszcPinpai = fszcPinpai == null ? null : fszcPinpai.trim();
    }

    public String getFszcXuliehao() {
        return fszcXuliehao;
    }

    public void setFszcXuliehao(String fszcXuliehao) {
        this.fszcXuliehao = fszcXuliehao == null ? null : fszcXuliehao.trim();
    }

    public String getFszcQita() {
        return fszcQita;
    }

    public void setFszcQita(String fszcQita) {
        this.fszcQita = fszcQita == null ? null : fszcQita.trim();
    }
}