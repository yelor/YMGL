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
    
    private String barcode;
    
    private String didian;
    
    private String xuliehao;

    public String getZcName() {
        return zcName;
    }

    public void setZcName(String zcName) {
        this.zcName = zcName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDidian() {
        return didian;
    }

    public String getXuliehao() {
        return xuliehao;
    }

    public void setDidian(String didian) {
        this.didian = didian;
    }

    public void setXuliehao(String xuliehao) {
        this.xuliehao = xuliehao;
    }
    
}
