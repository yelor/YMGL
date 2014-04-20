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
