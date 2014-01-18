package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author tt
 */
public class YimiaoShenpiFindEntity extends Yimiaoshenpiliucheng{
    
    private int count;
    
    private  List<Yimiaoshenpiliucheng> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Yimiaoshenpiliucheng> getResult() {
        return result;
    }

    public void setResult(List<Yimiaoshenpiliucheng> result) {
        this.result = result;
    }
    
}
