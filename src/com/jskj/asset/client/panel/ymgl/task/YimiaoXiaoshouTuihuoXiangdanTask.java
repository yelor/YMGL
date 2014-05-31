/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.bean.entity.XiaoshoutuihuoxiangdanEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.DanHao;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author tt
 */
public class YimiaoXiaoshouTuihuoXiangdanTask extends BaseTask {

    public static final Logger logger = Logger.getLogger(YimiaoXiaoshouTuihuoXiangdanTask.class);

//  //对应的URL在YimiaoxiangdanxianshiController里
    private final String YMXSTH_URI = Constants.HTTP + Constants.APPID + "yimiaoxiaoshoutuihuoxiangdan";
    private String xiangdanID;
    private XiaoshoutuihuoxiangdanEntity yimiaoxiaoshoutuihuoEntiy;

    public YimiaoXiaoshouTuihuoXiangdanTask(String xiangdanID) {
        super();
        this.xiangdanID = xiangdanID;
    }

    @Override
    public Object doBackgrounp() {
        try {
            logger.debug("xiangdanID:" + xiangdanID);
            if (xiangdanID.contains(DanHao.TYPE_YIMIAOXF) | xiangdanID.contains(DanHao.TYPE_YIMIAOXS)
                    | xiangdanID.contains(DanHao.TYPE_YIMIAOLYTH) | xiangdanID.contains(DanHao.TYPE_YIMIAOCGTH)) {
                yimiaoxiaoshoutuihuoEntiy = new XiaoshoutuihuoxiangdanEntity();
                yimiaoxiaoshoutuihuoEntiy = restTemplate.getForObject(YMXSTH_URI + "?xiangdanID=" + xiangdanID, XiaoshoutuihuoxiangdanEntity.class);
                return yimiaoxiaoshoutuihuoEntiy;
            }
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }
        return null;
    }

    @Override
    public void onSucceeded(Object object) {

    }

}
