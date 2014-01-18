/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

/**
 *
 * @author 305027939
 */
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
