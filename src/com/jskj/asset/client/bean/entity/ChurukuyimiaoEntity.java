/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author tt
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChurukuyimiaoEntity extends YiMiaotb{   
    
   private Yimiaodengjitb yimiaodengjitb;

    public Yimiaodengjitb getYimiaodengjitb() {
        return yimiaodengjitb;
    }

    public void setYimiaodengjitb(Yimiaodengjitb yimiaodengjitb) {
        this.yimiaodengjitb = yimiaodengjitb;
    }
   
}
