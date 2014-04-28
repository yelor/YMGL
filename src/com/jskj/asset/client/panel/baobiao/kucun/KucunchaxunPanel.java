/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UserPanel.java
 *
 * Created on Feb 21, 2010, 10:42:18 PM
 */
package com.jskj.asset.client.panel.baobiao.kucun;

import com.jskj.asset.client.panel.user.*;
import com.jskj.asset.client.bean.entity.KucunchaxunEntity;
import com.jskj.asset.client.bean.entity.KucunchaxunFindEntity;
import com.jskj.asset.client.bean.entity.UsertbFindEntity;
import com.jskj.asset.client.layout.BasePanel;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.ITableHeaderPopupBuilder;
import com.jskj.asset.client.panel.OpenTabTask;
import com.jskj.asset.client.util.BindTableHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author woderchen
 */
public final class KucunchaxunPanel extends BasePanel {

    private final static Logger logger = Logger.getLogger(KucunchaxunPanel.class);

    private int pageIndex;
    public int pageSize;
    private int count;
    private String conditionSql;
    private String type;

    private List<KucunchaxunEntity> kucun;

    private final BindTableHelper<KucunchaxunEntity> bindTable;

    /**
     * Creates new form NoFoundPane
     */
    public KucunchaxunPanel() {
        super();
        initComponents();
        pageIndex = 1;
        pageSize = 20;
        conditionSql = "";
        type = "所有";
        count = 0;
        bindTable = new BindTableHelper<KucunchaxunEntity>(jTableStockpile, new ArrayList<KucunchaxunEntity>());
        bindTable.createTable(new String[][]{{"id", "物品编号"}, {"name", "物品名称"}, {"type", "类型"}, 
            {"kucunshu", "库存数"},{"danjia", "成本均价"}, {"zongjine", "库存金额"}});
        bindTable.setColumnType(Integer.class, 1);
        bindTable.bind().setColumnWidth(new int[]{0, 200},new int[]{1, 200},new int[]{2, 200},new int[]{3, 200},new int[]{4, 200},new int[]{5, 200}).setRowHeight(30);
        bindTable.createHeaderFilter(new ITableHeaderPopupBuilder() {

            @Override
            public int[] getFilterColumnHeader() {
                //那些列需要有查询功能，这样就可以点击列头弹出一个popup
                return new int[]{1,2};
            }

            @Override
            public Task filterData(HashMap<Integer, String> searchKeys) {

                if (searchKeys.size() > 0) {
                    StringBuilder sql = new StringBuilder();
                    if (!searchKeys.get(1).trim().equals("")) {
                        if(type.equals("Ⅰ类疫苗") || type.equals("Ⅱ类疫苗")){
                            sql.append(" (yimiao_name ");
                        } else if( type.equals("固定资产")){
                            sql.append(" (gdzc_name ");
                        } else {
                            sql.append(" (dzyhp_name ");
                        }
                        sql.append("like \"%").append(searchKeys.get(1).trim()).append("%\"").append(" or zujima like \"%").append(searchKeys.get(1).trim().toLowerCase()).append("%\") ").append(" and ");
                    }
                    if (!searchKeys.get(2).trim().equals("")) {
                        if(type.equals("Ⅰ类疫苗") || type.equals("Ⅱ类疫苗")){
                            sql.append(" yimiao_type ");
                        } else if( type.equals("固定资产")){
                            sql.append(" gdzc_type ");
                        } else {
                            sql.append(" dzyhp_type ");
                        }
                        sql.append("like \"%").append(searchKeys.get(2).trim()).append("%\"").append(" and ");
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
        return new RefureTask(0, pageSize);
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

    @Override
    public Task reload(Object param) {
        return null;

    }

    private class RefureTask extends KucunchaxunTask {

        RefureTask(int pageIndex, int pageSize) {
            super(pageIndex, pageSize, type, conditionSql);
        }

        @Override
        public void onSucceeded(Object object) {

            if (object instanceof Exception) {
                Exception e = (Exception) object;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }

            KucunchaxunFindEntity kucunEntiy = (KucunchaxunFindEntity) object;

            if (kucunEntiy != null) {
                count = kucunEntiy.getCount();
                jLabelTotal.setText(((pageIndex - 1) * pageSize + 1) + "/" + count);
                logger.debug("total:" + count + ",get size:" + kucunEntiy.getResult().size());

                //存下所有的数据
                kucun = kucunEntiy.getResult();
                bindTable.refreshData(kucun);
            }
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

        ctrlPane = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonReload = new javax.swing.JButton();
        jButtonPrint = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabelTotal = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableStockpile = new BaseTable(null);

        setName("Form"); // NOI18N

        ctrlPane.setName("ctrlPane"); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(KucunchaxunPanel.class, this);
        jButtonReload.setAction(actionMap.get("reload")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(KucunchaxunPanel.class);
        jButtonReload.setIcon(resourceMap.getIcon("jButtonReload.icon")); // NOI18N
        jButtonReload.setText(resourceMap.getString("jButtonReload.text")); // NOI18N
        jButtonReload.setBorderPainted(false);
        jButtonReload.setFocusable(false);
        jButtonReload.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonReload.setName("jButtonReload"); // NOI18N
        jButtonReload.setOpaque(false);
        jToolBar1.add(jButtonReload);

        jButtonPrint.setAction(actionMap.get("print")); // NOI18N
        jButtonPrint.setIcon(resourceMap.getIcon("jButtonPrint.icon")); // NOI18N
        jButtonPrint.setText(resourceMap.getString("jButtonPrint.text")); // NOI18N
        jButtonPrint.setBorderPainted(false);
        jButtonPrint.setFocusable(false);
        jButtonPrint.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonPrint.setName("jButtonPrint"); // NOI18N
        jButtonPrint.setOpaque(false);
        jToolBar1.add(jButtonPrint);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jToolBar1.add(jLabel1);

        jButton1.setAction(actionMap.get("yileiyimiao")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);

        jButton2.setAction(actionMap.get("erleiyimiao")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton2);

        jButton5.setAction(actionMap.get("gdzc")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setName("jButton5"); // NOI18N
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton5);

        jButton6.setAction(actionMap.get("yhp")); // NOI18N
        jButton6.setText(resourceMap.getString("jButton6.text")); // NOI18N
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setName("jButton6"); // NOI18N
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton6);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setBorderPainted(false);
        jToolBar2.setName("jToolBar2"); // NOI18N
        jToolBar2.setOpaque(false);

        jButton3.setAction(actionMap.get("pagePrev")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setMaximumSize(new java.awt.Dimension(60, 25));
        jButton3.setMinimumSize(new java.awt.Dimension(60, 25));
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setOpaque(false);
        jButton3.setPreferredSize(new java.awt.Dimension(60, 25));
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton3);

        jButton4.setAction(actionMap.get("pageNext")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setMaximumSize(new java.awt.Dimension(60, 25));
        jButton4.setMinimumSize(new java.awt.Dimension(60, 25));
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setOpaque(false);
        jButton4.setPreferredSize(new java.awt.Dimension(60, 25));
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton4);

        jLabelTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTotal.setName("jLabelTotal"); // NOI18N

        javax.swing.GroupLayout ctrlPaneLayout = new javax.swing.GroupLayout(ctrlPane);
        ctrlPane.setLayout(ctrlPaneLayout);
        ctrlPaneLayout.setHorizontalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctrlPaneLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        ctrlPaneLayout.setVerticalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jLabelTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTableStockpile.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableStockpile.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableStockpile.setName("jTableStockpile"); // NOI18N
        jTableStockpile.setShowVerticalLines(false);
        jScrollPane1.setViewportView(jTableStockpile);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ctrlPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ctrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void pagePrev() {
        pageIndex = pageIndex - 1;
        pageIndex = pageIndex <= 0 ? 1 : pageIndex;
        new RefureTask(pageIndex, pageSize).execute();
    }

    @Action
    public void pageNext() {
        if (pageSize * (pageIndex) <= count) {
            pageIndex = pageIndex + 1;
        }
        new RefureTask(pageIndex, pageSize).execute();
    }

//    @Action
//    public Task yileiyimiao() {
//        type = "Ⅰ类疫苗";
//        conditionSql = "";
//        return reload();
//    }
    @Action
    public Task yileiyimiao() {
        return new OpenTabTask("报表-Ⅰ类疫苗库存状况表", new YileiYimiaoKucunPanel(), false);
    }
    @Action
    public Task erleiyimiao() {
        return new OpenTabTask("报表-Ⅱ类疫苗库存状况表", new ErleiYimiaoKucunPanel(), false);
    }

//    @Action
//    public Task erleiyimiao() {
//        type = "Ⅱ类疫苗";
//        conditionSql = "";
//        return reload();
//    }
//    
    @Action
    public Task gdzc() {
        type = "固定资产";
        conditionSql = "";
        return reload();
    }
    
    @Action
    public Task yhp() {
        type = "易耗品";
        conditionSql = "";
        return reload();
    }
    
    @Action
    public Task print() {
        Task printData = new UserTask(0, count) {
            @Override
            public void onSucceeded(Object object) {
                if (object instanceof Exception) {
                    Exception e = (Exception) object;
                    AssetMessage.ERRORSYS(e.getMessage());
                    logger.error(e);
                    return;
                }
                UsertbFindEntity usertbs = (UsertbFindEntity) object;
                if (usertbs != null) {
                    bindTable.createPrinter("职员信息", usertbs.getResult()).buildInBackgound().execute();
                } else {
                    bindTable.createPrinter("职员信息").buildInBackgound().execute();
                }

            }

        };
        return printData;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ctrlPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JButton jButtonReload;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableStockpile;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables
}
