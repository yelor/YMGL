package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author tt
 */
public class CaiGouShenQingFindEntity extends ZiChanCaiGouShenQing{
    
    private int count;
    
    private  List<ZiChanCaiGouShenQing> shenqing;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ZiChanCaiGouShenQing> getShenQing() {
        return shenqing;
    }

    public void setShenQing(List<ZiChanCaiGouShenQing> shenqing) {
        this.shenqing = shenqing;
    }
    
}
