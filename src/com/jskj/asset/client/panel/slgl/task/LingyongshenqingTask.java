/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.slgl.task;

import com.jskj.asset.client.bean.entity.LingyongshenqingDetailEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author tt
 */
public class LingyongshenqingTask extends BaseTask{

    static final Logger logger = Logger.getLogger(LingyongshenqingTask.class);
    private final String ADD_URI = Constants.HTTP + Constants.APPID + "lysq";
    
    private final LingyongshenqingDetailEntity lysq;
    
    public LingyongshenqingTask(LingyongshenqingDetailEntity lysq) {
        this.lysq = lysq;
    }
    
    @Override
    public Object doBackgrounp() {
        try{
            restTemplate.postForObject(ADD_URI, lysq, LingyongshenqingDetailEntity.class);
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
