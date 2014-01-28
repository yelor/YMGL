/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.report;

import com.jskj.asset.client.bean.entity.YiMiaotb;
import com.jskj.asset.client.bean.entity.Yimiaoshenpiliucheng;
import com.jskj.asset.client.bean.entity.Yimiaoshenqingdantb;


/**
 *
 * @author 305027939
 */
public class CaiGouYimiaoReport extends Yimiaoshenqingdantb {

    private YiMiaotb yimiaotb;
    private Yimiaoshenpiliucheng shenpiliuchengtb;

    /**
     * @return the yimiaotb
     */
    public YiMiaotb getYimiaotb() {
        return yimiaotb;
    }

    /**
     * @param yimiaotb the yimiaotb to set
     */
    public void setYimiaotb(YiMiaotb yimiaotb) {
        this.yimiaotb = yimiaotb;
    }

    /**
     * @return the shenpiliuchengtb
     */
    public Yimiaoshenpiliucheng getShenpiliuchengtb() {
        return shenpiliuchengtb;
    }

    /**
     * @param shenpiliuchengtb the shenpiliuchengtb to set
     */
    public void setShenpiliuchengtb(Yimiaoshenpiliucheng shenpiliuchengtb) {
        this.shenpiliuchengtb = shenpiliuchengtb;
    }

}
