/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author haitao
 */
public class QitafukuanDetailEntity {
    
    Qitafukuandantb fukuandan;
    
    List<Qitafukuanliebiaotb> list;

    public Qitafukuandantb getFukuandan() {
        return fukuandan;
    }

    public List<Qitafukuanliebiaotb> getList() {
        return list;
    }

    public void setFukuandan(Qitafukuandantb fukuandan) {
        this.fukuandan = fukuandan;
    }

    public void setList(List<Qitafukuanliebiaotb> list) {
        this.list = list;
    }

}
