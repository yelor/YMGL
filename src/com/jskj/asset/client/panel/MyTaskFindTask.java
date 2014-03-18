/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.MyTaskEntity;
import com.jskj.asset.client.layout.ws.*;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BasePanel;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.panel.slgl.ShenQingShenPiJDialog;
import com.jskj.asset.client.panel.ymgl.YimiaoCaigouShenPiJDialog;
import com.jskj.asset.client.util.DateHelper;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author woderchen
 */
public abstract class MyTaskFindTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(MyTaskFindTask.class);
    private final String URI = Constants.HTTP + Constants.APPID;
    private final String serviceId = "/spfind/findmytask";
    javax.swing.JLabel messageLabel;
    List<javax.swing.JLabel> labelArray;
    private final BasePanel basePanel;
    private final int days;
    private boolean displayTask;
    private boolean displayApplication;

    public MyTaskFindTask(javax.swing.JLabel messageLabel, BasePanel basePanel, int days, boolean displayTask, boolean displayApplication) {
        super();
        this.messageLabel = messageLabel;
        this.basePanel = basePanel;
        this.days = days;
        this.displayTask = displayTask;
        this.displayApplication = displayApplication;
    }

    @Action
    public Task refresh() {
        return basePanel.reload();
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

    @Action
    public void selectShenPiDanAction() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                ShenQingShenPiJDialog sqsp = new ShenQingShenPiJDialog(new javax.swing.JFrame(), true);
                sqsp.setLocationRelativeTo(mainFrame);
                AssetClientApp.getApplication().show(sqsp);
            }
        });
    }

    @Override
    public Object doBackgrounp() {
        try {
            if (displayTask) {
                //使用Spring3 RESTful client来获取http数据            
//            CommFindEntity<T> response = restTemplate.getForObject(URI + serviceId + "?pagesize=" + pageSize + "&pageindex=" + pageIndex, CommFindEntity.class);
                CommFindEntity<MyTaskEntity> response = restTemplate.exchange(URI + serviceId,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<CommFindEntity<MyTaskEntity>>() {
                        }).getBody();
                return response;
            } else {
                CommFindEntity<MyTaskEntity> response = new CommFindEntity<MyTaskEntity>();
                response.setCount(0);
                List<MyTaskEntity> array = new ArrayList();
                response.setResult(array);
                return response;
            }
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
            //StringBuilder builder = new StringBuilder("<html>");
//            if (topButtonEnable) {
//                builder.append("<br /><br />");
//            }
            int i = 1;

            labelArray = new ArrayList<javax.swing.JLabel>();

            for (final MyTaskEntity re : results) {
                javax.swing.JLabel messageShenpi = new javax.swing.JLabel();
                messageShenpi.setName(re.getShenqingdanId());
                labelArray.add(messageShenpi);
                StringBuilder builder = new StringBuilder("<html>");
                builder.append("<font color=\"red\" style=\"FONT-FAMILY:").append(Constants.GLOBAL_FONT.getFontName()).append("\" >");
//                if (i > 4) {
//                    builder.append("...");
//                    messageShenpi.setText(builder.toString());
//                    break;
//                }

                builder.append("&nbsp;审批任务").append(i).append(": ").append(re.getOwner()).append("[").append(re.getDepartment()).append("],提出\"")
                        .append(re.getDanjuleixing()).append("\"[").append(re.getShenqingdanId()).append("]-").append(DateHelper.formatTime(re.getSubmitDate())).append("<br />");
                builder.append("</font>");
                builder.append("</html>");
                i++;
                messageShenpi.setText(builder.toString());
                messageShenpi.setToolTipText("点击打开" + re.getShenqingdanId());
                messageShenpi.setOpaque(true);
                messageShenpi.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {

                        if (re.getShenqingdanId().toUpperCase().startsWith("YM")) {
                            //疫苗审批
                            gotoShenpi();
                        } else {//资产审批
                            selectShenPiDanAction();
                        }

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        e.getComponent().setBackground(Color.WHITE);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        e.getComponent().setBackground(null);
                    }

                });
            }

            layout(labelArray);
            new MySubmitFindTask().execute();
        }
    }

    public abstract void layout(List<javax.swing.JLabel> labelArray);

    class MySubmitFindTask extends BaseTask {

        private final Logger logger = Logger.getLogger(MySubmitFindTask.class);
        private final String URI = Constants.HTTP + Constants.APPID;
        private final String serviceId = "/spfind/mysubmit?days=" + days;

        public MySubmitFindTask() {
            super();
        }

        @Override
        public Object doBackgrounp() {
            try {
                if (displayApplication) {
                    CommFindEntity<MyTaskEntity> response = restTemplate.exchange(URI + serviceId,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<CommFindEntity<MyTaskEntity>>() {
                            }).getBody();
                    return response;
                } else {
                    CommFindEntity<MyTaskEntity> response = new CommFindEntity<MyTaskEntity>();
                    response.setCount(0);
                    List<MyTaskEntity> array = new ArrayList();
                    response.setResult(array);
                    return response;
                }
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
                StringBuilder builder = new StringBuilder("<html><font color=\"blue\" style=\"FONT-FAMILY:").append(Constants.GLOBAL_FONT.getFontName()).append("\" >");
                int i = 1;
                for (MyTaskEntity re : results) {
//                    if (i > 4) {
//                        builder.append("...");
//                        break;
//                    }
                    builder.append("&nbsp;我的申请单").append(i).append(": ").append(DateHelper.formatTime(re.getSubmitDate())).append("],\"")
                            .append(re.getDanjuleixing()).append("\"[").append(re.getShenqingdanId()).append("],状态[").append(re.getContext()).append("]").append("<br />");
                    i++;
                }
                builder.append("</font></html>");
//                String shenpiTask = messageLabel.getText();
//                StringBuilder sb = new StringBuilder();
//                if (builder.toString().indexOf("我的申请单") >= 0 && sb.toString().indexOf("审批任务") > 0) {
//                    builder.insert(builder.indexOf("<font"), "<br />");
//                }
//                sb.insert(sb.lastIndexOf("</html>"), builder);
                if (builder.toString().indexOf("我的申请单") < 0 && labelArray != null && labelArray.size() <= 0) {
                    StringBuilder builderNoMsg = new StringBuilder("<font style=\"FONT-FAMILY:")
                            .append(Constants.GLOBAL_FONT.getFontName()).append("\" >");
                    builderNoMsg.append("您当前没有消息.");
                    builderNoMsg.append("</font>");
                    builder.insert(builder.lastIndexOf("</html>"), builderNoMsg);
                }
                // System.out.println("@@@@@@@@@@@@@@@@@@@@@messageLabel:"+sb.toString());
                messageLabel.setText(builder.toString());
            }
        }
    }
}
