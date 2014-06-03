/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.bean.entity.XiaoshoutuihuoEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author huiqi
 */
public class XiaoshoutuihuoTask extends BaseTask{
    private static final Logger logger = Logger.getLogger(XiaoshoutuihuoTask.class);
    public final static int TUIHUOENTITY_SAVE = 0;
    public final static int TUIHUOENTITY_UPDATE = 1;
    public final static int TUIHUOENTITY_DELETE = 2;
    private final String TUIHUOADD_URI = Constants.HTTP + Constants.APPID + "yimiaoxiaoshoutuihuo/add";
    private final String TUIHUOUPD_URI = Constants.HTTP + Constants.APPID + "yimiaoxiaoshoutuihuo/update";
    private final String TUIHUOdelete_URI = Constants.HTTP + Constants.APPID + "yimiaoxiaoshoutuihuo/delete";
    
    private final XiaoshoutuihuoEntity yimiaotuihuoEntity;
    private final int actionType;

    public XiaoshoutuihuoTask(XiaoshoutuihuoEntity tuihuoEntity, int actionType) {
        this.yimiaotuihuoEntity = tuihuoEntity;
        this.actionType = actionType;
    }
    
    

    @Override
    public Object doBackgrounp() {
         try {
            if (actionType == TUIHUOENTITY_SAVE) {
                restTemplate.postForObject(TUIHUOADD_URI, yimiaotuihuoEntity, XiaoshoutuihuoEntity.class);
            } else if (actionType == TUIHUOENTITY_UPDATE) {
                restTemplate.postForObject(TUIHUOUPD_URI, yimiaotuihuoEntity, XiaoshoutuihuoEntity.class);
            } else if (actionType == TUIHUOENTITY_DELETE) {
                restTemplate.postForLocation(TUIHUOdelete_URI + "/" + yimiaotuihuoEntity.getBacksaletb().getBacksaleId(),null);
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
