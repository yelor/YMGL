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
public class ChukuyimiaoFindEntity {

    private int count;

    private List<ChukuyimiaoEntity> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ChukuyimiaoEntity> getResult() {
        return result;
    }

    public void setResult(List<ChukuyimiaoEntity> result) {
        this.result = result;
    }

}
