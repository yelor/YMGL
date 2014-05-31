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
public class XiaoshoutuihuoxiangdanEntity{
    
    private Supplier gongyingdanwei;

    private Kehudanweitb kehudanwei;

    private UsertbAll userAll;
    
    private Backsaletb backsaletb;
    
    private List<BacksaleyimiaoEntity> result;

    public Supplier getGongyingdanwei() {
        return gongyingdanwei;
    }

    public void setGongyingdanwei(Supplier gongyingdanwei) {
        this.gongyingdanwei = gongyingdanwei;
    }    

    public Kehudanweitb getKehudanwei() {
        return kehudanwei;
    }

    public void setKehudanwei(Kehudanweitb kehudanwei) {
        this.kehudanwei = kehudanwei;
    }

    public UsertbAll getUserAll() {
        return userAll;
    }

    public void setUserAll(UsertbAll userAll) {
        this.userAll = userAll;
    }

    public Backsaletb getBacksaletb() {
        return backsaletb;
    }

    public void setBacksaletb(Backsaletb backsaletb) {
        this.backsaletb = backsaletb;
    }

    public List<BacksaleyimiaoEntity> getResult() {
        return result;
    }

    public void setResult(List<BacksaleyimiaoEntity> result) {
        this.result = result;
    }

    

}
