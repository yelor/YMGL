/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.slgl.task;

import com.jskj.asset.client.bean.entity.Zichandiaobodantb;
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
public class DiaoboTask extends BaseTask{

    static final Logger logger = Logger.getLogger(DiaoboTask.class);
    private final String ADD_URI = Constants.HTTP + Constants.APPID + "gdzc/diaobo";
    
    private final Zichandiaobodantb zcdb;
    
    public DiaoboTask(Zichandiaobodantb zcdb) {
        this.zcdb = zcdb;
    }
    
    @Override
    public Object doBackgrounp() {
        try{
            restTemplate.postForObject(ADD_URI, zcdb, Zichandiaobodantb.class);
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
