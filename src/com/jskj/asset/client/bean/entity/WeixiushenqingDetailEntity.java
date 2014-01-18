/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author tt
 */
public class WeixiushenqingDetailEntity {
    
    private Weixiushenqingdantb wxsq;
    
    private List<ZiChanLieBiaotb> zc;

    public Weixiushenqingdantb getWxsq() {
        return wxsq;
    }

    public List<ZiChanLieBiaotb> getZc() {
        return zc;
    }

    public void setWxsq(Weixiushenqingdantb wxsq) {
        this.wxsq = wxsq;
    }

    public void setZc(List<ZiChanLieBiaotb> zc) {
        this.zc = zc;
    }
    
}
