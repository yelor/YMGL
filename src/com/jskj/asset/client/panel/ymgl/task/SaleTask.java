/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.bean.entity.Sale_detail_tbFindEntity;
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
public class SaleTask extends BaseTask {
    private static final Logger logger = Logger.getLogger(SaleTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "sale_detail";
    public static final int pageSize = 10;
    private int pageIndex = 1;

    public SaleTask() {
        this(1);
    }

    public SaleTask(int pageIndex) {
        super();
        this.pageIndex = pageIndex;
    }
    
    

    @Override
    public Object doBackgrounp() {
        try {
            Map map = new HashMap();
            Sale_detail_tbFindEntity sale_details = restTemplate.getForObject(URI + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, Sale_detail_tbFindEntity.class);
//            System.out.println("sale_detail");
            return sale_details;
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
    }

}
