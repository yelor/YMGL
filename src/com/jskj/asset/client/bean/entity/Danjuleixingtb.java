package com.jskj.asset.client.bean.entity;

public class Danjuleixingtb {
    private Integer danjuleixingId;

    private String danjuleixingName;

    public Integer getDanjuleixingId() {
        return danjuleixingId;
    }

    public void setDanjuleixingId(Integer danjuleixingId) {
        this.danjuleixingId = danjuleixingId;
    }

    public String getDanjuleixingName() {
        return danjuleixingName;
    }

    public void setDanjuleixingName(String danjuleixingName) {
        this.danjuleixingName = danjuleixingName == null ? null : danjuleixingName.trim();
    }
}