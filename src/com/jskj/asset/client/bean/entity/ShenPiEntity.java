/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author tt
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShenPiEntity {
    
    private String user;
    
    private String id;
    
    private String result;
    
    private String reason;

    public String getUser() {
        return user;
    }

    public String getId() {
        return id;
    }

    public String getResult() {
        return result;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
       
}
