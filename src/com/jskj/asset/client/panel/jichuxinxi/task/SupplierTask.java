/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.jichuxinxi.task;

import com.jskj.asset.client.bean.entity.SupplierFindEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.BeanFactory;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author huiqi
 */
public class SupplierTask extends BaseTask {
    private static final Logger logger = Logger.getLogger(SupplierTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "supplier";
    private int pageSize = 10;
    private int pageIndex = 1;

    public SupplierTask() {
        this(1,20);
    }

    public SupplierTask(int pageIndex,int pageSize) {
        super();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
    
    

    @Override
    public Object doBackgrounp() {
        try {
            Map map = new HashMap();
            //使用Spring3 RESTful client来获取http数据
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);
            SupplierFindEntity suppliers = restTemplate.getForObject(URI + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, SupplierFindEntity.class);
            return suppliers;
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
    }

}
