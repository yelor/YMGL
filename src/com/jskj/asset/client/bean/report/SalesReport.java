/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jskj.asset.client.bean.entity.Kehudanweitb;
import com.jskj.asset.client.bean.entity.Saletb;
import com.jskj.asset.client.bean.entity.UsertbAll;

/**
 *
 * @author 305027939
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesReport extends Saletb {

    private Kehudanweitb kehudanwei;

    private UsertbAll usertball;

    private String danjuleixing;
    
  

    /**
     * @return the usertball
     */
    public UsertbAll getUsertball() {
        return usertball;
    }

    /**
     * @param usertball the usertball to set
     */
    public void setUsertball(UsertbAll usertball) {
        this.usertball = usertball;
    }

    /**
     * @return the kehudanwei
     */
    public Kehudanweitb getKehudanwei() {
        return kehudanwei;
    }

    /**
     * @param kehudanwei the kehudanwei to set
     */
    public void setKehudanwei(Kehudanweitb kehudanwei) {
        this.kehudanwei = kehudanwei;
    }

    /**
     * @return the danjuleixing
     */
    public String getDanjuleixing() {
        return danjuleixing;
    }

    /**
     * @param danjuleixing the danjuleixing to set
     */
    public void setDanjuleixing(String danjuleixing) {
        this.danjuleixing = danjuleixing;
    }

}
