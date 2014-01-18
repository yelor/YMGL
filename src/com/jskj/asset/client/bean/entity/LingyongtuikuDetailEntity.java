/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author tt
 */
public class LingyongtuikuDetailEntity {
    
    Lingyongtuikudantb sqd;
    
    List<ZiChanLieBiaotb> zc;

    public Lingyongtuikudantb getSqd() {
        return sqd;
    }

    public List<ZiChanLieBiaotb> getZc() {
        return zc;
    }

    public void setSqd(Lingyongtuikudantb sqd) {
        this.sqd = sqd;
    }

    public void setZc(List<ZiChanLieBiaotb> zc) {
        this.zc = zc;
    }

}
