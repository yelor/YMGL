/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.shjs.task;

import com.jskj.asset.client.bean.entity.ShoukuanshenqingDetailEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author tt
 */
public class SkShenqingDetailTask extends BaseTask{

    public static final Logger logger = Logger.getLogger(SkShenqingDetailTask.class);
    private final String CX_URI = Constants.HTTP + Constants.APPID + "shoukuandan/detail/";
    private String id = "";
    
    public SkShenqingDetailTask(String id){
        super();
        this.id = id;
    }
    
    @Override
    public Object doBackgrounp() {
        try{
            logger.debug("ShenqingDetailTask:" + id);
            ShoukuanshenqingDetailEntity cgsqs = restTemplate.getForObject(CX_URI +id ,ShoukuanshenqingDetailEntity.class);
            return cgsqs;
        }catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
        
    }
    
}
