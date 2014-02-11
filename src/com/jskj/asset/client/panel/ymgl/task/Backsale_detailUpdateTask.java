/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.bean.entity.Backsale_detail_tb;
import com.jskj.asset.client.bean.entity.Backsaletb;
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
public class Backsale_detailUpdateTask extends BaseTask{
    private static final Logger logger = Logger.getLogger(Backsale_detailUpdateTask.class);
    public final static int ENTITY_SAVE = 0;
    public final static int ENTITY_UPDATE = 1;
    public final static int ENTITY_DELETE = 2;
    private final String ADD_URI1 = Constants.HTTP + Constants.APPID + "backsale_detail/add";
    private final String ADD_URI2 = Constants.HTTP + Constants.APPID + "backsale/add";
    private final String UPD_URI1 = Constants.HTTP + Constants.APPID + "backsale_detail/update";
    private final String UPD_URI2 = Constants.HTTP + Constants.APPID + "backsale/update";
    private final String delete_URI1 = Constants.HTTP + Constants.APPID + "backsale_detail/delete";
    private final String delete_URI2 = Constants.HTTP + Constants.APPID + "backsale/delete";
    
    private final Backsale_detail_tb backsale_detail;
    private final Backsaletb backsale;
    private final int actionType;

    public Backsale_detailUpdateTask(Backsale_detail_tb backsale_detail,Backsaletb backsale, int actionType) {
        this.backsale=backsale;
        this.backsale_detail = backsale_detail;
        this.actionType = actionType;
    }
    
    

    @Override
    public Object doBackgrounp() {
         try {
            if (actionType == ENTITY_SAVE) {
                restTemplate.postForObject(ADD_URI2, backsale, Backsaletb.class);
                restTemplate.postForObject(ADD_URI1, backsale_detail, Backsale_detail_tb.class);
            } else if (actionType == ENTITY_UPDATE) {
                restTemplate.postForObject(UPD_URI2, backsale, Backsaletb.class);
                restTemplate.postForObject(UPD_URI1, backsale_detail, Backsale_detail_tb.class);
            } else if (actionType == ENTITY_DELETE) {
                restTemplate.postForLocation(delete_URI2 + "/" + backsale.getBacksaleId(),null);
                restTemplate.postForLocation(delete_URI1 + "/" + backsale_detail.getBackDetailId(),null);
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
