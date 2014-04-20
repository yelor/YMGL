/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author huiqi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaleyimiaoEntity{
    
    private YimiaoAll yimiaoAll;
    
    private Stockpiletb stockpile;
    
    private Saletb saletb;
    
    private Sale_detail_tb sale_detail_tb;

    private Kehudanweitb kehudanwei;
    
    public YimiaoAll getYimiaoAll() {
        return yimiaoAll;
    }

    public void setYimiaoAll(YimiaoAll yimiaoAll) {
        this.yimiaoAll = yimiaoAll;
    }

    public Stockpiletb getStockpile() {
        return stockpile;
    }

    public void setStockpile(Stockpiletb stockpile) {
        this.stockpile = stockpile;
    }

    public Saletb getSaletb() {
        return saletb;
    }

    public void setSaletb(Saletb saletb) {
        this.saletb = saletb;
    }

    public Sale_detail_tb getSale_detail_tb() {
        return sale_detail_tb;
    }

    public void setSale_detail_tb(Sale_detail_tb sale_detail_tb) {
        this.sale_detail_tb = sale_detail_tb;
    }

    public Kehudanweitb getKehudanwei() {
        return kehudanwei;
    }

    public void setKehudanwei(Kehudanweitb kehudanwei) {
        this.kehudanwei = kehudanwei;
    }
    
    
    
}
