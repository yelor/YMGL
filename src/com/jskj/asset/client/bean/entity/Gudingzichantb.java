package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Gudingzichantb {
    private Integer gdzcId;

    private String gdzcName;

    private String gdzcType;

    private String gdzcGuige;

    private String gdzcXinghao;

    private String unitId;

    private String gdzcPinpai;

    private String supplier;

    private Date gdzcGuaranteedate;

    private Float gdzcValue;

    private String gdzcSequence;

    private String gdzcRemark;

    private String gdzcPhoto;

    private Integer deportId;

    private Integer supplierId;

    private Integer kucunxiaxian;

    private Integer kucunshangxian;

    private String zujima;

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

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
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

    public Integer getDeportId() {
        return deportId;
    }

    public void setDeportId(Integer deportId) {
        this.deportId = deportId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getKucunxiaxian() {
        return kucunxiaxian;
    }

    public void setKucunxiaxian(Integer kucunxiaxian) {
        this.kucunxiaxian = kucunxiaxian;
    }

    public Integer getKucunshangxian() {
        return kucunshangxian;
    }

    public void setKucunshangxian(Integer kucunshangxian) {
        this.kucunshangxian = kucunshangxian;
    }

    public String getZujima() {
        return zujima;
    }

    public void setZujima(String zujima) {
        this.zujima = zujima == null ? null : zujima.trim();
    }
}