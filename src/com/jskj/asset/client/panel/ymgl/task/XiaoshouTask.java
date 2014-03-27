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
 * @author huiqi
 */
public class XiaoshouTask extends BaseTask{
    private static final Logger logger = Logger.getLogger(XiaoshouTask.class);
    public final static int ENTITY_SAVE = 0;
    public final static int ENTITY_UPDATE = 1;
    public final static int ENTITY_DELETE = 2;
    private final String ADD_URI = Constants.HTTP + Constants.APPID + "yimiaoxiaoshou/add";
    private final String UPD_URI = Constants.HTTP + Constants.APPID + "yimiaoxiaoshou/update";
    private final String delete_URI = Constants.HTTP + Constants.APPID + "yimiaoxiaoshou/delete";
    
    private final Sale_detail_tbFindEntity sale_detail;
    private final int actionType;

    public XiaoshouTask(Sale_detail_tbFindEntity sale_detail, int actionType) {
        this.sale_detail = sale_detail;
        this.actionType = actionType;
    }
    
    

    @Override
    public Object doBackgrounp() {
         try {
            if (actionType == ENTITY_SAVE) {
                restTemplate.postForObject(ADD_URI, sale_detail, Sale_detail_tbFindEntity.class);
            } else if (actionType == ENTITY_UPDATE) {
                restTemplate.postForObject(UPD_URI, sale_detail, Sale_detail_tbFindEntity.class);
            } else if (actionType == ENTITY_DELETE) {
                restTemplate.postForLocation(delete_URI + "/" + sale_detail.getSale().getSaleId(),null);
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
