/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author haitao
 */
public class FukuanDetailEntity {
    
    Fukuandantb fukuandan;
    
    List<Yuandanliebiaotb> list;

    public Fukuandantb getFukuandan() {
        return fukuandan;
    }

    public List<Yuandanliebiaotb> getList() {
        return list;
    }

    public void setFukuandan(Fukuandantb fukuandan) {
        this.fukuandan = fukuandan;
    }

    public void setList(List<Yuandanliebiaotb> list) {
        this.list = list;
    }
    
}
