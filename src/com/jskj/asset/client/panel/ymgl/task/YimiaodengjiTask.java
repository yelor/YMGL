/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.bean.entity.YimiaodengjitbFindEntity;
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
public class YimiaodengjiTask extends BaseTask {
    private static final Logger logger = Logger.getLogger(YimiaodengjiTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "yimiaodengji";
    public static final int pageSize = 10;
    private int pageIndex = 1;

    public YimiaodengjiTask() {
        this(1);
    }

    public YimiaodengjiTask(int pageIndex) {
        super();
        this.pageIndex = pageIndex;
    }
    
    

    @Override
    public Object doBackgrounp() {
        try {
            Map map = new HashMap();
            //使用Spring3 RESTful client来获取http数据
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);
            YimiaodengjitbFindEntity yimiaodengjis = restTemplate.getForObject(URI + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, YimiaodengjitbFindEntity.class);
//            System.out.println("yimiaodengji");
            return yimiaodengjis;
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
    }

}
