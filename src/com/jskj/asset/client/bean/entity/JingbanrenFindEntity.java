/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.assets.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author huiqi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JingbanrenFindEntity {
    private int count;
    
    private  List<JingbanrenEntity> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<JingbanrenEntity> getResult() {
        return result;
    }

    public void setResult(List<JingbanrenEntity> result) {
        this.result = result;
    }    
    
}
