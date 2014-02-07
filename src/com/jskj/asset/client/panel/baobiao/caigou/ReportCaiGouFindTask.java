/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.baobiao.caigou;

import com.jskj.asset.client.bean.report.CaigouReport;
import com.jskj.asset.client.layout.ws.*;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.BeanFactory;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author woderchen
 */
public abstract class ReportCaiGouFindTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(ReportCaiGouFindTask.class);
    private final String URI = Constants.HTTP + Constants.APPID;
    private String serviceId;
    private HashMap map;

    public ReportCaiGouFindTask(HashMap map, String serviceId) {
        super();
        this.map = map;
        this.serviceId = serviceId;
    }

    public ReportCaiGouFindTask(String serviceId) {
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
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);
            CommFindEntity<CaigouReport> response = restTemplate.exchange(URI + serviceId + "?" + paramater,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CommFindEntity<CaigouReport>>() {
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
            responseResult((CommFindEntity<CaigouReport>) object);
        } else {
            clientView.setStatus("response data is not a valid object", AssetMessage.ERROR_MESSAGE);
        }
    }

    public abstract void responseResult(CommFindEntity<CaigouReport> response);
}
