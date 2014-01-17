package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Gudingzichantb {
    private Integer gdzcId;

    private String gdzcName;

    private String gdzcType;

    private String gdzcGuige;

    private String gdzcXinghao;

    private Integer unitId;

    private String gdzcPinpai;

    private String supplier;

    private Date gdzcGuaranteedate;

    private Float gdzcValue;

    private String gdzcSequence;

    private String gdzcRemark;

    private String gdzcPhoto;

    public Integer getGdzcId() {
        return gdzcId;
    }

    public void setGdzcId(Integer gdzcId) {
        this.gdzcId = gdzcId;
    }

    public String getGdzcName() {
        return gdzcName;
    }

    public void setGdzcName(String gdzcName) {
        this.gdzcName = gdzcName == null ? null : gdzcName.trim();
    }

    public String getGdzcType() {
        return gdzcType;
    }

    public void setGdzcType(String gdzcType) {
        this.gdzcType = gdzcType == null ? null : gdzcType.trim();
    }

    public String getGdzcGuige() {
        return gdzcGuige;
    }

    public void setGdzcGuige(String gdzcGuige) {
        this.gdzcGuige = gdzcGuige == null ? null : gdzcGuige.trim();
    }

    public String getGdzcXinghao() {
        return gdzcXinghao;
    }

    public void setGdzcXinghao(String gdzcXinghao) {
        this.gdzcXinghao = gdzcXinghao == null ? null : gdzcXinghao.trim();
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getGdzcPinpai() {
        return gdzcPinpai;
    }

    public void setGdzcPinpai(String gdzcPinpai) {
        this.gdzcPinpai = gdzcPinpai == null ? null : gdzcPinpai.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public Date getGdzcGuaranteedate() {
        return gdzcGuaranteedate;
    }

    public void setGdzcGuaranteedate(Date gdzcGuaranteedate) {
        this.gdzcGuaranteedate = gdzcGuaranteedate;
    }

    public Float getGdzcValue() {
        return gdzcValue;
    }

    public void setGdzcValue(Float gdzcValue) {
        this.gdzcValue = gdzcValue;
    }

    public String getGdzcSequence() {
        return gdzcSequence;
    }

    public void setGdzcSequence(String gdzcSequence) {
        this.gdzcSequence = gdzcSequence == null ? null : gdzcSequence.trim();
    }

    public String getGdzcRemark() {
        return gdzcRemark;
    }

    public void setGdzcRemark(String gdzcRemark) {
        this.gdzcRemark = gdzcRemark == null ? null : gdzcRemark.trim();
    }

    public String getGdzcPhoto() {
        return gdzcPhoto;
    }

    public void setGdzcPhoto(String gdzcPhoto) {
        this.gdzcPhoto = gdzcPhoto == null ? null : gdzcPhoto.trim();
    }
}