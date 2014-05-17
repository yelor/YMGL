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


    
}
