/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author haitao
 */
public class RukudanDetailEntity {
    
    Zichanrukudantb rukudan;
    
    List<ZiChanLieBiaotb> zc;

    public Zichanrukudantb getRukudan() {
        return rukudan;
    }

    public List<ZiChanLieBiaotb> getZc() {
        return zc;
    }

    public void setRukudan(Zichanrukudantb rukudan) {
        this.rukudan = rukudan;
    }

    public void setZc(List<ZiChanLieBiaotb> zc) {
        this.zc = zc;
    }
    
}
