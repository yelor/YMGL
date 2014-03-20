/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.baobiao.caiwubaobiao;

import com.jskj.asset.client.bean.entity.DanweiyingshouyingfuFindEntity;
import com.jskj.asset.client.panel.baobiao.caigou.*;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTask;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author woderchen
 */
public abstract class DanweiyingshouyingfuFindTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(ReportCaiGouFindTask.class);
    private final String URI = Constants.HTTP + Constants.APPID;
    private String serviceId;

    public DanweiyingshouyingfuFindTask(String serviceId) {
        super();
        this.serviceId = serviceId;
    }

    @Override
    public Object doBackgrounp() {
        try {            
            //使用Spring3 RESTful client来获取http数据
            DanweiyingshouyingfuFindEntity response = restTemplate.exchange(URI + serviceId + "?",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<DanweiyingshouyingfuFindEntity>() {
                    }).getBody();
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
        if (object instanceof DanweiyingshouyingfuFindEntity) {
            responseResult((DanweiyingshouyingfuFindEntity) object);
        } else {
            clientView.setStatus("response data is not a valid object", AssetMessage.ERROR_MESSAGE);
        }
    }

    public abstract void responseResult(DanweiyingshouyingfuFindEntity response);
}
