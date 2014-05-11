/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.baobiao.caigou;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.AssetClientView;
import com.jskj.asset.client.bean.entity.ChurukujiluyimiaoEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BasePanel;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.BaseTreePane;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ws.CommFindEntity;
import com.jskj.asset.client.util.BindTableHelper;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author huiqi
 */
public class YimiaochukujiluPanel extends BasePanel {

    private final static Logger logger = Logger.getLogger(YimiaochukujiluPanel.class);
    private int pageIndex;
    private final int pageSize;
    private int count;
    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private List<ChurukujiluyimiaoEntity> currentPageData;

    private final BindTableHelper<ChurukujiluyimiaoEntity> bindTable;

    private final HashMap parameterMap;

    /**
     * Creates new form YimiaocaigoumingxiJDialog
     */
    public YimiaochukujiluPanel() {
        super();
        initComponents();
        pageIndex = 1;
        pageSize = 20;
        count = 0;
        bindTable = new BindTableHelper<ChurukujiluyimiaoEntity>(jTable1, new ArrayList<ChurukujiluyimiaoEntity>());
        bindTable.createTable(new String[][]{
            {"churukudan.zhidandate", "日期", "false"}, {"churukudan.churukuId", "单据编号", "false"}, {"yimiao.yimiaoName", "疫苗名称", "false"}, {"chukuyimiao.rukuQuantity", "入库数(支)", "false"}, {"chukuyimiao.chukuQuantity", "出库数(支)", "false"}, {"yimiao.yimiaoShengchanqiye", "生产企业", "false"}, {"yimiao.yimiaoGuige", "规格", "false"}, {"yimiao.yimiaoJixing", "剂型", "false"},
            {"chukuyimiao.pihao", "批号", "false"}, {"chukuyimiao.youxiaoqi", "有效期", "false"}, {"yimiao.unitId", "单位", "false"}, {"chukuyimiao.piqianfahegeno", "批签发合格证编号", "false"}, {"yimiao.yimiaoPizhunwenhao", "批准文号", "true"},
            //            {"yimiaoId", "疫苗编号", "false"}, {"yimiaoName", "疫苗名称", "true"}, {"source", "国产/出口", "false"}, {"tongguandanNo", "进口通关单编号", "false"}, {"quantity", "数量", "true"}, 
            {"churukudan.gongyingdanwei", "往来单位", "true"}, {"chukuyimiao.price", "价格(元)", "true"},
            {"churukudan.zhidanren", "经办人", "true"}, {"churukudan.zhidanren", "对方经办人", "true"}, {"churukudan.zhidanren", "累计库存(支)", "true"}});
        bindTable.setColumnType(Date.class, 1, 10);
        bindTable.bind().setColumnWidth(new int[]{0, 80}, new int[]{1, 180}, new int[]{2, 150}, new int[]{3, 80}, new int[]{4, 80}, new int[]{5, 80}, new int[]{6, 80}).setRowHeight(25);

        parameterMap = new HashMap();
        parameterMap.put("pagesize", String.valueOf(pageSize));
        parameterMap.put("pageindex", String.valueOf(pageIndex));
        parameterMap.put("yimiaoName", "");

        ((BaseTextField) jTextFieldStart).registerPopup(IPopupBuilder.TYPE_DATE_CLICK, "yyyy-MM-dd HH:mm:ss");
        ((BaseTextField) jTextFieldEnd).registerPopup(IPopupBuilder.TYPE_DATE_CLICK, "yyyy-MM-dd HH:mm:ss");

        ((BaseTextField) jTextFieldYimiaoName).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addyimiao";
            }

            public String getConditionSQL() {
                String sql = "";
                if (!jTextFieldYimiaoName.getText().trim().equals("")) {
                    sql += "(yimiao_name like \"%" + jTextFieldYimiaoName.getText().toString() + "%\" or zujima like \"%" + jTextFieldYimiaoName.getText().toString().toLowerCase() + "%\"))";
                } else {
                    sql += "";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"yimiaoId", "疫苗编号"}, {"yimiaoName", "疫苗名称"}, {"yimiaoGuige", "规格"},
                {"yimiaoJixing", "剂型"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    jTextFieldYimiaoName.setText(bindedMap.get("yimiaoName") == null ? "" : bindedMap.get("yimiaoName").toString());
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpanel2 = new javax.swing.JPanel();
        ctrlPane = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton12 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jLabelTotal = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLabelImg = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldStart = new BaseTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldEnd = new BaseTextField();
        jTextFieldYimiaoName = new BaseTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();

        setName("Form"); // NOI18N

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.RIGHT);
        jTabbedPane1.setName("jTabbedPane1"); // NOI18N
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jpanel2.setName("jpanel2"); // NOI18N

        ctrlPane.setName("ctrlPane"); // NOI18N

        jToolBar2.setBorder(null);
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setBorderPainted(false);
        jToolBar2.setName("jToolBar2"); // NOI18N
        jToolBar2.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YimiaochukujiluPanel.class, this);
        jButton12.setAction(actionMap.get("print")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YimiaochukujiluPanel.class);
        jButton12.setIcon(resourceMap.getIcon("jButton12.icon")); // NOI18N
        jButton12.setText(resourceMap.getString("jButton12.text")); // NOI18N
        jButton12.setBorderPainted(false);
        jButton12.setFocusable(false);
        jButton12.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton12.setName("jButton12"); // NOI18N
        jButton12.setOpaque(false);
        jToolBar2.add(jButton12);

        jButton1.setAction(actionMap.get("disDetail")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jToolBar2.add(jButton1);

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);
        jToolBar3.setBorderPainted(false);
        jToolBar3.setName("jToolBar3"); // NOI18N
        jToolBar3.setOpaque(false);

        jButton17.setAction(actionMap.get("pagePrev")); // NOI18N
        jButton17.setText(resourceMap.getString("jButton17.text")); // NOI18N
        jButton17.setBorderPainted(false);
        jButton17.setFocusable(false);
        jButton17.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton17.setMaximumSize(new java.awt.Dimension(60, 25));
        jButton17.setMinimumSize(new java.awt.Dimension(60, 25));
        jButton17.setName("jButton17"); // NOI18N
        jButton17.setOpaque(false);
        jButton17.setPreferredSize(new java.awt.Dimension(60, 25));
        jButton17.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(jButton17);

        jButton18.setAction(actionMap.get("pageNext")); // NOI18N
        jButton18.setText(resourceMap.getString("jButton18.text")); // NOI18N
        jButton18.setBorderPainted(false);
        jButton18.setFocusable(false);
        jButton18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton18.setMaximumSize(new java.awt.Dimension(60, 25));
        jButton18.setMinimumSize(new java.awt.Dimension(60, 25));
        jButton18.setName("jButton18"); // NOI18N
        jButton18.setOpaque(false);
        jButton18.setPreferredSize(new java.awt.Dimension(60, 25));
        jButton18.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(jButton18);

        jLabelTotal.setForeground(resourceMap.getColor("jLabelTotal.foreground")); // NOI18N
        jLabelTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTotal.setName("jLabelTotal"); // NOI18N

        javax.swing.GroupLayout ctrlPaneLayout = new javax.swing.GroupLayout(ctrlPane);
        ctrlPane.setLayout(ctrlPaneLayout);
        ctrlPaneLayout.setHorizontalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctrlPaneLayout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        ctrlPaneLayout.setVerticalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabelTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
            jTable1.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title1")); // NOI18N
            jTable1.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
            jTable1.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title3")); // NOI18N
            jTable1.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
            jTable1.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable1.columnModel.title5")); // NOI18N
            jTable1.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTable1.columnModel.title6")); // NOI18N
            jTable1.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTable1.columnModel.title7")); // NOI18N
        }

        javax.swing.GroupLayout jpanel2Layout = new javax.swing.GroupLayout(jpanel2);
        jpanel2.setLayout(jpanel2Layout);
        jpanel2Layout.setHorizontalGroup(
            jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ctrlPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jpanel2Layout.setVerticalGroup(
            jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanel2Layout.createSequentialGroup()
                .addComponent(ctrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(resourceMap.getString("jpanel2.TabConstraints.tabTitle"), jpanel2); // NOI18N

        jScrollPane2.setBackground(resourceMap.getColor("jScrollPane2.background")); // NOI18N
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jLabelImg.setBackground(resourceMap.getColor("jLabelImg.background")); // NOI18N
        jLabelImg.setForeground(resourceMap.getColor("jLabelImg.foreground")); // NOI18N
        jLabelImg.setText(resourceMap.getString("jLabelImg.text")); // NOI18N
        jLabelImg.setName("jLabelImg"); // NOI18N
        jScrollPane2.setViewportView(jLabelImg);

        jTabbedPane1.addTab(resourceMap.getString("jScrollPane2.TabConstraints.tabTitle"), jScrollPane2); // NOI18N

        jPanel2.setName("jPanel2"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextFieldStart.setText(resourceMap.getString("jTextFieldStart.text")); // NOI18N
        jTextFieldStart.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextFieldStart.setName("jTextFieldStart"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextFieldEnd.setText(resourceMap.getString("jTextFieldEnd.text")); // NOI18N
        jTextFieldEnd.setName("jTextFieldEnd"); // NOI18N

        jTextFieldYimiaoName.setText(resourceMap.getString("jTextFieldYimiaoName.text")); // NOI18N
        jTextFieldYimiaoName.setName("jTextFieldYimiaoName"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jButton15.setAction(actionMap.get("reload")); // NOI18N
        jButton15.setIcon(resourceMap.getIcon("jButton15.icon")); // NOI18N
        jButton15.setText(resourceMap.getString("jButton15.text")); // NOI18N
        jButton15.setBorder(null);
        jButton15.setBorderPainted(false);
        jButton15.setFocusable(false);
        jButton15.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton15.setName("jButton15"); // NOI18N
        jButton15.setOpaque(false);

        jButton16.setAction(actionMap.get("close")); // NOI18N
        jButton16.setIcon(resourceMap.getIcon("jButton16.icon")); // NOI18N
        jButton16.setText(resourceMap.getString("jButton16.text")); // NOI18N
        jButton16.setBorder(null);
        jButton16.setBorderPainted(false);
        jButton16.setFocusable(false);
        jButton16.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton16.setName("jButton16"); // NOI18N
        jButton16.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldYimiaoName, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldStart, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton15)
                .addGap(18, 18, 18)
                .addComponent(jButton16)
                .addGap(12, 12, 12))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jTextFieldEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldStart, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldYimiaoName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jButton15)
                    .addComponent(jButton16))
                .addGap(0, 5, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 859, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        if (((JTabbedPane) evt.getSource()).getSelectedIndex() == 1) {
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = dateformate.parse(jTextFieldStart.getText());
                endDate = dateformate.parse(jTextFieldEnd.getText());
                System.out.println("查找开始的时间是：" + startDate);
            } catch (ParseException ex) {
                java.util.logging.Logger.getLogger(YimiaochukujiluPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            parameterMap.put("startDate", startDate);
            parameterMap.put("endDate", endDate);
            parameterMap.put("yimiaoName", "");

            jLabelImg.setText("loading chart...");
            jLabelImg.setIcon(null);
            ReportChartFindTask reportTask = new ReportChartFindTask(parameterMap) {
                @Override
                public void responseResult(File imgPath) {
                    jLabelImg.setText("");
                    if (imgPath != null && imgPath.exists()) {
                        ImageIcon icon = new ImageIcon(imgPath.getPath());
                        jLabelImg.setIcon(icon);
                    }
                }
            };
            reportTask.execute();

        }
    }//GEN-LAST:event_jTabbedPane1StateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ctrlPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelImg;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldEnd;
    private javax.swing.JTextField jTextFieldStart;
    private javax.swing.JTextField jTextFieldYimiaoName;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JPanel jpanel2;
    // End of variables declaration//GEN-END:variables

    @Override
    @Action
    public Task reload() {
        parameterMap.put("pageindex", 0);
        String startDate = jTextFieldStart.getText();
        String endDate = jTextFieldEnd.getText();
        parameterMap.put("startDate", startDate);
        parameterMap.put("endDate", endDate);
        parameterMap.put("yimiaoName", jTextFieldYimiaoName.getText());
        return new RefreshTask(parameterMap);
    }

    private class ReloadTask extends org.jdesktop.application.Task<Object, Void> {

        ReloadTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to ReloadTask fields, here.
            super(app);
        }

        @Override
        protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }

    @Override
    public Task reload(Object param) {
        return null;
    }

    @Action
    public Task pagePrev() {
        pageIndex = pageIndex - 1;
        pageIndex = pageIndex <= 0 ? 1 : pageIndex;
        parameterMap.put("pageindex", String.valueOf(pageIndex));
        return new RefreshTask(parameterMap);
    }

    private class PagePrevTask extends org.jdesktop.application.Task<Object, Void> {

        PagePrevTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to PagePrevTask fields, here.
            super(app);
        }

        @Override
        protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }

    @Action
    public Task pageNext() {
        if (pageSize * (pageIndex) <= count) {
            pageIndex = pageIndex + 1;
        }
        parameterMap.put("pageindex", String.valueOf(pageIndex));
        return new RefreshTask(parameterMap);
    }

    private class PageNextTask extends org.jdesktop.application.Task<Object, Void> {

        PageNextTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to PageNextTask fields, here.
            super(app);
        }

        @Override
        protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }

    public ChurukujiluyimiaoEntity selectedDataFromTable() {
        if (jTable1.getSelectedRow() >= 0) {
            if (currentPageData != null) {
                return currentPageData.get(jTable1.getSelectedRow());
            }
        }
        return null;
    }

    public List<ChurukujiluyimiaoEntity> getTableData() {
        return currentPageData;
    }

    private class RefreshTask extends ChurukuyimiaoEntityFindTask {

        RefreshTask(HashMap parameterMap) {
            super(parameterMap, "report/churukujilu/list");
        }

        @Override
        public void responseResult(CommFindEntity<ChurukujiluyimiaoEntity> response) {

            count = response.getCount();
            jLabelTotal.setText(((pageIndex - 1) * pageSize + 1) + "/" + count);
            logger.debug("total:" + count + ",get current size:" + response.getResult().size());

            //存下所有的数据
            currentPageData = response.getResult();
            bindTable.refreshData(currentPageData);

        }
    }

    @Action
    public Task print() {
        HashMap printMap = new HashMap();
        printMap.put("pagesize", count);
        printMap.put("pageindex", 0);
        String startDate = jTextFieldStart.getText();
        String endDate = jTextFieldEnd.getText();
        printMap.put("startDate", startDate);
        printMap.put("endDate", endDate);
        printMap.put("yimiaoName", jTextFieldYimiaoName.getText());
        ChurukuyimiaoEntityFindTask printData = new ChurukuyimiaoEntityFindTask(printMap, "report/churukujilu/list") {
            @Override
            public void responseResult(CommFindEntity response) {
                bindTable.createPrinter("疫苗出入库记录", response.getResult()).buildInBackgound().execute();
            }
        };
        return printData;
    }

    private class PrintTask extends org.jdesktop.application.Task<Object, Void> {

        PrintTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to PrintTask fields, here.
            super(app);
        }

        @Override
        protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }

    @Action
    public Task disDetail() {
        ChurukujiluyimiaoEntity selectedData = selectedDataFromTable();
        if (selectedData == null) {
            AssetMessage.ERRORSYS("请选择一条数据!");
            return null;
        }
        return null;
    }

    private class DisDetailTask extends org.jdesktop.application.Task<Object, Void> {
        DisDetailTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to DisDetailTask fields, here.
            super(app);
        }
        @Override protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            return null;  // return your result
        }
        @Override protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }

    @Action
    public void close() {

        ((AssetClientView) Application.getInstance(AssetClientApp.class).getMainView()).getMainViewPane().getRightPane().remove(this);
        BaseTreePane.disTabCount.remove(this.getName());

    }

}
