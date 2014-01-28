/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.report;

import com.jskj.asset.client.bean.entity.Danjuleixingtb;
import com.jskj.asset.client.bean.entity.Shenqingdantb;
import com.jskj.asset.client.bean.entity.Supplier;
import com.jskj.asset.client.bean.entity.UsertbAll;



/**
 *
 * @author 305027939
 */
public class CaigouReport extends Shenqingdantb {

    private Supplier suppliertb;

    private UsertbAll usertball;

    private Danjuleixingtb danjuleixing;
    
    /**
     * @return the suppliertb
     */
    public Supplier getSuppliertb() {
        return suppliertb;
    }

    /**
     * @param suppliertb the suppliertb to set
     */
    public void setSuppliertb(Supplier suppliertb) {
        this.suppliertb = suppliertb;
    }

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
     * @return the danjuleixing
     */
    public Danjuleixingtb getDanjuleixing() {
        return danjuleixing;
    }

    /**
     * @param danjuleixing the danjuleixing to set
     */
    public void setDanjuleixing(Danjuleixingtb danjuleixing) {
        this.danjuleixing = danjuleixing;
    }

}
