/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author huiqi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sale_detail_tbFindEntity{

    private Saletb sale;

    private int count;

    private List<Sale_detail_tb> sale_details;

    public Saletb getSale() {
        return sale;
    }

    public void setSale(Saletb sale) {
        this.sale = sale;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Sale_detail_tb> getSale_details() {
        return sale_details;
    }

    public void setSale_details(List<Sale_detail_tb> sale_details) {
        this.sale_details = sale_details;
    }

}
