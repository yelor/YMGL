/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.slgl;

import com.jskj.asset.client.bean.entity.LingyongtuikuDetailEntity;
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
public class LingyongtuikuTask extends BaseTask{

    static final Logger logger = Logger.getLogger(LingyongtuikuTask.class);
    private final String ADD_URI = Constants.HTTP + Constants.APPID + "lytk";
    
    private final LingyongtuikuDetailEntity lytk;
    
    public LingyongtuikuTask(LingyongtuikuDetailEntity lytk) {
        this.lytk = lytk;
    }
    
    @Override
    public Object doBackgrounp() {
        try{
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);
            restTemplate.postForObject(ADD_URI, lytk, LingyongtuikuDetailEntity.class);
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
