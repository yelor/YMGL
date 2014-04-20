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
