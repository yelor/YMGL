/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author tt
 */
public class DizhiyihaopinFindEntity {
    
    private int count;
    
    private  List<DizhiyihaopinAll> result;

    public int getCount() {
        return count;
    }

    public List<DizhiyihaopinAll> getResult() {
        return result;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setResult(List<DizhiyihaopinAll> result) {
        this.result = result;
    }
    
}
