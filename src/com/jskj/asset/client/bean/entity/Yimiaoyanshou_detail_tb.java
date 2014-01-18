package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Yimiaoyanshou_detail_tb {
    private Integer detailId;

    private String ymysId;

    private Integer yimiaoId;

    private String pihao;

    private Date youxiaodate;

    private Integer piqianfahegeno;

    private Integer quantity;

    private String fuheyuan;

    private String fahuoyuan;

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public String getYmysId() {
        return ymysId;
    }

    public void setYmysId(String ymysId) {
        this.ymysId = ymysId == null ? null : ymysId.trim();
    }

    public Integer getYimiaoId() {
        return yimiaoId;
    }

    public void setYimiaoId(Integer yimiaoId) {
        this.yimiaoId = yimiaoId;
    }

    public String getPihao() {
        return pihao;
    }

    public void setPihao(String pihao) {
        this.pihao = pihao == null ? null : pihao.trim();
    }

    public Date getYouxiaodate() {
        return youxiaodate;
    }

    public void setYouxiaodate(Date youxiaodate) {
        this.youxiaodate = youxiaodate;
    }

    public Integer getPiqianfahegeno() {
        return piqianfahegeno;
    }

    public void setPiqianfahegeno(Integer piqianfahegeno) {
        this.piqianfahegeno = piqianfahegeno;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getFuheyuan() {
        return fuheyuan;
    }

    public void setFuheyuan(String fuheyuan) {
        this.fuheyuan = fuheyuan == null ? null : fuheyuan.trim();
    }

    public String getFahuoyuan() {
        return fahuoyuan;
    }

    public void setFahuoyuan(String fahuoyuan) {
        this.fahuoyuan = fahuoyuan == null ? null : fahuoyuan.trim();
    }
}