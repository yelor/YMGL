/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.baobiao.kucun;

import com.jskj.asset.client.bean.entity.KucunchaxunFindEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTask;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author woderchen
 */
public class KucunchaxunTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(KucunchaxunTask.class);
    private final String URI = Constants.HTTP + Constants.APPID + "kucunchaxun";
    private int pageSize = 20;
    private int pageIndex = 1;
    private String conditionSql;
    private String type;

    public KucunchaxunTask(int pageIndex,int pageSize, String type, String conditionSql) {
        super();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.conditionSql = conditionSql;
        this.type = type;
    }

    @Override
    public Object doBackgrounp() {
        try {
            String urlParam = "pagesize="+pageSize+"&pageindex="+pageIndex+"&type="+type+"&conditionSql="+conditionSql;
            logger.info("URL parameter:"+urlParam);
            KucunchaxunFindEntity stockpiletbEntiy = restTemplate.getForObject(URI+"?"+urlParam, KucunchaxunFindEntity.class);
            return stockpiletbEntiy;
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
