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
public class BacksaleyimiaoEntity{
    
    private YimiaoAll yimiaoAll;
    
    private Stockpiletb stockpile;
        
    private Backsale_detail_tb backsale_detail_tb;

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

    public Backsale_detail_tb getBacksale_detail_tb() {
        return backsale_detail_tb;
    }

    public void setBacksale_detail_tb(Backsale_detail_tb backsale_detail_tb) {
        this.backsale_detail_tb = backsale_detail_tb;
    }

    public Kehudanweitb getKehudanwei() {
        return kehudanwei;
    }

    public void setKehudanwei(Kehudanweitb kehudanwei) {
        this.kehudanwei = kehudanwei;
    }
    
    
    
}
