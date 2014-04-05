/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.bean.entity.YimiaoyunshuEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author huiqi
 疫苗运输记录单的Task
 */
public class YimiaoyunshuTask extends BaseTask{
    private static final Logger logger = Logger.getLogger(YimiaoyunshuTask.class);
    public final static int ENTITY_SAVE = 0;
    public final static int ENTITY_UPDATE = 1;
    public final static int ENTITY_DELETE = 2;
    private final String ADD_URI = Constants.HTTP + Constants.APPID + "yimiaoyunshu/add";
    private final String UPD_URI = Constants.HTTP + Constants.APPID + "yimiaoyunshu/update";
    private final String delete_URI = Constants.HTTP + Constants.APPID + "yimiaoyunshu/delete";
    
    private final YimiaoyunshuEntity yimiaoyunshuEntity;
    private final int actionType;

    public YimiaoyunshuTask(YimiaoyunshuEntity yimiaoyunshuEntity, int actionType) {
        this.yimiaoyunshuEntity = yimiaoyunshuEntity;
        this.actionType = actionType;
    }
    
    

    @Override
    public Object doBackgrounp() {
         try {
            if (actionType == ENTITY_SAVE) {
                restTemplate.postForObject(ADD_URI, yimiaoyunshuEntity, YimiaoyunshuEntity.class);
            } else if (actionType == ENTITY_UPDATE) {
                restTemplate.postForObject(UPD_URI, yimiaoyunshuEntity, YimiaoyunshuEntity.class);
            } else if (actionType == ENTITY_DELETE) {
                restTemplate.postForLocation(delete_URI + "/" + yimiaoyunshuEntity.getYimiaoyunshu().getYmysId(),null);
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
