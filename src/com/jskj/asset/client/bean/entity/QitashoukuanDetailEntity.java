/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author haitao
 */
public class QitashoukuanDetailEntity {
    
    Qitashoukuandantb fukuandan;
    
    List<Qitashoukuanliebiaotb> list;

    public Qitashoukuandantb getFukuandan() {
        return fukuandan;
    }

    public List<Qitashoukuanliebiaotb> getList() {
        return list;
    }

    public void setFukuandan(Qitashoukuandantb fukuandan) {
        this.fukuandan = fukuandan;
    }

    public void setList(List<Qitashoukuanliebiaotb> list) {
        this.list = list;
    }

}
