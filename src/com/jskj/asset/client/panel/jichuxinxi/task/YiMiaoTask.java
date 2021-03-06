/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.jichuxinxi.task;

import com.jskj.asset.client.bean.entity.YiMiaotbFindEntity;
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
public class YiMiaoTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(YiMiaoTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "yimiao";
    private int pageSize;
    private int pageIndex;
    private String conditionSql;

    public YiMiaoTask() {
        this(0, 20, "");
    }

    public YiMiaoTask(int pageIndex, int pageSize, String conditionSql) {
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
            YiMiaotbFindEntity yimiaos = restTemplate.getForObject(URI + "?"+urlParam, YiMiaotbFindEntity.class);
            return yimiaos;
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
    }

}
