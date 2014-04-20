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
public class YimiaobaosunDetailEntity{

    private YimiaoAll yimiaoAll;
    
    private Stockpiletb stockpileYimiao;
    
    private Yimiaobaosuntb yimiaobaosuntb;

    public YimiaoAll getYimiaoAll() {
        return yimiaoAll;
    }

    public void setYimiaoAll(YimiaoAll yimiaoAll) {
        this.yimiaoAll = yimiaoAll;
    }

    public Stockpiletb getStockpileYimiao() {
        return stockpileYimiao;
    }

    public void setStockpileYimiao(Stockpiletb stockpileYimiao) {
        this.stockpileYimiao = stockpileYimiao;
    }

    public Yimiaobaosuntb getYimiaobaosuntb() {
        return yimiaobaosuntb;
    }

    public void setYimiaobaosuntb(Yimiaobaosuntb yimiaobaosuntb) {
        this.yimiaobaosuntb = yimiaobaosuntb;
    }

   

}
