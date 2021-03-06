/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.jichuxinxi;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Kehudanweitb;
import com.jskj.asset.client.bean.entity.KehudanweitbFindEntity;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BasePanel;
import com.jskj.asset.client.layout.ITableHeaderPopupBuilder;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.layout.ws.CommUpdateTask;
import com.jskj.asset.client.panel.jichuxinxi.task.KehudanweiTask;
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
public class KeHuDanWeiPanel extends BasePanel {
    
    private final static Logger logger = Logger.getLogger(KeHuDanWeiPanel.class);
    
    private final int pageSize;
    private int pageIndex;
    private String conditionSql;
    
    private int count;
    
    private List<Kehudanweitb> kehudanweis;
    
    private KeHuDanWeiInfoJDialog keHuDanWeiInfoJDialog;
    
    private final BindTableHelper<Kehudanweitb> bindTable;
    
    private int uiType;

    /**
     * Creates new form YiMiaoJDialog
     */
    public KeHuDanWeiPanel(int type) {
        super();
        initComponents();
        pageIndex = 1;
        count = 0;
        pageSize = 20;
        conditionSql = "";
        this.uiType = type;
        bindTable = new BindTableHelper<Kehudanweitb>(jTableKehudanwei, new ArrayList<Kehudanweitb>());
        bindTable.createTable(new String[][]{{"kehudanweiId", "编号"}, {"kehudanweiName", "名称"}, {"kehudanweiConstactperson", "联系人"}, {"kehudanweiPhone", "电话"}, {"kehudanweiFax", "传真"}, {"kehudanweiAddr", "单位地址"}, {"kehudanweiRemark", "备注"}});
        bindTable.setIntegerType(1);
        bindTable.bind().setColumnWidth(new int[]{0, 100}).setRowHeight(30);
        
        bindTable.createHeaderFilter(new ITableHeaderPopupBuilder() {
            
            @Override
            public int[] getFilterColumnHeader() {
                //那些列需要有查询功能，这样就可以点击列头弹出一个popup
                return new int[]{0, 1, 2};
            }
            
            @Override
            public Task filterData(HashMap<Integer, String> searchKeys) {
                
                if (searchKeys.size() > 0) {
                    StringBuilder sql = new StringBuilder();
                    if (!searchKeys.get(0).trim().equals("")) {
                        sql.append("kehudanwei_id =").append(searchKeys.get(0).trim()).append(" and ");
                    }
                    if (!searchKeys.get(1).trim().equals("")) {
                        sql.append("(kehudanwei_name like \"%").append(searchKeys.get(1).trim()).append("%\"").append(" or kehudanwei_zujima like \"%").append(searchKeys.get(1).trim().toLowerCase()).append("%\")").append(" and ");
                    }
                    if (!searchKeys.get(2).trim().equals("")) {
                        sql.append("kehudanwei_constactPerson like \"%").append(searchKeys.get(2).trim()).append("%\"").append(" and ");
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
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jLabelTotal = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableKehudanwei = new javax.swing.JTable();

        setName("Form"); // NOI18N

        ctrlPane.setName("ctrlPane"); // NOI18N

        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(KeHuDanWeiPanel.class, this);
        jButton6.setAction(actionMap.get("addKeHuDanWei")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(KeHuDanWeiPanel.class);
        jButton6.setIcon(resourceMap.getIcon("jButton6.icon")); // NOI18N
        jButton6.setText(resourceMap.getString("jButton6.text")); // NOI18N
        jButton6.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jButton6.setBorderPainted(false);
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton6.setName("jButton6"); // NOI18N
        jButton6.setOpaque(false);
        jToolBar1.add(jButton6);

        jButton7.setAction(actionMap.get("updateKehudanwei")); // NOI18N
        jButton7.setIcon(resourceMap.getIcon("jButton7.icon")); // NOI18N
        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jButton7.setBorderPainted(false);
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton7.setName("jButton7"); // NOI18N
        jButton7.setOpaque(false);
        jToolBar1.add(jButton7);

        jButton8.setAction(actionMap.get("deleteKehudanwei")); // NOI18N
        jButton8.setIcon(resourceMap.getIcon("jButton8.icon")); // NOI18N
        jButton8.setText(resourceMap.getString("jButton8.text")); // NOI18N
        jButton8.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jButton8.setBorderPainted(false);
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton8.setName("jButton8"); // NOI18N
        jButton8.setOpaque(false);
        jToolBar1.add(jButton8);

        jButton4.setAction(actionMap.get("reload")); // NOI18N
        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jButton4.setBorderPainted(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setOpaque(false);
        jToolBar1.add(jButton4);

        jButton3.setAction(actionMap.get("print")); // NOI18N
        jButton3.setIcon(resourceMap.getIcon("jButton3.icon")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setBorder(javax.swing.BorderFactory.createCompoundBorder());
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

        jButton18.setAction(actionMap.get("pagePrev")); // NOI18N
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

        jButton19.setAction(actionMap.get("pageNext")); // NOI18N
        jButton19.setText(resourceMap.getString("jButton19.text")); // NOI18N
        jButton19.setBorderPainted(false);
        jButton19.setFocusable(false);
        jButton19.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton19.setMaximumSize(new java.awt.Dimension(60, 25));
        jButton19.setMinimumSize(new java.awt.Dimension(60, 25));
        jButton19.setName("jButton19"); // NOI18N
        jButton19.setOpaque(false);
        jButton19.setPreferredSize(new java.awt.Dimension(60, 25));
        jButton19.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(jButton19);

        jLabelTotal.setForeground(resourceMap.getColor("jLabelTotal.foreground")); // NOI18N
        jLabelTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTotal.setName("jLabelTotal"); // NOI18N

        javax.swing.GroupLayout ctrlPaneLayout = new javax.swing.GroupLayout(ctrlPane);
        ctrlPane.setLayout(ctrlPaneLayout);
        ctrlPaneLayout.setHorizontalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctrlPaneLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        ctrlPaneLayout.setVerticalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabelTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
        );

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTableKehudanwei.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableKehudanwei.setName("jTableKehudanwei"); // NOI18N
        jScrollPane2.setViewportView(jTableKehudanwei);
        if (jTableKehudanwei.getColumnModel().getColumnCount() > 0) {
            jTableKehudanwei.getColumnModel().getColumn(0).setPreferredWidth(120);
            jTableKehudanwei.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTableKehudanwei.columnModel.title0")); // NOI18N
            jTableKehudanwei.getColumnModel().getColumn(1).setPreferredWidth(120);
            jTableKehudanwei.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTableKehudanwei.columnModel.title1")); // NOI18N
            jTableKehudanwei.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable1.columnModel.title5")); // NOI18N
            jTableKehudanwei.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
            jTableKehudanwei.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable1.columnModel.title3")); // NOI18N
            jTableKehudanwei.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable1.columnModel.title6")); // NOI18N
            jTableKehudanwei.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ctrlPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ctrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(KeHuDanWeiPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KeHuDanWeiPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KeHuDanWeiPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KeHuDanWeiPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                KeHuDanWeiJDialog dialog = new KeHuDanWeiJDialog(new javax.swing.JFrame(), true);
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
    @Override
    public Task reload() {
        conditionSql = " kehudanwei_type = " + uiType + (!conditionSql.trim().equals("") ? " and " + conditionSql : "");
        return new RefreshTask(0, pageSize);
    }
    
    private Kehudanweitb selectedKehudanwei() {
        if (jTableKehudanwei.getSelectedRow() >= 0) {
            if (kehudanweis != null) {
                return kehudanweis.get(jTableKehudanwei.getSelectedRow());
            }
        }
        return null;
    }
    
    @Override
    public Task reload(Object param) {
        return null;
    }
    
    private class RefreshTask extends KehudanweiTask {
        
        RefreshTask(int pageIndex, int pageSize) {
            super(pageIndex, pageSize, conditionSql);
        }
        
        @Override
        public void onSucceeded(Object object) {
            
            if (object instanceof Exception) {
                Exception e = (Exception) object;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            
            KehudanweitbFindEntity danjuleixingtbs = (KehudanweitbFindEntity) object;
            
            if (danjuleixingtbs != null && danjuleixingtbs.getResult() != null && danjuleixingtbs.getResult().size() > 0) {
                count = danjuleixingtbs.getCount();
                jLabelTotal.setText(((pageIndex - 1) * pageSize + 1) + "/" + count);
                logger.debug("total:" + count + ",get danjuleixing size:" + danjuleixingtbs.getResult().size());

                //存下所有的数据
                kehudanweis = danjuleixingtbs.getResult();
                
                bindTable.refreshData(kehudanweis);
                
            }
        }
    }
    
    @Action
    public void addKeHuDanWei() {
        SwingUtilities.invokeLater(new Runnable() {
            private KeHuDanWeiInfoJDialog keHuDanWeiInfoJDialog;
            
            @Override
            public void run() {
                if (keHuDanWeiInfoJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    keHuDanWeiInfoJDialog = new KeHuDanWeiInfoJDialog(KeHuDanWeiPanel.this);
                    keHuDanWeiInfoJDialog.setLocationRelativeTo(mainFrame);
                }
                Kehudanweitb kehutb = new Kehudanweitb();
                kehutb.setKehudanweiType(uiType);
                keHuDanWeiInfoJDialog.setUpdatedData(kehutb);
                AssetClientApp.getApplication().show(keHuDanWeiInfoJDialog);
            }
        });
    }
    
    @Action
    public void updateKehudanwei() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Kehudanweitb danjuleixing = selectedKehudanwei();
                if (danjuleixing == null) {
                    AssetMessage.ERRORSYS("请选择客户单位!");
                    return;
                }
                
                if (keHuDanWeiInfoJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    keHuDanWeiInfoJDialog = new KeHuDanWeiInfoJDialog(KeHuDanWeiPanel.this);
                    keHuDanWeiInfoJDialog.setLocationRelativeTo(mainFrame);
                }
                keHuDanWeiInfoJDialog.setUpdatedData(danjuleixing);
                AssetClientApp.getApplication().show(keHuDanWeiInfoJDialog);
            }
        });
    }
    
    @Action
    public Task deleteKehudanwei() {
        Kehudanweitb danjuleixing = selectedKehudanwei();
        if (danjuleixing == null) {
            AssetMessage.ERRORSYS("请选择客户单位");
            return null;
        }
        int result = AssetMessage.CONFIRM("确定删除客户单位:" + danjuleixing.getKehudanweiName());
        if (result == JOptionPane.OK_OPTION) {
            return new CommUpdateTask<Kehudanweitb>(danjuleixing, "kehudanwei/delete/" + danjuleixing.getKehudanweiId()) {
                @Override
                public void responseResult(ComResponse<Kehudanweitb> response) {
                    if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                        reload().execute();
                    } else {
                        AssetMessage.ERROR(response.getErrorMessage(), KeHuDanWeiPanel.this);
                    }
                }
                
            };
        }
        return null;
    }
    
    @Action
    public void pagePrev() {
        pageIndex = pageIndex - 1;
        pageIndex = pageIndex <= 0 ? 1 : pageIndex;
        conditionSql = " kehudanwei_type = " + uiType + (!conditionSql.trim().equals("") ? " and " + conditionSql : "");
        new RefreshTask(pageIndex, pageSize).execute();
    }
    
    @Action
    public void pageNext() {
        if (pageSize * (pageIndex) <= count) {
            pageIndex = pageIndex + 1;
        }
        conditionSql = " kehudanwei_type = " + uiType + (!conditionSql.trim().equals("") ? " and " + conditionSql : "");
        new RefreshTask(pageIndex, pageSize).execute();
    }
    
    @Action
    public void exit() {
    }
    
    @Action
    public Task print() {
        conditionSql = " kehudanwei_type = " + uiType + (!conditionSql.trim().equals("") ? " and " + conditionSql : "");
        KehudanweiTask printData = new KehudanweiTask(0, count, conditionSql) {
            
            @Override
            public void onSucceeded(Object object) {
                if (object instanceof Exception) {
                    Exception e = (Exception) object;
                    logger.error(e);
                    return;
                }
                KehudanweitbFindEntity danjuleixingtbs = (KehudanweitbFindEntity) object;
                
                if (danjuleixingtbs != null && danjuleixingtbs.getResult() != null && danjuleixingtbs.getResult().size() > 0) {
                    bindTable.createPrinter("客户单位", danjuleixingtbs.getResult()).buildInBackgound().execute();
                }
            }
            
        };
        return printData;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ctrlPane;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableKehudanwei;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar3;
    // End of variables declaration//GEN-END:variables
}
