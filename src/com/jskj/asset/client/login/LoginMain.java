/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.login;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.PathCacheBean;
import com.jskj.asset.client.bean.PathCacheBean.PathBean;
import com.jskj.asset.client.bean.UserSessionEntity;
import com.jskj.asset.client.bean.entity.Usertb;
import com.jskj.asset.client.constants.Constants;
import static com.jskj.asset.client.layout.AssetMessage.INFO_MESSAGE;
import com.jskj.asset.client.panel.message.MessagePanel;
import com.jskj.asset.client.util.XMLHelper;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author 305027939
 */
public class LoginMain extends javax.swing.JFrame {

    private String[] args;

    private boolean logined = false;

    private JPanel jPanelNet;
    private JTextField serviceText;
    private JTextField portText;
    private boolean isDisplay;

    java.util.Timer timer = new java.util.Timer(true);
    TimerTask timetask;
    Thread clearThread;

    /**
     * Creates new form LoginMain
     */
    public LoginMain(String[] args) {
        initComponents();
        this.args = args;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int y = screenSize.height / 2 - getHeight() / 2;
        int x = screenSize.width / 2 - getWidth() / 2;
        setBounds(x, y, Constants.LOGIN_WIDTH, Constants.LOGIN_HEIGHT);
        this.setMaximumSize(new Dimension(Constants.LOGIN_WIDTH, Constants.LOGIN_HEIGHT));
        setTitle(Constants.WINTITLE);
        setResizable(false);
        versionLabel.setText(Constants.VERSION);
        new LoginInfoTask(LoginInfoTask.READ_XML).execute();
        initNetPanel();
        displayNetPanel(false);

    }

    private void initNetPanel() {
        jPanelNet = new javax.swing.JPanel();
        jPanelNet.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelNet.setName("jPanelNet"); // NOI18N

        JLabel jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        serviceText = new JTextField();
        portText = new JTextField();

        StringBuilder builderNoMsg = new StringBuilder("<html><font size=2 style=\"FONT-FAMILY:")
                .append(Constants.GLOBAL_FONT.getFontName()).append("\" ><u>");
        builderNoMsg.append("网络配置");
        builderNoMsg.append("</u></font></html>");
        jLabel6.setText(builderNoMsg.toString());

        jLabel4.setFont(new java.awt.Font("宋体", 0, 12)); // NOI18N
        jLabel4.setText("服务器:"); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        //serviceText.setText(resourceMap.getString("serviceText.text")); // NOI18N
        serviceText.setName("serviceText"); // NOI18N

        jLabel5.setFont(new java.awt.Font("宋体", 0, 12)); // NOI18N
        jLabel5.setText("端口:"); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        // portText.setText(resourceMap.getString("portText.text")); // NOI18N
        portText.setName("portText"); // NOI18N

        javax.swing.GroupLayout jPanelNetLayout = new javax.swing.GroupLayout(jPanelNet);
        jPanelNet.setLayout(jPanelNetLayout);
        jPanelNetLayout.setHorizontalGroup(
                jPanelNetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelNetLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(serviceText, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(portText, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
        );
        jPanelNetLayout.setVerticalGroup(
                jPanelNetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelNetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(serviceText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(portText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }

    private void displayNetPanel(boolean display) {

        isDisplay = display;
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);

        javax.swing.GroupLayout.ParallelGroup parGroup = jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false);
        if (display) {
            parGroup.addComponent(jPanelNet, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        }
        parGroup.addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(loginButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1))
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(userNameFiled)
                                .addComponent(passwordFiled, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)));

        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(warningMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, parGroup))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout.SequentialGroup seqGroup = jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(warningMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(userNameFiled, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(passwordFiled, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel6))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(loginButton)
                                        .addComponent(jButton1)))).addGap(18, 18, 18);
        if (display) {
            seqGroup.addComponent(jPanelNet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap();
        }

        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(seqGroup));
        if (display) {
            setMinimumSize(new java.awt.Dimension(360, 326));
            setMaximumSize(new java.awt.Dimension(360, 326));
        } else {
            setMinimumSize(new java.awt.Dimension(360, 274));
            setMaximumSize(new java.awt.Dimension(360, 274));
        }
        validate();
        pack();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        userNameFiled = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        passwordFiled = new javax.swing.JPasswordField();
        warningMsg = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        versionLabel = new javax.swing.JLabel();
        jPanel2 = new TitlePanel(371,88);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setIconImage(new ImageIcon(this.getClass().getResource("/com/jskj/asset/client/resources/icon.png")).getImage());
        setMinimumSize(new java.awt.Dimension(371, 274));
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel2.setFont(Constants.GLOBAL_FONT);
        jLabel2.setText("用户名:"); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setFont(Constants.GLOBAL_FONT);
        jLabel3.setText("密码:"); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(LoginMain.class);
        userNameFiled.setText(resourceMap.getString("userNameFiled.text")); // NOI18N
        userNameFiled.setName("userNameFiled"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(LoginMain.class, this);
        loginButton.setAction(actionMap.get("sendLogin")); // NOI18N
        loginButton.setFont(Constants.GLOBAL_FONT);
        loginButton.setIcon(resourceMap.getIcon("loginButton.icon")); // NOI18N
        loginButton.setEnabled(false);
        loginButton.setName("loginButton"); // NOI18N

        jButton1.setAction(actionMap.get("close")); // NOI18N
        jButton1.setFont(Constants.GLOBAL_FONT);
        jButton1.setName("jButton1"); // NOI18N

        passwordFiled.setText(resourceMap.getString("passwordFiled.text")); // NOI18N
        passwordFiled.setName("passwordFiled"); // NOI18N
        passwordFiled.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                passwordFiledKeyReleased(evt);
            }
        });

        warningMsg.setFont(new java.awt.Font("宋体", 1, 12)); // NOI18N
        warningMsg.setForeground(new java.awt.Color(255, 0, 0));
        warningMsg.setName("warningMsg"); // NOI18N

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(warningMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                                .addComponent(loginButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(userNameFiled, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addComponent(passwordFiled))))
                        .addGap(32, 32, 32))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(warningMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userNameFiled, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(passwordFiled, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(loginButton)
                        .addComponent(jButton1))
                    .addComponent(jLabel6))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        versionLabel.setFont(resourceMap.getFont("versionLabel.font")); // NOI18N
        versionLabel.setText("version 1.00"); // NOI18N
        versionLabel.setName("versionLabel"); // NOI18N

        jPanel2.setMaximumSize(new java.awt.Dimension(350, 88));
        jPanel2.setMinimumSize(new java.awt.Dimension(350, 88));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 88));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 88, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(versionLabel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(versionLabel))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void passwordFiledKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordFiledKeyReleased
        int keyCode = evt.getKeyCode();

        String password = String.valueOf(passwordFiled.getPassword());

        if (password.length() > 0) {
            loginButton.setEnabled(true);
            if (keyCode == KeyEvent.VK_ENTER) {
                sendLogin().execute();
            }
        } else {
            loginButton.setEnabled(false);
        }


    }//GEN-LAST:event_passwordFiledKeyReleased

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        if (!isDisplay) {
            displayNetPanel(true);
        } else {
            displayNetPanel(false);
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        jLabel6.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        jLabel6.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jLabel6MouseExited

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(LoginMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(LoginMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(LoginMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(LoginMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new LoginMain().setVisible(true);
//            }
//        });
//    }
    @Action
    public Task sendLogin() {
        String userName = userNameFiled.getText();
        String serviceAdd = serviceText.getText();
        drawLoopLine(1, 10);
        String password = String.valueOf(passwordFiled.getPassword());
        if (userName.trim().equals("")) {
            setWarningMsg("请输入用户名!");
            userNameFiled.grabFocus();
            return null;
        }

        if (serviceAdd.trim().equals("")) {
            setWarningMsg("请输入服务端IP!");
            serviceText.grabFocus();
            return null;
        }

        setWarningMsg("");
        new LoginInfoTask(LoginInfoTask.WRITE_XML).execute();
        drawLoopLine(10, 80);
        HashMap map = new HashMap();
        map.put("userName", userName.trim());
        map.put("userPassword", password);
        loginButton.setEnabled(false);
        return new SendLoginTask(map);
    }

    private void setWarningMsg(String mgs) {
        if (timetask != null) {
            timetask.cancel();
        }
        warningMsg.setText(mgs);
        if (clearThread == null || !clearThread.isAlive()) {
            clearThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(10000);
                        warningMsg.setText("");
                    } catch (InterruptedException ex) {
                    }
                }

            });
            clearThread.start();
        }
    }

    private void drawLoopLine(final double toX, final double nextToX) {
        if (timetask != null) {
            timetask.cancel();
        }

        if (nextToX > 0) {
            if (toX < 100) {
                timetask = new TimerTask() {
                    double step = 1d;
                    double a = nextToX - toX;
                    double stepPre = 5d;

                    @Override
                    public void run() {

                        if (toX + step >= nextToX) {
                            stepPre = (stepPre / 10);
                        }
                        step = step + stepPre;

                        drawProgress(toX + step);

                    }
                };
                timer.schedule(timetask, 1, 1000);
            } else {
                drawProgress(100);
            }
        }
    }

    private void drawProgress(double toX) {
        if (clearThread != null && clearThread.isAlive()) {
            clearThread.interrupt();
        }
        setColorAndDrawGraphics(warningMsg.getGraphics(), toX);
    }

    public void setColorAndDrawGraphics(Graphics g, double toX) {
        g.setColor(new Color(122, 163, 204));
        g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 14));
        double per = toX / 100;
        int width = warningMsg.getWidth();
        double correctProc = width * per;
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(6));
        g.drawLine(0, 0, (int) correctProc, 0);
    }

    private class LoginInfoTask extends Task<Object, Void> {

        public final static int READ_XML = 0;
        public final static int WRITE_XML = 1;

        private int type = 0;

        public LoginInfoTask(int type) {
            super(Application.getInstance(AssetClientApp.class));
            this.type = type;
        }

        @Override
        protected Object doInBackground() throws Exception {
            try {
                if (type == WRITE_XML) {
                    String userName = userNameFiled.getText();
                    String password = String.valueOf(passwordFiled.getPassword());
                    String serviceAdd = serviceText.getText();
                    String port = portText.getText();

                    Constants.setHTTPHost(serviceAdd, port);

                    PathCacheBean pathBeans = new PathCacheBean();
                    List<PathBean> maps = new ArrayList<PathBean>();
                    PathCacheBean.PathBean nameMap = new PathCacheBean.PathBean();
                    nameMap.setKey("username");
                    nameMap.setValue(userName);
                    maps.add(nameMap);
                    PathCacheBean.PathBean serverMap = new PathCacheBean.PathBean();
                    serverMap.setKey("server");
                    serverMap.setValue(serviceAdd);
                    maps.add(serverMap);
                    PathCacheBean.PathBean portMap = new PathCacheBean.PathBean();
                    portMap.setKey("port");
                    portMap.setValue(port);
                    maps.add(portMap);

                    pathBeans.setPaths(maps);

                    XMLHelper<PathCacheBean> helper = new XMLHelper<PathCacheBean>(pathBeans);
                    helper.write();
                } else {
                    XMLHelper<PathCacheBean> helper = new XMLHelper<PathCacheBean>(new PathCacheBean());
                    PathCacheBean paths = helper.read();
                    List<PathBean> pathBeans = paths.getPaths();
                    if (pathBeans != null) {
                        for (PathBean bean : pathBeans) {
                            String key = bean.getKey();
                            if (key.equalsIgnoreCase("username")) {
                                userNameFiled.setText(bean.getValue());
                            } else if (key.equalsIgnoreCase("server")) {
                                serviceText.setText(bean.getValue());
                            } else if (key.equalsIgnoreCase("port")) {
                                portText.setText(bean.getValue());
                            }
                        }
                        passwordFiled.grabFocus();
                    }

                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            return "";
        }
    }

    private class SendLoginTask extends LoginTask {

        SendLoginTask(HashMap map) {
            super(map, logined);
        }

        @Override
        public void onSucceeded(Object object) {
            loginButton.setEnabled(true);
            drawLoopLine(80, 100);
            if (object == null) {
                setWarningMsg("服务端异常!");
                return;
            }
            if (object instanceof Exception) {
                Exception e = (Exception) object;
                setWarningMsg(e.getMessage());
                return;
            }

            if (object instanceof UserSessionEntity) {
                UserSessionEntity session = (UserSessionEntity) object;
                Usertb user = session.getUsertb();
                if (user != null) {
                    if (logined) {
                        AssetClientApp.getApplication().getMainFrame().dispose();
                    }
                    AssetClientApp.startupApplication(args, LoginMain.this, session);
                    drawLoopLine(100, 100);
                    dispose();
                    logined = true;
                } else {
                    if (session.getMessage() != null && !session.getMessage().equals("")) {
                        setWarningMsg(session.getMessage());
                    } else {
                        setWarningMsg("请检查用户名和密码!");
                    }
                }
            } else {
                setWarningMsg("签入服务器异常!");
            }
        }

    }

    @Action
    public void close() {
        if (logined) {
            dispose();
        } else {
            System.exit(0);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordFiled;
    private javax.swing.JTextField userNameFiled;
    private javax.swing.JLabel versionLabel;
    private javax.swing.JLabel warningMsg;
    // End of variables declaration//GEN-END:variables
}
