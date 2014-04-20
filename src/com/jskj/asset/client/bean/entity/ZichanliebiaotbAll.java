/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author haitao
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZichanliebiaotbAll extends ZiChanLieBiaotb{
    
    private String zcName;

    public String getZcName() {
        return zcName;
    }

    public void setZcName(String zcName) {
        this.zcName = zcName;
    }
    
}
