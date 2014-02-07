/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.bean.report;

import java.util.Date;

/**
 *
 * @author 305027939
 */
public class CaigouChartReport {
    
    private String state;
    
    private String item;
    
    private Date orderdate;
    
    private Integer quantity;
    
    private Float price;

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the item
     */
    public String getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * @return the orderdate
     */
    public Date getOrderdate() {
        return orderdate;
    }

    /**
     * @param orderdate the orderdate to set
     */
    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
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

  
    
    
}
