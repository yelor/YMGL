/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.jichuxinxi;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.YiMiaotbFindEntity;
import com.jskj.asset.client.bean.entity.YimiaoAll;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BasePanel;
import com.jskj.asset.client.layout.ITableHeaderPopupBuilder;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.layout.ws.CommUpdateTask;
import com.jskj.asset.client.panel.jichuxinxi.task.YiMiaoTask;
import com.jskj.asset.client.util.BindTableHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author huiqi
 */
public class YiMiaoPanel extends BasePanel {

    private final static Logger logger = Logger.getLogger(YiMiaoPanel.class);
    private YiMiaoInfoJDialog yiMiaoInfoJDialog;

    private int pageIndex;
    public int pageSize;
    private int count;
    private String conditionSql;

    private List<YimiaoAll> yimiaos;

    private YimiaoAll yimiao;

    private final BindTableHelper<YimiaoAll> bindTable;

    /**
     * Creates new form YiMiaoJDialog
     */

    public YiMiaoPanel() {
        super();
        initComponents();
        pageIndex = 1;
        pageSize = 20;
        count = 0;
        conditionSql = "";

        bindTable = new BindTableHelper<YimiaoAll>(jTableYiMiao, new ArrayList<YimiaoAll>());
        bindTable.createTable(new String[][]{{"yimiaoId", "疫苗编号"}, {"yimiaoName", "疫苗名称"}, {"yimiaoType", "疫苗类别"}, {"yimiaoGuige", "规格"}, {"yimiaoJixing", "剂型"}, {"unitId", "单位"}, {"yimiaoStockdown", "库存下限"}, {"yimiaoStockup", "库存上限"}, {"yimiaoTiaoxingma", "条形码"}});
        bindTable.setIntegerType(1, 7, 8);
        bindTable.bind().setColumnWidth(new int[]{0, 80}).setRowHeight(30);;
        bindTable.createHeaderFilter(new ITableHeaderPopupBuilder() {

            @Override
            public int[] getFilterColumnHeader() {
                //那些列需要有查询功能，这样就可以点击列头弹出一个popup
                return new int[]{0,1,2,4,8};
            }

            @Override
            public Task filterData(HashMap<Integer, String> searchKeys) {

                if (searchKeys.size() > 0) {
                    StringBuilder sql = new StringBuilder();
                    if (!searchKeys.get(0).trim().equals("")) {
                        sql.append("yimiao_id = ").append(searchKeys.get(0).trim()).append(" and ");
                    }
                    if (!searchKeys.get(1).trim().equals("")) {
                        sql.append("(yimiao_name like \"%").append(searchKeys.get(1).trim()).append("%\"").append(" or zujima like \"%").append(searchKeys.get(1).trim().toLowerCase()).append("%\")").append(" and ");
                    }
                    if (!searchKeys.get(2).trim().equals("")) {
                        sql.append("yimiao_type like \"%").append(searchKeys.get(2).trim()).append("%\"").append(" and ");
                    }
                    if (!searchKeys.get(4).trim().equals("")) {
                        sql.append("yimiao_jixing like \"%").append(searchKeys.get(4).trim()).append("%\"").append(" and ");
                    }
                    if (!searchKeys.get(8).trim().equals("")) {
                        sql.append("yimiao_tiaoxingma = ").append(searchKeys.get(8).trim()).append(" and ");
                    }
                    if (sql.length() > 0) {
                        sql.delete(sql.length() - 5, sql.length() - 1);
                    }
                    conditionSql = sql.toString();
                } else {
                    conditionSql = "";
                }

                return reload();
            }

        });
    }

    @Action
    @Override
    public Task reload() {
        return new RefreshTask(0, pageSize);
    }

    private class ReloadTask extends org.jdesktop.application.Task<Object, Void> {
        ReloadTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to ReloadTask fields, here.
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
    public void pagePrev() {
        pageIndex = pageIndex - 1;
        pageIndex = pageIndex <= 0 ? 1 : pageIndex;
        new RefreshTask(pageIndex, pageSize).execute();
    }

    @Action
    public void pageNext() {
        if (pageSize * (pageIndex) <= count) {
            pageIndex = pageIndex + 1;
        }
        new RefreshTask(pageIndex, pageSize).execute();
    }

    private YimiaoAll selectedYiMiao() {
        if (jTableYiMiao.getSelectedRow() >= 0) {
            if (yimiaos != null) {
                return yimiaos.get(jTableYiMiao.getSelectedRow());
            }
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ctrlPane = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jLabelTotal = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableYiMiao = new javax.swing.JTable();

        setName("Form"); // NOI18N

        ctrlPane.setName("ctrlPane"); // NOI18N

        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoPanel.class, this);
        jButton6.setAction(actionMap.get("addYiMiao")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoPanel.class);
        jButton6.setIcon(resourceMap.getIcon("jButton6.icon")); // NOI18N
        jButton6.setText(resourceMap.getString("jButton6.text")); // NOI18N
        jButton6.setBorder(null);
        jButton6.setBorderPainted(false);
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton6.setName("jButton6"); // NOI18N
        jButton6.setOpaque(false);
        jToolBar1.add(jButton6);

        jButton7.setAction(actionMap.get("updateYiMiao")); // NOI18N
        jButton7.setIcon(resourceMap.getIcon("jButton7.icon")); // NOI18N
        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setBorder(null);
        jButton7.setBorderPainted(false);
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton7.setName("jButton7"); // NOI18N
        jButton7.setOpaque(false);
        jToolBar1.add(jButton7);

        jButton8.setAction(actionMap.get("deleteYiMiaotb")); // NOI18N
        jButton8.setIcon(resourceMap.getIcon("jButton8.icon")); // NOI18N
        jButton8.setText(resourceMap.getString("jButton8.text")); // NOI18N
        jButton8.setBorder(null);
        jButton8.setBorderPainted(false);
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton8.setName("jButton8"); // NOI18N
        jToolBar1.add(jButton8);

        jButton4.setAction(actionMap.get("reload")); // NOI18N
        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setBorder(null);
        jButton4.setBorderPainted(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setOpaque(false);
        jToolBar1.add(jButton4);

        jButton3.setAction(actionMap.get("print")); // NOI18N
        jButton3.setIcon(resourceMap.getIcon("jButton3.icon")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setOpaque(false);
        jToolBar1.add(jButton3);

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
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        ctrlPaneLayout.setVerticalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
            .addComponent(jLabelTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTableYiMiao.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTableYiMiao.setName("jTableYiMiao"); // NOI18N
        jScrollPane2.setViewportView(jTableYiMiao);
        if (jTableYiMiao.getColumnModel().getColumnCount() > 0) {
            jTableYiMiao.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTableYiMiao.columnModel.title0")); // NOI18N
            jTableYiMiao.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTableYiMiao.columnModel.title1")); // NOI18N
            jTableYiMiao.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTableYiMiao.columnModel.title2")); // NOI18N
            jTableYiMiao.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTableYiMiao.columnModel.title3")); // NOI18N
            jTableYiMiao.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTableYiMiao.columnModel.title4")); // NOI18N
            jTableYiMiao.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTableYiMiao.columnModel.title5")); // NOI18N
            jTableYiMiao.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTableYiMiao.columnModel.title6")); // NOI18N
            jTableYiMiao.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTableYiMiao.columnModel.title7")); // NOI18N
            jTableYiMiao.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTableYiMiao.columnModel.title8")); // NOI18N
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
            .addComponent(ctrlPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(ctrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE))
        );
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
            java.util.logging.Logger.getLogger(YiMiaoPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                YiMiaoJDialog dialog = new YiMiaoJDialog(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
    }

    @Action
    public void addYiMiao() {
        SwingUtilities.invokeLater(new Runnable() {
            private YiMiaoInfoJDialog yiMiaoInfoJDialog;

            @Override
            public void run() {
                if (yiMiaoInfoJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    yiMiaoInfoJDialog = new YiMiaoInfoJDialog(YiMiaoPanel.this);
                    yiMiaoInfoJDialog.setLocationRelativeTo(mainFrame);
                }
                yiMiaoInfoJDialog.setUpdatedData(new YimiaoAll());
                AssetClientApp.getApplication().show(yiMiaoInfoJDialog);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ctrlPane;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableYiMiao;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar3;
    // End of variables declaration//GEN-END:variables

    @Override
    public Task reload(Object param) {
        return null;
    }

    private class RefreshTask extends YiMiaoTask {

        public RefreshTask(int pageIndex, int pageSize) {
            super(pageIndex, pageSize,conditionSql);
        }

        @Override
        public void onSucceeded(Object object) {

            if (object instanceof Exception) {
                Exception e = (Exception) object;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }

            YiMiaotbFindEntity yimiaotbs = (YiMiaotbFindEntity) object;

            if (yimiaotbs != null && yimiaotbs.getResult().size() > 0) {
                count = yimiaotbs.getCount();
                //             jLabelTotal.setText(((pageIndex - 1) * YiMiaoTask.pageSize + 1) + "/" + count);
                logger.debug("total:" + count + ",get yimao size:" + yimiaotbs.getResult().size());
                jLabelTotal.setText(((pageIndex - 1) * pageSize + 1) + "/" + count);
                //存下所有的数据
                yimiaos = yimiaotbs.getResult();

                bindTable.refreshData(yimiaos);

            }
        }
    }

    @Action
    public void updateYiMiao() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                YimiaoAll yimiao = selectedYiMiao();
                if (yimiao == null) {
                    AssetMessage.ERRORSYS("请选择疫苗!");
                    return;
                }

                if (yiMiaoInfoJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    yiMiaoInfoJDialog = new YiMiaoInfoJDialog(YiMiaoPanel.this);
                    yiMiaoInfoJDialog.setLocationRelativeTo(mainFrame);
                }
                yiMiaoInfoJDialog.setUpdatedData(yimiao);
                AssetClientApp.getApplication().show(yiMiaoInfoJDialog);
            }
        });
    }

    @Action
    public Task deleteYiMiaotb() {
        YimiaoAll selectYimiao = selectedYiMiao();
        if (selectYimiao == null) {
            AssetMessage.ERRORSYS("请选择疫苗!");
            return null;
        }
        int result = AssetMessage.CONFIRM("确定删除疫苗:" + selectYimiao.getYimiaoName());
        if (result == JOptionPane.OK_OPTION) {
            return new CommUpdateTask<YimiaoAll>(selectYimiao, "yimiao/delete/" + selectYimiao.getYimiaoId()) {
                @Override
                public void responseResult(ComResponse<YimiaoAll> response) {
                    if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                        reload().execute();
                    } else {
                        AssetMessage.ERROR(response.getErrorMessage(), YiMiaoPanel.this);
                    }
                }

            };
        }
        return null;
    }

    private class DeleteYiMiaotbTask extends org.jdesktop.application.Task<Object, Void> {
        DeleteYiMiaotbTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to DeleteYiMiaotbTask fields, here.
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
    public Task print() {
        YiMiaoTask printData = new YiMiaoTask(0, count,conditionSql) {
            @Override
        public void onSucceeded(Object object) {

            if (object instanceof Exception) {
                Exception e = (Exception) object;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }

            YiMiaotbFindEntity yimiaotbs = (YiMiaotbFindEntity) object;

            if (yimiaotbs != null && yimiaotbs.getResult().size() > 0) {
                bindTable.createPrinter("疫苗信息", yimiaotbs.getResult()).buildInBackgound().execute();
            }
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
}
