/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.bean.entity.Sale_detail_tbFindEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author tt
 */
public class YimiaoXiaoshouXiangdanTask extends BaseTask{

    public static final Logger logger = Logger.getLogger(YimiaoXiaoshouXiangdanTask.class);
    private final String CX_URI = Constants.HTTP + Constants.APPID + "yimiaoxiaoshouxiangdan";
    private String xiangdanID;    
    
    public YimiaoXiaoshouXiangdanTask(String xiangdanID){
        super();
        this.xiangdanID = xiangdanID;
    }
    
    @Override
    public Object doBackgrounp() {
        try{
            logger.debug("xiangdanID:"+xiangdanID);
            Sale_detail_tbFindEntity yimiaoxiaoshouEntiy = restTemplate.getForObject(CX_URI+"?xiangdanID="+xiangdanID,Sale_detail_tbFindEntity.class);
            return yimiaoxiaoshouEntiy;
        }catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
        
    }
    
}
