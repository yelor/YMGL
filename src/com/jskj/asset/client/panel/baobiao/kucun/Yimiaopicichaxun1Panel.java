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

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.AssetClientView;
import com.jskj.asset.client.panel.user.*;
import com.jskj.asset.client.bean.entity.StockpiletbFindEntity;
import com.jskj.asset.client.bean.entity.UsertbFindEntity;
import com.jskj.asset.client.bean.entity.YimiaopicichaxunAll;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BasePanel;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.BaseTreePane;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ws.CommFindEntity;
import com.jskj.asset.client.panel.baobiao.caigou.ReportCaiGouFindTask;
import com.jskj.asset.client.util.BindTableHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.sf.cglib.beans.BeanCopier;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author woderchen
 */
public final class Yimiaopicichaxun1Panel extends BasePanel {

    private final static Logger logger = Logger.getLogger(UserPanel.class);

    private int pageIndex;
    public int pageSize;
    private int count;
    private String conditionSql;

    private List<YimiaopicichaxunAll> yimiaopicichaxunList = new ArrayList<YimiaopicichaxunAll>();

    private final BindTableHelper<YimiaopicichaxunAll> bindTable;

    /**
     * Creates new form NoFoundPane
     */
    public Yimiaopicichaxun1Panel(String conditionSql, String yimiaoName) {
        super();
        initComponents();
        pageIndex = 1;
        pageSize = 20;
        this.conditionSql = conditionSql;
        jTextFieldYimiaoName.setText(yimiaoName);
        jComboBoxShengchanqiye.setModel(new javax.swing.DefaultComboBoxModel(AssetClientApp.getParamNamesByType("生产企业")));
//        conditionSql = "yimiao_id in (select distinct stockPile.yimiao_id from stockpile,yimiao where stockpile.stockPile_price=0 and yimiao.yimiao_id=stockpile.yimiao_id)";
        count = 0;
        bindTable = new BindTableHelper<YimiaopicichaxunAll>(jTableStockpile, new ArrayList<YimiaopicichaxunAll>());
        bindTable.createTable(new String[][]{{"stockpileId", "库存编号"}, {"yimiao.yimiaoId", "疫苗编号"}, {"yimiao.yimiaoName", "疫苗名称"}, {"yimiao.yimiaoType", "疫苗类型"}, {"yimiao.yimiaoJixing", "剂型"},
        {"yimiao.yimiaoGuige", "疫苗规格"}, {"jiliang", "剂量"}, {"yimiao.yimiaoShengchanqiye", "生产企业"}, {"yimiao.yimiaoPizhunwenhao", "批准文号"},
        {"yimiao.unitId", "单位"}, {"youxiaodate", "有效期至"}, {"kufang", "库房"}, {"pihao", "批号"}, {"piqianfano", "批签发合格证编号"}, {"source", "进口/国产"}, {"jinkoutongguanno", "进口通关单号"}, {"stockpileQuantity", "库存数量"}, {"stockpilePrice", "成本均价"}, {"stockpileTotalprice", "库存金额"}});
        bindTable.setColumnType(Date.class, 11);
        bindTable.bind().setColumnWidth(new int[]{0, 100}, new int[]{1, 100}, new int[]{2, 100}, new int[]{3, 150}, new int[]{5, 150}, new int[]{6, 100}, new int[]{7, 100}, new int[]{8, 150}, new int[]{11, 100}).setRowHeight(30);

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
                    sql += "(yimiao_name like \"%" + jTextFieldYimiaoName.getText() + "%\" or zujima like \"%" + jTextFieldYimiaoName.getText().toLowerCase() + "%\")";
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

    @Action
    public Task picichaxunAction() {
        conditionSql = "";
        if (!jTextFieldYimiaoName.getText().trim().equals("") && !jComboBoxShengchanqiye.getSelectedItem().toString().equals("")) {
            conditionSql += "yimiao_id in (select distinct stockpile.yimiao_id from stockpile,yimiao where yimiao.yimiao_id=stockpile.yimiao_id and (yimiao.yimiao_name like \"%" + jTextFieldYimiaoName.getText().toString() + "%\") "
                    + "and (yimiao.yimiao_shengchanqiye like \"%" + jComboBoxShengchanqiye.getSelectedItem().toString() + "%\"))";
        } else if (!jTextFieldYimiaoName.getText().trim().equals("") && jComboBoxShengchanqiye.getSelectedItem().toString().equals("")) {
            conditionSql += "yimiao_id in (select distinct stockpile.yimiao_id from stockpile,yimiao where yimiao.yimiao_id=stockpile.yimiao_id and (yimiao.yimiao_name like \"%" + jTextFieldYimiaoName.getText().toString() + "%\"))";
        } else if (jTextFieldYimiaoName.getText().trim().equals("") && !jComboBoxShengchanqiye.getSelectedItem().toString().equals("")) {
            conditionSql += "yimiao_id in (select distinct stockpile.yimiao_id from stockpile,yimiao where yimiao.yimiao_id=stockpile.yimiao_id and (yimiao.yimiao_shengchanqiye like \"%" + jComboBoxShengchanqiye.getSelectedItem().toString() + "%\"))";
        } else {
            conditionSql = "";
        }
        return reload();

    }

    private class PicichaxunActionTask extends org.jdesktop.application.Task<Object, Void> {

        PicichaxunActionTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to PicichaxunActionTask fields, here.
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
    @Override
    public Task reload() {
        return new RefureTask(0, pageSize);
    }

    @Override
    public Task reload(Object param) {
        return null;

    }

    private class RefureTask extends YimiaoPicichaxunEntityTask {

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
                for (int i = 0; i < stockpileEntiy.getResult().size(); i++) {
                    YimiaopicichaxunAll yimiaopicichaxun = new YimiaopicichaxunAll();
                    yimiaopicichaxun.setJiliang("" + stockpileEntiy.getResult().get(i).getYimiao().getYimiaoJiliang() + stockpileEntiy.getResult().get(i).getYimiao().getJiliangdanwei());
                    yimiaopicichaxun.setYimiao(stockpileEntiy.getResult().get(i).getYimiao());
                    yimiaopicichaxun.setJinkoutongguanno(stockpileEntiy.getResult().get(i).getJinkoutongguanno());
                    yimiaopicichaxun.setKufang(stockpileEntiy.getResult().get(i).getKufang());
                    yimiaopicichaxun.setPihao(stockpileEntiy.getResult().get(i).getPihao());
                    yimiaopicichaxun.setPiqianfano(stockpileEntiy.getResult().get(i).getPiqianfano());
                    yimiaopicichaxun.setSource(stockpileEntiy.getResult().get(i).getSource());
                    yimiaopicichaxun.setStockpileDate(stockpileEntiy.getResult().get(i).getStockpileDate());
                    yimiaopicichaxun.setStockpileId(stockpileEntiy.getResult().get(i).getStockpileId());
                    yimiaopicichaxun.setStockpilePrice(stockpileEntiy.getResult().get(i).getStockpilePrice());
                    yimiaopicichaxun.setStockpileQuantity(stockpileEntiy.getResult().get(i).getStockpileQuantity());
                    yimiaopicichaxun.setStockpileTotalprice(stockpileEntiy.getResult().get(i).getStockpileTotalprice());
                    yimiaopicichaxun.setYimiaoId(stockpileEntiy.getResult().get(i).getYimiaoId());
                    yimiaopicichaxun.setYimiaoyushoujia(stockpileEntiy.getResult().get(i).getYimiaoyushoujia());
                    yimiaopicichaxun.setYouxiaodate(stockpileEntiy.getResult().get(i).getYouxiaodate());
                    yimiaopicichaxunList.add(yimiaopicichaxun);
                }
                bindTable.refreshData(yimiaopicichaxunList);

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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextFieldYimiaoName = new BaseTextField();
        jComboBoxShengchanqiye = new javax.swing.JComboBox();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableStockpile = new BaseTable(null);

        setName("Form"); // NOI18N

        ctrlPane.setName("ctrlPane"); // NOI18N

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setBorderPainted(false);
        jToolBar2.setName("jToolBar2"); // NOI18N
        jToolBar2.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(Yimiaopicichaxun1Panel.class, this);
        jButton3.setAction(actionMap.get("pagePrev")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(Yimiaopicichaxun1Panel.class);
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

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jButton1.setAction(actionMap.get("picichaxunAction")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jButton2.setAction(actionMap.get("close")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jTextFieldYimiaoName.setText(resourceMap.getString("jTextFieldYimiaoName.text")); // NOI18N
        jTextFieldYimiaoName.setName("jTextFieldYimiaoName"); // NOI18N

        jComboBoxShengchanqiye.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxShengchanqiye.setName("jComboBoxShengchanqiye"); // NOI18N

        jButton5.setAction(actionMap.get("print")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setName("jButton5"); // NOI18N

        javax.swing.GroupLayout ctrlPaneLayout = new javax.swing.GroupLayout(ctrlPane);
        ctrlPane.setLayout(ctrlPaneLayout);
        ctrlPaneLayout.setHorizontalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ctrlPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldYimiaoName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxShengchanqiye, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ctrlPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap())
        );
        ctrlPaneLayout.setVerticalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctrlPaneLayout.createSequentialGroup()
                .addGroup(ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ctrlPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldYimiaoName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxShengchanqiye, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton5))
                .addContainerGap())
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ctrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
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

        YimiaoPicichaxunEntityTask printData = new YimiaoPicichaxunEntityTask(pageIndex, pageSize, conditionSql) {
            @Override
            public void onSucceeded(Object object) {

                if (object instanceof Exception) {
                    Exception e = (Exception) object;
                    AssetMessage.ERRORSYS(e.getMessage());
                    logger.error(e);
                    return;
                }

                StockpiletbFindEntity stockpileEntiy = (StockpiletbFindEntity) object;
                yimiaopicichaxunList = new ArrayList<YimiaopicichaxunAll>();
                for (int i = 0; i < stockpileEntiy.getResult().size(); i++) {
                    YimiaopicichaxunAll yimiaopicichaxun = new YimiaopicichaxunAll();
                    yimiaopicichaxun.setJiliang("" + stockpileEntiy.getResult().get(i).getYimiao().getYimiaoJiliang() + stockpileEntiy.getResult().get(i).getYimiao().getJiliangdanwei());
                    yimiaopicichaxun.setYimiao(stockpileEntiy.getResult().get(i).getYimiao());
                    yimiaopicichaxun.setJinkoutongguanno(stockpileEntiy.getResult().get(i).getJinkoutongguanno());
                    yimiaopicichaxun.setKufang(stockpileEntiy.getResult().get(i).getKufang());
                    yimiaopicichaxun.setPihao(stockpileEntiy.getResult().get(i).getPihao());
                    yimiaopicichaxun.setPiqianfano(stockpileEntiy.getResult().get(i).getPiqianfano());
                    yimiaopicichaxun.setSource(stockpileEntiy.getResult().get(i).getSource());
                    yimiaopicichaxun.setStockpileDate(stockpileEntiy.getResult().get(i).getStockpileDate());
                    yimiaopicichaxun.setStockpileId(stockpileEntiy.getResult().get(i).getStockpileId());
                    yimiaopicichaxun.setStockpilePrice(stockpileEntiy.getResult().get(i).getStockpilePrice());
                    yimiaopicichaxun.setStockpileQuantity(stockpileEntiy.getResult().get(i).getStockpileQuantity());
                    yimiaopicichaxun.setStockpileTotalprice(stockpileEntiy.getResult().get(i).getStockpileTotalprice());
                    yimiaopicichaxun.setYimiaoId(stockpileEntiy.getResult().get(i).getYimiaoId());
                    yimiaopicichaxun.setYimiaoyushoujia(stockpileEntiy.getResult().get(i).getYimiaoyushoujia());
                    yimiaopicichaxun.setYouxiaodate(stockpileEntiy.getResult().get(i).getYouxiaodate());
                    yimiaopicichaxunList.add(yimiaopicichaxun);
                }
                bindTable.createPrinter("疫苗批次查询表", yimiaopicichaxunList).buildInBackgound().execute();
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

    @Action
    public void close() {

        ((AssetClientView) Application.getInstance(AssetClientApp.class).getMainView()).getMainViewPane().getRightPane().remove(this);
        BaseTreePane.disTabCount.remove(this.getName());

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ctrlPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBoxShengchanqiye;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableStockpile;
    private javax.swing.JTextField jTextFieldYimiaoName;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables
}
