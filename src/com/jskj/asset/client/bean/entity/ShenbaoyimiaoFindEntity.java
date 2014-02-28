/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.entity;

import com.jskj.asset.client.bean.entity.Shenqingdantb;
import com.jskj.asset.client.bean.entity.Yimiaoshenqingdantb;
import java.util.List;

/**
 *
 * @author huiqi
 */
public class ShenbaoyimiaoFindEntity {

    private int count;

    private List<ShenbaoyimiaoEntity> shenbaoyimiaoEntity;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ShenbaoyimiaoEntity> getShenbaoyimiaoEntity() {
        return shenbaoyimiaoEntity;
    }

    public void setShenbaoyimiaoEntity(List<ShenbaoyimiaoEntity> shenbaoyimiaoEntity) {
        this.shenbaoyimiaoEntity = shenbaoyimiaoEntity;
    }

}
