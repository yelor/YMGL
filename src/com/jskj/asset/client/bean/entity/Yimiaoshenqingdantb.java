package com.jskj.asset.client.bean.entity;

public class Yimiaoshenqingdantb {
    private Integer xiangdanId;

    private String shenqingdanId;

    private Integer yimiaoId;

    private Integer quantity;

    private Float saleprice;

    private Float totalprice;

    private Integer checkId1;

    private Integer checkId2;

    private Integer checkId3;

    private Integer checkId4;

    public Integer getXiangdanId() {
        return xiangdanId;
    }

    public void setXiangdanId(Integer xiangdanId) {
        this.xiangdanId = xiangdanId;
    }

    public String getShenqingdanId() {
        return shenqingdanId;
    }

    public void setShenqingdanId(String shenqingdanId) {
        this.shenqingdanId = shenqingdanId == null ? null : shenqingdanId.trim();
    }

    public Integer getYimiaoId() {
        return yimiaoId;
    }

    public void setYimiaoId(Integer yimiaoId) {
        this.yimiaoId = yimiaoId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(Float saleprice) {
        this.saleprice = saleprice;
    }

    public Float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Float totalprice) {
        this.totalprice = totalprice;
    }

    public Integer getCheckId1() {
        return checkId1;
    }

    public void setCheckId1(Integer checkId1) {
        this.checkId1 = checkId1;
    }

    public Integer getCheckId2() {
        return checkId2;
    }

    public void setCheckId2(Integer checkId2) {
        this.checkId2 = checkId2;
    }

    public Integer getCheckId3() {
        return checkId3;
    }

    public void setCheckId3(Integer checkId3) {
        this.checkId3 = checkId3;
    }

    public Integer getCheckId4() {
        return checkId4;
    }

    public void setCheckId4(Integer checkId4) {
        this.checkId4 = checkId4;
    }
}