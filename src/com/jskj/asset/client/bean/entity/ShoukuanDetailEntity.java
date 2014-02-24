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
public class ShoukuanDetailEntity {
    
    Shoukuandantb shoukuandan;
    
    List<Yuandanliebiaotb> list;

    public Shoukuandantb getShoukuandan() {
        return shoukuandan;
    }

    public List<Yuandanliebiaotb> getList() {
        return list;
    }

    public void setShoukuandan(Shoukuandantb shoukuandan) {
        this.shoukuandan = shoukuandan;
    }

    public void setList(List<Yuandanliebiaotb> list) {
        this.list = list;
    }
    
}
