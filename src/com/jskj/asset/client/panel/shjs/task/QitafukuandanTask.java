/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.shjs.task;

import com.jskj.asset.client.bean.entity.FukuanDetailEntity;
import com.jskj.asset.client.bean.entity.QitafukuanDetailEntity;
import com.jskj.asset.client.layout.ws.*;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTask;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author woderchen
 */
public abstract class QitafukuandanTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(QitafukuandanTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "qitafukuandan/add";
    private QitafukuanDetailEntity bean;

    public QitafukuandanTask(QitafukuanDetailEntity bean) {
        this.bean = bean;
    }

    @Override
    public Object doBackgrounp() {
        try {
            ComResponse response = restTemplate.postForObject(URI, bean,ComResponse.class);
            return response;
        } catch (RestClientException e) {
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
        if (object instanceof ComResponse) {
            responseResult((ComResponse<QitafukuanDetailEntity>) object);
        } else {
            clientView.setStatus("response data is not a valid object", AssetMessage.ERROR_MESSAGE);
        }
    }

    public abstract void responseResult(ComResponse<QitafukuanDetailEntity> response);
}
