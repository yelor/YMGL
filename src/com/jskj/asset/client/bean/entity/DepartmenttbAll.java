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
public class DepartmenttbAll extends Departmenttb{
    
    private Usertb owner;

    /**
     * @return the owner
     */
    public Usertb getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Usertb owner) {
        this.owner = owner;
    }
    
}
