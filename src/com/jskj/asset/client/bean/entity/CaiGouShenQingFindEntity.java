package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author tt
 */
public class CaiGouShenQingFindEntity extends ZiChanCaiGouShenQing{
    
    private int count;
    
    private  List<ZiChanCaiGouShenQing> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ZiChanCaiGouShenQing> getResult() {
        return result;
    }

    public void setResult(List<ZiChanCaiGouShenQing> result) {
        this.result = result;
    }
    
}
