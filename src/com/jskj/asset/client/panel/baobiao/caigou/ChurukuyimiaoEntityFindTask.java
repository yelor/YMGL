/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.baobiao.caigou;

import com.jskj.asset.client.bean.entity.ChurukujiluyimiaoEntity;
import com.jskj.asset.client.layout.ws.*;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTask;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author woderchen
 */
public abstract class ChurukuyimiaoEntityFindTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(ChurukuyimiaoEntityFindTask.class);
    private final String URI = Constants.HTTP + Constants.APPID;
    private String serviceId;
    private HashMap map;

    public ChurukuyimiaoEntityFindTask(HashMap map, String serviceId) {
        super();
        this.map = map;
        this.serviceId = serviceId;
    }

    public ChurukuyimiaoEntityFindTask(String serviceId) {
        this(new HashMap(), serviceId);
    }

    @Override
    public Object doBackgrounp() {
        try {
        System.out.println("出入库记录表的查询");
            StringBuilder paramater = new StringBuilder();
            if (map.get("yimiaoName") != null && !map.get("yimiaoName").toString().trim().equals("")) {
                paramater.append("yimiaoName=").append(map.get("yimiaoName")).append("&");
            }
            if (map.get("startDate") != null && !map.get("startDate").toString().trim().equals("")) {
                paramater.append("startDate=").append(map.get("startDate")).append("&");
            }
            if (map.get("endDate") != null && !map.get("endDate").toString().trim().equals("")) {
                paramater.append("endDate=").append(map.get("endDate")).append("&");
            }
//            paramater.deleteCharAt(paramater.length() - 1);

            logger.debug("parameter map:" + paramater);
            //使用Spring3 RESTful client来获取http数据
            CommFindEntity<ChurukujiluyimiaoEntity> response = restTemplate.exchange(URI + serviceId + "?" + paramater,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CommFindEntity<ChurukujiluyimiaoEntity>>() {
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
            responseResult((CommFindEntity<ChurukujiluyimiaoEntity>) object);
        } else {
            clientView.setStatus("response data is not a valid object", AssetMessage.ERROR_MESSAGE);
        }
    }

    public abstract void responseResult(CommFindEntity<ChurukujiluyimiaoEntity> response);
}
