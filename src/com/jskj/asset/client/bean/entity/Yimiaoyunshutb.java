package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Yimiaoyunshutb {
    private String ymysId;

    private Date ymysDate;

    private Integer departmentId;

    private String ymysSendperson;

    private String ymysEquipment;

    private Integer supplierId;

    private String ymysStoragetype;

    private Date ymysStarttime;

    private Float ymysStrattemp1;

    private Float ymysStarttemp2;

    private String ymysTotaltime;

    private Float ymysArrivetemp1;

    private Float ymysArrivetemp2;

    private Date ymysArrivetime;

    private String ymysCarcondition;

    private Float ymysBefmiles;

    private Float ymysBackmiles;

    private Float ymysKm;

    private String userName;

    private String ymysArriveaddr;

    public String getYmysId() {
        return ymysId;
    }

    public void setYmysId(String ymysId) {
        this.ymysId = ymysId == null ? null : ymysId.trim();
    }

    public Date getYmysDate() {
        return ymysDate;
    }

    public void setYmysDate(Date ymysDate) {
        this.ymysDate = ymysDate;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getYmysSendperson() {
        return ymysSendperson;
    }

    public void setYmysSendperson(String ymysSendperson) {
        this.ymysSendperson = ymysSendperson == null ? null : ymysSendperson.trim();
    }

    public String getYmysEquipment() {
        return ymysEquipment;
    }

    public void setYmysEquipment(String ymysEquipment) {
        this.ymysEquipment = ymysEquipment == null ? null : ymysEquipment.trim();
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getYmysStoragetype() {
        return ymysStoragetype;
    }

    public void setYmysStoragetype(String ymysStoragetype) {
        this.ymysStoragetype = ymysStoragetype == null ? null : ymysStoragetype.trim();
    }

    public Date getYmysStarttime() {
        return ymysStarttime;
    }

    public void setYmysStarttime(Date ymysStarttime) {
        this.ymysStarttime = ymysStarttime;
    }

    public Float getYmysStrattemp1() {
        return ymysStrattemp1;
    }

    public void setYmysStrattemp1(Float ymysStrattemp1) {
        this.ymysStrattemp1 = ymysStrattemp1;
    }

    public Float getYmysStarttemp2() {
        return ymysStarttemp2;
    }

    public void setYmysStarttemp2(Float ymysStarttemp2) {
        this.ymysStarttemp2 = ymysStarttemp2;
    }

    public String getYmysTotaltime() {
        return ymysTotaltime;
    }

    public void setYmysTotaltime(String ymysTotaltime) {
        this.ymysTotaltime = ymysTotaltime == null ? null : ymysTotaltime.trim();
    }

    public Float getYmysArrivetemp1() {
        return ymysArrivetemp1;
    }

    public void setYmysArrivetemp1(Float ymysArrivetemp1) {
        this.ymysArrivetemp1 = ymysArrivetemp1;
    }

    public Float getYmysArrivetemp2() {
        return ymysArrivetemp2;
    }

    public void setYmysArrivetemp2(Float ymysArrivetemp2) {
        this.ymysArrivetemp2 = ymysArrivetemp2;
    }

    public Date getYmysArrivetime() {
        return ymysArrivetime;
    }

    public void setYmysArrivetime(Date ymysArrivetime) {
        this.ymysArrivetime = ymysArrivetime;
    }

    public String getYmysCarcondition() {
        return ymysCarcondition;
    }

    public void setYmysCarcondition(String ymysCarcondition) {
        this.ymysCarcondition = ymysCarcondition == null ? null : ymysCarcondition.trim();
    }

    public Float getYmysBefmiles() {
        return ymysBefmiles;
    }

    public void setYmysBefmiles(Float ymysBefmiles) {
        this.ymysBefmiles = ymysBefmiles;
    }

    public Float getYmysBackmiles() {
        return ymysBackmiles;
    }

    public void setYmysBackmiles(Float ymysBackmiles) {
        this.ymysBackmiles = ymysBackmiles;
    }

    public Float getYmysKm() {
        return ymysKm;
    }

    public void setYmysKm(Float ymysKm) {
        this.ymysKm = ymysKm;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getYmysArriveaddr() {
        return ymysArriveaddr;
    }

    public void setYmysArriveaddr(String ymysArriveaddr) {
        this.ymysArriveaddr = ymysArriveaddr == null ? null : ymysArriveaddr.trim();
    }
}