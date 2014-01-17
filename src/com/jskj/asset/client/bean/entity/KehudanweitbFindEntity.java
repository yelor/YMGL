/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author huiqi
 */
public class KehudanweitbFindEntity extends Kehudanweitb{
     private int count;
    
    private  List<Kehudanweitb> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Kehudanweitb> getResult() {
        return result;
    }

    public void setResult(List<Kehudanweitb> result) {
        this.result = result;
    }
    
    
    
}
