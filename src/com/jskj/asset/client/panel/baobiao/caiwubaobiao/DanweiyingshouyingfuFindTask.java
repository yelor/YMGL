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
import java.util.HashMap;
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
    private final HashMap map;
    public static final int pageSize = 10;
    private int pageIndex = 1;   

    public DanweiyingshouyingfuFindTask(HashMap map) {
        super();
        this.map = map;
    }

    @Override
    public Object doBackgrounp() {
        try {        
            StringBuilder paramater = new StringBuilder();
            if (map.get("startDate") != null && !map.get("startDate").toString().trim().equals("")) {
                paramater.append("startDate=").append(map.get("startDate")).append("&");
            }
            if (map.get("endDate") != null && !map.get("endDate").toString().trim().equals("")) {
                paramater.append("endDate=").append(map.get("endDate")).append("&");
            }
            if (map.get("conditionSql") != null && !map.get("conditionSql").toString().trim().equals("")) {
                paramater.append("conditionSql=").append(map.get("conditionSql")).append("&");
            }
            if (map.get("pageIndex") != null && !map.get("pageIndex").toString().trim().equals("")) {
                paramater.append("pageIndex=").append(map.get("pageIndex")).append("&");
            }
            paramater.append("pageSize=").append(10);
            logger.debug("parameter map:" + paramater);
    
            //使用Spring3 RESTful client来获取http数据
            DanweiyingshouyingfuFindEntity response = restTemplate.exchange(URI + "/" + 
                    map.get("serviceId") + "?"+ paramater,
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
