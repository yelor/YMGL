/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author tt
 */
public class LingyongshenqingDetailEntity {
    
    Lingyongshenqingdantb sqd;
    
    List<ZiChanLieBiaotb> zc;

    public Lingyongshenqingdantb getSqd() {
        return sqd;
    }

    public List<ZiChanLieBiaotb> getZc() {
        return zc;
    }

    public void setSqd(Lingyongshenqingdantb sqd) {
        this.sqd = sqd;
    }

    public void setZc(List<ZiChanLieBiaotb> zc) {
        this.zc = zc;
    }
    
}
