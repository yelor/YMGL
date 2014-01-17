package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author tt
 */
public class CaiGouShenQingFindEntity {
    
    private int count;
    
    private  List<CaigoushenqingDetailEntity> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CaigoushenqingDetailEntity> getResult() {
        return result;
    }

    public void setResult(List<CaigoushenqingDetailEntity> result) {
        this.result = result;
    }
    
}
