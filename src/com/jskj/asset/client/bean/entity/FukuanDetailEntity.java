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
    
    List<Yingfukuandanjutb> yfklist;

    public Fukuandantb getFukuandan() {
        return fukuandan;
    }

    public void setFukuandan(Fukuandantb fukuandan) {
        this.fukuandan = fukuandan;
    }

    public List<Yingfukuandanjutb> getYfklist() {
        return yfklist;
    }

    public void setYfklist(List<Yingfukuandanjutb> yfklist) {
        this.yfklist = yfklist;
    }
    
}
