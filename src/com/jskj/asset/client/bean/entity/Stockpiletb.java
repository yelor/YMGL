package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stockpiletb {
    private Integer stockpileId;

    private Date stockpileDate;

    private Integer deportId;

    private Integer yimiaoId;

    private Date youxiaodate;

    private String pihao;

    private String piqianfano;

    private String source;

    private String jinkoutongguanno;

    private Integer stockpileQuantity;

    private Float stockpilePrice;

    private Float stockpileTotalprice;

    private Float yimiaoyushoujia;

    public Integer getStockpileId() {
        return stockpileId;
    }

    public void setStockpileId(Integer stockpileId) {
        this.stockpileId = stockpileId;
    }

    public Date getStockpileDate() {
        return stockpileDate;
    }

    public void setStockpileDate(Date stockpileDate) {
        this.stockpileDate = stockpileDate;
    }

    public Integer getDeportId() {
        return deportId;
    }

    public void setDeportId(Integer deportId) {
        this.deportId = deportId;
    }

    public Integer getYimiaoId() {
        return yimiaoId;
    }

    public void setYimiaoId(Integer yimiaoId) {
        this.yimiaoId = yimiaoId;
    }

    public Date getYouxiaodate() {
        return youxiaodate;
    }

    public void setYouxiaodate(Date youxiaodate) {
        this.youxiaodate = youxiaodate;
    }

    public String getPihao() {
        return pihao;
    }

    public void setPihao(String pihao) {
        this.pihao = pihao == null ? null : pihao.trim();
    }

    public String getPiqianfano() {
        return piqianfano;
    }

    public void setPiqianfano(String piqianfano) {
        this.piqianfano = piqianfano == null ? null : piqianfano.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getJinkoutongguanno() {
        return jinkoutongguanno;
    }

    public void setJinkoutongguanno(String jinkoutongguanno) {
        this.jinkoutongguanno = jinkoutongguanno == null ? null : jinkoutongguanno.trim();
    }

    public Integer getStockpileQuantity() {
        return stockpileQuantity;
    }

    public void setStockpileQuantity(Integer stockpileQuantity) {
        this.stockpileQuantity = stockpileQuantity;
    }

    public Float getStockpilePrice() {
        return stockpilePrice;
    }

    public void setStockpilePrice(Float stockpilePrice) {
        this.stockpilePrice = stockpilePrice;
    }

    public Float getStockpileTotalprice() {
        return stockpileTotalprice;
    }

    public void setStockpileTotalprice(Float stockpileTotalprice) {
        this.stockpileTotalprice = stockpileTotalprice;
    }

    public Float getYimiaoyushoujia() {
        return yimiaoyushoujia;
    }

    public void setYimiaoyushoujia(Float yimiaoyushoujia) {
        this.yimiaoyushoujia = yimiaoyushoujia;
    }
}