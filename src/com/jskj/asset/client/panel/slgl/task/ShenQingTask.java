/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.slgl.task;

import com.jskj.asset.client.bean.entity.ShenQingDetailEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author tt
 */
public class ShenQingTask extends BaseTask{

    public static final Logger logger = Logger.getLogger(ShenQingTask.class);
    private final String ADD_URI = Constants.HTTP + Constants.APPID + "cgsq/add";
    
    private final ShenQingDetailEntity cgsq;
    
    public ShenQingTask(ShenQingDetailEntity cgsq) {
        this.cgsq = cgsq;
    }
    
    @Override
    public Object doBackgrounp() {
        try{
            restTemplate.postForObject(ADD_URI, cgsq, ShenQingDetailEntity.class);
        }catch (RestClientException e) {
            logger.error(e);
            return e;
        }
        
        return STATUS_OK;
    }

    @Override
    public void onSucceeded(Object object) {
        
    }
    
}
