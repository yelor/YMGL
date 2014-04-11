/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.baobiao.xiaoshou;

import com.jskj.asset.client.bean.report.SalesReport;
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
public abstract class ReportSalesFindTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(ReportSalesFindTask.class);
    private final String URI = Constants.HTTP + Constants.APPID;
    private String serviceId;
    private HashMap map;

    public ReportSalesFindTask(HashMap map, String serviceId) {
        super();
        this.map = map;
        this.serviceId = serviceId;
    }

    public ReportSalesFindTask(String serviceId) {
        this(new HashMap(), serviceId);
    }

    @Override
    public Object doBackgrounp() {
        try {
            StringBuilder paramater = new StringBuilder();
            if (map.get("idflag") != null && !map.get("idflag").toString().trim().equals("")) {
                paramater.append("idflag=").append(map.get("idflag")).append("&");
            }
            if (map.get("startDate") != null && !map.get("startDate").toString().trim().equals("")) {
                paramater.append("startDate=").append(map.get("startDate")).append("&");
            }
            if (map.get("endDate") != null && !map.get("endDate").toString().trim().equals("")) {
                paramater.append("endDate=").append(map.get("endDate")).append("&");
            }
            paramater.deleteCharAt(paramater.length() - 1);

            logger.debug("parameter map:" + paramater);
            //使用Spring3 RESTful client来获取http数据
            CommFindEntity<SalesReport> response = restTemplate.exchange(URI + serviceId + "?" + paramater,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CommFindEntity<SalesReport>>() {
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
            responseResult((CommFindEntity<SalesReport>) object);
        } else {
            clientView.setStatus("response data is not a valid object", AssetMessage.ERROR_MESSAGE);
        }
    }

    public abstract void responseResult(CommFindEntity<SalesReport> response);
}
