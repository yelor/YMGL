/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.user;

import com.jskj.asset.client.bean.entity.Pkgenerator;
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
public abstract class PkFindTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(PkFindTask.class);
    private final String URI = Constants.HTTP + Constants.APPID;
    private int pageSize = 20;
    private int pageIndex = 1;
    private String serviceId;
    private String conditionSql;

    public PkFindTask(int pageIndex, int pageSize, String serviceId, String conditionSql) {
        super();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.serviceId = serviceId;
        this.conditionSql = conditionSql;
    }

    public PkFindTask(String serviceId) {
        this(1, 20, serviceId,"");
    }

    @Override
    public Object doBackgrounp() {
        try {
            Map map = new HashMap();
            map.put("pagesize", String.valueOf(pageSize));
            map.put("pageindex", String.valueOf(pageIndex));
            map.put("conditionSql", conditionSql);

            logger.debug("pagesize:" + pageSize + ",pageindex:" + pageIndex + ",serviceId:" + serviceId);
//            CommFindEntity<T> response = restTemplate.getForObject(URI + serviceId + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, CommFindEntity.class);
            CommFindEntity<Pkgenerator> response = restTemplate.exchange(URI + serviceId + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, 
                          HttpMethod.GET, 
                          null, 
                          new ParameterizedTypeReference<CommFindEntity<Pkgenerator>>(){}).getBody();
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
            responseResult((CommFindEntity<Pkgenerator>) object);
        } else {
            clientView.setStatus("response data is not a valid object", AssetMessage.ERROR_MESSAGE);
        }
    }

    public abstract void responseResult(CommFindEntity<Pkgenerator> response);
}
