/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ymgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Backsale_detail_tb;
import com.jskj.asset.client.bean.entity.Backsaletb;
import com.jskj.asset.client.bean.entity.XiaoshoutuihuoEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ScanButton;
import com.jskj.asset.client.panel.ymgl.task.XiaoshoutuihuoTask;
import com.jskj.asset.client.util.DanHao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class YiMiaoXiaoShouTuiHuoJDialog extends javax.swing.JDialog {

    private static final Logger logger = Logger.getLogger(YiMiaoXiaoShouTuiHuoJDialog.class);
    private XiaoshoutuihuoEntity yimiaoxiaoshouTuihuo;
    private Backsale_detail_tb backsale_detail;
    private Backsaletb backsale;
    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat riqiformate = new SimpleDateFormat("yyyy-MM-dd");
    private boolean isNew;

    /**
     * Creates new form yimiaoyanshouJDialog
     */
    public YiMiaoXiaoShouTuiHuoJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        init();
        initComponents();
        jTextFieldXiaoshouTuihuoId.setText(DanHao.getDanHao("YMXSTH"));
        jTextFieldXiaoshouTuihuoId.setEditable(false);

        jTextFieldzhidanDate.setText(dateformate.format(new Date()).toString());
        jTextFieldjingbanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFieldzhidanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFielddepartment.setText(AssetClientApp.getSessionMap().getDepartment().getDepartmentName());

        //客户单位的popup
        ((BaseTextField) jTextFieldXiaoshoudanwei).registerPopup(new IPopupBuilder() {

            @Override
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            @Override
            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "kehudanwei";
            }

            @Override
            public String getConditionSQL() {
                String sql = "  kehudanwei_type = 0 ";
                if (!jTextFieldXiaoshoudanwei.getText().trim().equals("")) {
                    sql += " and (kehudanwei_name like \"%" + jTextFieldXiaoshoudanwei.getText() + "%\"" + " or kehudanwei_zujima like \"%" + jTextFieldXiaoshoudanwei.getText().toLowerCase() + "%\")";
                }
                return sql;
            }

            @Override
            public String[][] displayColumns() {
                return new String[][]{{"kehudanweiName", "客户单位名称"}, {"kehudanweiConstactperson", "联系人"}};
            }

            @Override
            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    jTextFieldXiaoshoudanwei.setText(bindedMap.get("kehudanweiName") == null ? "" : bindedMap.get("kehudanweiName").toString());
                    jTextFieldContactPerson.setText(bindedMap.get("kehudanweiConstactperson") == null ? "" : bindedMap.get("kehudanweiConstactperson").toString());
                    backsale.setCustomerId((Integer) bindedMap.get("kehudanweiId"));
                }
            }
        });

        //疫苗表中的内容
        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTableyimiao).createSingleEditModel(new String[][]{
            {"yimiaoId", "库存编号"}, {"yimiaoName", "疫苗名称"}, {"yimiaoGuige", "规格", "false"}, {"yimiaoJixing", "剂型", "false"},
            {"shengchanqiye", "生产企业", "false"}, {"unit", "单位", "false"}, {"youxiaoqi", "有效期至", "false"}, {"tuihuoQuantity", "数量", "true"}, {"yimiaoYushoujia", "单价", "true"}});

        editTable.registerPopup(1, new IPopupBuilder() {
            @Override
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            @Override
            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addkucunyimiao";
            }

            @Override
            public String getConditionSQL() {
                int selectedColumn = jTableyimiao.getSelectedColumn();
                int selectedRow = jTableyimiao.getSelectedRow();
                Object newColumnObj = jTableyimiao.getValueAt(selectedRow, selectedColumn);
                String sql = "";
                if (newColumnObj instanceof String && !newColumnObj.toString().trim().equals("")) {
                    sql += "stockPile_id in (select distinct stockPile.stockpile_id from stockpile,yimiao where stockpile.stockPile_price=0 and yimiao.yimiao_id=stockpile.yimiao_id and (yimiao.yimiao_name like \"%" + newColumnObj.toString() + "%\"" + " or zujima like \"%" + newColumnObj.toString().toLowerCase() + "%\"))";
                } else {
                    sql += "stockPile_id in (select distinct stockPile_id from stockpile where stockPile_price>0)";
                }
                return sql;
            }

            @Override
            public String[][] displayColumns() {
                return new String[][]{{"stockpileId", "库存编号"}, {"yimiao.yimiaoName", "疫苗名称"}, {"pihao", "批号"},
                {"youxiaodate", "有效期"}};
            }

            @Override
            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object yimiaomap = bindedMap.get("yimiao");
                    HashMap yimiao = (HashMap) yimiaomap;

                    Object kucunId = bindedMap.get("stockpileId");
                    Object yimiaoName = yimiao.get("yimiaoName");
                    Object yimiaoGuige = yimiao.get("yimiaoGuige");
                    Object yimiaoJixing = yimiao.get("yimiaoJixing");
                    Object shengchanqiye = yimiao.get("yimiaoShengchanqiye");
                    Object unit = yimiao.get("unitId");
                    Float stockpilePrice = Float.parseFloat("" + bindedMap.get("stockpilePrice"));
                    Object youxiaoqi = bindedMap.get("youxiaodate");
                    Object saleprice = yimiao.get("yimiaoYushoujia");

                    editTable.insertValue(0, kucunId);
                    editTable.insertValue(1, yimiaoName);
                    editTable.insertValue(2, yimiaoGuige);
                    editTable.insertValue(3, yimiaoJixing);
                    editTable.insertValue(4, shengchanqiye);
                    editTable.insertValue(5, unit);
                    editTable.insertValue(6, youxiaoqi);
                    editTable.insertValue(8, stockpilePrice);

                }

            }
        });

        ((ScanButton) jButton2).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_SCAN;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addyimiao";
            }

            public String getConditionSQL() {
                return "yimiao_tiaoxingma =";
            }

            public String[][] displayColumns() {
                return null;
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {

                    Object yimiaoId = bindedMap.get("yimiaoId");
                    Object yimiaoName = bindedMap.get("yimiaoName");
                    Object yimiaoGuige = bindedMap.get("yimiaoGuige");
                    Object yimiaoJixing = bindedMap.get("yimiaoJixing");
                    Object shengchanqiye = bindedMap.get("shengchanqiye");
                    Object unit = bindedMap.get("unit");
                    Object price = bindedMap.get("price");
                    Object youxiaoqi = bindedMap.get("youxiaoqi");
                    Object saleprice = bindedMap.get("saleprice");

                    jTableyimiao.getSelectionModel().setSelectionInterval(jTableyimiao.getRowCount() - 1, jTableyimiao.getRowCount() - 1);

                    editTable.insertValue(jTableyimiao.getSelectedRow(), 0, yimiaoId);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 1, yimiaoName);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 2, yimiaoGuige);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 3, yimiaoJixing);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 4, shengchanqiye);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 5, unit);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 6, youxiaoqi);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 8, price);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 10, saleprice);

                    editTable.addNewRow();
                }

            }
        });
    }

    JTextField regTextField;

    private void init() {
        regTextField = new JTextField();
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
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new ScanButton();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldXiaoshouTuihuoId = new javax.swing.JTextField();
        jTextFieldzhidanDate = regTextField;
        jTextFieldjingbanren = new javax.swing.JTextField();
        jTextFielddepartment = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableyimiao = new BaseTable(null);
        jLabel4 = new javax.swing.JLabel();
        jTextFieldzhidanren = new javax.swing.JTextField();
        jTextFieldXiaoshoudanwei = new BaseTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldContactPerson = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();

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
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoXiaoShouTuiHuoJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoXiaoShouTuiHuoJDialog.class, this);
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

        jTextFieldXiaoshouTuihuoId.setEditable(false);
        jTextFieldXiaoshouTuihuoId.setText(resourceMap.getString("jTextFieldXiaoshouTuihuoId.text")); // NOI18N
        jTextFieldXiaoshouTuihuoId.setName("jTextFieldXiaoshouTuihuoId"); // NOI18N

        jTextFieldzhidanDate.setEditable(false);
        jTextFieldzhidanDate.setText(resourceMap.getString("jTextFieldzhidanDate.text")); // NOI18N
        jTextFieldzhidanDate.setName("jTextFieldzhidanDate"); // NOI18N
        jTextFieldzhidanDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldzhidanDateActionPerformed(evt);
            }
        });

        jTextFieldjingbanren.setEditable(false);
        jTextFieldjingbanren.setText(resourceMap.getString("jTextFieldjingbanren.text")); // NOI18N
        jTextFieldjingbanren.setName("jTextFieldjingbanren"); // NOI18N

        jTextFielddepartment.setEditable(false);
        jTextFielddepartment.setText(resourceMap.getString("jTextFielddepartment.text")); // NOI18N
        jTextFielddepartment.setName("jTextFielddepartment"); // NOI18N

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
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "疫苗编号", "疫苗名称", "规格", "剂型", "生产企业", "单位", "有效期至", "数量", "单价", "合价"
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
            jTableyimiao.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title8")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title9")); // NOI18N
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextFieldzhidanren.setEditable(false);
        jTextFieldzhidanren.setText(resourceMap.getString("jTextFieldzhidanren.text")); // NOI18N
        jTextFieldzhidanren.setName("jTextFieldzhidanren"); // NOI18N

        jTextFieldXiaoshoudanwei.setName("jTextFieldXiaoshoudanwei"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jTextFieldContactPerson.setName("jTextFieldContactPerson"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldXiaoshouTuihuoId, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldXiaoshoudanwei, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFielddepartment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(jTextFieldContactPerson, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextFieldXiaoshouTuihuoId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldXiaoshoudanwei, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextFieldContactPerson, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jTextFielddepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            java.util.logging.Logger.getLogger(YiMiaoXiaoShouTuiHuoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoXiaoShouTuiHuoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoXiaoShouTuiHuoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoXiaoShouTuiHuoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                YiMiaoXiaoShouTuiHuoJDialog dialog = new YiMiaoXiaoShouTuiHuoJDialog(new javax.swing.JFrame(), true);
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

    public void setAddOrUpdate(boolean b) {
        isNew = b;
        if (isNew) {
            this.setTitle("Ⅱ类疫苗销售退货单");
            backsale = new Backsaletb();
        } else {
            this.setTitle("Ⅱ类疫苗销售退货单");
        }
    }

    public void setUpdatedData(Backsale_detail_tb backsale_detail) {
        if (backsale_detail == null) {
            return;
        }
        this.backsale_detail = backsale_detail;
        jTextFieldXiaoshouTuihuoId.setText((backsale_detail.getBacksaleId()).toString());

    }

    @Action
    public Task submitForm() throws ParseException {
        yimiaoxiaoshouTuihuo=new XiaoshoutuihuoEntity();
        jTableyimiao.getCellEditor(jTableyimiao.getSelectedRow(),
                jTableyimiao.getSelectedColumn()).stopCellEditing();
        if (jTextFieldzhidanDate.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入制单日期!");
            return null;
        } else if (jTextFieldXiaoshoudanwei.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入客户单位!");
            return null;
        } else if (jTableyimiao.getValueAt(1, 0) == null || jTableyimiao.getValueAt(1, 1) == null) {
            AssetMessage.ERRORSYS("请输入退货疫苗!");
            return null;
        }
        backsale.setBacksaleId(jTextFieldXiaoshouTuihuoId.getText());
        backsale.setBacksaleDate(dateformate.parse(jTextFieldzhidanDate.getText()));
        backsale.setCheckId(AssetClientApp.getSessionMap().getUsertb().getUserId());
        backsale.setRemark(jTextArea1.getText());

        List<Backsale_detail_tb> list = new ArrayList<Backsale_detail_tb>();
        for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
            BaseTable yimiaotable = ((BaseTable) jTableyimiao);
            backsale_detail=new Backsale_detail_tb();
            backsale_detail.setBacksaleId(jTextFieldXiaoshouTuihuoId.getText());
            backsale_detail.setYouxiaoqi(riqiformate.parse(yimiaotable.getValue(i, "youxiaoqi").toString()));
            backsale_detail.setStockpileId(Integer.parseInt(yimiaotable.getValue(i, "yimiaoId").toString()));
            if (yimiaotable.getValue(i, "tuihuoQuantity").equals("")) {
                AssetMessage.ERRORSYS("请输入疫苗退货数量!");
                return null;
            }
            backsale_detail.setQuantity(Integer.parseInt(yimiaotable.getValue(i, "tuihuoQuantity").toString()));
            backsale_detail.setPrice(Float.parseFloat((String) ("" + yimiaotable.getValue(i, "yimiaoYushoujia"))));
            backsale_detail.setTotalprice(backsale_detail.getQuantity() * backsale_detail.getPrice());
            list.add(backsale_detail);
        }

        yimiaoxiaoshouTuihuo.setBacksaletb(backsale);
        yimiaoxiaoshouTuihuo.setBacksale_details(list);
        return new SubmitFormTask(yimiaoxiaoshouTuihuo);
    }

    @Action
    public void exit() {
        this.dispose();
    }

    private class SubmitFormTask extends XiaoshoutuihuoTask {

        SubmitFormTask(XiaoshoutuihuoEntity tuihuoEntity) {
            super(tuihuoEntity,XiaoshoutuihuoTask.TUIHUOENTITY_SAVE);
        }

        @Override
        public void onSucceeded(Object result) {
            if (result instanceof Exception) {
                Exception e = (Exception) result;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            AssetMessage.INFO("提交成功！", YiMiaoXiaoShouTuiHuoJDialog.this);
            exit();
        }
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
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JTextField jTextFieldContactPerson;
    private javax.swing.JTextField jTextFieldXiaoshouTuihuoId;
    private javax.swing.JTextField jTextFieldXiaoshoudanwei;
    private javax.swing.JTextField jTextFielddepartment;
    private javax.swing.JTextField jTextFieldjingbanren;
    private javax.swing.JTextField jTextFieldzhidanDate;
    private javax.swing.JTextField jTextFieldzhidanren;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
