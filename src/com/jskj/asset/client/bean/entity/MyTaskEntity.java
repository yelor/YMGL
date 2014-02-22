/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.Date;

/**
 *
 * @author 305027939
 */
public class MyTaskEntity {
    private String shenqingdanId;
    private Date submitDate;
    private String danjuleixing;
    private String owner;
    private String department;
    private String context;

    /**
     * @return the shenqingdanId
     */
    public String getShenqingdanId() {
        return shenqingdanId;
    }

    /**
     * @param shenqingdanId the shenqingdanId to set
     */
    public void setShenqingdanId(String shenqingdanId) {
        this.shenqingdanId = shenqingdanId;
    }
    /**
     * @return the submitDate
     */
    public Date getSubmitDate() {
        return submitDate;
    }

    /**
     * @param submitDate the submitDate to set
     */
    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    /**
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(String context) {
        this.context = context;
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

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }
    
    
}
