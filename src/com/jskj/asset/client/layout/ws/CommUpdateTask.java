/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout.ws;

import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.BeanFactory;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author 305027939
 * @param <T>
 */
public abstract class CommUpdateTask<T> extends BaseTask {

    private static final Logger logger = Logger.getLogger(CommUpdateTask.class);
    private final String URI = Constants.HTTP + Constants.APPID;

    private final T requestData;
    private final String serviceId;

    public CommUpdateTask(T requestData, String serviceId) {
        this.requestData = requestData;
        this.serviceId = serviceId;
    }

    @Override
    public Object doBackgrounp() {
        ComResponse<T> response = null;
        try {
            //使用Spring3 RESTful client来POSThttp数据
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);
           // response = restTemplate.postForObject(URI + serviceId, requestData, ComResponse.class);
            
          response = restTemplate.exchange(URI + serviceId, HttpMethod.POST, new HttpEntity<T>(requestData),  new ParameterizedTypeReference<ComResponse<T>>(){}).getBody();
            
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }

        return response;
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
            responseResult((ComResponse<T>) object);
        } else {
            clientView.setStatus("response data is not a valid object", AssetMessage.ERROR_MESSAGE);
        }
    }

    public abstract void responseResult(ComResponse<T> response);

}
