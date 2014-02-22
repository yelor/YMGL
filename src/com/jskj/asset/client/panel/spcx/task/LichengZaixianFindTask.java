/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.spcx.task;

import com.jskj.asset.client.panel.user.*;
import com.jskj.asset.client.bean.entity.Appparam;
import com.jskj.asset.client.bean.entity.RecordProcessEntity;
import com.jskj.asset.client.layout.ws.*;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTask;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author woderchen
 */
public abstract class LichengZaixianFindTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(ParamFindTask.class);
    private final String URI = Constants.HTTP + Constants.APPID;
    private final String serviceId = "/spfind/lczx";
    private final String danjuID;

    public LichengZaixianFindTask(String danjuID) {
        super();
        this.danjuID = danjuID;
    }


    @Override
    public Object doBackgrounp() {
        try {
            //使用Spring3 RESTful client来获取http数据            
//            CommFindEntity<T> response = restTemplate.getForObject(URI + serviceId + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, CommFindEntity.class);
            CommFindEntity<RecordProcessEntity> response = restTemplate.exchange(URI + serviceId + "/"+danjuID,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CommFindEntity<RecordProcessEntity>>() {
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
            responseResult((CommFindEntity<RecordProcessEntity>) object);
        } else {
            clientView.setStatus("response data is not a valid object", AssetMessage.ERROR_MESSAGE);
        }
    }

    public abstract void responseResult(CommFindEntity<RecordProcessEntity> response);
}
