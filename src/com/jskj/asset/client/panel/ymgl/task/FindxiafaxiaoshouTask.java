/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.bean.entity.YimiaoShenpiFindEntity;
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
public class FindxiafaxiaoshouTask extends BaseTask{

    public static final Logger logger = Logger.getLogger(FindxiafaxiaoshouTask.class);
    private final String CX_URI = Constants.HTTP + Constants.APPID + "yimiaoxiaoshoushenqing";
    public static final int pageSize = 20;
    private int pageIndex = 1;    
    
    public FindxiafaxiaoshouTask(int pageIndex){
        super();
        this.pageIndex = pageIndex;
    }
    
    public FindxiafaxiaoshouTask(){
        this(1);
    }
    
    @Override
    public Object doBackgrounp() {
        try{
            logger.debug("pagesize:"+pageSize+",pageindex:"+pageIndex);
            YimiaoShenpiFindEntity yimiaoshenpi = restTemplate.getForObject(CX_URI+"?pagesize="+pageSize+"&pageindex="+pageIndex,YimiaoShenpiFindEntity.class);
            return yimiaoshenpi;
        }catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
        
    }
    
}
