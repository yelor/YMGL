/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.user;

import com.jskj.asset.client.bean.entity.Usertb;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.BeanFactory;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author 305027939
 */
public class UserUpdateTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(UserUpdateTask.class);
    public final static int ENTITY_SAVE = 0;
    public final static int ENTITY_UPDATE = 1;
    public final static int ENTITY_DELETE = 2;
    private final String ADD_URI = Constants.HTTP + Constants.APPID + "user/add";
    private final String UPD_URI = Constants.HTTP + Constants.APPID + "user/update";
    private final String delete_URI = Constants.HTTP + Constants.APPID + "user/delete";

    private final Usertb usertb;
    private final int actionType;

    public UserUpdateTask(Usertb usertb, int actionType) {
        this.usertb = usertb;
        this.actionType = actionType;
    }

    @Override
    public Object doBackgrounp() {
        try {
            //使用Spring3 RESTful client来POSThttp数据
            if (actionType == ENTITY_SAVE) {
                restTemplate.postForObject(ADD_URI, usertb, Usertb.class);
            } else if (actionType == ENTITY_UPDATE) {
                restTemplate.postForObject(UPD_URI, usertb, Usertb.class);
            } else if (actionType == ENTITY_DELETE) {
                restTemplate.postForLocation(delete_URI + "/" + usertb.getUserId(),null);
            }
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }

        return STATUS_OK;
    }

    @Override
    public void onSucceeded(Object object) {
         //此处不写任何内容，因为user dialog上有一个task extends这个类，dialog上上重写了这个方法。
    }

}
