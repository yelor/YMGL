/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.bean.entity.YimiaoshenqingdantbFindEntity;
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
public class YimiaoshenqingdanTask extends BaseTask {
    private static final Logger logger = Logger.getLogger(YimiaoshenqingdanTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "yimiaoshenqingdan";
    public static final int pageSize = 10;
    private int pageIndex = 1;

    public YimiaoshenqingdanTask() {
        this(1);
    }

    public YimiaoshenqingdanTask(int pageIndex) {
        super();
        this.pageIndex = pageIndex;
    }
    
    

    @Override
    public Object doBackgrounp() {
        try {
            Map map = new HashMap();
            YimiaoshenqingdantbFindEntity yimiaoshenqingdans = restTemplate.getForObject(URI + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, YimiaoshenqingdantbFindEntity.class);
//            System.out.println("yimiaoshenqingdan");
            return yimiaoshenqingdans;
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
    }

}
