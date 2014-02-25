/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jskj.asset.client.bean.entity.Departmenttb;
import com.jskj.asset.client.bean.entity.Depot;
import com.jskj.asset.client.bean.entity.Kehudanweitb;
import com.jskj.asset.client.bean.entity.Usertb;
import com.jskj.asset.client.bean.entity.YiMiaotb;

import java.util.Date;

/**
 *
 * @author 305027939
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Yimiaoxiaoshoudetail {

    private Integer saleDetailId;

    private String saleId;

    private YiMiaotb yimiaotb;

    private Integer quantity;

    private Float price;

    private Float totalprice;

    private Date saleDate;

    private Departmenttb departmenttb;

    private Kehudanweitb kehudanweitb;

    private String gongyingtype;

    private String fahuotype;

    private Usertb zhidanren;

    private String shoumiaopeople;

    private Depot depottb;

    private Integer processId;

    private String remark;

    private Integer isPaid;

    /**
     * @return the saleDetailId
     */
    public Integer getSaleDetailId() {
        return saleDetailId;
    }

    /**
     * @param saleDetailId the saleDetailId to set
     */
    public void setSaleDetailId(Integer saleDetailId) {
        this.saleDetailId = saleDetailId;
    }

    /**
     * @return the saleId
     */
    public String getSaleId() {
        return saleId;
    }

    /**
     * @param saleId the saleId to set
     */
    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    /**
     * @return the yimiaotb
     */
    public YiMiaotb getYimiaotb() {
        return yimiaotb;
    }

    /**
     * @param yimiaotb the yimiaotb to set
     */
    public void setYimiaotb(YiMiaotb yimiaotb) {
        this.yimiaotb = yimiaotb;
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the price
     */
    public Float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * @return the totalprice
     */
    public Float getTotalprice() {
        return totalprice;
    }

    /**
     * @param totalprice the totalprice to set
     */
    public void setTotalprice(Float totalprice) {
        this.totalprice = totalprice;
    }

    /**
     * @return the saleDate
     */
    public Date getSaleDate() {
        return saleDate;
    }

    /**
     * @param saleDate the saleDate to set
     */
    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    /**
     * @return the departmenttb
     */
    public Departmenttb getDepartmenttb() {
        return departmenttb;
    }

    /**
     * @param departmenttb the departmenttb to set
     */
    public void setDepartmenttb(Departmenttb departmenttb) {
        this.departmenttb = departmenttb;
    }

    /**
     * @return the kehudanweitb
     */
    public Kehudanweitb getKehudanweitb() {
        return kehudanweitb;
    }

    /**
     * @param kehudanweitb the kehudanweitb to set
     */
    public void setKehudanweitb(Kehudanweitb kehudanweitb) {
        this.kehudanweitb = kehudanweitb;
    }

    /**
     * @return the gongyingtype
     */
    public String getGongyingtype() {
        return gongyingtype;
    }

    /**
     * @param gongyingtype the gongyingtype to set
     */
    public void setGongyingtype(String gongyingtype) {
        this.gongyingtype = gongyingtype;
    }

    /**
     * @return the fahuotype
     */
    public String getFahuotype() {
        return fahuotype;
    }

    /**
     * @param fahuotype the fahuotype to set
     */
    public void setFahuotype(String fahuotype) {
        this.fahuotype = fahuotype;
    }

    /**
     * @return the zhidanren
     */
    public Usertb getZhidanren() {
        return zhidanren;
    }

    /**
     * @param zhidanren the zhidanren to set
     */
    public void setZhidanren(Usertb zhidanren) {
        this.zhidanren = zhidanren;
    }

    /**
     * @return the shoumiaopeople
     */
    public String getShoumiaopeople() {
        return shoumiaopeople;
    }

    /**
     * @param shoumiaopeople the shoumiaopeople to set
     */
    public void setShoumiaopeople(String shoumiaopeople) {
        this.shoumiaopeople = shoumiaopeople;
    }

    /**
     * @return the depottb
     */
    public Depot getDepottb() {
        return depottb;
    }

    /**
     * @param depottb the depottb to set
     */
    public void setDepottb(Depot depottb) {
        this.depottb = depottb;
    }

    /**
     * @return the processId
     */
    public Integer getProcessId() {
        return processId;
    }

    /**
     * @param processId the processId to set
     */
    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the isPaid
     */
    public Integer getIsPaid() {
        return isPaid;
    }

    /**
     * @param isPaid the isPaid to set
     */
    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
    }

}
