/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.jichuxinxi.task;

import com.jskj.asset.client.bean.entity.KehudanweitbFindEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author huiqi
 */
public class KehudanweiTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(KehudanweiTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "kehudanwei";
    private int pageSize = 20;
    private int pageIndex = 1;
    private String conditionSql;
    

    public KehudanweiTask() {
        this(1, 20,"");
    }

    public KehudanweiTask(int pageIndex, int pageSize, String conditionSql) {
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
            KehudanweitbFindEntity kehudanweis = restTemplate.getForObject(URI + "?"+urlParam, KehudanweitbFindEntity.class);
//            System.out.println("kehudanwei");
            return kehudanweis;
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
    }

}
