/*
 * 2014 Chengdu JunChen Technology
 */
package com.jskj.asset.client.bean.entity;


/**
 *
 * @author huiqi
 */
public class YanshouyimiaoEntity extends DengjiyimiaoEntity {

    private Yimiaoyanshoutb yimiaoyanshou;

    private Yimiaoyanshou_detail_tb yimiaoyanshou_detail;

    public Yimiaoyanshoutb getYimiaoyanshou() {
        return yimiaoyanshou;
    }

    public void setYimiaoyanshou(Yimiaoyanshoutb yimiaoyanshou) {
        this.yimiaoyanshou = yimiaoyanshou;
    }

    public Yimiaoyanshou_detail_tb getYimiaoyanshou_detail() {

        return yimiaoyanshou_detail;
    }

    public void setYimiaoyanshou_detail(Yimiaoyanshou_detail_tb yimiaoyanshou_detail) {

        this.yimiaoyanshou_detail = yimiaoyanshou_detail;
    }

}
