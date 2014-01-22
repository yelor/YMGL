package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Dizhiyihaopin {
    private Integer dzyhpId;

    private String dzyhpName;

    private String dzyhpType;

    private String dzyhpGuige;

    private String dzyhpXinghao;

    private String unitId;

    private String unitPhoto;

    private String dzyhpPinpai;

    private Integer deportId;

    private Integer supplierId;

    private Integer dzyhpKucunxiaxian;

    private Integer dzyhpKucunshangxian;

    private String dzyhpBarcode;

    private String dzyhpRemark;

    public Integer getDzyhpId() {
        return dzyhpId;
    }

    public void setDzyhpId(Integer dzyhpId) {
        this.dzyhpId = dzyhpId;
    }

    public String getDzyhpName() {
        return dzyhpName;
    }

    public void setDzyhpName(String dzyhpName) {
        this.dzyhpName = dzyhpName == null ? null : dzyhpName.trim();
    }

    public String getDzyhpType() {
        return dzyhpType;
    }

    public void setDzyhpType(String dzyhpType) {
        this.dzyhpType = dzyhpType == null ? null : dzyhpType.trim();
    }

    public String getDzyhpGuige() {
        return dzyhpGuige;
    }

    public void setDzyhpGuige(String dzyhpGuige) {
        this.dzyhpGuige = dzyhpGuige == null ? null : dzyhpGuige.trim();
    }

    public String getDzyhpXinghao() {
        return dzyhpXinghao;
    }

    public void setDzyhpXinghao(String dzyhpXinghao) {
        this.dzyhpXinghao = dzyhpXinghao == null ? null : dzyhpXinghao.trim();
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public String getUnitPhoto() {
        return unitPhoto;
    }

    public void setUnitPhoto(String unitPhoto) {
        this.unitPhoto = unitPhoto == null ? null : unitPhoto.trim();
    }

    public String getDzyhpPinpai() {
        return dzyhpPinpai;
    }

    public void setDzyhpPinpai(String dzyhpPinpai) {
        this.dzyhpPinpai = dzyhpPinpai == null ? null : dzyhpPinpai.trim();
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

    public Integer getDzyhpKucunxiaxian() {
        return dzyhpKucunxiaxian;
    }

    public void setDzyhpKucunxiaxian(Integer dzyhpKucunxiaxian) {
        this.dzyhpKucunxiaxian = dzyhpKucunxiaxian;
    }

    public Integer getDzyhpKucunshangxian() {
        return dzyhpKucunshangxian;
    }

    public void setDzyhpKucunshangxian(Integer dzyhpKucunshangxian) {
        this.dzyhpKucunshangxian = dzyhpKucunshangxian;
    }

    public String getDzyhpBarcode() {
        return dzyhpBarcode;
    }

    public void setDzyhpBarcode(String dzyhpBarcode) {
        this.dzyhpBarcode = dzyhpBarcode == null ? null : dzyhpBarcode.trim();
    }

    public String getDzyhpRemark() {
        return dzyhpRemark;
    }

    public void setDzyhpRemark(String dzyhpRemark) {
        this.dzyhpRemark = dzyhpRemark == null ? null : dzyhpRemark.trim();
    }
}