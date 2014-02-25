/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.spcx;

import com.jskj.asset.client.bean.report.Yimiaoxiaoshoudetail;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ws.CommFindEntity;
import com.jskj.asset.client.panel.spcx.task.Yimiao2leichengbenFindTask;
import com.jskj.asset.client.panel.ymgl.*;
import com.jskj.asset.client.util.BindTableHelper;
import com.jskj.asset.client.util.DateChooser;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JTextField;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author huiqi
 */
public class YiMiaoChengBenChaXunJDialog extends BaseDialog {

    private final static Logger logger = Logger.getLogger(YiMiaoChengBenChaXunJDialog.class);
    private List<Yimiaoxiaoshoudetail> currentPageData;

    private final BindTableHelper<Yimiaoxiaoshoudetail> bindTable;

    private final HashMap parameterMap;
    private int pageIndex;
    public int pageSize;
    private int count;
    private final String conditionSql;
    BaseTextField jTextFieldStartInit;
    BaseTextField jTextFieldEndInit;

    /**
     * Creates new form yimiaoyanshouJDialog
     */
    public YiMiaoChengBenChaXunJDialog(java.awt.Frame parent, boolean modal) {
        super();

        jTextFieldStartInit = new BaseTextField();
        jTextFieldEndInit = new BaseTextField();
        jTextFieldStartInit.registerIcon(IPopupBuilder.TYPE_DATE_CLICK);
        jTextFieldEndInit.registerIcon(IPopupBuilder.TYPE_DATE_CLICK);

        DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd HH:mm:ss");
        dateChooser1.register(jTextFieldStartInit);

        DateChooser dateChooser2 = DateChooser.getInstance("yyyy-MM-dd HH:mm:ss");
        dateChooser2.register(jTextFieldEndInit);

        initComponents();

        pageIndex = 1;
        pageSize = 20;
        count = 0;
        conditionSql = "";

        parameterMap = new HashMap();
        parameterMap.put("pagesize", String.valueOf(pageSize));
        parameterMap.put("pageindex", String.valueOf(pageIndex));
        parameterMap.put("conditionSql", "");
        bindTable = new BindTableHelper<Yimiaoxiaoshoudetail>(jTable4, new ArrayList<Yimiaoxiaoshoudetail>());
        bindTable.createTable(new String[][]{{"saleId", "单据编号"}, {"saleDate", "制单日期"},
        {"yimiaotb.yimiaoName", "疫苗名称"}, {"yimiaotb.yimiaoGuige", "规格"}, {"yimiaotb.yimiaoJixing", "剂型"}, {"yimiaotb.yimiaoShengchanqiye", "生产企业"}, {"yimiaotb.yimiaoPizhunwenhao", "批号"}, {"yimiaotb.yimiaoYushoujia", "预售价"}, {"price", "进价"}, {"quantity", "数量"}, {"totalprice", "销售毛利"}, {"kehudanweitb.kehudanweiName", "客户单位"}, {"zhidanren.userName", "经办人"}, {"depottb.depotName", "仓库"}});
        bindTable.setColumnType(Date.class, 2);
        bindTable.setColumnType(Float.class, 8, 9);
        bindTable.bind().setColumnWidth(new int[]{0, 80}, new int[]{1, 150}, new int[]{2, 150}).setRowHeight(25);

        Dimension dimension = new Dimension();
        dimension.setSize(800, 600);
        this.setMinimumSize(dimension);
        this.setMaximumSize(dimension);

//        bindTable.createHeaderFilter(new ITableHeaderPopupBuilder() {
//
//            @Override
//            public int[] getFilterColumnHeader() {
//                //那些列需要有查询功能，这样就可以点击列头弹出一个popup
//                return new int[]{3};
//            }
//
//            @Override
//            public Task filterData(HashMap<Integer, String> searchKeys) {
//
//                if (searchKeys.size() > 0) {
//                    StringBuilder sql = new StringBuilder();
//                    if (!searchKeys.get(2).trim().equals("")) {
//                        sql.append("appparam_type like \"%").append(searchKeys.get(2).trim()).append("%\"").append(" and ");
//                    }
//                    if (!searchKeys.get(3).trim().equals("")) {
//                        sql.append("appparam_name like \"%").append(searchKeys.get(3).trim()).append("%\"").append(" and ");
//                    }
//                    if (sql.length() > 0) {
//                        sql.delete(sql.length() - 5, sql.length() - 1);
//                    }
//                    conditionSql = sql.toString();
//                } else {
//                    conditionSql = "";
//                }
//
//                return reload();
//            }
//
//        });
    }

    private class RefreshTask extends Yimiao2leichengbenFindTask {

        RefreshTask() {
            super(parameterMap);
        }

        @Override
        public void responseResult(CommFindEntity<Yimiaoxiaoshoudetail> response) {

            count = response.getCount();
            jLabelTotal.setText(((pageIndex - 1) * pageSize + 1) + "/" + count);
            logger.debug("total:" + count + ",get current size:" + response.getResult().size());

            //存下所有的数据
            currentPageData = response.getResult();
            bindTable.refreshData(currentPageData);

        }
    }

    @Action
    public void pagePrev() {
        pageIndex = pageIndex - 1;
        pageIndex = pageIndex <= 0 ? 1 : pageIndex;
        parameterMap.put("pageindex", String.valueOf(pageIndex));
        new RefreshTask().execute();
    }

    @Action
    public void pageNext() {
        if (pageSize * (pageIndex) <= count) {
            pageIndex = pageIndex + 1;
        }
        parameterMap.put("pageindex", String.valueOf(pageIndex));
        new RefreshTask().execute();
    }

    @Action
    public Task print() {
        Yimiao2leichengbenFindTask printData = new Yimiao2leichengbenFindTask(parameterMap) {
            @Override
            public void responseResult(CommFindEntity response) {
                bindTable.createPrinter("二类疫苗成本查询", response.getResult()).buildInBackgound().execute();
            }
        };
        return printData;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldStart = jTextFieldStartInit;
        jLabel2 = new javax.swing.JLabel();
        jTextFieldEnd = jTextFieldEndInit;
        jToolBar3 = new javax.swing.JToolBar();
        jButton15 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabelTotal = new javax.swing.JLabel();

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane2.setViewportView(jTable1);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.setName("jTable2"); // NOI18N
        jScrollPane3.setViewportView(jTable2);

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable3.setName("jTable3"); // NOI18N
        jScrollPane4.setViewportView(jTable3);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoChengBenChaXunJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);

        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoChengBenChaXunJDialog.class, this);
        jButton2.setAction(actionMap.get("search")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setOpaque(false);
        jToolBar1.add(jButton2);

        jButton4.setAction(actionMap.get("print")); // NOI18N
        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setBorder(null);
        jButton4.setBorderPainted(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setOpaque(false);
        jToolBar1.add(jButton4);

        jButton5.setAction(actionMap.get("close")); // NOI18N
        jButton5.setIcon(resourceMap.getIcon("jButton5.icon")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setBorder(null);
        jButton5.setBorderPainted(false);
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton5.setName("jButton5"); // NOI18N
        jButton5.setOpaque(false);
        jToolBar1.add(jButton5);

        jToolBar2.setBorder(null);
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setBorderPainted(false);
        jToolBar2.setName("jToolBar2"); // NOI18N
        jToolBar2.setOpaque(false);

        jButton1.setAction(actionMap.get("pagePrev")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton1);

        jButton6.setAction(actionMap.get("pageNext")); // NOI18N
        jButton6.setText(resourceMap.getString("jButton6.text")); // NOI18N
        jButton6.setBorder(null);
        jButton6.setBorderPainted(false);
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setName("jButton6"); // NOI18N
        jButton6.setOpaque(false);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton6);

        jPanel2.setName("jPanel2"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextFieldStart.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextFieldStart.setName("jTextFieldStart"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextFieldEnd.setName("jTextFieldEnd"); // NOI18N

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);
        jToolBar3.setBorderPainted(false);
        jToolBar3.setName("jToolBar3"); // NOI18N
        jToolBar3.setOpaque(false);

        jButton15.setAction(actionMap.get("search")); // NOI18N
        jButton15.setIcon(resourceMap.getIcon("jButton15.icon")); // NOI18N
        jButton15.setText(resourceMap.getString("jButton15.text")); // NOI18N
        jButton15.setBorderPainted(false);
        jButton15.setFocusable(false);
        jButton15.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton15.setName("jButton15"); // NOI18N
        jButton15.setOpaque(false);
        jButton15.setRolloverEnabled(true);
        jToolBar3.add(jButton15);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldStart, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldEnd)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldStart, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jScrollPane5.setMaximumSize(new java.awt.Dimension(800, 32767));
        jScrollPane5.setMinimumSize(new java.awt.Dimension(800, 24));
        jScrollPane5.setName("jScrollPane5"); // NOI18N
        jScrollPane5.setPreferredSize(new java.awt.Dimension(800, 404));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable4.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable4.setName("jTable4"); // NOI18N
        jScrollPane5.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable4.columnModel.title0")); // NOI18N
            jTable4.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable4.columnModel.title1")); // NOI18N
            jTable4.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable4.columnModel.title2")); // NOI18N
            jTable4.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable4.columnModel.title10")); // NOI18N
            jTable4.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable4.columnModel.title5")); // NOI18N
            jTable4.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable4.columnModel.title6")); // NOI18N
            jTable4.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTable4.columnModel.title7")); // NOI18N
            jTable4.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTable4.columnModel.title8")); // NOI18N
            jTable4.getColumnModel().getColumn(8).setPreferredWidth(100);
            jTable4.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTable4.columnModel.title9")); // NOI18N
            jTable4.getColumnModel().getColumn(9).setPreferredWidth(100);
            jTable4.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("jTable4.columnModel.title11")); // NOI18N
            jTable4.getColumnModel().getColumn(10).setHeaderValue(resourceMap.getString("jTable4.columnModel.title10")); // NOI18N
            jTable4.getColumnModel().getColumn(11).setHeaderValue(resourceMap.getString("jTable4.columnModel.title11")); // NOI18N
            jTable4.getColumnModel().getColumn(12).setHeaderValue(resourceMap.getString("jTable4.columnModel.title12")); // NOI18N
            jTable4.getColumnModel().getColumn(13).setHeaderValue(resourceMap.getString("jTable4.columnModel.title13")); // NOI18N
            jTable4.getColumnModel().getColumn(14).setHeaderValue(resourceMap.getString("jTable4.columnModel.title14")); // NOI18N
            jTable4.getColumnModel().getColumn(15).setHeaderValue(resourceMap.getString("jTable4.columnModel.title15")); // NOI18N
            jTable4.getColumnModel().getColumn(16).setHeaderValue(resourceMap.getString("jTable4.columnModel.title16")); // NOI18N
        }

        jLabelTotal.setText(resourceMap.getString("jLabelTotal.text")); // NOI18N
        jLabelTotal.setName("jLabelTotal"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(YiMiaoCaiGouJiHuaFenXiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoCaiGouJiHuaFenXiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoCaiGouJiHuaFenXiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoCaiGouJiHuaFenXiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                YiMiaoCaiGouJiHuaFenXiJDialog dialog = new YiMiaoCaiGouJiHuaFenXiJDialog(new javax.swing.JFrame(), true);
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
    public void close() {
        this.dispose();
    }

    @Action
    public Task search() {
        String  endDate = jTextFieldEnd.getText();
        String  startDate= jTextFieldStart.getText();
        parameterMap.put("startDate", startDate);
        parameterMap.put("endDate", endDate);

        return new RefreshTask();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextFieldEnd;
    private javax.swing.JTextField jTextFieldStart;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    // End of variables declaration//GEN-END:variables
}
