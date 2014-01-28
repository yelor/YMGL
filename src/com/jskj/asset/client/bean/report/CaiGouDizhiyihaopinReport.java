/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.report;

import com.jskj.asset.client.bean.entity.Dizhiyihaopin;
import com.jskj.asset.client.bean.entity.ZiChanCaiGouShenQing;
import com.jskj.asset.client.bean.entity.ZiChanLieBiaotb;



/**
 *
 * @author 305027939
 */
public class CaiGouDizhiyihaopinReport extends ZiChanLieBiaotb{
    private Dizhiyihaopin dizhiyihaopintb;
    
    private ZiChanCaiGouShenQing ziChanCaiGouShenQing;

   

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

    /**
     * @return the dizhiyihaopintb
     */
    public Dizhiyihaopin getDizhiyihaopintb() {
        return dizhiyihaopintb;
    }

    /**
     * @param dizhiyihaopintb the dizhiyihaopintb to set
     */
    public void setDizhiyihaopintb(Dizhiyihaopin dizhiyihaopintb) {
        this.dizhiyihaopintb = dizhiyihaopintb;
    }
}
