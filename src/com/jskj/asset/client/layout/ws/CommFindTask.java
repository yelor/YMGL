/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout.ws;

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
 * @param <T>  目前spring的ParameterizedTypeReference这个有点问题?????
 * 用这个类，需要拷贝这个类，改名，并且把T改成真实的entity bean，就可以直接用了。
 */
public abstract class CommFindTask<T> extends BaseTask {

    private static final Logger logger = Logger.getLogger(CommFindTask.class);
    private final String URI = Constants.HTTP + Constants.APPID;
    private int pageSize = 20;
    private int pageIndex = 1;
    private String serviceId;
    private String conditionSql;

    public CommFindTask(int pageIndex, int pageSize, String serviceId, String conditionSql) {
        super();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.serviceId = serviceId;
        this.conditionSql = conditionSql;
    }

    public CommFindTask(String serviceId) {
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
            //使用Spring3 RESTful client来获取http数据
//            CommFindEntity<T> response = restTemplate.getForObject(URI + serviceId + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, CommFindEntity.class);
            CommFindEntity<T> response = restTemplate.exchange(URI + serviceId + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, 
                          HttpMethod.GET, 
                          null, 
                          new ParameterizedTypeReference<CommFindEntity<T>>(){}).getBody();
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
            responseResult((CommFindEntity<T>) object);
        } else {
            clientView.setStatus("response data is not a valid object", AssetMessage.ERROR_MESSAGE);
        }
    }

    public abstract void responseResult(CommFindEntity<T> response);
}
