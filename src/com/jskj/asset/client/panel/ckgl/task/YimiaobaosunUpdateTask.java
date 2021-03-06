/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.ckgl.task;

import com.jskj.asset.client.bean.entity.YimiaobaosuntbFindEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author huiqi
 */
public class YimiaobaosunUpdateTask extends BaseTask{
    public static final Logger logger = Logger.getLogger(YimiaobaosunUpdateTask.class);
    public final static int ENTITY_SAVE = 0;
    public final static int ENTITY_UPDATE = 1;
    public final static int ENTITY_DELETE = 2;
    private final String ADD_URI = Constants.HTTP + Constants.APPID + "yimiaobaosunEntity/add";
    private final String UPD_URI = Constants.HTTP + Constants.APPID + "yimiaobaosunEntity/update";
    private final String delete_URI = Constants.HTTP + Constants.APPID + "yimiaobaosunEntity/delete";
    
    private final YimiaobaosuntbFindEntity yimiaobaosunEntity;
    private final int actionType;

    public YimiaobaosunUpdateTask(YimiaobaosuntbFindEntity yimiaobaosunEntity, int actionType) {
        this.yimiaobaosunEntity = yimiaobaosunEntity;
        this.actionType = actionType;
    }
    
    

    @Override
    public Object doBackgrounp() {
         try {
            //使用Spring3 RESTful client来POSThttp数据
            if (actionType == ENTITY_SAVE) {
                restTemplate.postForObject(ADD_URI, yimiaobaosunEntity, YimiaobaosuntbFindEntity.class);
            } else if (actionType == ENTITY_UPDATE) {
                restTemplate.postForObject(UPD_URI, yimiaobaosunEntity, YimiaobaosuntbFindEntity.class);
            } else if (actionType == ENTITY_DELETE) {
                restTemplate.postForLocation(delete_URI + "/" + yimiaobaosunEntity.getBaosun().getBaosunId(),null);
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
