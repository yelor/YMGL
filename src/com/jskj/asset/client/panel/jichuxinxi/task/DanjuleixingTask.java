/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.jichuxinxi.task;

import com.jskj.asset.client.bean.entity.DanjuleixingtbFindEntity;
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
public class DanjuleixingTask extends BaseTask {
    private static final Logger logger = Logger.getLogger(DanjuleixingTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "danjuleixing";
    public static final int pageSize = 10;
    private int pageIndex = 1;

    public DanjuleixingTask() {
        this(1);
    }

    public DanjuleixingTask(int pageIndex) {
        super();
        this.pageIndex = pageIndex;
    }
    
    

    @Override
    public Object doBackgrounp() {
        try {
            Map map = new HashMap();
            //使用Spring3 RESTful client来获取http数据
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);
            DanjuleixingtbFindEntity danjuleixings = restTemplate.getForObject(URI + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, DanjuleixingtbFindEntity.class);
//            System.out.println("danjuleixing");
            return danjuleixings;
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
    }

}
