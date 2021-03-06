/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UserPanel.java
 *
 * Created on Feb 21, 2010, 10:42:18 PM
 */
package com.jskj.asset.client.panel.user;

import com.jskj.asset.client.layout.ws.CommUpdateTask;
import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.layout.ws.CommFindEntity;
import com.jskj.asset.client.bean.entity.Appparam;
import com.jskj.asset.client.layout.BasePanel;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.ITableHeaderPopupBuilder;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.layout.ws.CommFindTask;
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
 * @author woderchen
 */
public final class ParamPanel extends BasePanel {

    private final static Logger logger = Logger.getLogger(ParamPanel.class);

    private ParamDialog childDialog;
    private int pageIndex;
    public int pageSize;
    private int count;
    private String conditionSql;

    private List<Appparam> currentPageData;

    private final BindTableHelper<Appparam> bindTable;

    /**
     * Creates new form NoFoundPane
     */
    public ParamPanel() {
        super();
        initComponents();
        pageIndex = 1;
        pageSize = 20;
        count = 0;
        conditionSql = "";
        bindTable = new BindTableHelper<Appparam>(jTableParam, new ArrayList<Appparam>());
        bindTable.createTable(new String[][]{{"appparamId", "参数ID"}, {"appparamPid", "参数父ID"}, {"appparamType", "参数类型"}, {"appparamName", "参数名"}, {"systemparam", "系统参数"},
        {"appparamDesc", "描述"}});
        bindTable.setColumnType(Integer.class, 1, 2, 5);
        bindTable.bind().setColumnWidth(new int[]{0, 80}, new int[]{1, 80}, new int[]{2, 100}, new int[]{3, 150}, new int[]{4, 100}).setRowHeight(25);
        bindTable.createHeaderFilter(new ITableHeaderPopupBuilder() {

            @Override
            public int[] getFilterColumnHeader() {
                //那些列需要有查询功能，这样就可以点击列头弹出一个popup
                return new int[]{3};
            }

            @Override
            public Task filterData(HashMap<Integer, String> searchKeys) {

                if (searchKeys.size() > 0) {
                    StringBuilder sql = new StringBuilder();
                    if (!searchKeys.get(3).trim().equals("")) {
                        sql.append("appparam_name like \"%").append(searchKeys.get(3).trim()).append("%\"").append(" and ");
                    }
                    String value = getComboxSql();
                    if(!value.equals("")){
                        sql.append(value).append(" and ");
                    }
                    if (sql.length() > 0) {
                        sql.delete(sql.length() - 5, sql.length() - 1);
                    }
                    
                    conditionSql = sql.toString();
                } else {
                    conditionSql = "";
                }

                return new RefreshTask(0, 20);
            }

        });
    }

    @Action
    @Override
    public Task reload() {
        return new RefreshComboxTask();
    }

    @Action
    public Task refresh() {
        return new RefreshTask(0, 20);
    }

    @Override
    public Task reload(Object param) {
        return null;
    }

    private class RefreshComboxTask extends ParamFindTask {

        RefreshComboxTask() {
            super(0, 10000, "appparam/", conditionSql);
        }

        @Override
        public void responseResult(CommFindEntity<Appparam> response) {

            //存下所有的数据
            List<Appparam> totalData = response.getResult();

            List typeArray = new ArrayList<String>();
            typeArray.add("");
            for (Appparam app : totalData) {
                if (!typeArray.contains(app.getAppparamType())) {
                    typeArray.add(app.getAppparamType());
                }
            }

            String[] temp = new String[typeArray.size()];
            typeArray.toArray(temp);
            jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel(temp));

        }
    }

    private class RefreshTask extends ParamFindTask {

        RefreshTask(int pageIndex, int pageSize) {
            super(pageIndex, pageSize, "appparam/", conditionSql);
        }

        @Override
        public void responseResult(CommFindEntity<Appparam> response) {

            count = response.getCount();
            jLabelTotal.setText(((pageIndex - 1) * pageSize + 1) + "/" + count);
            logger.debug("total:" + count + ",get current size:" + response.getResult().size());

            //存下所有的数据
            currentPageData = response.getResult();
            bindTable.refreshData(currentPageData);

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
        jButtonAdd = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButtonReload = new javax.swing.JButton();
        jButtonPrint = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabelTotal = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableParam = new BaseTable(null);
        jLabel1 = new javax.swing.JLabel();
        jComboBoxType = new javax.swing.JComboBox();
        jButton5 = new javax.swing.JButton();

        setName("Form"); // NOI18N

        ctrlPane.setName("ctrlPane"); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(ParamPanel.class, this);
        jButtonAdd.setAction(actionMap.get("add")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(ParamPanel.class);
        jButtonAdd.setIcon(resourceMap.getIcon("jButtonAdd.icon")); // NOI18N
        jButtonAdd.setText(resourceMap.getString("jButtonAdd.text")); // NOI18N
        jButtonAdd.setBorderPainted(false);
        jButtonAdd.setFocusable(false);
        jButtonAdd.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonAdd.setName("jButtonAdd"); // NOI18N
        jButtonAdd.setOpaque(false);
        jToolBar1.add(jButtonAdd);

        jButton1.setAction(actionMap.get("update")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jToolBar1.add(jButton1);

        jButton2.setAction(actionMap.get("delete")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setOpaque(false);
        jToolBar1.add(jButton2);

        jButtonReload.setAction(actionMap.get("refresh")); // NOI18N
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

        jLabelTotal.setForeground(resourceMap.getColor("jLabelTotal.foreground")); // NOI18N
        jLabelTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTotal.setText(resourceMap.getString("jLabelTotal.text")); // NOI18N
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
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(jLabelTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTableParam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableParam.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTableParam.setName("jTableParam"); // NOI18N
        jTableParam.setShowVerticalLines(false);
        jScrollPane1.setViewportView(jTableParam);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jComboBoxType.setName("jComboBoxType"); // NOI18N

        jButton5.setAction(actionMap.get("filter")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setName("jButton5"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ctrlPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ctrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void add() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (childDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    childDialog = new ParamDialog(ParamPanel.this);
                    childDialog.setLocationRelativeTo(mainFrame);
                }
                childDialog.setUpdatedData(new Appparam());
                AssetClientApp.getApplication().show(childDialog);
            }
        });
    }

    @Action
    public void update() {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Appparam selectedData = selectedDataFromTable();
                if (selectedData == null) {
                    AssetMessage.ERRORSYS("请选择一条数据!");
                    return;
                }
                if (childDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    childDialog = new ParamDialog(ParamPanel.this);
                    childDialog.setLocationRelativeTo(mainFrame);
                }
                childDialog.setUpdatedData(selectedData);
                AssetClientApp.getApplication().show(childDialog);
            }
        });
    }

    @Action
    public Task delete() {
        Appparam selectedData = selectedDataFromTable();
        if (selectedData == null) {
            AssetMessage.ERRORSYS("请选择一条数据!");
            return null;
        }
        if (selectedData.getSystemparam() == 0) {
            AssetMessage.ERRORSYS("系统参数不能删除");
            return null;
        }
        int result = AssetMessage.CONFIRM("确定删除数据:" + selectedData.getAppparamName() + "\r\n注:将在下次系统重启后生效.");
        if (result == JOptionPane.OK_OPTION) {
            return new CommUpdateTask<Appparam>(selectedData, "appparam/delete/" + selectedData.getAppparamId()) {
                @Override
                public void responseResult(ComResponse<Appparam> response) {
                    if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                        reload().execute();
                    } else {
                        AssetMessage.ERROR(response.getErrorMessage(), ParamPanel.this);
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
        new RefreshTask(pageIndex, pageSize).execute();
    }

    @Action
    public void pageNext() {
        if (pageSize * (pageIndex) <= count) {
            pageIndex = pageIndex + 1;
        }
        new RefreshTask(pageIndex, pageSize).execute();
    }

    public List<Appparam> getTableData() {
        return currentPageData;
    }

    public Appparam selectedDataFromTable() {
        if (jTableParam.getSelectedRow() >= 0) {
            if (currentPageData != null) {
                return currentPageData.get(jTableParam.getSelectedRow());
            }
        }
        return null;
    }

    @Action
    public Task print() {
        ParamFindTask printData = new ParamFindTask(0, count, "appparam/", "") {
            @Override
            public void responseResult(CommFindEntity response) {
                bindTable.createPrinter("系统参数配置信息", response.getResult()).buildInBackgound().execute();
            }
        };
        return printData;
    }

    @Action
    public Task filter() {
        conditionSql = getComboxSql();
        return refresh();
    }

    private String getComboxSql() {
        Object selectObj = jComboBoxType.getSelectedItem();
        if (selectObj != null) {
            StringBuilder sql = new StringBuilder();
            if (!selectObj.toString().trim().equals("")) {
                sql.append("appparam_type = \"").append(selectObj).append("\"");
            }
            return sql.toString();
        }

        return "";
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ctrlPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JButton jButtonReload;
    private javax.swing.JComboBox jComboBoxType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableParam;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables
}
