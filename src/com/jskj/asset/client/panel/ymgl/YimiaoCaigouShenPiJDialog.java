/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ymgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.ShenPiEntity;
import com.jskj.asset.client.bean.entity.Usertb;
import com.jskj.asset.client.bean.entity.XiaoshoushenpixiangdanEntity;
import com.jskj.asset.client.bean.entity.YimiaoShenpiFindEntity;
import com.jskj.asset.client.bean.entity.YimiaobaosunxiangdanEntity;
import com.jskj.asset.client.bean.entity.YimiaocaigouxiangdanEntity;
import com.jskj.asset.client.bean.entity.Yimiaoshenpiliucheng;
import com.jskj.asset.client.bean.entity.YimiaotiaojiaxiangdanEntity;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.DetailPanel;
import com.jskj.asset.client.panel.ckgl.YiMiaoBaoSunJDialog;
import com.jskj.asset.client.panel.ymgl.task.ShenPiTask;
import com.jskj.asset.client.panel.ymgl.task.YimiaoDanjuChaxunTask;
import com.jskj.asset.client.panel.ymgl.task.YimiaoXiaoshouXiangdanTask;
import com.jskj.asset.client.util.BindTableHelper;
import com.jskj.asset.client.util.DanHao;
import com.jskj.asset.client.util.DateHelper;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author tt
 */
public class YimiaoCaigouShenPiJDialog extends BaseDialog {

    private int pageIndex;

    private int count;

    private List<Yimiaoshenpiliucheng> yimiaoshenpiList;

    private ShenPiEntity shenPiEntity;

    BindTableHelper<Yimiaoshenpiliucheng> bindTable;

    private DetailPanel detailPanel;

    /**
     * Creates new form GuDingZiChanRuKu
     *
     * @param parent
     * @param modal
     */
    public YimiaoCaigouShenPiJDialog(java.awt.Frame parent, boolean modal) {
        super(parent,modal);
        initComponents();
        pageIndex = 1;
        count = 0;
        bindTable = new BindTableHelper<Yimiaoshenpiliucheng>(jSQTable, new ArrayList<Yimiaoshenpiliucheng>());
        bindTable.createTable(new String[][]{{"danjuId", "单据单号"}, {"checkId1", "免规科"}, {"checkId2", "财务科"}, {"checkId3", "分管领导"}, {"checkId4", "主要领导"}});
//        bindTable.setIntegerType(1);
        bindTable.bind().setRowHeight(25);
        new RefreshTask(0).execute();
        detailPanel = new DetailPanel();

    }

    @Action
    public void exit() {
        this.dispose();
    }

    @Action
    public void reload() {
        new RefreshTask(0).execute();
        this.repaint();
    }

    private class RefreshTask extends YimiaoDanjuChaxunTask {

        BindingGroup bindingGroup = new BindingGroup();

        RefreshTask(int pageIndex) {
            super(pageIndex);
        }

        @Override
        public void onSucceeded(Object object) {

            if (object instanceof Exception) {
                Exception e = (Exception) object;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }

            YimiaoShenpiFindEntity yimiaoshenpi = (YimiaoShenpiFindEntity) object;

            if (yimiaoshenpi != null) {
                count = yimiaoshenpi.getCount();
                jLabelTotal.setText(((pageIndex - 1) * YimiaoDanjuChaxunTask.pageSize + 1) + "/" + count);
                logger.debug("total:" + count + ",get shenqing size:" + yimiaoshenpi.getResult().size());

                //存下所有的数据
                yimiaoshenpiList = yimiaoshenpi.getResult();
                //把时间和用户加到checkid这个字段上
                if (yimiaoshenpiList != null) {
                    for (Yimiaoshenpiliucheng liucheng : yimiaoshenpiList) {
                        if (liucheng.getCheckId1() != null) {
                            liucheng.setCheckId1(liucheng.getCheckId1() + "," + liucheng.getCheckUser1() + "," + DateHelper.formatTime(liucheng.getCheckTime1()));
                        }
                        if (liucheng.getCheckId2() != null) {
                            liucheng.setCheckId2(liucheng.getCheckId2() + "," + liucheng.getCheckUser2() + "," + DateHelper.formatTime(liucheng.getCheckTime2()));
                        }
                        if (liucheng.getCheckId3() != null) {
                            liucheng.setCheckId3(liucheng.getCheckId3() + "," + liucheng.getCheckUser3() + "," + DateHelper.formatTime(liucheng.getCheckTime3()));
                        }
                        if (liucheng.getCheckId4() != null) {
                            liucheng.setCheckId4(liucheng.getCheckId4() + "," + liucheng.getCheckUser4() + "," + DateHelper.formatTime(liucheng.getCheckTime4()));
                        }

                    }
                }
                bindTable.refreshData(yimiaoshenpiList);
            }

        }
    }

    @Action
    public void pagePrev() {
        pageIndex = pageIndex - 1;
        pageIndex = pageIndex <= 0 ? 1 : pageIndex;
        new RefreshTask(pageIndex).execute();
    }

    @Action
    public void pageNext() {
        if (YimiaoDanjuChaxunTask.pageSize * (pageIndex) <= count) {
            pageIndex = pageIndex + 1;
        }
        new RefreshTask(pageIndex).execute();
    }

    @Action
    public Task shenPiY() {
        if (jSQTable.getSelectedRow() < 0) {
            AssetMessage.ERRORSYS("请选择一条要审批的数据!");
            return null;
        }

        String passwd = AssetMessage.CONFIRM_PASSWD(this);
        if (passwd == null) {
            return null;
        } else {
            Usertb usertb = AssetClientApp.getSessionMap().getUsertb();
            if (!passwd.equalsIgnoreCase(usertb.getUserPassword())) {
                AssetMessage.ERRORSYS("密码错误!");
                return null;
            }
        }

        Yimiaoshenpiliucheng yimiaoshenpiliucheng = yimiaoshenpiList.get(jSQTable.getSelectedRow());
        shenPiEntity = new ShenPiEntity();
        shenPiEntity.setId(yimiaoshenpiliucheng.getDanjuId().toString());
        shenPiEntity.setResult("同意");
        //shenPiEntity.setUser(user);
        yimiaoshenpiList.remove(jSQTable.getSelectedRow());
        return new SPTask(shenPiEntity);
    }

    @Action
    public Task shenPiNMessage() {
        if (jSQTable.getSelectedRow() < 0) {
            AssetMessage.ERRORSYS("请选择一条要审批的数据!");
            return null;
        }
        
        String passwd = AssetMessage.CONFIRM_PASSWD(this);
        if (passwd == null) {
            return null;
        } else {
            Usertb usertb = AssetClientApp.getSessionMap().getUsertb();
            if (!passwd.equalsIgnoreCase(usertb.getUserPassword())) {
                AssetMessage.ERRORSYS("密码错误!");
                return null;
            }
        }

        String reason = AssetMessage.showInputDialog(this, "请输入拒绝理由");

        if (reason == null) {
            return null;
        }
        //JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
        Yimiaoshenpiliucheng yimiaoshenpiliucheng = yimiaoshenpiList.get(jSQTable.getSelectedRow());
        shenPiEntity = new ShenPiEntity();
        shenPiEntity.setId(yimiaoshenpiliucheng.getDanjuId().toString());
        shenPiEntity.setResult("拒绝");
        shenPiEntity.setReason(reason);
        //shenPiEntity.setUser(user);
        yimiaoshenpiList.remove(jSQTable.getSelectedRow());
        return new SPTask(shenPiEntity);
    }

    private class SPTask extends ShenPiTask {

        public SPTask(ShenPiEntity yimiaosp) {
            super(yimiaosp);
        }

        @Override
        public void onSucceeded(Object result) {
            if (result != null && result instanceof ShenPiEntity) {
                ShenPiEntity entity = (ShenPiEntity) result;
                AssetMessage.INFO(entity.getResult(), YimiaoCaigouShenPiJDialog.this);
            }
            AssetMessage.showMessageDialog(null, "审批完成！");
            reload();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        ctrlPane = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jLabelTotal = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jSQTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YimiaoCaigouShenPiJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel2.setMinimumSize(new java.awt.Dimension(796, 577));
        jPanel2.setName("jPanel2"); // NOI18N

        ctrlPane.setName("ctrlPane"); // NOI18N
        ctrlPane.setPreferredSize(new java.awt.Dimension(796, 30));

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setBorderPainted(false);
        jToolBar2.setName("jToolBar2"); // NOI18N
        jToolBar2.setOpaque(false);

        jLabelTotal.setText(resourceMap.getString("jLabelTotal.text")); // NOI18N
        jLabelTotal.setMaximumSize(new java.awt.Dimension(80, 40));
        jLabelTotal.setMinimumSize(new java.awt.Dimension(20, 40));
        jLabelTotal.setName("jLabelTotal"); // NOI18N
        jLabelTotal.setPreferredSize(new java.awt.Dimension(80, 40));
        jToolBar2.add(jLabelTotal);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YimiaoCaigouShenPiJDialog.class, this);
        jButton1.setAction(actionMap.get("pagePrev")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton1);

        jButton3.setAction(actionMap.get("pageNext")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setOpaque(false);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton3);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        jButton11.setAction(actionMap.get("shenPiNMessage")); // NOI18N
        jButton11.setIcon(resourceMap.getIcon("jButton11.icon")); // NOI18N
        jButton11.setText(resourceMap.getString("jButton11.text")); // NOI18N
        jButton11.setBorderPainted(false);
        jButton11.setFocusable(false);
        jButton11.setName("jButton11"); // NOI18N
        jButton11.setOpaque(false);
        jToolBar1.add(jButton11);

        jButton12.setAction(actionMap.get("shenPiY")); // NOI18N
        jButton12.setIcon(resourceMap.getIcon("jButton12.icon")); // NOI18N
        jButton12.setText(resourceMap.getString("jButton12.text")); // NOI18N
        jButton12.setBorderPainted(false);
        jButton12.setFocusable(false);
        jButton12.setName("jButton12"); // NOI18N
        jButton12.setOpaque(false);
        jToolBar1.add(jButton12);

        jButton2.setAction(actionMap.get("detail")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setOpaque(false);
        jToolBar1.add(jButton2);

        jButton15.setAction(actionMap.get("exit")); // NOI18N
        jButton15.setIcon(resourceMap.getIcon("jButton15.icon")); // NOI18N
        jButton15.setText(resourceMap.getString("jButton15.text")); // NOI18N
        jButton15.setBorderPainted(false);
        jButton15.setFocusable(false);
        jButton15.setName("jButton15"); // NOI18N
        jButton15.setOpaque(false);
        jToolBar1.add(jButton15);

        javax.swing.GroupLayout ctrlPaneLayout = new javax.swing.GroupLayout(ctrlPane);
        ctrlPane.setLayout(ctrlPaneLayout);
        ctrlPaneLayout.setHorizontalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctrlPaneLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                .addGap(291, 291, 291)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        ctrlPaneLayout.setVerticalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jSQTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jSQTable.setName("jSQTable"); // NOI18N
        jSQTable.setShowVerticalLines(false);
        jScrollPane1.setViewportView(jSQTable);
        jSQTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ctrlPane, javax.swing.GroupLayout.DEFAULT_SIZE, 829, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(ctrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 501, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(YimiaoCaigouShenPiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YimiaoCaigouShenPiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YimiaoCaigouShenPiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YimiaoCaigouShenPiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                YimiaoCaigouShenPiJDialog dialog = new YimiaoCaigouShenPiJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    public Yimiaoshenpiliucheng selectedDataFromTable() {
        if (jSQTable.getSelectedRow() >= 0) {
            if (yimiaoshenpiList != null) {
                return yimiaoshenpiList.get(jSQTable.getSelectedRow());
            }
        }
        return null;
    }

    @Action
    public void detail() {
        int n = jSQTable.getSelectedRow();
        if (n < 0) {
            AssetMessage.showMessageDialog(this, "请选择某个申请单!");
            return;
        }
        this.setVisible(false);
        Yimiaoshenpiliucheng shenpidan = yimiaoshenpiList.get(n);
        if (shenpidan.getDanjuId().contains(DanHao.TYPE_YIMIAOXS) | shenpidan.getDanjuId().contains(DanHao.TYPE_YIMIAOXF)) {
            new YimiaosalexiangdanTask(shenpidan.getDanjuId()).execute();
        } else if (shenpidan.getDanjuId().contains(DanHao.TYPE_YIMIAOSB) | shenpidan.getDanjuId().contains(DanHao.TYPE_YIMIAOSG) | shenpidan.getDanjuId().contains(DanHao.TYPE_YIMIAOLY) | shenpidan.getDanjuId().contains(DanHao.TYPE_YIMIAOCG)) {
            new YimiaocaigouxiangdanTask(shenpidan.getDanjuId()).execute();
        } else if (shenpidan.getDanjuId().contains(DanHao.TYPE_YIMIAOTJ)) {
            new YimiaotiaojiaxiangdanTask(shenpidan.getDanjuId()).execute();
        } else if (shenpidan.getDanjuId().contains(DanHao.TYPE_YIMIAOBS)) {
            new YimiaobaosunxiangdanTask(shenpidan.getDanjuId()).execute();
        }
    }

    private class YimiaosalexiangdanTask extends YimiaoXiaoshouXiangdanTask {

        public YimiaosalexiangdanTask(String xiangdanID) {
            super(xiangdanID);
        }

        @Override
        public void onSucceeded(Object result) {
            XiaoshoushenpixiangdanEntity yimiaoxiaoshouxiangdanEntiy = (XiaoshoushenpixiangdanEntity) result;
            xiaoshouxiangdandetailshow(yimiaoxiaoshouxiangdanEntiy, yimiaoxiaoshouxiangdanEntiy.getSaletb().getSaleId());

        }
    }

    private class YimiaocaigouxiangdanTask extends YimiaoXiaoshouXiangdanTask {

        public YimiaocaigouxiangdanTask(String xiangdanID) {
            super(xiangdanID);
        }

        @Override
        public void onSucceeded(Object result) {
            YimiaocaigouxiangdanEntity yimiaocaigouxiangdanEntiy = (YimiaocaigouxiangdanEntity) result;
            yimiaocaigouxiangdandetailshow(yimiaocaigouxiangdanEntiy, yimiaocaigouxiangdanEntiy.getShenqingdantb().getShenqingdanId());

        }
    }

    private class YimiaobaosunxiangdanTask extends YimiaoXiaoshouXiangdanTask {

        public YimiaobaosunxiangdanTask(String xiangdanID) {
            super(xiangdanID);
        }

        @Override
        public void onSucceeded(Object result) {
            YimiaobaosunxiangdanEntity yimiaobaosunxiangdanEntiy = (YimiaobaosunxiangdanEntity) result;
            yimiaobaosunxiangdanshow(yimiaobaosunxiangdanEntiy);

        }
    }

    private class YimiaotiaojiaxiangdanTask extends YimiaoXiaoshouXiangdanTask {

        public YimiaotiaojiaxiangdanTask(String xiangdanID) {
            super(xiangdanID);
        }

        @Override
        public void onSucceeded(Object result) {
            YimiaotiaojiaxiangdanEntity yimiaotiaojiaxiangdanEntiy = (YimiaotiaojiaxiangdanEntity) result;
            yimiaotiaojiaxiangdanshow(yimiaotiaojiaxiangdanEntiy);

        }
    }

    @Action
    public void xiaoshouxiangdandetailshow(XiaoshoushenpixiangdanEntity yimiaoxiaoshouxiangdanEntity, String xiangdanID) {
        JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
        if (xiangdanID.contains(DanHao.TYPE_YIMIAOXF)) {
            YiMiaoXiaFaJDialog yimiaoxiafaJDialog = new YiMiaoXiaFaJDialog(this, yimiaoxiaoshouxiangdanEntity);
            yimiaoxiafaJDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(yimiaoxiafaJDialog);
        } else if (xiangdanID.contains(DanHao.TYPE_YIMIAOXS)) {
            YiMiaoXiaoShouJDialog yimiaoxiaoshouJDialog = new YiMiaoXiaoShouJDialog(this, yimiaoxiaoshouxiangdanEntity);
            yimiaoxiaoshouJDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(yimiaoxiaoshouJDialog);
        }
    }

    @Action
    public void yimiaocaigouxiangdandetailshow(YimiaocaigouxiangdanEntity yimiaocaigouxiangdanEntity, String xiangdanID) {
        JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
        if (xiangdanID.contains(DanHao.TYPE_YIMIAOSB)) {
            YiMiaoLingQuPlanJDialog yimiaoshenbaoJDialog = new YiMiaoLingQuPlanJDialog(this, yimiaocaigouxiangdanEntity);
            yimiaoshenbaoJDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(yimiaoshenbaoJDialog);
        } else if (xiangdanID.contains(DanHao.TYPE_YIMIAOLY)) {
            YiMiaoLingQuShenQingJDialog yimiaolingyongJDialog = new YiMiaoLingQuShenQingJDialog(this, yimiaocaigouxiangdanEntity);
            yimiaolingyongJDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(yimiaolingyongJDialog);
        } else if (xiangdanID.contains(DanHao.TYPE_YIMIAOSG)) {
            YiMiaoSheGouPlanJDialog yimiaoshegouJDialog = new YiMiaoSheGouPlanJDialog(this, yimiaocaigouxiangdanEntity);
            yimiaoshegouJDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(yimiaoshegouJDialog);
        } else if (xiangdanID.contains(DanHao.TYPE_YIMIAOCG)) {
            YiMiaoSheGouShenQingJDialog yimiaocaigouJDialog = new YiMiaoSheGouShenQingJDialog(this, yimiaocaigouxiangdanEntity);
            yimiaocaigouJDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(yimiaocaigouJDialog);
        }
    }

    @Action
    public void yimiaobaosunxiangdanshow(YimiaobaosunxiangdanEntity yimiaobaosunxiangdanEntity) {
        JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
        YiMiaoBaoSunJDialog yimiaobaosunJDialog = new YiMiaoBaoSunJDialog(this, yimiaobaosunxiangdanEntity);
        yimiaobaosunJDialog.setLocationRelativeTo(mainFrame);
        AssetClientApp.getApplication().show(yimiaobaosunJDialog);
    }

    @Action
    public void yimiaotiaojiaxiangdanshow(YimiaotiaojiaxiangdanEntity yimiaotiaojiaxiangdanEntity) {
        JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
        YiMiaoTiaoJiaJDialog yimiaotiaojiaJDialog = new YiMiaoTiaoJiaJDialog(this, yimiaotiaojiaxiangdanEntity);
        yimiaotiaojiaJDialog.setLocationRelativeTo(mainFrame);
        AssetClientApp.getApplication().show(yimiaotiaojiaJDialog);
    }

    public Yimiaoshenpiliucheng selectedDataTable() {
        if (jSQTable.getSelectedRow() >= 0) {
            if (yimiaoshenpiList != null) {
                return yimiaoshenpiList.get(jSQTable.getSelectedRow());
            }
        }
        return null;
    }

    @Action
    public void detailPopup() {

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ctrlPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTable jSQTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables
}
