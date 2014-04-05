/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.slgl.task;

import com.jskj.asset.client.bean.entity.CaigoushenqingDetailEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author tt
 */
public class ShenqingDetailTask extends BaseTask{

    public static final Logger logger = Logger.getLogger(ShenqingDetailTask.class);
    private final String CX_URI = Constants.HTTP + Constants.APPID + "cgsqsp/detail/";
    private String id = "";
    
    public ShenqingDetailTask(String id){
        super();
        this.id = id;
    }
    
    @Override
    public Object doBackgrounp() {
        try{
            logger.debug("ShenqingDetailTask:" + id);
            CaigoushenqingDetailEntity cgsqs = restTemplate.getForObject(CX_URI +id ,CaigoushenqingDetailEntity.class);
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
