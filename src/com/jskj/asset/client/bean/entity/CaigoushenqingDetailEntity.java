/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tt
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaigoushenqingDetailEntity {
    
    private String cgsqId;
    
    private String danjuleixing;
    
    private String jingbanren;
    
    private Date shenqingdanDate;
    
    private String shenqingdanRemark;
    
    private String supplier;
    
    private Float danjujine;
    
    private String dept;
    
    private int isCompleted;

    private List<ZichanliebiaoDetailEntity> result;
    
    private List<YihaopinliebiaoEntity> yhplist;

    public String getCgsqId() {
        return cgsqId;
    }

    public String getDanjuleixing() {
        return danjuleixing;
    }

    public String getJingbanren() {
        return jingbanren;
    }

    public Date getShenqingdanDate() {
        return shenqingdanDate;
    }

    public String getShenqingdanRemark() {
        return shenqingdanRemark;
    }

    public void setCgsqId(String cgsqId) {
        this.cgsqId = cgsqId;
    }

    public void setDanjuleixing(String danjuleixing) {
        this.danjuleixing = danjuleixing;
    }

    public void setJingbanren(String jingbanren) {
        this.jingbanren = jingbanren;
    }
    
    public void setShenqingdanDate(Date shenqingdanDate) {
        this.shenqingdanDate = shenqingdanDate;
    }

    public void setShenqingdanRemark(String shenqingdanRemark) {
        this.shenqingdanRemark = shenqingdanRemark;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public List<ZichanliebiaoDetailEntity> getResult() {
        return result;
    }

    public void setResult(List<ZichanliebiaoDetailEntity> result) {
        this.result = result;
    }
    
    public Float getDanjujine() {
        return danjujine;
    }

    public void setDanjujine(Float danjujine) {
        this.danjujine = danjujine;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public List<YihaopinliebiaoEntity> getYhplist() {
        return yhplist;
    }

    public void setYhplist(List<YihaopinliebiaoEntity> yhplist) {
        this.yhplist = yhplist;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }
    
}
