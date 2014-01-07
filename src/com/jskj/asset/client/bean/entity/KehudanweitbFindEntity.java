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
    
    private  List<Kehudanweitb> kehudanweis;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Kehudanweitb> getKehudanweis() {
        return kehudanweis;
    }

    public void setKehudanweis(List<Kehudanweitb> kehudanweis) {
        this.kehudanweis = kehudanweis;
    }
    
    
    
}
