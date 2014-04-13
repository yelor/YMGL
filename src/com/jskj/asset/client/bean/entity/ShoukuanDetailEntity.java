/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author haitao
 */
public class ShoukuanDetailEntity {
    
    Shoukuandantb shoukuandan;
    
    List<Yingfukuandanjutb> ysklist;

    public Shoukuandantb getShoukuandan() {
        return shoukuandan;
    }

    public List<Yingfukuandanjutb> getYsklist() {
        return ysklist;
    }

    public void setShoukuandan(Shoukuandantb shoukuandan) {
        this.shoukuandan = shoukuandan;
    }

    public void setYsklist(List<Yingfukuandanjutb> ysklist) {
        this.ysklist = ysklist;
    }
    
}
