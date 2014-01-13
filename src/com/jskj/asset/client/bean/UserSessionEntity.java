/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean;


import com.jskj.asset.client.bean.entity.Departmenttb;
import com.jskj.asset.client.bean.entity.UserRolesKey;
import com.jskj.asset.client.bean.entity.Usertb;
import java.util.List;

/**
 *
 * @author 305027939
 */
public class UserSessionEntity {

    private Usertb usertb;

    private Departmenttb department;

    private List<UserRolesKey> roles;

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
    
    

}
