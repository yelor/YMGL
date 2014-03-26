package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YiMiaotb {
    private Integer yimiaoId;

    private String yimiaoName;

    private String yimiaoType;

    private String yimiaoGuige;

    private String yimiaoJixing;

    private Float yimiaoJiliang;

    private String jiliangdanwei;

    private String yimiaoShengchanqiye;

    private String yimiaoPizhunwenhao;

    private String unitId;

    private Float chengbenjia;

    private Float yimiaoYushoujia;

    private String yimiaoPicture;

    private Integer yimiaoStockup;

    private Integer yimiaoStockdown;

    private String yimiaoFuzhuunit;

    private Integer yimiaoHuansuanlv;

    private String yimiaoRemark;

    private String zujima;

    public Integer getYimiaoId() {
        return yimiaoId;
    }

    public void setYimiaoId(Integer yimiaoId) {
        this.yimiaoId = yimiaoId;
    }

    public String getYimiaoName() {
        return yimiaoName;
    }

    public void setYimiaoName(String yimiaoName) {
        this.yimiaoName = yimiaoName == null ? null : yimiaoName.trim();
    }

    public String getYimiaoType() {
        return yimiaoType;
    }

    public void setYimiaoType(String yimiaoType) {
        this.yimiaoType = yimiaoType == null ? null : yimiaoType.trim();
    }

    public String getYimiaoGuige() {
        return yimiaoGuige;
    }

    public void setYimiaoGuige(String yimiaoGuige) {
        this.yimiaoGuige = yimiaoGuige == null ? null : yimiaoGuige.trim();
    }

    public String getYimiaoJixing() {
        return yimiaoJixing;
    }

    public void setYimiaoJixing(String yimiaoJixing) {
        this.yimiaoJixing = yimiaoJixing == null ? null : yimiaoJixing.trim();
    }

    public Float getYimiaoJiliang() {
        return yimiaoJiliang;
    }

    public void setYimiaoJiliang(Float yimiaoJiliang) {
        this.yimiaoJiliang = yimiaoJiliang;
    }

    public String getJiliangdanwei() {
        return jiliangdanwei;
    }

    public void setJiliangdanwei(String jiliangdanwei) {
        this.jiliangdanwei = jiliangdanwei == null ? null : jiliangdanwei.trim();
    }

    public String getYimiaoShengchanqiye() {
        return yimiaoShengchanqiye;
    }

    public void setYimiaoShengchanqiye(String yimiaoShengchanqiye) {
        this.yimiaoShengchanqiye = yimiaoShengchanqiye == null ? null : yimiaoShengchanqiye.trim();
    }

    public String getYimiaoPizhunwenhao() {
        return yimiaoPizhunwenhao;
    }

    public void setYimiaoPizhunwenhao(String yimiaoPizhunwenhao) {
        this.yimiaoPizhunwenhao = yimiaoPizhunwenhao == null ? null : yimiaoPizhunwenhao.trim();
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public Float getChengbenjia() {
        return chengbenjia;
    }

    public void setChengbenjia(Float chengbenjia) {
        this.chengbenjia = chengbenjia;
    }

    public Float getYimiaoYushoujia() {
        return yimiaoYushoujia;
    }

    public void setYimiaoYushoujia(Float yimiaoYushoujia) {
        this.yimiaoYushoujia = yimiaoYushoujia;
    }

    public String getYimiaoPicture() {
        return yimiaoPicture;
    }

    public void setYimiaoPicture(String yimiaoPicture) {
        this.yimiaoPicture = yimiaoPicture == null ? null : yimiaoPicture.trim();
    }

    public Integer getYimiaoStockup() {
        return yimiaoStockup;
    }

    public void setYimiaoStockup(Integer yimiaoStockup) {
        this.yimiaoStockup = yimiaoStockup;
    }

    public Integer getYimiaoStockdown() {
        return yimiaoStockdown;
    }

    public void setYimiaoStockdown(Integer yimiaoStockdown) {
        this.yimiaoStockdown = yimiaoStockdown;
    }

    public String getYimiaoFuzhuunit() {
        return yimiaoFuzhuunit;
    }

    public void setYimiaoFuzhuunit(String yimiaoFuzhuunit) {
        this.yimiaoFuzhuunit = yimiaoFuzhuunit == null ? null : yimiaoFuzhuunit.trim();
    }

    public Integer getYimiaoHuansuanlv() {
        return yimiaoHuansuanlv;
    }

    public void setYimiaoHuansuanlv(Integer yimiaoHuansuanlv) {
        this.yimiaoHuansuanlv = yimiaoHuansuanlv;
    }

    public String getYimiaoRemark() {
        return yimiaoRemark;
    }

    public void setYimiaoRemark(String yimiaoRemark) {
        this.yimiaoRemark = yimiaoRemark == null ? null : yimiaoRemark.trim();
    }

    public String getZujima() {
        return zujima;
    }

    public void setZujima(String zujima) {
        this.zujima = zujima == null ? null : zujima.trim();
    }
}