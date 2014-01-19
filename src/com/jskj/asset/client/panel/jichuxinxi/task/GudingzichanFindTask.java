/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.jichuxinxi.task;

import com.jskj.asset.client.bean.entity.Appparam;
import com.jskj.asset.client.bean.entity.GudingzichanFindEntity;
import com.jskj.asset.client.layout.ws.*;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.BeanFactory;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author woderchen
 */
public abstract class GudingzichanFindTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(GudingzichanFindTask.class);
    private final String URI = Constants.HTTP + Constants.APPID;
    private int pageSize = 20;
    private int pageIndex = 1;
    private String serviceId;
    private String conditionSql;

    public GudingzichanFindTask(int pageIndex, int pageSize, String serviceId) {
        this(pageIndex, pageSize, serviceId, "");
    }

    public GudingzichanFindTask(int pageIndex, int pageSize, String serviceId, String conditionSql) {
        super();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.serviceId = serviceId;
        this.conditionSql = conditionSql;
    }

    public GudingzichanFindTask(String serviceId) {
        this(1, 20, serviceId);
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
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);
//            CommFindEntity<T> response = restTemplate.getForObject(URI + serviceId + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, CommFindEntity.class);
            GudingzichanFindEntity gudingzhichan = restTemplate.getForObject(URI+serviceId+ "?pagesize=" + pageSize + "&pageindex=" + pageIndex, GudingzichanFindEntity.class);
            return gudingzhichan;
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
        if (object instanceof GudingzichanFindEntity) {
            responseResult((GudingzichanFindEntity) object);
        } else {
            clientView.setStatus("response data is not a valid object", AssetMessage.ERROR_MESSAGE);
        }
    }

    public abstract void responseResult(GudingzichanFindEntity response);
}
