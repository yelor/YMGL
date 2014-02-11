/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.slgl;

import com.jskj.asset.client.bean.entity.CaigoujihuaFindEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.BeanFactory;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author tt
 */
public class JihuaTask extends BaseTask {
    
    private static final Logger logger = Logger.getLogger(JihuaTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "cgjh";
    public static final int pageSize = 10;
    private int pageIndex = 1;

    public JihuaTask(int pageIndex){
        super();
        this.pageIndex = pageIndex;
    }
    
    public JihuaTask(){
        this(1);
    }
    
    @Override
    public Object doBackgrounp() {
        try {
            logger.debug("pagesize:"+pageSize+",pageindex:"+pageIndex);
            CaigoujihuaFindEntity jihua = restTemplate.getForObject(URI+"?pagesize="+pageSize+"&pageindex="+pageIndex, CaigoujihuaFindEntity.class);
            return jihua;
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
        
    }
    
}
