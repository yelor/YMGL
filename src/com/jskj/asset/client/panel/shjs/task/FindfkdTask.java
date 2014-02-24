/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.shjs.task;

import com.jskj.asset.client.panel.slgl.*;
import com.jskj.asset.client.bean.entity.FukuanshenqingDetailEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.layout.ws.CommFindEntity;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author tt
 */
public abstract class FindfkdTask extends BaseTask{

    public static final Logger logger = Logger.getLogger(ChaXunTask.class);
    private final String CX_URI = Constants.HTTP + Constants.APPID + "fukuandan/findall";
    public static final int pageSize = 10;
    private int pageIndex = 1;
    
    public FindfkdTask(int pageIndex){
        super();
        this.pageIndex = pageIndex;
    }
    
    public FindfkdTask(){
        this(1);
    }
    
    @Override
    public Object doBackgrounp() {
        try{
            logger.debug("pagesize:"+pageSize+",pageindex:"+pageIndex);
            CommFindEntity<FukuanshenqingDetailEntity> sqs = restTemplate.exchange(CX_URI,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CommFindEntity<FukuanshenqingDetailEntity>>() {
                    }).getBody();
            return sqs;
        }catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
        if (object == null) {
            clientView.setStatus("response data is null", AssetMessage.ERROR_MESSAGE);
            return;
        }
        if (object instanceof Exception) {
            Exception e = (Exception) object;
            clientView.setStatus(e.getMessage(), AssetMessage.ERROR_MESSAGE);
            logger.error(e);
            return;
        }
        if (object instanceof CommFindEntity) {
            responseResult((CommFindEntity<FukuanshenqingDetailEntity>) object);
        } else {
            clientView.setStatus("response data is not a valid object", AssetMessage.ERROR_MESSAGE);
        }
    }

    public abstract void responseResult(CommFindEntity<FukuanshenqingDetailEntity> response);
}
