/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author tt
 */
public class ShenQingDetailEntity {
    
    Shenqingdantb sqd;
    
    List<ZiChanLieBiaotb> zc;

    public Shenqingdantb getSqd() {
        return sqd;
    }

    public List<ZiChanLieBiaotb> getZc() {
        return zc;
    }

    public void setSqd(Shenqingdantb sqd) {
        this.sqd = sqd;
    }

    public void setZc(List<ZiChanLieBiaotb> zc) {
        this.zc = zc;
    }
    
}
