/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.user;

import com.jskj.asset.client.bean.entity.UsertbFindEntity;
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
 * @author woderchen
 */
public class UserTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(UserTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "user";
    public static final int pageSize = 10;
    private int pageIndex = 1;

    public UserTask(int pageIndex) {
        super();
        this.pageIndex = pageIndex;
    }

    public UserTask() {
        this(1);
    }

    @Override
    public Object doBackgrounp() {
        try {
            Map map = new HashMap();
            map.put("pagesize", String.valueOf(pageSize));
            map.put("pageindex", String.valueOf(pageIndex));
            
            logger.debug("pagesize:"+pageSize+",pageindex:"+pageIndex);
            //使用Spring3 RESTful client来获取http数据
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);
            UsertbFindEntity users = restTemplate.getForObject(URI+"?pagesize="+pageSize+"&pageindex="+pageIndex, UsertbFindEntity.class);
            return users;
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
        //此处不写任何内容，因为user panel上有一个task extends这个类，userpanel上重写了这个方法。
    }
}
