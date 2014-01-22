package com.jskj.asset.client.bean.entity;

public class Pkgenerator {
    private Integer id;

    private String targettable;

    private String pkcolumnname;

    private Integer initialvalue;

    private Integer allocationsize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTargettable() {
        return targettable;
    }

    public void setTargettable(String targettable) {
        this.targettable = targettable == null ? null : targettable.trim();
    }

    public String getPkcolumnname() {
        return pkcolumnname;
    }

    public void setPkcolumnname(String pkcolumnname) {
        this.pkcolumnname = pkcolumnname == null ? null : pkcolumnname.trim();
    }

    public Integer getInitialvalue() {
        return initialvalue;
    }

    public void setInitialvalue(Integer initialvalue) {
        this.initialvalue = initialvalue;
    }

    public Integer getAllocationsize() {
        return allocationsize;
    }

    public void setAllocationsize(Integer allocationsize) {
        this.allocationsize = allocationsize;
    }
}