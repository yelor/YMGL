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
public class YimiaoDanjuChaxunTask extends BaseTask{

    public static final Logger logger = Logger.getLogger(YimiaoDanjuChaxunTask.class);
    private final String CX_URI = Constants.HTTP + Constants.APPID + "yimiaoshenqing";
    public static final int pageSize = 10;
    private int pageIndex = 1;    
    private String user = "";
    
    public YimiaoDanjuChaxunTask(String user,int pageIndex){
        super();
        this.pageIndex = pageIndex;
        this.user = user;
    }
    
    public YimiaoDanjuChaxunTask(String user){
        this(user,1);
    }
    
    @Override
    public Object doBackgrounp() {
        try{
            logger.debug("pagesize:"+pageSize+",pageindex:"+pageIndex);
            YimiaoShenpiFindEntity yimiaoshenpi = restTemplate.getForObject(CX_URI + "/" + user +"?pagesize="+pageSize+"&pageindex="+pageIndex,YimiaoShenpiFindEntity.class);
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
