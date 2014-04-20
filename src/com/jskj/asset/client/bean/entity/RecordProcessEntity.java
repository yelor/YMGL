/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 *
 * @author 305027939
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecordProcessEntity {

    private String shenqingdanId;
    
    private String handleType;

    private Date handleDate;
    
    private String danjuleixing;
    
    private String jingbanren;
    
    private String departmentName;
    
    private String comments;

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
     * @return the handleType
     */
    public String getHandleType() {
        return handleType;
    }

    /**
     * @param handleType the handleType to set
     */
    public void setHandleType(String handleType) {
        this.handleType = handleType;
    }

    /**
     * @return the handleDate
     */
    public Date getHandleDate() {
        return handleDate;
    }

    /**
     * @param handleDate the handleDate to set
     */
    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
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
     * @return the jingbanren
     */
    public String getJingbanren() {
        return jingbanren;
    }

    /**
     * @param jingbanren the jingbanren to set
     */
    public void setJingbanren(String jingbanren) {
        this.jingbanren = jingbanren;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName the departmentName to set
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

}
