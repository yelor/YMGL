/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author 305027939
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepotALL extends Depot {

    private Usertb usertb;

    /**
     * @return the usertb
     */
    public Usertb getUsertb() {
        return usertb;
    }

    /**
     * @param usertb the usertb to set
     */
    public void setUsertb(Usertb usertb) {
        this.usertb = usertb;
    }
}
