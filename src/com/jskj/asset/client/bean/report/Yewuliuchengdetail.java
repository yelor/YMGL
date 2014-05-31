/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 *
 * @author 305027939
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Yewuliuchengdetail {

    private String danjubianhao;

    private String danjuleixingName;

    private Date submitDate;

    private String wanglaidanwei;

    private String fuzeren;

    private String jinbanren;

    private String bumen;

    private float jiner;

    private String zhidanren;

    private String comments;

    public String getDanjubianhao() {
        return danjubianhao;
    }

    public void setDanjubianhao(String danjubianhao) {
        this.danjubianhao = danjubianhao;
    }

    /**
     * @return the danjuleixingName
     */
    public String getDanjuleixingName() {
        return danjuleixingName;
    }

    /**
     * @param danjuleixingName the danjuleixingName to set
     */
    public void setDanjuleixingName(String danjuleixingName) {
        this.danjuleixingName = danjuleixingName;
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
     * @return the wanglaidanwei
     */
    public String getWanglaidanwei() {
        return wanglaidanwei;
    }

    /**
     * @param wanglaidanwei the wanglaidanwei to set
     */
    public void setWanglaidanwei(String wanglaidanwei) {
        this.wanglaidanwei = wanglaidanwei;
    }

    /**
     * @return the fuzeren
     */
    public String getFuzeren() {
        return fuzeren;
    }

    /**
     * @param fuzeren the fuzeren to set
     */
    public void setFuzeren(String fuzeren) {
        this.fuzeren = fuzeren;
    }

    /**
     * @return the jinbanren
     */
    public String getJinbanren() {
        return jinbanren;
    }

    /**
     * @param jinbanren the jinbanren to set
     */
    public void setJinbanren(String jinbanren) {
        this.jinbanren = jinbanren;
    }

    /**
     * @return the bumen
     */
    public String getBumen() {
        return bumen;
    }

    /**
     * @param bumen the bumen to set
     */
    public void setBumen(String bumen) {
        this.bumen = bumen;
    }

    /**
     * @return the jiner
     */
    public float getJiner() {
        return jiner;
    }

    /**
     * @param jiner the jiner to set
     */
    public void setJiner(float jiner) {
        this.jiner = jiner;
    }

    /**
     * @return the zhidanren
     */
    public String getZhidanren() {
        return zhidanren;
    }

    /**
     * @param zhidanren the zhidanren to set
     */
    public void setZhidanren(String zhidanren) {
        this.zhidanren = zhidanren;
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
