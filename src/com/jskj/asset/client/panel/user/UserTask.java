/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.user;

import com.jskj.asset.client.bean.entity.Usertb;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.BeanFactory;
import java.util.List;
import org.apache.log4j.Logger;
import org.jdesktop.application.Application;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author woderchen
 */
public class UserTask extends BaseTask {

    private static Logger logger = Logger.getLogger(UserTask.class);
    private String URI = Constants.HTTP + Constants.APPID + "user";

    public UserTask(Application app) {
        super(app);
    }

    @Override
    public Object doBackgrounp() {
        try {
            //使用Spring3 RESTful client来获取http数据
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);
            List<Usertb> users = restTemplate.getForObject(URI, List.class);
            return users;
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
        //此处不写任何内容，因为user panel上有一个task extends这个类，userpanel上重写了这个方法。
    }
}
