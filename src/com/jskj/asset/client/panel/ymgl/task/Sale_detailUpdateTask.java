/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.bean.entity.Sale_detail_tb;
import com.jskj.asset.client.bean.entity.Saletb;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.BeanFactory;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author huiqi
 */
public class Sale_detailUpdateTask extends BaseTask{
    private static final Logger logger = Logger.getLogger(Sale_detailUpdateTask.class);
    public final static int ENTITY_SAVE = 0;
    public final static int ENTITY_UPDATE = 1;
    public final static int ENTITY_DELETE = 2;
    private final String ADD_URI1 = Constants.HTTP + Constants.APPID + "sale_detail/add";
    private final String ADD_URI2 = Constants.HTTP + Constants.APPID + "sale/add";
    private final String UPD_URI1 = Constants.HTTP + Constants.APPID + "sale_detail/update";
    private final String UPD_URI2 = Constants.HTTP + Constants.APPID + "sale/update";
    private final String delete_URI1 = Constants.HTTP + Constants.APPID + "sale_detail/delete";
    private final String delete_URI2 = Constants.HTTP + Constants.APPID + "sale/delete";
    
    private final Sale_detail_tb sale_detail;
    private final Saletb sale;
    private final int actionType;

    public Sale_detailUpdateTask(Sale_detail_tb sale_detail,Saletb sale, int actionType) {
        this.sale=sale;
        this.sale_detail = sale_detail;
        this.actionType = actionType;
    }
    
    

    @Override
    public Object doBackgrounp() {
         try {
            //使用Spring3 RESTful client来POSThttp数据
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);
            if (actionType == ENTITY_SAVE) {
                restTemplate.postForObject(ADD_URI2, sale, Saletb.class);
                restTemplate.postForObject(ADD_URI1, sale_detail, Sale_detail_tb.class);
            } else if (actionType == ENTITY_UPDATE) {
                restTemplate.postForObject(UPD_URI2, sale, Saletb.class);
                restTemplate.postForObject(UPD_URI1, sale_detail, Sale_detail_tb.class);
            } else if (actionType == ENTITY_DELETE) {
                restTemplate.postForLocation(delete_URI2 + "/" + sale.getSaleId(),null);
                restTemplate.postForLocation(delete_URI1 + "/" + sale_detail.getSaleDetailId(),null);
            }
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }

        return STATUS_OK;
    }

    @Override
    public void onSucceeded(Object object) {
    }
    
}
