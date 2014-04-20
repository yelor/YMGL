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
public class UsertbAll extends Usertb{
    
    private Departmenttb department;

    /**
     * @return the department
     */
    public Departmenttb getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(Departmenttb department) {
        this.department = department;
    }
    
}
