/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel;

import com.jskj.asset.client.panel.user.*;
import com.jskj.asset.client.bean.entity.MyTaskEntity;
import com.jskj.asset.client.layout.ws.*;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.DateHelper;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author woderchen
 */
public class MyTaskFindTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(ParamFindTask.class);
    private final String URI = Constants.HTTP + Constants.APPID;
    private final String serviceId = "/spfind/findmytask";
    javax.swing.JLabel messageLabel;

    public MyTaskFindTask(javax.swing.JLabel messageLabel) {
        super();
        this.messageLabel = messageLabel;
    }

    @Override
    public Object doBackgrounp() {
        try {
            //使用Spring3 RESTful client来获取http数据            
//            CommFindEntity<T> response = restTemplate.getForObject(URI + serviceId + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, CommFindEntity.class);
            CommFindEntity<MyTaskEntity> response = restTemplate.exchange(URI + serviceId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CommFindEntity<MyTaskEntity>>() {
                    }).getBody();
            return response;
        } catch (RestClientException e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
        if (object == null) {
            clientView.setStatus("response data is null", AssetMessage.ERROR_MESSAGE);
            return;
        }
        if (object instanceof Exception) {
            Exception e = (Exception) object;
            clientView.setStatus(e.getMessage(), AssetMessage.ERROR_MESSAGE);
            logger.error(e);
            return;
        }
        if (object instanceof CommFindEntity) {
            responseResult((CommFindEntity<MyTaskEntity>) object);

        } else {
            clientView.setStatus("response data is not a valid object", AssetMessage.ERROR_MESSAGE);
        }
    }

    public void responseResult(CommFindEntity<MyTaskEntity> response) {
        List<MyTaskEntity> results = response.getResult();

        if (results != null) {
            logger.info("获取任务数:" + response.getCount());
            StringBuilder builder = new StringBuilder("<html>");
            int i = 1;
            for (MyTaskEntity re : results) {
                if (i > 4) {
                    builder.append("...");
                    break;
                }
                builder.append("审批任务<").append(i).append(">: ").append(re.getOwner()).append("[").append(re.getDepartment()).append("],提出\"")
                        .append(re.getDanjuleixing()).append("\"[").append(re.getShenqingdanId()).append("]-").append(DateHelper.formatTime(re.getSubmitDate())).append("<br />");
                i++;
            }
            builder.append("</html>");
            messageLabel.setText(builder.toString());
            new MySubmitFindTask(messageLabel).execute();
        }
    }

    class MySubmitFindTask extends BaseTask {

        private final Logger logger = Logger.getLogger(MySubmitFindTask.class);
        private final String URI = Constants.HTTP + Constants.APPID;
        private final String serviceId = "/spfind/mysubmit";
        javax.swing.JLabel messageLabel;

        public MySubmitFindTask(javax.swing.JLabel messageLabel) {
            super();
            this.messageLabel = messageLabel;
        }

        @Override
        public Object doBackgrounp() {
            try {
                CommFindEntity<MyTaskEntity> response = restTemplate.exchange(URI + serviceId,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<CommFindEntity<MyTaskEntity>>() {
                        }).getBody();
                return response;
            } catch (RestClientException e) {
                logger.error(e);
                return e;
            }
        }

        @Override
        public void onSucceeded(Object object) {
            if (object == null) {
                clientView.setStatus("response data is null", AssetMessage.ERROR_MESSAGE);
                return;
            }
            if (object instanceof Exception) {
                Exception e = (Exception) object;
                clientView.setStatus(e.getMessage(), AssetMessage.ERROR_MESSAGE);
                logger.error(e);
                return;
            }
            if (object instanceof CommFindEntity) {
                responseResult((CommFindEntity<MyTaskEntity>) object);

            } else {
                clientView.setStatus("response data is not a valid object", AssetMessage.ERROR_MESSAGE);
            }
        }

        public void responseResult(CommFindEntity<MyTaskEntity> response) {
            List<MyTaskEntity> results = response.getResult();

            if (results != null) {
                logger.info("获取任务数:" + response.getCount());
                StringBuilder builder = new StringBuilder("<font color=\"blue\">");
                int i = 1;
                for (MyTaskEntity re : results) {
                    if (i > 4) {
                        builder.append("...");
                        break;
                    }
                    builder.append("我的申请单<").append(i).append(">: ").append(DateHelper.formatTime(re.getSubmitDate())).append("],\"")
                            .append(re.getDanjuleixing()).append("\"[").append(re.getShenqingdanId()).append("],状态[").append(re.getContext()).append("]").append("<br />");
                    i++;
                }
                builder.append("</font>");
                String shenpiTask = messageLabel.getText();
                StringBuilder sb = new StringBuilder(shenpiTask);
                sb.insert(sb.lastIndexOf("</html>"), builder);
                messageLabel.setText(sb.toString());
            }
        }
    }
}
