/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.bean.entity.ShenPiEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author tt
 */
public class ShenPiTask extends BaseTask{

    private static final Logger logger = Logger.getLogger(ShenPiTask.class);
    
    //对应的controller在YimiaoShenpiController里面
    private final String SP_URI = Constants.HTTP + Constants.APPID + "yimiaocaigoushenpi/shenpi";
    
    private ShenPiEntity yimiaosp;
    
    public ShenPiTask(ShenPiEntity yimiaosp){
        this.yimiaosp = yimiaosp;
    }
    
    @Override
    public Object doBackgrounp() {
        try{
            restTemplate.postForObject(SP_URI, yimiaosp,ShenPiEntity.class );
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
