/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ymgl;

import com.jskj.asset.client.bean.entity.Yimiaoyanshou_detail_tb;
import com.jskj.asset.client.bean.entity.Yimiaoyanshou_detail_tbFindEntity;
import com.jskj.asset.client.bean.entity.Yimiaoyanshoutb;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.panel.ymgl.task.Yimiaoyanshou_detailUpdateTask;
import com.jskj.asset.client.util.DanHao;
import com.jskj.asset.client.util.DateChooser;
import java.text.DateFormat;
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
public class YiMiaoYanShouDanJDialog extends javax.swing.JDialog {
    
    private static final Logger logger = Logger.getLogger(YiMiaoYanShouDanJDialog.class);
    private Yimiaoyanshou_detail_tbFindEntity yimiaoyanshouEntity;
    private Yimiaoyanshou_detail_tb yimiaoyanshou_detail;
    private Yimiaoyanshoutb yimiaoyanshou;
    private boolean isNew;
    private SimpleDateFormat dateformate;

    /**
     * Creates new form yimiaoyanshouJDialog
     */
    public YiMiaoYanShouDanJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        init();
        initComponents();
        jTextFieldYimiaoyanshouId.setText(DanHao.getDanHao("YMYS"));
        jTextFieldYimiaoyanshouId.setEditable(false);

        //        部门popup
        ((BaseTextField) jTextFielddepartment).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }
            
            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "findDepartment";
            }
            
            public String getConditionSQL() {
                String sql = "";
                if (!jTextFielddepartment.getText().trim().equals("")) {
                    sql = "department_name like \"%" + jTextFielddepartment.getText() + "%\"";
                }
                return sql;
            }
            
            public String[][] displayColumns() {
                return new String[][]{{"departmentId", "部门编号"}, {"departmentName", "部门名称"}};
            }
            
            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    jTextFielddepartment.setText(bindedMap.get("departmentName") == null ? "" : bindedMap.get("departmentName").toString());
                    yimiaoyanshou.setDepartmentId((Integer) bindedMap.get("departmentId"));
                }
            }
        });

        //供应单位的popup
        ((BaseTextField) jTextFieldSupplierName).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }
            
            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "supplier";
            }
            
            public String getConditionSQL() {
                String sql = "";
                if (!jTextFieldSupplierName.getText().trim().equals("")) {
                    sql = "supplier_name like \"%" + jTextFieldSupplierName.getText() + "%\"";
                }
                return sql;
            }
            
            public String[][] displayColumns() {
                return new String[][]{{"supplierId", "供应单位编号"}, {"supplierName", "供应单位名称"}, {"supplierConstactperson", "联系人"}};
            }
            
            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    jTextFieldSupplierName.setText(bindedMap.get("supplierName") == null ? "" : bindedMap.get("supplierName").toString());
                    jTextFieldFamiaoperson.setText(bindedMap.get("supplierConstactperson") == null ? "" : bindedMap.get("supplierConstactperson").toString());
                    yimiaoyanshou.setSupplierId((Integer) (bindedMap.get("supplierId")));
                }
            }
        });

        //疫苗表中的内容
        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTableyimiao).createSingleEditModel(new String[][]{
            {"yimiaoId", "疫苗编号"}, {"yimiaoName", "疫苗名称", "true"}, {"yimiaoGuige", "规格", "false"},
            {"yimiaoJixing", "剂型", "false"}, {"yimiaoShengchanqiye", "生产企业", "false"}, {"pihao", "批号", "false"}, {"unitId", "单位", "false"},{"price", "进价", "false"},
            {"quantity", "数量", "true"}, {"fuheyuan", "复核员", "true"}, {"fahuoyuan", "发货员", "true"}});
        
        editTable.registerPopup(1, new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }
            
            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addyimiao";
            }
            
            public String getConditionSQL() {
                int selectedColumn = jTableyimiao.getSelectedColumn();
                int selectedRow = jTableyimiao.getSelectedRow();
                Object newColumnObj = jTableyimiao.getValueAt(selectedRow, selectedColumn);
                String sql = "";
                if (newColumnObj instanceof String && !newColumnObj.toString().trim().equals("")) {
                    sql = "yimiao_name like \"%" + newColumnObj.toString() + "%\"";
                }
                return sql;
            }
            
            public String[][] displayColumns() {
                return new String[][]{{"yimiaoId", "疫苗编号"}, {"yimiaoName", "疫苗名称"}, {"yimiaoGuige", "规格"},
                {"yimiaoJixing", "剂型"}};
            }
            
            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object yimiaoId = bindedMap.get("yimiaoId");
                    Object yimiaoName = bindedMap.get("yimiaoName");
                    Object yimiaoGuige = bindedMap.get("yimiaoGuige");
                    Object yimiaoJixing = bindedMap.get("yimiaoJixing");
                    Object shengchanqiye = bindedMap.get("yimiaoShengchanqiye");
                    Object unit = bindedMap.get("unitId");
                    
                    editTable.insertValue(0, yimiaoId);
                    editTable.insertValue(1, yimiaoName);
                    editTable.insertValue(2, yimiaoGuige);
                    editTable.insertValue(3, yimiaoJixing);
                    editTable.insertValue(4, shengchanqiye);
                    editTable.insertValue(5, unit);
                    
                }
                
            }
        });
    }
    
    DateChooser dateChooser1;
    JTextField regTextField1;
    DateChooser dateChooser2;
    JTextField regTextField2;
    DateChooser dateChooser3;
    JTextField regTextField3;
    
    private void init() {
        regTextField1 = new JTextField();
        dateChooser1 = DateChooser.getInstance("yyyy-MM-dd");
        dateChooser1.register(regTextField1);
        regTextField2 = new JTextField();
        dateChooser2 = DateChooser.getInstance("yyyy-MM-dd");
        dateChooser2.register(regTextField2);
        regTextField3 = new JTextField();
        dateChooser3 = DateChooser.getInstance("yyyy-MM-dd");
        dateChooser3.register(regTextField3);
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
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldYimiaoyanshouId = new javax.swing.JTextField();
        jTextFieldYimiaoyanshou_detailDate = regTextField1;
        jTextFielddepartment = new BaseTextField();
        jTextFieldsongmiaoren = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextFieldFamiaoperson = new javax.swing.JTextField();
        jTextFieldstarttemp1 = new javax.swing.JTextField();
        jTextFieldArrivetemp1 = new javax.swing.JTextField();
        jTextFieldCarcondition = new javax.swing.JTextField();
        jTextFieldXingshiKM = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextFieldEquipment = new javax.swing.JTextField();
        jTextFieldStoragetype = new javax.swing.JTextField();
        jTextFieldstarttemp2 = new javax.swing.JTextField();
        jTextFieldArrivetemp2 = new javax.swing.JTextField();
        jTextFieldBefmiles = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextFieldSupplierName = new BaseTextField();
        jTextFieldStarttime = regTextField2;
        jTextFieldTotaltime = new javax.swing.JTextField();
        jTextFieldArrivetime = regTextField3;
        jTextFieldBackmiles = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextField24 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableyimiao = new BaseTable(null);

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
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoYanShouDanJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoYanShouDanJDialog.class, this);
        jButton1.setAction(actionMap.get("submitForm")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jToolBar1.add(jButton1);

        jButton3.setIcon(resourceMap.getIcon("jButton3.icon")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setOpaque(false);
        jToolBar1.add(jButton3);

        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setOpaque(false);
        jToolBar1.add(jButton4);

        jButton5.setAction(actionMap.get("exit")); // NOI18N
        jButton5.setIcon(resourceMap.getIcon("jButton5.icon")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton5.setName("jButton5"); // NOI18N
        jButton5.setOpaque(false);
        jToolBar1.add(jButton5);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextFieldYimiaoyanshouId.setText(resourceMap.getString("jTextFieldYimiaoyanshouId.text")); // NOI18N
        jTextFieldYimiaoyanshouId.setName("jTextFieldYimiaoyanshouId"); // NOI18N

        jTextFieldYimiaoyanshou_detailDate.setText(resourceMap.getString("jTextFieldYimiaoyanshou_detailDate.text")); // NOI18N
        jTextFieldYimiaoyanshou_detailDate.setName("jTextFieldYimiaoyanshou_detailDate"); // NOI18N
        jTextFieldYimiaoyanshou_detailDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldYimiaoyanshou_detailDateActionPerformed(evt);
            }
        });

        jTextFielddepartment.setText(resourceMap.getString("jTextFielddepartment.text")); // NOI18N
        jTextFielddepartment.setName("jTextFielddepartment"); // NOI18N

        jTextFieldsongmiaoren.setText(resourceMap.getString("jTextFieldsongmiaoren.text")); // NOI18N
        jTextFieldsongmiaoren.setName("jTextFieldsongmiaoren"); // NOI18N

        jTextField5.setText(resourceMap.getString("jTextField5.text")); // NOI18N
        jTextField5.setName("jTextField5"); // NOI18N

        jTextField6.setText(resourceMap.getString("jTextField6.text")); // NOI18N
        jTextField6.setName("jTextField6"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jTextField7.setText(resourceMap.getString("jTextField7.text")); // NOI18N
        jTextField7.setName("jTextField7"); // NOI18N

        jTextFieldFamiaoperson.setText(resourceMap.getString("jTextFieldFamiaoperson.text")); // NOI18N
        jTextFieldFamiaoperson.setName("jTextFieldFamiaoperson"); // NOI18N

        jTextFieldstarttemp1.setText(resourceMap.getString("jTextFieldstarttemp1.text")); // NOI18N
        jTextFieldstarttemp1.setName("jTextFieldstarttemp1"); // NOI18N

        jTextFieldArrivetemp1.setText(resourceMap.getString("jTextFieldArrivetemp1.text")); // NOI18N
        jTextFieldArrivetemp1.setName("jTextFieldArrivetemp1"); // NOI18N

        jTextFieldCarcondition.setText(resourceMap.getString("jTextFieldCarcondition.text")); // NOI18N
        jTextFieldCarcondition.setName("jTextFieldCarcondition"); // NOI18N

        jTextFieldXingshiKM.setText(resourceMap.getString("jTextFieldXingshiKM.text")); // NOI18N
        jTextFieldXingshiKM.setName("jTextFieldXingshiKM"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N

        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N

        jLabel22.setText(resourceMap.getString("jLabel22.text")); // NOI18N
        jLabel22.setName("jLabel22"); // NOI18N

        jLabel23.setText(resourceMap.getString("jLabel23.text")); // NOI18N
        jLabel23.setName("jLabel23"); // NOI18N

        jTextFieldEquipment.setText(resourceMap.getString("jTextFieldEquipment.text")); // NOI18N
        jTextFieldEquipment.setName("jTextFieldEquipment"); // NOI18N

        jTextFieldStoragetype.setText(resourceMap.getString("jTextFieldStoragetype.text")); // NOI18N
        jTextFieldStoragetype.setName("jTextFieldStoragetype"); // NOI18N

        jTextFieldstarttemp2.setText(resourceMap.getString("jTextFieldstarttemp2.text")); // NOI18N
        jTextFieldstarttemp2.setName("jTextFieldstarttemp2"); // NOI18N

        jTextFieldArrivetemp2.setText(resourceMap.getString("jTextFieldArrivetemp2.text")); // NOI18N
        jTextFieldArrivetemp2.setName("jTextFieldArrivetemp2"); // NOI18N

        jTextFieldBefmiles.setText(resourceMap.getString("jTextFieldBefmiles.text")); // NOI18N
        jTextFieldBefmiles.setName("jTextFieldBefmiles"); // NOI18N

        jTextField18.setText(resourceMap.getString("jTextField18.text")); // NOI18N
        jTextField18.setName("jTextField18"); // NOI18N

        jTextFieldSupplierName.setText(resourceMap.getString("jTextFieldSupplierName.text")); // NOI18N
        jTextFieldSupplierName.setName("jTextFieldSupplierName"); // NOI18N

        jTextFieldStarttime.setText(resourceMap.getString("jTextFieldStarttime.text")); // NOI18N
        jTextFieldStarttime.setName("jTextFieldStarttime"); // NOI18N

        jTextFieldTotaltime.setText(resourceMap.getString("jTextFieldTotaltime.text")); // NOI18N
        jTextFieldTotaltime.setName("jTextFieldTotaltime"); // NOI18N

        jTextFieldArrivetime.setText(resourceMap.getString("jTextFieldArrivetime.text")); // NOI18N
        jTextFieldArrivetime.setName("jTextFieldArrivetime"); // NOI18N

        jTextFieldBackmiles.setText(resourceMap.getString("jTextFieldBackmiles.text")); // NOI18N
        jTextFieldBackmiles.setName("jTextFieldBackmiles"); // NOI18N

        jLabel24.setText(resourceMap.getString("jLabel24.text")); // NOI18N
        jLabel24.setName("jLabel24"); // NOI18N

        jTextField24.setText(resourceMap.getString("jTextField24.text")); // NOI18N
        jTextField24.setName("jTextField24"); // NOI18N

        jLabel25.setText(resourceMap.getString("jLabel25.text")); // NOI18N
        jLabel25.setName("jLabel25"); // NOI18N

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        jTextArea2.setColumns(20);
        jTextArea2.setRows(2);
        jTextArea2.setName("jTextArea2"); // NOI18N
        jScrollPane6.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 844, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldYimiaoyanshouId, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldsongmiaoren, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel12))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextFieldCarcondition, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                            .addComponent(jTextFieldXingshiKM, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                            .addComponent(jTextFieldArrivetemp1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldstarttemp1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldFamiaoperson, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(80, 80, 80)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldYimiaoyanshou_detailDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldStoragetype, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldstarttemp2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldArrivetemp2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldBefmiles, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(80, 80, 80)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel19))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldSupplierName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldStarttime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldTotaltime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldArrivetime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldBackmiles, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTextField24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFielddepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldYimiaoyanshouId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldYimiaoyanshou_detailDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldsongmiaoren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextFieldFamiaoperson, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTextFieldstarttemp1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextFieldArrivetemp1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCarcondition, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jTextFieldXingshiKM, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFielddepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(jLabel13)
                            .addComponent(jTextFieldEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldStarttime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(jTextFieldStoragetype, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldTotaltime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)
                            .addComponent(jLabel15)
                            .addComponent(jTextFieldstarttemp2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldArrivetime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel23))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel16)
                                .addComponent(jTextFieldArrivetemp2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldBackmiles, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(jLabel17)
                            .addComponent(jTextFieldBefmiles, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel18)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addContainerGap())
        );

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
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "源单编号", "疫苗名称", "规格", "批号", "生产企业", "有效期至", "批签发合格证编号", "单位数量", "复核员", "发货员"
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldYimiaoyanshou_detailDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldYimiaoyanshou_detailDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldYimiaoyanshou_detailDateActionPerformed

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
            java.util.logging.Logger.getLogger(YiMiaoYanShouDanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoYanShouDanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoYanShouDanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoYanShouDanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                YiMiaoYanShouDanJDialog dialog = new YiMiaoYanShouDanJDialog(new javax.swing.JFrame(), true);
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
            this.setTitle("疫苗验收单");
            yimiaoyanshou = new Yimiaoyanshoutb();
            yimiaoyanshou_detail = new Yimiaoyanshou_detail_tb();
        } else {
            this.setTitle("疫苗验收单");
        }
    }
    
    public void setUpdatedData(Yimiaoyanshou_detail_tb yimiaoyanshou_detail) {
        if (yimiaoyanshou_detail == null) {
            return;
        }
        this.yimiaoyanshou_detail = yimiaoyanshou_detail;
        jTextFieldYimiaoyanshouId.setText((yimiaoyanshou_detail.getYmysId()).toString());
        jTextFieldYimiaoyanshou_detailDate.setText(yimiaoyanshou.getYmysDate().toString());
    }
    
    @Action
    public Task submitForm() throws ParseException {
        if (jTextFieldsongmiaoren.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入送苗人!");
        }else if (jTextFieldYimiaoyanshou_detailDate.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入制单日期!");
        }else if (jTextFielddepartment.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入验收部门!");
        }
        
        yimiaoyanshouEntity=new Yimiaoyanshou_detail_tbFindEntity();
        dateformate = new SimpleDateFormat("yyyy-MM-dd");
        
        yimiaoyanshou.setYmysId(jTextFieldYimiaoyanshouId.getText());    
        yimiaoyanshou.setYmysDate(dateformate.parse(jTextFieldYimiaoyanshou_detailDate.getText()));
        yimiaoyanshou.setYmysSendperson(jTextFieldsongmiaoren.getText());
        yimiaoyanshou.setYmysEquipment(jTextFieldEquipment.getText());
        yimiaoyanshou.setYmysStoragetype(jTextFieldStoragetype.getText());
        yimiaoyanshou.setYmysStarttime(dateformate.parse(jTextFieldStarttime.getText()));        
        yimiaoyanshou.setYmysStrattemp1(Float.valueOf(jTextFieldstarttemp1.getText()));
        yimiaoyanshou.setYmysStarttemp2(Float.valueOf(jTextFieldstarttemp2.getText()));
        yimiaoyanshou.setYmysTotaltime(Float.valueOf(jTextFieldTotaltime.getText()));
        yimiaoyanshou.setYmysArrivetemp1(Float.valueOf(jTextFieldArrivetemp1.getText()));
        yimiaoyanshou.setYmysArrivetemp2(Float.valueOf(jTextFieldArrivetemp2.getText()));
        yimiaoyanshou.setYmysArrivetime(dateformate.parse(jTextFieldArrivetime.getText()));
        float BefMiles = Float.valueOf(jTextFieldBefmiles.getText());
        yimiaoyanshou.setYmysBefmiles(BefMiles);
        float BackMiles = Float.valueOf(jTextFieldBackmiles.getText());
        yimiaoyanshou.setYmysBackmiles(BackMiles);
        yimiaoyanshou.setYmysCarcondition(jTextFieldCarcondition.getText());
        yimiaoyanshou.setYmysKm(Float.valueOf(jTextFieldXingshiKM.getText()));
        yimiaoyanshou.setYmysArriveaddr("广安疾控中心");        
        
        List<Yimiaoyanshou_detail_tb> list = new ArrayList<Yimiaoyanshou_detail_tb>();
        for (int i = 0; i < 3; i++) {
            yimiaoyanshou_detail.setYimiaoId(i+100);
            yimiaoyanshou_detail.setYmysId(jTextFieldYimiaoyanshouId.getText());
            yimiaoyanshou_detail.setYouxiaodate(new Date());
            yimiaoyanshou_detail.setPiqianfahegeno(i+200);
            yimiaoyanshou_detail.setPihao("sff12324354677");
            yimiaoyanshou_detail.setFahuoyuan("李四");
            yimiaoyanshou_detail.setFuheyuan("张三");
            yimiaoyanshou_detail.setQuantity((i+1) * 10);
            list.add(yimiaoyanshou_detail);
        }
        yimiaoyanshouEntity.setYimiaoyanshou(yimiaoyanshou);
        yimiaoyanshouEntity.setResult(list);
        return new SubmitFormTask(yimiaoyanshouEntity);
    }
    
    @Action
    public void exit() {
        this.dispose();
    }
    
    private class SubmitFormTask extends Yimiaoyanshou_detailUpdateTask {
        
        SubmitFormTask(Yimiaoyanshou_detail_tbFindEntity yimiaoyanshouEntity) {
            super(yimiaoyanshouEntity, isNew ? Yimiaoyanshou_detailUpdateTask.ENTITY_SAVE : Yimiaoyanshou_detailUpdateTask.ENTITY_UPDATE);
        }
        
        @Override
        public void onSucceeded(Object result) {
            if (result instanceof Exception) {
                Exception e = (Exception) result;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            exit();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTableyimiao;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextFieldArrivetemp1;
    private javax.swing.JTextField jTextFieldArrivetemp2;
    private javax.swing.JTextField jTextFieldArrivetime;
    private javax.swing.JTextField jTextFieldBackmiles;
    private javax.swing.JTextField jTextFieldBefmiles;
    private javax.swing.JTextField jTextFieldCarcondition;
    private javax.swing.JTextField jTextFieldEquipment;
    private javax.swing.JTextField jTextFieldFamiaoperson;
    private javax.swing.JTextField jTextFieldStarttime;
    private javax.swing.JTextField jTextFieldStoragetype;
    private javax.swing.JTextField jTextFieldSupplierName;
    private javax.swing.JTextField jTextFieldTotaltime;
    private javax.swing.JTextField jTextFieldXingshiKM;
    private javax.swing.JTextField jTextFieldYimiaoyanshouId;
    private javax.swing.JTextField jTextFieldYimiaoyanshou_detailDate;
    private javax.swing.JTextField jTextFielddepartment;
    private javax.swing.JTextField jTextFieldsongmiaoren;
    private javax.swing.JTextField jTextFieldstarttemp1;
    private javax.swing.JTextField jTextFieldstarttemp2;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
