/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.bean.entity.Yimiaoshenqingdantb;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.BeanFactory;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author huiqi
 */
public class YimiaoshenqingdanUpdateTask extends BaseTask{
    private static final Logger logger = Logger.getLogger(YimiaoshenqingdanUpdateTask.class);
    public final static int ENTITY_SAVE = 0;
    public final static int ENTITY_UPDATE = 1;
    public final static int ENTITY_DELETE = 2;
    private final String ADD_URI = Constants.HTTP + Constants.APPID + "yimiaoshenqingdan/add";
    private final String UPD_URI = Constants.HTTP + Constants.APPID + "yimiaoshenqingdan/update";
    private final String delete_URI = Constants.HTTP + Constants.APPID + "yimiaoshenqingdan/delete";
    
    private final Yimiaoshenqingdantb yimiaoshenqingdan;
    private final int actionType;

    public YimiaoshenqingdanUpdateTask(Yimiaoshenqingdantb yimiaoshenqingdan, int actionType) {
        this.yimiaoshenqingdan = yimiaoshenqingdan;
        this.actionType = actionType;
    }
    
    

    @Override
    public Object doBackgrounp() {
         try {
            //使用Spring3 RESTful client来POSThttp数据
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);
            if (actionType == ENTITY_SAVE) {
                restTemplate.postForObject(ADD_URI, yimiaoshenqingdan, Yimiaoshenqingdantb.class);
            } else if (actionType == ENTITY_UPDATE) {
                restTemplate.postForObject(UPD_URI, yimiaoshenqingdan, Yimiaoshenqingdantb.class);
            } else if (actionType == ENTITY_DELETE) {
                restTemplate.postForLocation(delete_URI + "/" + yimiaoshenqingdan.getShenqingdanId(),null);
            }
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }

        return STATUS_OK;
    }

    @Override
    public void onSucceeded(Object object) {
    }
    
}
