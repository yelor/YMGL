/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.jichuxinxi.task;

import com.jskj.asset.client.bean.entity.ReduceTypetbFindEntity;
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
public class ReduceTypeTask extends BaseTask {
    private static final Logger logger = Logger.getLogger(ReduceTypeTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "reduceType";
    private int pageSize;
    private int pageIndex;

    public ReduceTypeTask() {
        this(1,20);
    }

    public ReduceTypeTask(int pageIndex,int pageSize) {
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
            ReduceTypetbFindEntity reduceTypes = restTemplate.getForObject(URI + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, ReduceTypetbFindEntity.class);
            System.out.println("reduceType");
            return reduceTypes;
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
    }

}
