/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.spcx.task;

import com.jskj.asset.client.panel.user.*;
import com.jskj.asset.client.bean.report.Yimiaoxiaoshoudetail;
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
public abstract class Yimiao2leichengbenFindTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(ParamFindTask.class);
    private final String URI = Constants.HTTP + Constants.APPID;
    private final HashMap map;

    public Yimiao2leichengbenFindTask(HashMap map) {
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
            if (map.get("pageindex") != null && !map.get("pageindex").toString().trim().equals("")) {
                paramater.append("pageindex=").append(map.get("pageindex")).append("&");
            }
            if (map.get("pagesize") != null && !map.get("pagesize").toString().trim().equals("")) {
                paramater.append("pagesize=").append(map.get("pagesize")).append("&");
            }
            if (map.get("conditionSql") != null && !map.get("conditionSql").toString().trim().equals("")) {
                paramater.append("conditionSql=").append(map.get("conditionSql")).append("&");
            }
            paramater.deleteCharAt(paramater.length() - 1);

            logger.debug("parameter map:" + paramater);

            logger.info("URL parameter:" + paramater.toString());
            //使用Spring3 RESTful client来获取http数据            
//            CommFindEntity<T> response = restTemplate.getForObject(URI + serviceId + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, CommFindEntity.class);
            CommFindEntity<Yimiaoxiaoshoudetail> response = restTemplate.exchange(URI + "/spfind/yimiao2?"+ paramater,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CommFindEntity<Yimiaoxiaoshoudetail>>() {
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
            responseResult((CommFindEntity<Yimiaoxiaoshoudetail>) object);
        } else {
            clientView.setStatus("response data is not a valid object", AssetMessage.ERROR_MESSAGE);
        }
    }

    public abstract void responseResult(CommFindEntity<Yimiaoxiaoshoudetail> response);
}
