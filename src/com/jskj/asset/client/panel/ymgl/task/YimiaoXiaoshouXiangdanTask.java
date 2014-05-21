/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.bean.entity.XiaoshoushenpixiangdanEntity;
import com.jskj.asset.client.bean.entity.YimiaobaosunxiangdanEntity;
import com.jskj.asset.client.bean.entity.YimiaocaigouxiangdanEntity;
import com.jskj.asset.client.bean.entity.YimiaotiaojiaxiangdanEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.DanHao;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author tt
 */
public class YimiaoXiaoshouXiangdanTask extends BaseTask {

    public static final Logger logger = Logger.getLogger(YimiaoXiaoshouXiangdanTask.class);
    
//  //对应的URL在YimiaoxiangdanxianshiController里
    private final String YMXS_URI = Constants.HTTP + Constants.APPID + "yimiaoxiaoshouxiangdan";
    private final String YMCG_URI = Constants.HTTP + Constants.APPID + "yimiaocaigouxiangdan";
    private final String YMBS_URI = Constants.HTTP + Constants.APPID + "yimiaobaosunxiangdan";
    private final String YMTJ_URI = Constants.HTTP + Constants.APPID + "yimiaotiaojiaxiangdan";
    private String xiangdanID;
    private XiaoshoushenpixiangdanEntity yimiaoxiaoshouEntiy;
    private YimiaocaigouxiangdanEntity yimiaocaigouEntiy;
    private YimiaobaosunxiangdanEntity yimiaobaosunEntiy;
    private YimiaotiaojiaxiangdanEntity yimiaoxiaotiaojiaEntiy;

    public YimiaoXiaoshouXiangdanTask(String xiangdanID) {
        super();
        this.xiangdanID = xiangdanID;
    }

    @Override
    public Object doBackgrounp() {
        try {
            logger.debug("xiangdanID:" + xiangdanID);
            if (xiangdanID.contains(DanHao.TYPE_YIMIAOXF) | xiangdanID.contains(DanHao.TYPE_YIMIAOXS)) {
                yimiaoxiaoshouEntiy = new XiaoshoushenpixiangdanEntity();
                yimiaoxiaoshouEntiy = restTemplate.getForObject(YMXS_URI + "?xiangdanID=" + xiangdanID, XiaoshoushenpixiangdanEntity.class);
                return yimiaoxiaoshouEntiy;
            } else if (xiangdanID.contains(DanHao.TYPE_YIMIAOSB) | xiangdanID.contains(DanHao.TYPE_YIMIAOLY) | xiangdanID.contains(DanHao.TYPE_YIMIAOSG) | xiangdanID.contains(DanHao.TYPE_YIMIAOCG)) {
                yimiaocaigouEntiy = restTemplate.getForObject(YMCG_URI + "?xiangdanID=" + xiangdanID, YimiaocaigouxiangdanEntity.class);
                return yimiaocaigouEntiy;
            } else if (xiangdanID.contains(DanHao.TYPE_YIMIAOBS)) {
                yimiaobaosunEntiy = restTemplate.getForObject(YMBS_URI + "?xiangdanID=" + xiangdanID, YimiaobaosunxiangdanEntity.class);
                return yimiaobaosunEntiy;
            } else if (xiangdanID.contains(DanHao.TYPE_YIMIAOTJ)) {
                yimiaoxiaotiaojiaEntiy = restTemplate.getForObject(YMTJ_URI + "?xiangdanID=" + xiangdanID, YimiaotiaojiaxiangdanEntity.class);

                return yimiaoxiaotiaojiaEntiy;
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
