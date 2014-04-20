/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author tt
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaigoujihuaFindEntity {
    
    private int count;
    
    private  List<CaigoujihuaChaxunEntity> result;

    public int getCount() {
        return count;
    }

    public List<CaigoujihuaChaxunEntity> getResult() {
        return result;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setResult(List<CaigoujihuaChaxunEntity> result) {
        this.result = result;
    }
        
}
