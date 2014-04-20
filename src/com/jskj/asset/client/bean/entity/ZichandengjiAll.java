/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author haitao
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZichandengjiAll extends Zichandengjitb{
    
    private String imguri;
    
    private List<Fushuliebiaotb> fushulist;
    
    private List<Kuozhanxinxitb> kuozhanlist;

    public String getImguri() {
        return imguri;
    }

    public List<Fushuliebiaotb> getFushulist() {
        return fushulist;
    }

    public List<Kuozhanxinxitb> getKuozhanlist() {
        return kuozhanlist;
    }

    public void setImguri(String imguri) {
        this.imguri = imguri;
    }

    public void setFushulist(List<Fushuliebiaotb> fushulist) {
        this.fushulist = fushulist;
    }

    public void setKuozhanlist(List<Kuozhanxinxitb> kuozhanlist) {
        this.kuozhanlist = kuozhanlist;
    }
    
}
