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
public class QitafukuanDetailEntity {
    
    Fukuandantb fukuandan;
    
    List<Qitafukuanliebiaotb> list;

    public Fukuandantb getFukuandan() {
        return fukuandan;
    }

    public List<Qitafukuanliebiaotb> getList() {
        return list;
    }

    public void setFukuandan(Fukuandantb fukuandan) {
        this.fukuandan = fukuandan;
    }

    public void setList(List<Qitafukuanliebiaotb> list) {
        this.list = list;
    }

}
