/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.jichuxinxi.task;

import com.jskj.asset.client.bean.entity.DepartmentFindEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.BeanFactory;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author woderchen
 */
public class BuMenTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(BuMenTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "dp";
    public static final int pageSize = 1000;
    private int pageIndex = 1;

    public BuMenTask(int pageIndex) {
        super();
        this.pageIndex = pageIndex;
    }

    public BuMenTask() {
        this(1);
    }

    @Override
    public Object doBackgrounp() {
        try {
            DepartmentFindEntity dps = restTemplate.getForObject(URI, DepartmentFindEntity.class);
            return dps;
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
        //此处不写任何内容，因为dp panel上有一个task extends这个类，dppanel上重写了这个方法。
    }
}
