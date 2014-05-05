/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ymgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Shenqingdantb;
import com.jskj.asset.client.bean.entity.YimiaoAll;
import com.jskj.asset.client.bean.entity.YimiaocaigouEntity;
import com.jskj.asset.client.bean.entity.YimiaocaigouxiangdanEntity;
import com.jskj.asset.client.bean.entity.Yimiaoshenqingdantb;
import com.jskj.asset.client.bean.entity.YimiaoshenqingdantbFindEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseCellFocusListener;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.panel.ymgl.task.YimiaoshenqingdanUpdateTask;
import com.jskj.asset.client.util.DanHao;
import com.jskj.asset.client.util.DateChooser;
import com.jskj.asset.client.util.DateHelper;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author huiqi
 */
public class YiMiaoSheGouShenQingJDialog extends BaseDialog {

    private static final Logger logger = Logger.getLogger(YiMiaoSheGouShenQingJDialog.class);
    private Yimiaoshenqingdantb yimiaoshenqingdan;
    private YimiaoshenqingdantbFindEntity yimiaocaigou;
    private List<Yimiaoshenqingdantb> yimiaoshenqingdanlist = new ArrayList<Yimiaoshenqingdantb>();
    private Shenqingdantb shenqingdan;
    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private YimiaocaigouxiangdanEntity yimiaocaigouxiangdanEntity;
    private boolean isNew;
    private float total = 0;
    private Map shenqingdanmap;

    /**
     * Creates new form yimiaoyanshouJDialog
     */
    public YiMiaoSheGouShenQingJDialog() {
        super();
        initComponents();
        jTextFieldYimiaocaigoudanId.setText(DanHao.getDanHao(DanHao.TYPE_YIMIAOCG));
        jTextFieldYimiaocaigoudanId.setEditable(false);
        shenqingdanmap = new HashMap();

        jTextFieldzhidanDate.setText(dateformate.format(new Date()).toString());
        jTextFieldjingbanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFieldzhidanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFielddepartment.setText(AssetClientApp.getSessionMap().getDepartment().getDepartmentName());

//供应单位的popup
        ((BaseTextField) jTextFieldSupplierName).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "supplier";
            }

            public String getConditionSQL() {
                String sql = "  supplier_type = 0 ";
                if (!jTextFieldSupplierName.getText().trim().equals("")) {
                    sql += " and  (supplier_name like \"%" + jTextFieldSupplierName.getText() + "%\"" + " or supplier_zujima like \"%" + jTextFieldSupplierName.getText().trim().toLowerCase() + "%\")";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"supplierId", "供应单位编号"}, {"supplierName", "供应单位名称"}, {"supplierConstactperson", "联系人"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    shenqingdan.setSupplierId((Integer) (bindedMap.get("supplierId")));
                    jTextFieldSupplierName.setText(bindedMap.get("supplierName") == null ? "" : bindedMap.get("supplierName").toString());
                    jTextFieldConstactperson.setText(bindedMap.get("supplierConstactperson") == null ? "" : bindedMap.get("supplierConstactperson").toString());
                }
            }
        });

        //疫苗表中的内容
        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTableyimiao).createSingleEditModel(new String[][]{
            {"xiangdanId", "详单编号"}, {"yimiaoId", "疫苗编号"}, {"yimiaoName", "疫苗名称", "true"}, {"yimiaoGuige", "规格", "false"}, {"yimiaoJixing", "剂型", "false"},
            {"yimiaoShengchanqiye", "生产企业", "false"}, {"unitId", "单位", "false"}, {"quantity", "数量", "true"}, {"buyprice", "进价", "true"}, {"totalprice", "合价", "true"}});

        editTable.registerPopup(2, new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addshegouyimiao";
            }

            public String getConditionSQL() {
                int selectedColumn = jTableyimiao.getSelectedColumn();
                int selectedRow = jTableyimiao.getSelectedRow();
                Object newColumnObj = jTableyimiao.getValueAt(selectedRow, selectedColumn);
                String sql = "";
                if (newColumnObj instanceof String && !newColumnObj.toString().trim().equals("")) {
                    sql += "xiangdan_id in (select distinct yimiaoshenqingdan.xiangdan_id from yimiaoshenqingdan,yimiao where yimiaoshenqingdan.yimiao_id=yimiao.yimiao_id and yimiaoshenqingdan.danjuleixing_id=4 and yimiaoshenqingdan.is_completed = 1 and yimiaoshenqingdan.status = 7 and (yimiao.yimiao_name like \"%" + newColumnObj.toString() + "%\" or yimiao.zujima like \"%" + newColumnObj.toString() + "%\")) ";
                } else {
                    sql += "xiangdan_id in (select distinct xiangdan_id from yimiaoshenqingdan where danjuleixing_id=4 and is_completed = 1 and status = 7)";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"shenqingdan.shenqingdanId", "源单单号"}, {"shenqingdan.shenqingdanDate", "申报日期"}, {"yimiaoAll.yimiaoName", "疫苗名称"},
                {"yimiaoAll.yimiaoJixing", "剂型"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object yimiaomap = bindedMap.get("yimiaoAll");
                    HashMap yimiaoAll = (HashMap) yimiaomap;
                    Object yimiaoshenqingdanmap = bindedMap.get("yimiaoshenqingtb");
                    HashMap yimiaoshenqingdan = (HashMap) yimiaoshenqingdanmap;

                    Object xiangdanId = yimiaoshenqingdan.get("xiangdanId");
                    Object yimiaoId = yimiaoAll.get("yimiaoId");
                    Object yimiaoName = yimiaoAll.get("yimiaoName");
                    Object yimiaoGuige = yimiaoAll.get("yimiaoGuige");
                    Object yimiaoJixing = yimiaoAll.get("yimiaoJixing");
                    Object shengchanqiye = yimiaoAll.get("yimiaoShengchanqiye");
                    Object unit = yimiaoAll.get("unitId");
                    Object quantity = yimiaoshenqingdan.get("quantity");
                    Object buyprice = yimiaoshenqingdan.get("buyprice");
                    Object totalprice = yimiaoshenqingdan.get("totalprice");
//                    Object saleprice = yimiaoAll.get("yimiaoYushoujia");

                    editTable.insertValue(0, xiangdanId);
                    editTable.insertValue(1, yimiaoId);
                    editTable.insertValue(2, yimiaoName);
                    editTable.insertValue(3, yimiaoGuige);
                    editTable.insertValue(4, yimiaoJixing);
                    editTable.insertValue(5, shengchanqiye);
                    editTable.insertValue(6, unit);
                    editTable.insertValue(7, quantity);
                    editTable.insertValue(8, buyprice);
                    editTable.insertValue(9, totalprice);

                    //                    保存对应单据的详单id
                    shenqingdanmap.put(xiangdanId, xiangdanId);

                }

            }
        });

//        ((BaseTable) jTableyimiao).addCellListener(new BaseCellFocusListener() {
//            public void editingStopped(int selectedRow, int selectedColumn) {
//                int col = selectedColumn;
//                int row = selectedRow;
//
//                if (col == 3) {
//                    if ((!(("" + jTableyimiao.getValueAt(row, 7)).equals("")))
//                            && (!(("" + jTableyimiao.getValueAt(row, 8)).equals("")))) {
//                        int count = Integer.parseInt("" + jTableyimiao.getValueAt(row, 7));
//                        float price = Float.parseFloat("" + jTableyimiao.getValueAt(row, 8));
//                        jTableyimiao.setValueAt(price * count, row, 9);
//                    }
//                    int rows = jTableyimiao.getRowCount();
//                    total = 0;
//                    for (int i = 0; i < rows; i++) {
//                        if (!(("" + jTableyimiao.getValueAt(i, 9)).equals(""))) {
//                            total += Float.parseFloat("" + jTableyimiao.getValueAt(i, 9));
//                        }
//                    }
//                    totalPrice.setText(total + "元");
//                }
//            }
//        }
//        );
//        jTableyimiao.getModel().addTableModelListener(new TableModelListener() {
//            @Override
//            public void tableChanged(TableModelEvent e) {
//
//                int col = e.getColumn();
//                int row = e.getFirstRow();
//
//                if (col == 3) {
//                    if ((!(("" + jTableyimiao.getValueAt(row, 7)).equals("")))
//                            && (!(("" + jTableyimiao.getValueAt(row, 8)).equals("")))) {
//                        int count = Integer.parseInt("" + jTableyimiao.getValueAt(row, 7));
//                        float price = Float.parseFloat("" + jTableyimiao.getValueAt(row, 8));
//                        jTableyimiao.setValueAt(price * count, row, 9);
//                    }
//                    int rows = jTableyimiao.getRowCount();
//                    total = 0;
//                    for (int i = 0; i < rows; i++) {
//                        if (!(("" + jTableyimiao.getValueAt(i, 9)).equals(""))) {
//                            total += Float.parseFloat("" + jTableyimiao.getValueAt(i, 9));
//                        }
//                    }
//                    totalPrice.setText(total + "元");
//                }
//            }
//
//        });

        ((BaseTable) jTableyimiao).addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1) { //是单//击事件。

                    int rows = jTableyimiao.getRowCount();
                    total = 0;
                    for (int i = 0; i < rows; i++) {
                        if (!(("" + jTableyimiao.getValueAt(i, 9)).equals(""))) {
                            total += Float.parseFloat("" + jTableyimiao.getValueAt(i, 9));
                        }
                    }
                    totalPrice.setText(total + "元");
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
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldYimiaocaigoudanId = new javax.swing.JTextField();
        jTextFieldzhidanDate = new javax.swing.JTextField();
        jTextFieldjingbanren = new javax.swing.JTextField();
        jTextFielddepartment = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaRemark = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableyimiao = new BaseTable(null);
        jLabel13 = new javax.swing.JLabel();
        jTextFieldSupplierName = new BaseTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldConstactperson = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldzhidanren = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        totalPrice = new javax.swing.JLabel();

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
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoSheGouShenQingJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoSheGouShenQingJDialog.class, this);
        jButton1.setAction(actionMap.get("submitForm")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jToolBar1.add(jButton1);

        jButton4.setAction(actionMap.get("print")); // NOI18N
        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setFocusable(false);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setOpaque(false);
        jToolBar1.add(jButton4);

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

        jTextFieldYimiaocaigoudanId.setEditable(false);
        jTextFieldYimiaocaigoudanId.setText(resourceMap.getString("jTextFieldYimiaocaigoudanId.text")); // NOI18N
        jTextFieldYimiaocaigoudanId.setName("jTextFieldYimiaocaigoudanId"); // NOI18N

        jTextFieldzhidanDate.setEditable(false);
        jTextFieldzhidanDate.setText(resourceMap.getString("jTextFieldzhidanDate.text")); // NOI18N
        jTextFieldzhidanDate.setName("jTextFieldzhidanDate"); // NOI18N
        jTextFieldzhidanDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldzhidanDateMouseClicked(evt);
            }
        });
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

        jTextAreaRemark.setColumns(20);
        jTextAreaRemark.setRows(2);
        jTextAreaRemark.setName("jTextAreaRemark"); // NOI18N
        jScrollPane1.setViewportView(jTextAreaRemark);

        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTableyimiao.setBorder(javax.swing.BorderFactory.createEtchedBorder());
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
                "疫苗编号", "疫苗名称", "规格", "剂型", "生产企业", "单位", "数量", "单价", "合价", "预售价"
            }
        ));
        jTableyimiao.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
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
            jTableyimiao.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTable4.columnModel.title8")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTable4.columnModel.title9")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("jTable4.columnModel.title10")); // NOI18N
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
        );

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jTextFieldSupplierName.setName("jTextFieldSupplierName"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jTextFieldConstactperson.setName("jTextFieldConstactperson"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jTextFieldzhidanren.setEditable(false);
        jTextFieldzhidanren.setName("jTextFieldzhidanren"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        totalPrice.setText(resourceMap.getString("totalPrice.text")); // NOI18N
        totalPrice.setName("totalPrice"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldYimiaocaigoudanId, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 420, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFielddepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldConstactperson, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalPrice)
                        .addGap(21, 21, 21)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldYimiaocaigoudanId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFielddepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldConstactperson, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel4)
                    .addComponent(totalPrice))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldzhidanDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldzhidanDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldzhidanDateActionPerformed

    private void jTextFieldzhidanDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldzhidanDateMouseClicked
        // TODO add your handling code here:
        DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd");
    }//GEN-LAST:event_jTextFieldzhidanDateMouseClicked

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
            java.util.logging.Logger.getLogger(YiMiaoSheGouShenQingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoSheGouShenQingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoSheGouShenQingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoSheGouShenQingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    }

    public void setAddOrUpdate(boolean b) {
        isNew = b;
        if (isNew) {
            this.setTitle("Ⅱ类疫苗采购申请单");
            shenqingdan = new Shenqingdantb();
            yimiaoshenqingdan = new Yimiaoshenqingdantb();
        } else {
            this.setTitle("Ⅱ类疫苗采购申请单");
        }
    }

    public void setUpdatedData(Yimiaoshenqingdantb yimiaoshenqingdan) {
        if (yimiaoshenqingdan == null) {
            return;
        }
        this.yimiaoshenqingdan = yimiaoshenqingdan;
        jTextFieldYimiaocaigoudanId.setText((yimiaoshenqingdan.getShenqingdanId()).toString());
        jTextFieldSupplierName.setText((shenqingdan.getSupplierId()).toString());

    }

    @Action
    public Task submitForm() throws ParseException {
        jTableyimiao.getCellEditor(jTableyimiao.getSelectedRow(),
                jTableyimiao.getSelectedColumn()).stopCellEditing();
        if (jTextFieldSupplierName.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入供应单位名称!");
            return null;
        }
        yimiaocaigou = new YimiaoshenqingdantbFindEntity();
        shenqingdan.setShenqingdanId(jTextFieldYimiaocaigoudanId.getText());
        shenqingdan.setShenqingdanDate(dateformate.parse(jTextFieldzhidanDate.getText()));
        shenqingdan.setJingbanrenId(AssetClientApp.getSessionMap().getUsertb().getUserId());
        shenqingdan.setZhidanrenId(AssetClientApp.getSessionMap().getUsertb().getUserId());
        shenqingdan.setDanjuleixingId(6);
        shenqingdan.setShenqingdanRemark(jTextAreaRemark.getText());

        total = 0;

        List<Yimiaoshenqingdantb> list = new ArrayList<Yimiaoshenqingdantb>();
        for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
            BaseTable yimiaotable = ((BaseTable) jTableyimiao);
            yimiaoshenqingdan = new Yimiaoshenqingdantb();
            yimiaoshenqingdan.setDanjuleixingId(6);
            yimiaoshenqingdan.setStatus(0);
            yimiaoshenqingdan.setShenqingdanId(jTextFieldYimiaocaigoudanId.getText());
            yimiaoshenqingdan.setYimiaoId(Integer.parseInt(yimiaotable.getValue(i, "yimiaoId").toString()));
            yimiaoshenqingdan.setQuantity(Integer.parseInt((String) ("" + yimiaotable.getValue(i, "quantity"))));
            yimiaoshenqingdan.setBuyprice(Float.parseFloat((String) ("" + yimiaotable.getValue(i, "buyprice"))));
            yimiaoshenqingdan.setTotalprice(Float.parseFloat((String) ("" + yimiaotable.getValue(i, "totalprice"))));
            yimiaoshenqingdan.setYuandanId(Integer.parseInt(shenqingdanmap.get((Integer) yimiaotable.getValue(i, "xiangdanId")).toString()));
            list.add(yimiaoshenqingdan);
            total += yimiaoshenqingdan.getTotalprice();
        }
        shenqingdan.setDanjujine(total);
        yimiaocaigou.setShenqingdan(shenqingdan);
        yimiaocaigou.setYimiaoshenqingdans(list);
        return new SubmitFormTask(yimiaocaigou);
    }

    @Action
    public void print() {
        try {
            super.print(this.getTitle(),
                    new String[][]{{"单据编号", jTextFieldYimiaocaigoudanId.getText()},
                    {"制单日期", jTextFieldzhidanDate.getText()},
                    {"经办人", jTextFieldjingbanren.getText()},
                    {"部门", jTextFielddepartment.getText()},
                    {"供应单位", jTextFieldSupplierName.getText()},
                    {"联系人", jTextFieldConstactperson.getText()},
                    {"备注", jTextAreaRemark.getText()}},
                    jTableyimiao,
                    new String[][]{{"制单人", jTextFieldzhidanren.getText()}});
        } catch (DRException ex) {
            ex.printStackTrace();
            logger.error(ex);
        }
    }

    @Action
    public void exit() {
        this.dispose();
    }

    private class SubmitFormTask extends YimiaoshenqingdanUpdateTask {

        SubmitFormTask(YimiaoshenqingdantbFindEntity yimiaoshenqingdan) {
            super(yimiaoshenqingdan, isNew ? YimiaoshenqingdanUpdateTask.ENTITY_SAVE : YimiaoshenqingdanUpdateTask.ENTITY_UPDATE);
        }

        @Override
        public void onSucceeded(Object result) {
            if (result instanceof Exception) {
                Exception e = (Exception) result;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            AssetMessage.INFO("提交成功！", YiMiaoSheGouShenQingJDialog.this);
            exit();
            JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
            YiMiaoSheGouShenQingJDialog caiGouShenQingJDialog = new YiMiaoSheGouShenQingJDialog();
            caiGouShenQingJDialog.setLocationRelativeTo(mainFrame);
            caiGouShenQingJDialog.setAddOrUpdate(true);
            AssetClientApp.getApplication().show(caiGouShenQingJDialog);
        }
    }

    public YiMiaoSheGouShenQingJDialog(final JDialog parent, YimiaocaigouxiangdanEntity yimiaocaigouxiangdanEntity) {
        super();
        initComponents();
        this.yimiaocaigouxiangdanEntity = yimiaocaigouxiangdanEntity;
        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (parent != null) {
                    parent.setVisible(true);
                }
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

        jTextFieldYimiaocaigoudanId.setEditable(false);
        jTextFieldYimiaocaigoudanId.setText(yimiaocaigouxiangdanEntity.getShenqingdantb().getShenqingdanId());
        jTextFieldzhidanDate.setText(DateHelper.format(yimiaocaigouxiangdanEntity.getShenqingdantb().getShenqingdanDate(), "yyyy-MM-dd HH:mm:ss"));
        jTextFieldzhidanDate.setEditable(false);
        jTextFieldjingbanren.setEditable(false);
        jTextFieldjingbanren.setText(yimiaocaigouxiangdanEntity.getUserAll().getUserName());
        jTextFieldzhidanren.setEditable(false);
        jTextFieldzhidanren.setText(yimiaocaigouxiangdanEntity.getUserAll().getUserName());
        jTextFielddepartment.setEditable(false);
        jTextFielddepartment.setText(yimiaocaigouxiangdanEntity.getUserAll().getDepartment().getDepartmentName());
        jTextFieldSupplierName.setEditable(false);
        jTextFieldSupplierName.setText(yimiaocaigouxiangdanEntity.getSupplier().getSupplierName());
        jTextFieldConstactperson.setEditable(false);
        jTextFieldConstactperson.setText(yimiaocaigouxiangdanEntity.getSupplier().getSupplierConstactperson());
        jTextAreaRemark.setEditable(false);
        jTextAreaRemark.setText("" + yimiaocaigouxiangdanEntity.getShenqingdantb().getShenqingdanRemark());
        totalPrice.setText("" + yimiaocaigouxiangdanEntity.getShenqingdantb().getDanjujine() + "元");

        setListTable(yimiaocaigouxiangdanEntity.getResult());
    }

    public void setListTable(List<YimiaocaigouEntity> yimiaocaigouEntityList) {

        int size = yimiaocaigouEntityList.size();
        Object[][] o = new Object[size][9];
        for (int i = 0; i < size; i++) {
            Yimiaoshenqingdantb yimiaoshenqingdantb = yimiaocaigouEntityList.get(i).getYimiaoshenqingdantb();
            YimiaoAll yimiaoAll = yimiaocaigouEntityList.get(i).getYimiaoAll();
            o[i] = new Object[]{yimiaoAll.getYimiaoId(), yimiaoAll.getYimiaoName(), yimiaoAll.getYimiaoGuige(), yimiaoAll.getYimiaoJixing(), yimiaoAll.getYimiaoShengchanqiye(), yimiaoAll.getUnitId(),
                yimiaoshenqingdantb.getQuantity(), yimiaoshenqingdantb.getBuyprice(), yimiaoshenqingdantb.getTotalprice()};
        }

        jTableyimiao.setModel(new javax.swing.table.DefaultTableModel(
                o,
                new String[]{
                    "疫苗编号", "疫苗名称", "规格", "剂型", "生产企业", "单位", "数量", "单价", "合价"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false
            };
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JTextArea jTextAreaRemark;
    private javax.swing.JTextField jTextFieldConstactperson;
    private javax.swing.JTextField jTextFieldSupplierName;
    private javax.swing.JTextField jTextFieldYimiaocaigoudanId;
    private javax.swing.JTextField jTextFielddepartment;
    private javax.swing.JTextField jTextFieldjingbanren;
    private javax.swing.JTextField jTextFieldzhidanDate;
    private javax.swing.JTextField jTextFieldzhidanren;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel totalPrice;
    // End of variables declaration//GEN-END:variables
}
