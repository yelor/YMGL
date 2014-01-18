/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.slgl;

import com.jskj.asset.client.bean.entity.CaiGouShenQingFindEntity;
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
public class ChaXunTask extends BaseTask{

    public static final Logger logger = Logger.getLogger(ChaXunTask.class);
    private final String CX_URI = Constants.HTTP + Constants.APPID + "cgsqsp";
    public static final int pageSize = 10;
    private int pageIndex = 1;    
    private String user = "";
    
    public ChaXunTask(String user,int pageIndex){
        super();
        this.pageIndex = pageIndex;
        this.user = user;
    }
    
    public ChaXunTask(String user){
        this(user,1);
    }
    
    @Override
    public Object doBackgrounp() {
        try{
            logger.debug("pagesize:"+pageSize+",pageindex:"+pageIndex);
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);
            CaiGouShenQingFindEntity cgsqs = restTemplate.getForObject(CX_URI + "/" + user +"?pagesize="+pageSize+"&pageindex="+pageIndex,CaiGouShenQingFindEntity.class);
            return cgsqs;
        }catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
        
    }
    
}
