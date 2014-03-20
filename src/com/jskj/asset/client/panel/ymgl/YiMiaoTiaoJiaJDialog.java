/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ymgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Stockpiletb;
import com.jskj.asset.client.bean.entity.YimiaoAll;
import com.jskj.asset.client.bean.entity.YimiaotiaojiaDetailEntity;
import com.jskj.asset.client.bean.entity.Yimiaotiaojia_detail_tb;
import com.jskj.asset.client.bean.entity.Yimiaotiaojia_detail_tbFindEntity;
import com.jskj.asset.client.bean.entity.Yimiaotiaojiatb;
import com.jskj.asset.client.bean.entity.YimiaotiaojiaxiangdanEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ScanButton;
import com.jskj.asset.client.panel.ymgl.task.Yimiaotiaojia_detailUpdateTask;
import com.jskj.asset.client.util.DanHao;
import com.jskj.asset.client.util.DateHelper;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author huiqi
 */
public class YiMiaoTiaoJiaJDialog extends BaseDialog {

    private Yimiaotiaojia_detail_tbFindEntity yimiaotiaojiaEntity;
    private Yimiaotiaojia_detail_tb yimiaotiaojia_detail;
    private Yimiaotiaojiatb yimiaotiaojia;
    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH：mm:ss");
    private boolean isNew;
    private YimiaotiaojiaxiangdanEntity yimiaotiaojiaxiangdanEntity;

    /**
     * Creates new form yimiaoyanshouJDialog
     */
    public YiMiaoTiaoJiaJDialog() {
        super();
        initComponents();

        jTextFieldTiaojiaId.setText(DanHao.getDanHao("YMTJ"));
        jTextFieldTiaojiaId.setEditable(false);

        jTextFieldzhidanDate.setText(dateformate.format(new Date()).toString());
        jTextFieldjingbanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFieldzhidanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFielddepartment.setText(AssetClientApp.getSessionMap().getDepartment().getDepartmentName());

        //疫苗表中的内容
        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTableyimiao).createSingleEditModel(new String[][]{
            {"stockpileId", "库存编号"}, {"yimiaoName", "疫苗名称", "true"}, {"yimiaoGuige", "规格", "false"},
            {"yimiaoJixing", "剂型", "false"}, {"yimiaoShengchanqiye", "生产企业", "false"}, {"unitId", "单位", "false"}, {"beforebuyPrice", "调前进价", "false"}, {"lastbuyPrice", "调后进价", "true"}, {"beforesalePrice", "调前预售价", "false"}, {"lastsalePrice", "调后预售价", "true"}});

        editTable.registerPopup(1, new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addkucunyimiao";
            }

            public String getConditionSQL() {
                int selectedColumn = jTableyimiao.getSelectedColumn();
                int selectedRow = jTableyimiao.getSelectedRow();
                Object newColumnObj = jTableyimiao.getValueAt(selectedRow, selectedColumn);
                String sql = "";
                if (newColumnObj instanceof String && !newColumnObj.toString().trim().equals("")) {
                    sql += "stockpile_id in (select distinct stockpile_id from stockpile,yimiao where stockpile.stockPile_price>0 and stockpile.yimiao_id =yimiao.yimiao_id and (yimiao.yimiao_name like \"%" + newColumnObj.toString() + "%\" or yimiao.zujima like \"" + newColumnObj.toString() + "%\")) ";
                } else {
                    sql += "stockpile_id in (select distinct stockpile_id from stockpile where stockPile_price>0)";

                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"stockpileId", "库存编号"}, {"yimiao.yimiaoName", "疫苗名称"}, {"pihao", "批号"},
                {"youxiaodate", "有效期"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object yimiaomap = bindedMap.get("yimiao");
                    HashMap yimiao = (HashMap) yimiaomap;

                    Object stockpileId = bindedMap.get("stockpileId");
                    Float stockpilePrice = Float.parseFloat("" + bindedMap.get("stockpilePrice"));
                    Object yimiaoName = yimiao.get("yimiaoName");
                    Object yimiaoGuige = yimiao.get("yimiaoGuige");
                    Object yimiaoJixing = yimiao.get("yimiaoJixing");
                    Object shengchanqiye = yimiao.get("yimiaoShengchanqiye");
                    Object unit = yimiao.get("unitId");
                    Object yushoujia = yimiao.get("yimiaoYushoujia");

                    editTable.insertValue(0, stockpileId);
                    editTable.insertValue(1, yimiaoName);
                    editTable.insertValue(2, yimiaoGuige);
                    editTable.insertValue(3, yimiaoJixing);
                    editTable.insertValue(4, shengchanqiye);
                    editTable.insertValue(5, unit);
                    editTable.insertValue(6, stockpilePrice);
                    editTable.insertValue(8, yushoujia);

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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new ScanButton();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldTiaojiaId = new javax.swing.JTextField();
        jTextFieldzhidanDate = new javax.swing.JTextField();
        jTextFieldKemu = new javax.swing.JTextField();
        jTextFieldJine = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableyimiao = new BaseTable(null);
        jTextFieldzhidanren = new javax.swing.JTextField();
        jTextFieldjingbanren = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextFielddepartment = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

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

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoTiaoJiaJDialog.class);
        jToggleButton1.setText(resourceMap.getString("jToggleButton1.text")); // NOI18N
        jToggleButton1.setName("jToggleButton1"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoTiaoJiaJDialog.class, this);
        jButton1.setAction(actionMap.get("submitForm")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jToolBar1.add(jButton1);

        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setFocusable(false);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setOpaque(false);
        jToolBar1.add(jButton4);

        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setOpaque(false);
        jToolBar1.add(jButton2);

        jButton5.setAction(actionMap.get("exit")); // NOI18N
        jButton5.setIcon(resourceMap.getIcon("jButton5.icon")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setFocusable(false);
        jButton5.setName("jButton5"); // NOI18N
        jButton5.setOpaque(false);
        jToolBar1.add(jButton5);

        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jTextFieldTiaojiaId.setEditable(false);
        jTextFieldTiaojiaId.setText(resourceMap.getString("jTextFieldTiaojiaId.text")); // NOI18N
        jTextFieldTiaojiaId.setName("jTextFieldTiaojiaId"); // NOI18N

        jTextFieldzhidanDate.setEditable(false);
        jTextFieldzhidanDate.setText(resourceMap.getString("jTextFieldzhidanDate.text")); // NOI18N
        jTextFieldzhidanDate.setName("jTextFieldzhidanDate"); // NOI18N
        jTextFieldzhidanDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldzhidanDateActionPerformed(evt);
            }
        });

        jTextFieldKemu.setText(resourceMap.getString("jTextFieldKemu.text")); // NOI18N
        jTextFieldKemu.setName("jTextFieldKemu"); // NOI18N

        jTextFieldJine.setText(resourceMap.getString("jTextFieldJine.text")); // NOI18N
        jTextFieldJine.setName("jTextFieldJine"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel25.setText(resourceMap.getString("jLabel25.text")); // NOI18N
        jLabel25.setName("jLabel25"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(2);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTableyimiao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "疫苗编号", "疫苗名称", "规格", "剂型", "生产企业", "单位", "调前单价", "调后单价"
            }
        ));
        jTableyimiao.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTableyimiao.setName("jTableyimiao"); // NOI18N
        jScrollPane5.setViewportView(jTableyimiao);
        if (jTableyimiao.getColumnModel().getColumnCount() > 0) {
            jTableyimiao.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title0")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title1")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title2")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title3")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title4")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title5")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title6")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title7")); // NOI18N
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
        );

        jTextFieldzhidanren.setEditable(false);
        jTextFieldzhidanren.setText(resourceMap.getString("jTextFieldzhidanren.text")); // NOI18N
        jTextFieldzhidanren.setName("jTextFieldzhidanren"); // NOI18N

        jTextFieldjingbanren.setEditable(false);
        jTextFieldjingbanren.setName("jTextFieldjingbanren"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jTextFielddepartment.setEditable(false);
        jTextFielddepartment.setName("jTextFielddepartment"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldKemu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldTiaojiaId, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFielddepartment, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addComponent(jTextFieldzhidanDate)
                                    .addComponent(jTextFieldJine)))
                            .addComponent(jScrollPane1))
                        .addContainerGap())))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldTiaojiaId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldKemu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldJine, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFielddepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel3)))
                .addGap(341, 341, 341))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldzhidanDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldzhidanDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldzhidanDateActionPerformed

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
            java.util.logging.Logger.getLogger(YiMiaoTiaoJiaJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoTiaoJiaJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoTiaoJiaJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoTiaoJiaJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                YiMiaoTiaoJiaJDialog dialog = new YiMiaoTiaoJiaJDialog(new javax.swing.JFrame(), true);
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

    public void setAddOrUpdate(boolean b) {
        isNew = b;
        if (isNew) {
            this.setTitle("Ⅱ类疫苗调价单");
            yimiaotiaojia = new Yimiaotiaojiatb();
            yimiaotiaojia_detail = new Yimiaotiaojia_detail_tb();
        } else {
            this.setTitle("Ⅱ类疫苗调价单");
        }
    }

    public void setUpdatedData(Yimiaotiaojia_detail_tb yimiaotiaojia_detail) {
        if (yimiaotiaojia_detail == null) {
            return;
        }
        this.yimiaotiaojia_detail = yimiaotiaojia_detail;
        jTextFieldTiaojiaId.setText((yimiaotiaojia_detail.getTiaojiaId()).toString());

    }

    @Action
    public Task submitForm() throws ParseException {
        if (jTextFieldzhidanDate.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入制单日期!");
            return null;
        } else if (jTextFieldjingbanren.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入经办人!");
            return null;
        }
        yimiaotiaojiaEntity = new Yimiaotiaojia_detail_tbFindEntity();
        yimiaotiaojia.setTiaojiaId(jTextFieldTiaojiaId.getText());
        yimiaotiaojia.setZhidandate(dateformate.parse(jTextFieldzhidanDate.getText()));
        yimiaotiaojia.setTiaojiakemu(jTextFieldKemu.getText());
        if (jTextFieldJine.getText().trim().equals("")) {
            yimiaotiaojia.setTiaojiajine((float) 0);
        } else {
            yimiaotiaojia.setTiaojiajine(Float.parseFloat("" + jTextFieldJine.getText()));
        }
        yimiaotiaojia.setJingbanrenId(AssetClientApp.getSessionMap().getUsertb().getUserId());
        yimiaotiaojia.setZhidanrenId(AssetClientApp.getSessionMap().getUsertb().getUserId());
        yimiaotiaojia.setIsCompleted(0);
        List<Yimiaotiaojia_detail_tb> list = new ArrayList<Yimiaotiaojia_detail_tb>();
        for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
            BaseTable yimiaotable = ((BaseTable) jTableyimiao);
            yimiaotiaojia_detail.setTiaojiaId(jTextFieldTiaojiaId.getText());
            yimiaotiaojia_detail.setKucunyimiaoId((Integer) yimiaotable.getValue(i, "stockpileId"));
            yimiaotiaojia_detail.setBeforebuyprice(Float.parseFloat((String) ("" + yimiaotable.getValue(i, "beforebuyPrice"))));
            yimiaotiaojia_detail.setLastbuyprice(Float.parseFloat((String) ("" + yimiaotable.getValue(i, "lastbuyPrice"))));
            yimiaotiaojia_detail.setBeforesaleprice(Float.parseFloat((String) ("" + yimiaotable.getValue(i, "beforesalePrice"))));
            yimiaotiaojia_detail.setLastsaleprice(Float.parseFloat((String) ("" + yimiaotable.getValue(i, "lastsalePrice"))));
            yimiaotiaojia_detail.setIsCompleted(0);
            list.add(yimiaotiaojia_detail);
        }

        yimiaotiaojiaEntity.setYimiaotiaojia(yimiaotiaojia);
        yimiaotiaojiaEntity.setResult(list);
        return new SubmitFormTask(yimiaotiaojiaEntity);
    }

    @Action
    public void exit() {
        this.dispose();
    }

    private class SubmitFormTask extends Yimiaotiaojia_detailUpdateTask {

        SubmitFormTask(Yimiaotiaojia_detail_tbFindEntity yimiaotiaojiaEntity) {
            super(yimiaotiaojiaEntity, isNew ? Yimiaotiaojia_detailUpdateTask.ENTITY_SAVE : Yimiaotiaojia_detailUpdateTask.ENTITY_UPDATE);
        }

        @Override
        public void onSucceeded(Object result) {
            if (result instanceof Exception) {
                Exception e = (Exception) result;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            AssetMessage.INFO("提交成功！", YiMiaoTiaoJiaJDialog.this);
            exit();
            JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
            YiMiaoTiaoJiaJDialog yiMiaoTiaoJiaJDialog = new YiMiaoTiaoJiaJDialog();
            yiMiaoTiaoJiaJDialog.setLocationRelativeTo(mainFrame);
            yiMiaoTiaoJiaJDialog.setAddOrUpdate(true);
            AssetClientApp.getApplication().show(yiMiaoTiaoJiaJDialog);
        }
    }

    public YiMiaoTiaoJiaJDialog(final JDialog parent, YimiaotiaojiaxiangdanEntity yimiaotiaojiaxiangdanEntity) {
        super();
        initComponents();
        this.yimiaotiaojiaxiangdanEntity = yimiaotiaojiaxiangdanEntity;
        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                parent.setVisible(true);
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }

        });

        jButton1.setEnabled(false);

        jTextFieldTiaojiaId.setEditable(false);
        jTextFieldTiaojiaId.setText(yimiaotiaojiaxiangdanEntity.getYimiaotiaojiatb().getTiaojiaId());
        jTextFieldzhidanDate.setText(DateHelper.format(yimiaotiaojiaxiangdanEntity.getYimiaotiaojiatb().getZhidandate(), "yyyy-MM-dd HH:mm:ss"));
        jTextFieldzhidanDate.setEditable(false);
        jTextFieldjingbanren.setEditable(false);
        jTextFieldjingbanren.setText(yimiaotiaojiaxiangdanEntity.getUserAll().getUserName());
        jTextFielddepartment.setEditable(false);
        jTextFielddepartment.setText(yimiaotiaojiaxiangdanEntity.getUserAll().getDepartment().getDepartmentName());
        jTextFieldzhidanren.setEditable(false);
        jTextFieldzhidanren.setText(yimiaotiaojiaxiangdanEntity.getUserAll().getUserName());
        jTextArea1.setEditable(false);
        jTextArea1.setText("" + yimiaotiaojiaxiangdanEntity.getYimiaotiaojiatb().getTioajiaremark());

        setListTable(yimiaotiaojiaxiangdanEntity.getResult());
    }

    public void setListTable(List<YimiaotiaojiaDetailEntity> yimiaotiaojiaDetailEntityList) {

        int size = yimiaotiaojiaDetailEntityList.size();
        Object[][] o = new Object[size][9];
        for (int i = 0; i < size; i++) {
            Yimiaotiaojia_detail_tb yimiaotiaojia_detailtb = yimiaotiaojiaDetailEntityList.get(i).getYimiaotiaojia_detail_tb();
            YimiaoAll yimiaoAll = yimiaotiaojiaDetailEntityList.get(i).getYimiaoAll();
            Stockpiletb stockpile = yimiaotiaojiaDetailEntityList.get(i).getStockpileYimiao();
            o[i] = new Object[]{stockpile.getStockpileId(), yimiaoAll.getYimiaoName(), yimiaoAll.getYimiaoGuige(), yimiaoAll.getYimiaoJixing(), yimiaoAll.getYimiaoShengchanqiye(), yimiaoAll.getUnitId(),
                stockpile.getYouxiaodate(), yimiaotiaojia_detailtb.getBeforesaleprice(), yimiaotiaojia_detailtb.getLastsaleprice()};
        }

        jTableyimiao.setModel(new javax.swing.table.DefaultTableModel(
                o,
                new String[]{
                    "库存编号", "疫苗名称", "规格", "剂型", "批号", "单位", "有效期", "调前售价", "调后售价"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false
            };
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTableyimiao;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFieldJine;
    private javax.swing.JTextField jTextFieldKemu;
    private javax.swing.JTextField jTextFieldTiaojiaId;
    private javax.swing.JTextField jTextFielddepartment;
    private javax.swing.JTextField jTextFieldjingbanren;
    private javax.swing.JTextField jTextFieldzhidanDate;
    private javax.swing.JTextField jTextFieldzhidanren;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
