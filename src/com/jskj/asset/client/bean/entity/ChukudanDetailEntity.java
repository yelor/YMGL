/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author haitao
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChukudanDetailEntity {
    
    Zichanchukudantb chukudan;
    
    List<ZichanliebiaotbAll> zc;

    public Zichanchukudantb getChukudan() {
        return chukudan;
    }

    public List<ZichanliebiaotbAll> getZc() {
        return zc;
    }

    public void setChukudan(Zichanchukudantb chukudan) {
        this.chukudan = chukudan;
    }

    public void setZc(List<ZichanliebiaotbAll> zc) {
        this.zc = zc;
    }
    
}
