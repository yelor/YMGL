/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ymgl.task;

import com.jskj.asset.client.panel.jichuxinxi.task.*;
import com.jskj.asset.client.bean.entity.UnitFindEntity;
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
public class YimiaobaosunTask extends BaseTask {
    private static final Logger logger = Logger.getLogger(UnitTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "Yimiaobaosun";
    public static final int pageSize = 10;
    private int pageIndex = 1;

    public YimiaobaosunTask() {
        this(1);
    }

    public YimiaobaosunTask(int pageIndex) {
        super();
        this.pageIndex = pageIndex;
    }
    
    

    @Override
    public Object doBackgrounp() {
        try {
            Map map = new HashMap();
            UnitFindEntity units = restTemplate.getForObject(URI + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, UnitFindEntity.class);
            System.out.println("疫苗报损");
            return units;
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
    }

}
