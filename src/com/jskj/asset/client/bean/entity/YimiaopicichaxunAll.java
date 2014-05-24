/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 *
 * @author 305027939
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YimiaopicichaxunAll extends Stockpiletb{
    
    private YiMiaotb yimiao;
    
    private String jiliang;
    
//    单位关系：和辅助单位的关系
    private String danweiguanxi;
    
//    这里的辅助单位是：数量+辅助单位
    private String fuzhudanwei;

    public YiMiaotb getYimiao() {
        return yimiao;
    }

    public void setYimiao(YiMiaotb yimiao) {
        this.yimiao = yimiao;
    }

    public String getJiliang() {
        return jiliang;
    }

    public void setJiliang(String jiliang) {
        this.jiliang = jiliang;
    }

    public String getDanweiguanxi() {
        return danweiguanxi;
    }

    public void setDanweiguanxi(String danweiguanxi) {
        this.danweiguanxi = danweiguanxi;
    }

    public String getFuzhudanwei() {
        return fuzhudanwei;
    }

    public void setFuzhudanwei(String fuzhudanwei) {
        this.fuzhudanwei = fuzhudanwei;
    }
  
}
