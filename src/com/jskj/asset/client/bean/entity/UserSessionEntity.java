/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author 305027939
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSessionEntity implements java.io.Serializable{

    private Usertb usertb;

    private Departmenttb department;

    private List<UserRolesKey> roles;
    
    private String message;

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

    /**
     * @return the roles
     */
    public List<UserRolesKey> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<UserRolesKey> roles) {
        this.roles = roles;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    

}
