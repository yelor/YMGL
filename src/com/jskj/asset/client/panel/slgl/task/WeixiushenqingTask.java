/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.slgl.task;

import com.jskj.asset.client.bean.entity.WeixiushenqingDetailEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.BeanFactory;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author tt
 */
public class WeixiushenqingTask extends BaseTask{

    static final Logger logger = Logger.getLogger(WeixiushenqingTask.class);
    private final String ADD_URI = Constants.HTTP + Constants.APPID + "wxsq";
    
    private final WeixiushenqingDetailEntity wxsq;
    
    public WeixiushenqingTask(WeixiushenqingDetailEntity wxsq) {
        this.wxsq = wxsq;
    }
    
    @Override
    public Object doBackgrounp() {
        try{
            restTemplate.postForObject(ADD_URI, wxsq, WeixiushenqingDetailEntity.class);
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
