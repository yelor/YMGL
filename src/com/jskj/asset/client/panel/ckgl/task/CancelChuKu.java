/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.ckgl.task;

import com.jskj.asset.client.bean.entity.SaleyimiaoEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author tt
 */
public class CancelChuKu extends BaseTask{

    static final Logger logger = Logger.getLogger(CancelChuKu.class);
    private final String ADD_URI = Constants.HTTP + Constants.APPID + "yimiaochuku/cancel";
    
    private final List<SaleyimiaoEntity> zc;
    
    public CancelChuKu(List<SaleyimiaoEntity> zc) {
        this.zc = zc;
    }
    
    @Override
    public Object doBackgrounp() {
        try{
            restTemplate.postForObject(ADD_URI, zc, Integer.class);
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
