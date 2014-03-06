/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.panel.user.*;
import com.jskj.asset.client.bean.entity.MyTaskEntity;
import com.jskj.asset.client.layout.ws.*;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.panel.ymgl.YimiaoCaigouShenPiJDialog;
import com.jskj.asset.client.util.DateHelper;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
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
    private final ImageIcon icon = new ImageIcon(this.getClass().getResource("/com/jskj/asset/client/common/icon/resources/refresh.png"));
    private final ImageIcon gotoicon = new ImageIcon(this.getClass().getResource("/com/jskj/asset/client/common/icon/resources/selectsource.png"));
    private final ActionMap actionMap = Application.getInstance(AssetClientApp.class).getContext().getActionMap(MyTaskFindTask.class, this);
    private JButton shenpiButton;
    private boolean topButtonEnable;

    public MyTaskFindTask(javax.swing.JLabel messageLabel) {
        this(messageLabel, false);
    }

    public MyTaskFindTask(javax.swing.JLabel messageLabel, boolean topButtonEnable) {
        super();
        this.messageLabel = messageLabel;
        this.topButtonEnable = topButtonEnable;
        if (!messageLabel.getName().equals("attachedbutton")) {
            messageLabel.setLayout(new BorderLayout());
            JButton button = new JButton();
            button.setAction(actionMap.get("refresh"));
            button.setText("");
            button.setIcon(icon);
            button.setOpaque(false);
            button.setBorder(null);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setToolTipText("刷新消息");
            //button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            shenpiButton = new JButton();
            shenpiButton.setAction(actionMap.get("gotoShenpi"));
            shenpiButton.setText("");
            shenpiButton.setIcon(gotoicon);
            shenpiButton.setOpaque(false);
            shenpiButton.setBorder(null);
            shenpiButton.setBorderPainted(false);
            shenpiButton.setContentAreaFilled(false);
            shenpiButton.setToolTipText("去审批窗口");
            shenpiButton.setEnabled(false);
            //shenpiButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            shenpiButton.setMargin(new Insets(0, 0, 0, 40));

            messageLabel.setName("attachedbutton");
//            messageLabel.add(button, BorderLayout.EAST);
//            messageLabel.add(button2, BorderLayout.EAST);

            javax.swing.GroupLayout messageLayout = new javax.swing.GroupLayout(messageLabel);
            messageLabel.setLayout(messageLayout);
            messageLayout.setHorizontalGroup(
                    messageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, messageLayout.createSequentialGroup()
                            .addContainerGap(100, Short.MAX_VALUE)
                            .addComponent(shenpiButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(button)
                            .addContainerGap())
            );
            messageLayout.setVerticalGroup(
                    messageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(messageLayout.createSequentialGroup()
                            .addGroup(messageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(shenpiButton)
                                    .addComponent(button))
                            .addContainerGap(0, Short.MAX_VALUE))
            );

        }
    }

    @Action
    public Task refresh() {
        return new MyTaskFindTask(messageLabel,topButtonEnable);
    }

    @Action
    public void gotoShenpi() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                YimiaoCaigouShenPiJDialog yimiaoShenPiJDialog = new YimiaoCaigouShenPiJDialog(new javax.swing.JFrame(), true);
                yimiaoShenPiJDialog.setLocationRelativeTo(mainFrame);
                AssetClientApp.getApplication().show(yimiaoShenPiJDialog);
            }
        });
    }

    @Override
    public Object doBackgrounp() {
        if (shenpiButton != null) {
            shenpiButton.setEnabled(false);
        }
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
            if (topButtonEnable) {
                builder.append("<br /><br />");
            }
            int i = 1;
            builder.append("<font color=\"red\" style=\"FONT-FAMILY:").append(Constants.GLOBAL_FONT.getFontName()).append("\" >");
            for (MyTaskEntity re : results) {
                if (i > 4) {
                    builder.append("...");
                    break;
                }
                builder.append("审批任务<").append(i).append(">: ").append(re.getOwner()).append("[").append(re.getDepartment()).append("],提出\"")
                        .append(re.getDanjuleixing()).append("\"[").append(re.getShenqingdanId()).append("]-").append(DateHelper.formatTime(re.getSubmitDate())).append("<br />");
                i++;
            }
            builder.append("</font");
            builder.append("</html>");
            messageLabel.setText(builder.toString());
            if (i >= 2) {
                if (shenpiButton != null) {
                    shenpiButton.setEnabled(true);
                }
            }
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
                StringBuilder builder = new StringBuilder("<font color=\"blue\" style=\"FONT-FAMILY:").append(Constants.GLOBAL_FONT.getFontName()).append("\" >");
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
                if(sb.toString().indexOf("我的申请单")<0 && sb.toString().indexOf("审批任务")<0){
                    StringBuilder builderNoMsg = new StringBuilder("<font style=\"FONT-FAMILY:")
                            .append(Constants.GLOBAL_FONT.getFontName()).append("\" >");
                    builderNoMsg.append("您当前没有消息.");
                    builderNoMsg.append("</font>");
                    sb.insert(sb.lastIndexOf("</html>"), builderNoMsg);
                }
                messageLabel.setText(sb.toString());
            }
        }
    }
}
