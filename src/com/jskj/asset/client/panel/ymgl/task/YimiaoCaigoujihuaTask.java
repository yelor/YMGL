/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.bean.entity.YimiaoCaigoujihuaFindEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author tt
 */
public class YimiaoCaigoujihuaTask extends BaseTask {
    
    private static final Logger logger = Logger.getLogger(YimiaoCaigoujihuaTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "yimiaocaigoujihua";
    public static final int pageSize = 10;
    private int pageIndex = 1;

    public YimiaoCaigoujihuaTask(int pageIndex){
        super();
        this.pageIndex = pageIndex;
    }
    
    public YimiaoCaigoujihuaTask(){
        this(1);
    }
    
    @Override
    public Object doBackgrounp() {
        try {
            logger.debug("pagesize:"+pageSize+",pageindex:"+pageIndex);
            YimiaoCaigoujihuaFindEntity yimiaocaigoujihua = restTemplate.getForObject(URI+"?pagesize="+pageSize+"&pageindex="+pageIndex, YimiaoCaigoujihuaFindEntity.class);
            return yimiaocaigoujihua;
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
        
    }
    
}
