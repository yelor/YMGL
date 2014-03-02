/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author haitao
 */
public class LingyongzichanDetailEntity extends Lingyongshenqingdantb{
    
    private String shenqingren;
    
    private String dept;
    
    private List<ZichanliebiaoDetailEntity> zclist;

    public String getShenqingren() {
        return shenqingren;
    }

    public List<ZichanliebiaoDetailEntity> getZclist() {
        return zclist;
    }

    public void setShenqingren(String shenqingren) {
        this.shenqingren = shenqingren;
    }

    public void setZclist(List<ZichanliebiaoDetailEntity> zclist) {
        this.zclist = zclist;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
    
}
