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
public class ChukudanDetailEntity {
    
    Zichanchukudantb chukudan;
    
    List<ZiChanLieBiaotb> zc;

    public Zichanchukudantb getChukudan() {
        return chukudan;
    }

    public List<ZiChanLieBiaotb> getZc() {
        return zc;
    }

    public void setChukudan(Zichanchukudantb chukudan) {
        this.chukudan = chukudan;
    }

    public void setZc(List<ZiChanLieBiaotb> zc) {
        this.zc = zc;
    }
    
}
