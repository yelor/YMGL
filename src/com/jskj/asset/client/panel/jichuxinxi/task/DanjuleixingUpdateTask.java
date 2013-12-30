/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.jichuxinxi.task;

import com.jskj.asset.client.bean.entity.Danjuleixingtb;
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
public class DanjuleixingUpdateTask extends BaseTask{
    private static final Logger logger = Logger.getLogger(DanjuleixingUpdateTask.class);
    public final static int ENTITY_SAVE = 0;
    public final static int ENTITY_UPDATE = 1;
    public final static int ENTITY_DELETE = 2;
    private final String ADD_URI = Constants.HTTP + Constants.APPID + "danjuleixing/add";
    private final String UPD_URI = Constants.HTTP + Constants.APPID + "danjuleixing/update";
    private final String delete_URI = Constants.HTTP + Constants.APPID + "danjuleixing/delete";
    
    private final Danjuleixingtb danjuleixing;
    private final int actionType;

    public DanjuleixingUpdateTask(Danjuleixingtb danjuleixing, int actionType) {
        this.danjuleixing = danjuleixing;
        this.actionType = actionType;
    }
    
    

    @Override
    public Object doBackgrounp() {
         try {
            //使用Spring3 RESTful client来POSThttp数据
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);
            if (actionType == ENTITY_SAVE) {
                restTemplate.postForObject(ADD_URI, danjuleixing, Danjuleixingtb.class);
            } else if (actionType == ENTITY_UPDATE) {
                restTemplate.postForObject(UPD_URI, danjuleixing, Danjuleixingtb.class);
            } else if (actionType == ENTITY_DELETE) {
                restTemplate.postForLocation(delete_URI + "/" + danjuleixing.getDanjuleixingId(),null);
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
