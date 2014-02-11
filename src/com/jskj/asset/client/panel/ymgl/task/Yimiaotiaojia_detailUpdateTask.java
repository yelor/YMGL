/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.bean.entity.Yimiaotiaojia_detail_tb;
import com.jskj.asset.client.bean.entity.Yimiaotiaojia_detail_tbFindEntity;
import com.jskj.asset.client.bean.entity.Yimiaotiaojiatb;
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
public class Yimiaotiaojia_detailUpdateTask extends BaseTask{
    public static final Logger logger = Logger.getLogger(Yimiaotiaojia_detailUpdateTask.class);
    public final static int ENTITY_SAVE = 0;
    public final static int ENTITY_UPDATE = 1;
    public final static int ENTITY_DELETE = 2;
    private final String ADD_URI = Constants.HTTP + Constants.APPID + "yimiaotiaojia/add";
    private final String UPD_URI = Constants.HTTP + Constants.APPID + "yimiaotiaojia_detail/update";
    private final String delete_URI = Constants.HTTP + Constants.APPID + "yimiaotiaojia_detail/delete";
    
    private final Yimiaotiaojia_detail_tbFindEntity yimiaotiaojia_detail;
    private final int actionType;

    public Yimiaotiaojia_detailUpdateTask(Yimiaotiaojia_detail_tbFindEntity yimiaotiaojia_detail, int actionType) {
        this.yimiaotiaojia_detail = yimiaotiaojia_detail;
        this.actionType = actionType;
    }
    
    

    @Override
    public Object doBackgrounp() {
         try {
            if (actionType == ENTITY_SAVE) {
                restTemplate.postForObject(ADD_URI, yimiaotiaojia_detail, Yimiaotiaojia_detail_tbFindEntity.class);
            } else if (actionType == ENTITY_UPDATE) {
                restTemplate.postForObject(UPD_URI, yimiaotiaojia_detail, Yimiaotiaojia_detail_tbFindEntity.class);
            } else if (actionType == ENTITY_DELETE) {
                restTemplate.postForLocation(delete_URI + "/" + yimiaotiaojia_detail.getYimiaotiaojia().getTiaojiaId(),null);
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
