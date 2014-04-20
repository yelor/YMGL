/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author haitao
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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
