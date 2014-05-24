package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Zichandiaobodantb {
    private String zcdbId;

    private Integer zcId;

    private Date diaoboDate;

    private Integer yichurenId;

    private String yuanBarcode;

    private Integer jieshourenId;

    private String xianBarcode;

    private String cunfangdidian;

    private String remark;

    public String getZcdbId() {
        return zcdbId;
    }

    public void setZcdbId(String zcdbId) {
        this.zcdbId = zcdbId == null ? null : zcdbId.trim();
    }

    public Integer getZcId() {
        return zcId;
    }

    public void setZcId(Integer zcId) {
        this.zcId = zcId;
    }

    public Date getDiaoboDate() {
        return diaoboDate;
    }

    public void setDiaoboDate(Date diaoboDate) {
        this.diaoboDate = diaoboDate;
    }

    public Integer getYichurenId() {
        return yichurenId;
    }

    public void setYichurenId(Integer yichurenId) {
        this.yichurenId = yichurenId;
    }

    public String getYuanBarcode() {
        return yuanBarcode;
    }

    public void setYuanBarcode(String yuanBarcode) {
        this.yuanBarcode = yuanBarcode == null ? null : yuanBarcode.trim();
    }

    public Integer getJieshourenId() {
        return jieshourenId;
    }

    public void setJieshourenId(Integer jieshourenId) {
        this.jieshourenId = jieshourenId;
    }

    public String getXianBarcode() {
        return xianBarcode;
    }

    public void setXianBarcode(String xianBarcode) {
        this.xianBarcode = xianBarcode == null ? null : xianBarcode.trim();
    }

    public String getCunfangdidian() {
        return cunfangdidian;
    }

    public void setCunfangdidian(String cunfangdidian) {
        this.cunfangdidian = cunfangdidian == null ? null : cunfangdidian.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}