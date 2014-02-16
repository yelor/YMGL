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
    private String conditionSql;

    public SupplierTask() {
        this(1, 20, "");
    }

    public SupplierTask(int pageIndex, int pageSize, String conditionSql) {
        super();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.conditionSql = conditionSql;
    }

    @Override
    public Object doBackgrounp() {
        try {
            String urlParam = "pagesize=" + pageSize + "&pageindex=" + pageIndex + "&conditionSql=" + conditionSql;
            logger.info("URL parameter:" + urlParam);
            SupplierFindEntity suppliers = restTemplate.getForObject(URI + "?" + urlParam, SupplierFindEntity.class);
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
