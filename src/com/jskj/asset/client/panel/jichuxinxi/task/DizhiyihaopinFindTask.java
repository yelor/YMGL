/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.jichuxinxi.task;

import com.jskj.asset.client.bean.entity.DizhiyihaopinAll;
import com.jskj.asset.client.layout.ws.*;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.BeanFactory;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author woderchen
 */
public abstract class DizhiyihaopinFindTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(DizhiyihaopinFindTask.class);
    private final String URI = Constants.HTTP + Constants.APPID;
    private int pageSize = 20;
    private int pageIndex = 1;
    private String serviceId;
    private String conditionSql;

    public DizhiyihaopinFindTask(int pageIndex, int pageSize, String serviceId) {
        this(pageIndex, pageSize, serviceId, "");
    }

    public DizhiyihaopinFindTask(int pageIndex, int pageSize, String serviceId, String conditionSql) {
        super();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.serviceId = serviceId;
        this.conditionSql = conditionSql;
    }

    public DizhiyihaopinFindTask(String serviceId) {
        this(1, 20, serviceId);
    }

    @Override
    public Object doBackgrounp() {
        try {

            String urlParam = "pagesize=" + pageSize + "&pageindex=" + pageIndex + "&conditionSql=" + conditionSql;
            logger.info("URL parameter:" + urlParam);
//            CommFindEntity<T> response = restTemplate.getForObject(URI + serviceId + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, CommFindEntity.class);
            CommFindEntity<DizhiyihaopinAll> response = restTemplate.exchange(URI + serviceId + "?"+urlParam,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CommFindEntity<DizhiyihaopinAll>>() {
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
        if (object instanceof CommFindEntity) {
            responseResult((CommFindEntity<DizhiyihaopinAll>) object);
        } else {
            clientView.setStatus("response data is not a valid object", AssetMessage.ERROR_MESSAGE);
        }
    }

    public abstract void responseResult(CommFindEntity<DizhiyihaopinAll> response);
}
