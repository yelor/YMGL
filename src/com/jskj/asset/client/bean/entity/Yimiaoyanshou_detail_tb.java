package com.jskj.asset.client.bean.entity;

import java.util.Date;

public class Yimiaoyanshou_detail_tb {
    private Integer ymysId;

    private Integer yimiaoId;

    private String pihao;

    private Date youxiaodate;

    private Integer piqianfahegeno;

    private String fuheyuan;

    private String fahuoyuan;

    public Integer getYmysId() {
        return ymysId;
    }

    public void setYmysId(Integer ymysId) {
        this.ymysId = ymysId;
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