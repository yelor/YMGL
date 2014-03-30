/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 305027939
 */
@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageCacheBean {
    @XmlElement
    private boolean displayMyTask;
    @XmlElement
    private boolean displayMyApp;
    @XmlElement
    private String shenqingdanDate;
    @XmlElement
    private boolean displayMyShenpi;
    @XmlElement
    private String shenpidanDate;
    @XmlElement
    private boolean autoUpdate;
    @XmlElement
    private int sec;

    /**
     * @return the displayMyTask
     */
    public boolean isDisplayMyTask() {
        return displayMyTask;
    }

    /**
     * @param displayMyTask the displayMyTask to set
     */
    public void setDisplayMyTask(boolean displayMyTask) {
        this.displayMyTask = displayMyTask;
    }

    /**
     * @return the displayMyApp
     */
    public boolean isDisplayMyApp() {
        return displayMyApp;
    }

    /**
     * @param displayMyApp the displayMyApp to set
     */
    public void setDisplayMyApp(boolean displayMyApp) {
        this.displayMyApp = displayMyApp;
    }

    /**
     * @return the shenqingdanDate
     */
    public String getShenqingdanDate() {
        return shenqingdanDate;
    }

    /**
     * @param shenqingdanDate the shenqingdanDate to set
     */
    public void setShenqingdanDate(String shenqingdanDate) {
        this.shenqingdanDate = shenqingdanDate;
    }

    /**
     * @return the displayMyShenpi
     */
    public boolean isDisplayMyShenpi() {
        return displayMyShenpi;
    }

    /**
     * @param displayMyShenpi the displayMyShenpi to set
     */
    public void setDisplayMyShenpi(boolean displayMyShenpi) {
        this.displayMyShenpi = displayMyShenpi;
    }

    /**
     * @return the shenpidanDate
     */
    public String getShenpidanDate() {
        return shenpidanDate;
    }

    /**
     * @param shenpidanDate the shenpidanDate to set
     */
    public void setShenpidanDate(String shenpidanDate) {
        this.shenpidanDate = shenpidanDate;
    }

    /**
     * @return the autoUpdate
     */
    public boolean isAutoUpdate() {
        return autoUpdate;
    }

    /**
     * @param autoUpdate the autoUpdate to set
     */
    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    /**
     * @return the sec
     */
    public int getSec() {
        return sec;
    }

    /**
     * @param sec the sec to set
     */
    public void setSec(int sec) {
        this.sec = sec;
    }
    
}
