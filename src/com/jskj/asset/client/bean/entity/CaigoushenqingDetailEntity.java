/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.bean.entity;

import java.util.Date;
import java.util.List;

/**
 *
 * @author tt
 */
public class CaigoushenqingDetailEntity {
    
    private String cgsqId;
    
    private String danjuleixing;
    
    private String jingbanren;
    
    private Date shenqingdanDate;
    
    private String shenqingdanRemark;
    
    private String supplier;
    
    private String checkId1;

    private String checkId2;

    private String checkId3;
    
    private String checkId4;
    
    private List<ZichanliebiaoDetailEntity> zclist;

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

    public String getCheckId1() {
        return checkId1;
    }

    public String getCheckId2() {
        return checkId2;
    }

    public String getCheckId3() {
        return checkId3;
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

    public void setCheckId1(String checkId1) {
        this.checkId1 = checkId1;
    }

    public void setCheckId2(String checkId2) {
        this.checkId2 = checkId2;
    }

    public void setCheckId3(String checkId3) {
        this.checkId3 = checkId3;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public List<ZichanliebiaoDetailEntity> getZclist() {
        return zclist;
    }

    public void setZclist(List<ZichanliebiaoDetailEntity> zclist) {
        this.zclist = zclist;
    }

    public String getCheckId4() {
        return checkId4;
    }

    public void setCheckId4(String checkId4) {
        this.checkId4 = checkId4;
    }
    
}
