package com.jskj.asset.client.bean.entity;

public class ReduceTypetb {
    private Integer reducetypeId;

    private String reducetypeName;

    public Integer getReducetypeId() {
        return reducetypeId;
    }

    public void setReducetypeId(Integer reducetypeId) {
        this.reducetypeId = reducetypeId;
    }

    public String getReducetypeName() {
        return reducetypeName;
    }

    public void setReducetypeName(String reducetypeName) {
        this.reducetypeName = reducetypeName == null ? null : reducetypeName.trim();
    }
}