/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.slgl;

import com.jskj.asset.client.bean.entity.GudingzichanFindEntity;
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
public class GudingzichanTask extends BaseTask{

    public static final Logger logger = Logger.getLogger(GudingzichanTask.class);
    private final String CX_URI = Constants.HTTP + Constants.APPID + "gdzc";
    public static final int pageSize = 10;
    private int pageIndex = 1;   
    private String sql;
    
    public GudingzichanTask(String sql,int pageIndex){
        super();
        this.pageIndex = pageIndex;
        this.sql = sql;
    }
    
    public GudingzichanTask(String sql){
        this(sql,1);
    }
    
    @Override
    public Object doBackgrounp() {
        try{
            logger.debug("pagesize:"+pageSize+",pageindex:"+pageIndex);
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);
            GudingzichanFindEntity gdzc = restTemplate.getForObject(CX_URI +"?pagesize="+pageSize+"&pageindex="+pageIndex+"&conditionSql="+sql,GudingzichanFindEntity.class);
            System.out.println("GudingzichanFindEntity" + gdzc.getResult().size());
            return gdzc;
        }catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
        
    }
    
}
