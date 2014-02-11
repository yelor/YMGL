/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.jichuxinxi.task;

import com.jskj.asset.client.bean.entity.KehudanweitbFindEntity;
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
public class KehudanweiTask extends BaseTask {
    private static final Logger logger = Logger.getLogger(KehudanweiTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "kehudanwei";
    private int pageSize = 20;
    private int pageIndex = 1;

    public KehudanweiTask() {
        this(1,20);
    }

    public KehudanweiTask(int pageIndex,int pageSize) {
        super();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
    
    

    @Override
    public Object doBackgrounp() {
        try {
            Map map = new HashMap();
            KehudanweitbFindEntity kehudanweis = restTemplate.getForObject(URI + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, KehudanweitbFindEntity.class);
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
