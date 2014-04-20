/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author huiqi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YimiaochurukudantbFindEntity {
    private YiMiaotb yimiao;
    
    private int count;
    
    private  List<Yimiaoyanshou_detail_tb> result;

    public YiMiaotb getYimiao() {
        return yimiao;
    }

    public void setYimiao(YiMiaotb yimiao) {
        this.yimiao = yimiao;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Yimiaoyanshou_detail_tb> getResult() {
        return result;
    }

    public void setResult(List<Yimiaoyanshou_detail_tb> result) {
        this.result = result;
    }
    
    
}
