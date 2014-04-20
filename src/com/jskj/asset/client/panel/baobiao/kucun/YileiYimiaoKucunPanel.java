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
import com.jskj.asset.client.bean.entity.StockpiletbAll;
import com.jskj.asset.client.bean.entity.StockpiletbFindEntity;
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
public final class YileiYimiaoKucunPanel extends BasePanel {

    private final static Logger logger = Logger.getLogger(UserPanel.class);

    private int pageIndex;
    public int pageSize;
    private int count;
    private String conditionSql;

    private List<StockpiletbAll> stockpile;

    private final BindTableHelper<StockpiletbAll> bindTable;

    /**
     * Creates new form NoFoundPane
     */
    public YileiYimiaoKucunPanel() {
        super();
        initComponents();
        pageIndex = 1;
        pageSize = 20;
        conditionSql = "yimiao_id in (select distinct stockPile.yimiao_id from stockpile,yimiao where stockpile.stockPile_price=0 and yimiao.yimiao_id=stockpile.yimiao_id)";
        count = 0;
        bindTable = new BindTableHelper<StockpiletbAll>(jTableStockpile, new ArrayList<StockpiletbAll>());
        bindTable.createTable(new String[][]{{"stockpileId", "库存编号"}, {"yimiao.yimiaoId", "疫苗编号"}, {"yimiao.yimiaoName", "疫苗名称"}, {"yimiao.yimiaoJixing", "剂型"},
        {"yimiao.yimiaoGuige", "疫苗规格"}, {"yimiao.unitId", "单位"}, {"stockpileQuantity", "库存数量"}, {"stockpilePrice", "成本均价"}, {"stockpileTotalprice", "库存金额"}});
        bindTable.setColumnType(Integer.class, 1);
        bindTable.bind().setColumnWidth(new int[]{0, 100}, new int[]{1, 100}, new int[]{2, 100}, new int[]{3, 150}, new int[]{5, 150}, new int[]{6, 100}, new int[]{7, 100}, new int[]{8, 150}).setRowHeight(30);

        bindTable.createHeaderFilter(new ITableHeaderPopupBuilder() {

            @Override
            public int[] getFilterColumnHeader() {
                //那些列需要有查询功能，这样就可以点击列头弹出一个popup
                return new int[]{2, 3, 6};
            }

            @Override
            public Task filterData(HashMap<Integer, String> searchKeys) {

                if (searchKeys.size() > 0) {
                    String sql = "";
//                    if (!searchKeys.get(2).trim().equals("")) {
//                        sql.append("(yimiao_name like \"%").append(searchKeys.get(2).trim()).append("%\"").append(" or zujima like \"%").append(searchKeys.get(2).trim().toLowerCase()).append("%\")").append(" and ");
//                    }
//                    if (!searchKeys.get(4).trim().equals("")) {
//                        sql.append("yimiao_type like \"%").append(searchKeys.get(4).trim()).append("%\"").append(" and ");
//                    }
//                    if (!searchKeys.get(5).trim().equals("")) {
//                        sql.append("yimiao_shengchanqiye like \"%").append(searchKeys.get(5).trim()).append("%\"").append(" and ");
//                    }
                    if (bindTable.getSelectedBean() != null) {
                        sql += "stockPile_id in (select distinct stockPile.stockpile_id from stockpile,yimiao where stockpile.stockPile_price=0 and yimiao.yimiao_id=stockpile.yimiao_id and (yimiao.yimiao_name like \"%" + bindTable.getSelectedBean().getYimiao().getYimiaoName() + "%\"))";

                    }
//                    if (sql.length() > 0) {
//                        sql.delete(sql.length() - 5, sql.length() - 1);
//                    }
                    conditionSql = sql.toString();
                } else {
                    conditionSql = "";
                }

                return reload();
            }

        });
    }

    @Action
    public Task picichaxunAction() {
        conditionSql = "";
        if (bindTable.getSelectedBean() != null) {
            conditionSql += "yimiao_id in (select distinct stockPile.yimiao_id from stockpile,yimiao where stockpile.stockPile_price=0 and yimiao.yimiao_id=stockpile.yimiao_id and (yimiao.yimiao_name like \"%" + bindTable.getSelectedBean().getYimiao().getYimiaoName() + "%\"))";
        } else {
            conditionSql = "";
        }
        return new OpenTabTask("报表-疫苗批次查询", new Yimiaopicichaxun1Panel(conditionSql), false);

    }

    private class PicichaxunActionTask extends org.jdesktop.application.Task<Object, Void> {
        PicichaxunActionTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to PicichaxunActionTask fields, here.
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
    @Override
    public Task reload() {
        return new RefureTask(0, pageSize);
    }

    @Override
    public Task reload(Object param) {
        return null;

    }

    private class RefureTask extends StockpiletbAllFindEntityTask {

        RefureTask(int pageIndex, int pageSize) {
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

            StockpiletbFindEntity stockpileEntiy = (StockpiletbFindEntity) object;

            if (stockpileEntiy != null) {
                count = stockpileEntiy.getCount();
                jLabelTotal.setText(((pageIndex - 1) * pageSize + 1) + "/" + count);
                logger.debug("total:" + count + ",get user size:" + stockpileEntiy.getResult().size());

                //存下所有的数据
                stockpile = stockpileEntiy.getResult();
                bindTable.refreshData(stockpile);
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
        jToolBar2 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabelTotal = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jToolBar3 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableStockpile = new BaseTable(null);

        setName("Form"); // NOI18N

        ctrlPane.setName("ctrlPane"); // NOI18N

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setBorderPainted(false);
        jToolBar2.setName("jToolBar2"); // NOI18N
        jToolBar2.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YileiYimiaoKucunPanel.class, this);
        jButton3.setAction(actionMap.get("pagePrev")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YileiYimiaoKucunPanel.class);
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

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jToolBar3.setBorder(null);
        jToolBar3.setRollover(true);
        jToolBar3.setName("jToolBar3"); // NOI18N

        jButton1.setAction(actionMap.get("picichaxunAction")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(jButton1);

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(jButton2);

        javax.swing.GroupLayout ctrlPaneLayout = new javax.swing.GroupLayout(ctrlPane);
        ctrlPane.setLayout(ctrlPaneLayout);
        ctrlPaneLayout.setHorizontalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctrlPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ctrlPaneLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGroup(ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ctrlPaneLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ctrlPaneLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        ctrlPaneLayout.setVerticalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctrlPaneLayout.createSequentialGroup()
                .addGroup(ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ctrlPaneLayout.createSequentialGroup()
                        .addGroup(ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabelTotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jToolBar2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ctrlPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ctrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableStockpile;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    // End of variables declaration//GEN-END:variables
}
