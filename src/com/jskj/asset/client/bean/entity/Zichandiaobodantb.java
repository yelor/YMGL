package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Zichandiaobodantb {
    private String zcdbId;

    private Integer zcId;

    private Date diaoboDate;

    private Integer yichurenId;

    private Integer jieshourenId;

    private String cundangdidian;

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

    public Integer getJieshourenId() {
        return jieshourenId;
    }

    public void setJieshourenId(Integer jieshourenId) {
        this.jieshourenId = jieshourenId;
    }

    public String getCundangdidian() {
        return cundangdidian;
    }

    public void setCundangdidian(String cundangdidian) {
        this.cundangdidian = cundangdidian == null ? null : cundangdidian.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}