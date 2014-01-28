/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.report;

import com.jskj.asset.client.bean.entity.Gudingzichantb;
import com.jskj.asset.client.bean.entity.ZiChanCaiGouShenQing;
import com.jskj.asset.client.bean.entity.ZiChanLieBiaotb;



/**
 *
 * @author 305027939
 */
public class CaiGouZichanReport extends ZiChanLieBiaotb{
    private Gudingzichantb gudingzichantb;
    
    private ZiChanCaiGouShenQing ziChanCaiGouShenQing;

    /**
     * @return the gudingzichantb
     */
    public Gudingzichantb getGudingzichantb() {
        return gudingzichantb;
    }

    /**
     * @param gudingzichantb the gudingzichantb to set
     */
    public void setGudingzichantb(Gudingzichantb gudingzichantb) {
        this.gudingzichantb = gudingzichantb;
    }

    /**
     * @return the ziChanCaiGouShenQing
     */
    public ZiChanCaiGouShenQing getZiChanCaiGouShenQing() {
        return ziChanCaiGouShenQing;
    }

    /**
     * @param ziChanCaiGouShenQing the ziChanCaiGouShenQing to set
     */
    public void setZiChanCaiGouShenQing(ZiChanCaiGouShenQing ziChanCaiGouShenQing) {
        this.ziChanCaiGouShenQing = ziChanCaiGouShenQing;
    }
}
