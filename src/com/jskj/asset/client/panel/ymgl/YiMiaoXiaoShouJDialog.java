/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ymgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Sale_detail_tb;
import com.jskj.asset.client.bean.entity.Sale_detail_tbFindEntity;
import com.jskj.asset.client.bean.entity.Saletb;
import com.jskj.asset.client.bean.entity.SaleyimiaoEntity;
import com.jskj.asset.client.bean.entity.Stockpiletb;
import com.jskj.asset.client.bean.entity.XiaoshoushenpixiangdanEntity;
import com.jskj.asset.client.bean.entity.YimiaoAll;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseCellFocusListener;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ScanButton;
import com.jskj.asset.client.panel.ymgl.task.XiaoshouTask;
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
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JFrame;
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author huiqi
 */
public class YiMiaoXiaoShouJDialog extends BaseDialog {

    private static final Logger logger = Logger.getLogger(YiMiaoXiaoShouJDialog.class);
    private Sale_detail_tb sale_detail;
    private Saletb sale;
    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat riqiformate = new SimpleDateFormat("yyyy-MM-dd");
    private boolean isNew;
    private Sale_detail_tbFindEntity yimiaoxiaoshou;
    private XiaoshoushenpixiangdanEntity yimiaoxiaoshouxiangdanEntity;
    private Map kucunmap;
    private float total = 0;

    /**
     * Creates new form yimiaoyanshouJDialog
     */
    public YiMiaoXiaoShouJDialog() {
        super();
        initComponents();
        jTextFieldXiaoshouId.setText(DanHao.getDanHao(DanHao.TYPE_YIMIAOXS));
        jTextFieldXiaoshouId.setEditable(false);

        jTextFieldzhidanDate.setText(dateformate.format(new Date()).toString());
        jTextFieldjingbanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFieldzhidanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFielddepartment.setText(AssetClientApp.getSessionMap().getDepartment().getDepartmentName());
        kucunmap = new HashMap();

//客户单位的popup
        ((BaseTextField) jTextFieldXiaoshoudanwei).registerPopup(new IPopupBuilder() {

            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "kehudanwei";
            }

            public String getConditionSQL() {
                String sql = " kehudanwei_type = 0 ";
                if (!jTextFieldXiaoshoudanwei.getText().trim().equals("")) {
                    sql += " and (kehudanwei_name like \"%" + jTextFieldXiaoshoudanwei.getText() + "%\"" + " or kehudanwei_zujima like \"%" + jTextFieldXiaoshoudanwei.getText().toLowerCase() + "%\")";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"kehudanweiName", "客户单位名称"}, {"kehudanweiPhone", "电话"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    jTextFieldXiaoshoudanwei.setText(bindedMap.get("kehudanweiName") == null ? "" : bindedMap.get("kehudanweiName").toString());
                    jTextFieldTel.setText(bindedMap.get("kehudanweiPhone") == null ? "" : bindedMap.get("kehudanweiPhone").toString());
                    jTextFieldAddr.setText(bindedMap.get("kehudanweiAddr") == null ? "" : bindedMap.get("kehudanweiAddr").toString());
                    sale.setCustomerId((Integer) bindedMap.get("kehudanweiId"));
                }
            }
        });

        //疫苗表中的内容
        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTableyimiao).createSingleEditModel(new String[][]{
            {"stockpileId", "库存编号"}, {"yimiao.yimiaoName", "疫苗名称", "true"}, {"yimiao.yimiaoGuige", "规格", "false"}, {"yimiao.yimiaoJixing", "剂型", "false"},
            {"yimiao.yimiaoShengchanqiye", "生产企业", "false"}, {"pihao", "批号", "false"}, {"yimiao.unitId", "单位", "false"}, {"youxiaodate", "有效期至", "false"}, {"saleQuantity", "数量", "true"},
            {"yushouPrice", "单价", "false"}, {"hejia", "合价", "false"}});

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
                    sql += "stockpile.stockPile_id in (select distinct stockPile.stockpile_id from stockpile,yimiao where stockpile.stockPile_price>0 and yimiao.yimiao_id=stockpile.yimiao_id and (yimiao.yimiao_name like \"%" + newColumnObj.toString() + "%\"" + " or zujima like \"%" + newColumnObj.toString().toLowerCase() + "%\"))";
                } else {
                    sql += "stockPile_id in (select distinct stockPile_id from stockpile where stockPile_price>0)";
                }
                return sql;
            }

            public String[][] displayColumns() {
                
                return new String[][]{{"stockpileId", "库存编号"}, {"yimiao.yimiaoName", "疫苗名称"}, {"pihao", "批号"},
                {"youxiaodate", "有效期"}, {"stockpileQuantity", "库存数量"}};
                
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object yimiaomap = bindedMap.get("yimiao");
                    HashMap yimiao = (HashMap) yimiaomap;

                    Object stockpileId = bindedMap.get("stockpileId");
                    Object stockpileQuantity = bindedMap.get("stockpileQuantity");
                    Object yimiaoName = yimiao.get("yimiaoName");
                    Object yimiaoGuige = yimiao.get("yimiaoGuige");
                    Object yimiaoJixing = yimiao.get("yimiaoJixing");
                    Object shengchanqiye = yimiao.get("yimiaoShengchanqiye");
                    Object unit = yimiao.get("unitId");
                    Float yushouPrice = Float.parseFloat("" + yimiao.get("yimiaoYushoujia"));
                    Object youxiaoqi = bindedMap.get("youxiaodate").toString().subSequence(0, 10);
                    Object pihao = bindedMap.get("pihao");

                    editTable.insertValue(0, stockpileId);
                    editTable.insertValue(1, yimiaoName);
                    editTable.insertValue(2, yimiaoGuige);
                    editTable.insertValue(3, yimiaoJixing);
                    editTable.insertValue(4, shengchanqiye);
                    editTable.insertValue(5, pihao);
                    editTable.insertValue(6, unit);
                    editTable.insertValue(7, youxiaoqi);
                    editTable.insertValue(9, yushouPrice);

//                    保存库存数量
                    kucunmap.put(stockpileId, stockpileQuantity);
                }

            }
        });

//        自动计算出疫苗的合价显示
        ((BaseTable) jTableyimiao).addCellListener(new BaseCellFocusListener() {
            public void editingStopped(int selectedRow, int selectedColumn) {
                int col = selectedColumn;
                int row = selectedRow;

                if (col == 8) {
                    if ((!(("" + jTableyimiao.getValueAt(row, 8)).equals("")))
                            && (!(("" + jTableyimiao.getValueAt(row, 9)).equals("")))) {
                        try {
                            int count = Integer.parseInt("" + jTableyimiao.getValueAt(row, 8));
                            float price = Float.parseFloat("" + jTableyimiao.getValueAt(row, 9));
                            jTableyimiao.setValueAt(price * count, row, 10);
                        } catch (NumberFormatException e) {
                            AssetMessage.ERRORSYS("第" + (row + 1) + "个疫苗销售数量输入不合法，请输入纯数字，不能包含字母或特殊字符！");
                            return;
                        }
                    }
                    int rows = jTableyimiao.getRowCount();
                    total = 0;
                    for (int i = 0; i < rows; i++) {
                        if (!(("" + jTableyimiao.getValueAt(i, 10)).equals(""))) {
                            total += Float.parseFloat("" + jTableyimiao.getValueAt(i, 10));
                        }
                    }
                    totalPrice.setText(total + "元");
                }
            }
        }
        );

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

                    HashMap yimiao = (HashMap) bindedMap;
                    Object yimiaoId = yimiao.get("yimiaoId");
                    Object yimiaoName = yimiao.get("yimiaoName");
                    Object yimiaoGuige = yimiao.get("yimiaoGuige");
                    Object yimiaoJixing = yimiao.get("yimiaoJixing");
                    Object shengchanqiye = yimiao.get("yimiaoShengchanqiye");
                    Object unit = yimiao.get("unitId");
                    Float stockpilePrice = bindedMap.get("stockpilePrice") == null ? 0 : Float.parseFloat("" + bindedMap.get("stockpilePrice"));
                    Object youxiaoqi = bindedMap.get("youxiaodate");
                    Object saleprice = yimiao.get("yimiaoYushoujia");
                    jTableyimiao.getSelectionModel().setSelectionInterval(jTableyimiao.getRowCount() - 1, jTableyimiao.getRowCount() - 1);

                    editTable.insertValue(jTableyimiao.getSelectedRow(), 0, yimiaoId);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 1, yimiaoName);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 2, yimiaoGuige);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 3, yimiaoJixing);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 4, shengchanqiye);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 5, unit);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 6, youxiaoqi);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 8, stockpilePrice);
                    editTable.insertValue(jTableyimiao.getSelectedRow(), 9, saleprice);

                    editTable.addNewRow();
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
        jButton2 = new ScanButton();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldXiaoshouId = new javax.swing.JTextField();
        jTextFieldzhidanDate = new javax.swing.JTextField();
        jTextFieldAddr = new javax.swing.JTextField();
        jTextFieldTel = new javax.swing.JTextField();
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
        jTextFieldgongyingType = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldjingbanren = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFielddepartment = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
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
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoXiaoShouJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoXiaoShouJDialog.class, this);
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

        jTextFieldXiaoshouId.setEditable(false);
        jTextFieldXiaoshouId.setText(resourceMap.getString("jTextFieldXiaoshouId.text")); // NOI18N
        jTextFieldXiaoshouId.setName("jTextFieldXiaoshouId"); // NOI18N

        jTextFieldzhidanDate.setEditable(false);
        jTextFieldzhidanDate.setText(resourceMap.getString("jTextFieldzhidanDate.text")); // NOI18N
        jTextFieldzhidanDate.setName("jTextFieldzhidanDate"); // NOI18N

        jTextFieldAddr.setText(resourceMap.getString("jTextFieldAddr.text")); // NOI18N
        jTextFieldAddr.setName("jTextFieldAddr"); // NOI18N

        jTextFieldTel.setText(resourceMap.getString("jTextFieldTel.text")); // NOI18N
        jTextFieldTel.setName("jTextFieldTel"); // NOI18N

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
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jTextFieldgongyingType.setName("jTextFieldgongyingType"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jTextFieldjingbanren.setEditable(false);
        jTextFieldjingbanren.setName("jTextFieldjingbanren"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jTextFielddepartment.setEditable(false);
        jTextFielddepartment.setName("jTextFielddepartment"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        totalPrice.setText(resourceMap.getString("totalPrice.text")); // NOI18N
        totalPrice.setName("totalPrice"); // NOI18N

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
                                .addComponent(jTextFieldXiaoshouId, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldXiaoshoudanwei, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldAddr, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 390, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldTel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldgongyingType, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFielddepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalPrice)))
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
                            .addComponent(jTextFieldXiaoshouId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldXiaoshoudanwei, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel13))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldAddr, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextFieldgongyingType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jTextFieldTel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
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
                    .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(totalPrice))
                .addContainerGap(20, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
            java.util.logging.Logger.getLogger(YiMiaoXiaoShouJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoXiaoShouJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoXiaoShouJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoXiaoShouJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    }

    public void setAddOrUpdate(boolean b) {
        isNew = b;
        if (isNew) {
            this.setTitle("Ⅱ类疫苗销售单");
            sale = new Saletb();
        } else {
            this.setTitle("Ⅱ类疫苗销售单");
        }
    }

    @Action
    public Task submitForm() throws ParseException {
        jTableyimiao.getCellEditor(jTableyimiao.getSelectedRow(),
                jTableyimiao.getSelectedColumn()).stopCellEditing();
        if (jTextFieldzhidanDate.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入制单日期!");
            return null;
        } else if (jTextFieldXiaoshoudanwei.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入销售单位!");
            return null;
        }
        yimiaoxiaoshou = new Sale_detail_tbFindEntity();
        sale.setSaleId(jTextFieldXiaoshouId.getText());
        sale.setFahuotype(jTextFieldgongyingType.getText());
        sale.setSaleDate(dateformate.parse(jTextFieldzhidanDate.getText()));
        sale.setZhidanrenId(AssetClientApp.getSessionMap().getUsertb().getUserId());
        sale.setDepartmentId(AssetClientApp.getSessionMap().getDepartment().getDepartmentId());
        sale.setRemark(jTextArea1.getText());
        total = 0;

        List<Sale_detail_tb> list = new ArrayList<Sale_detail_tb>();
        for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
            BaseTable yimiaotable = ((BaseTable) jTableyimiao);
            sale_detail = new Sale_detail_tb();
            sale_detail.setSaleId(jTextFieldXiaoshouId.getText());
            sale_detail.setStockpileId(Integer.parseInt(yimiaotable.getValue(i, "stockpileId").toString()));
            try {
                if (yimiaotable.getValue(i, "saleQuantity").equals("")) {
                    AssetMessage.ERRORSYS("请输入疫苗销售数量!");
                    return null;
                } else if (Integer.parseInt("" + yimiaotable.getValue(i, "saleQuantity")) > Integer.parseInt(kucunmap.get(sale_detail.getStockpileId()).toString())) {
                    AssetMessage.ERRORSYS(yimiaotable.getValue(i, "yimiao.yimiaoName").toString() + "销售数量不能大于库存数量:" + Integer.parseInt(kucunmap.get(sale_detail.getStockpileId()).toString()));
                    return null;
                }
            } catch (NumberFormatException e) {
                AssetMessage.ERRORSYS("第" + (i + 1) + "个疫苗销售数量输入不合法，请输入纯数字，不能包含字母或特殊字符！");
                return null;
            }
            sale_detail.setQuantity(Integer.parseInt(yimiaotable.getValue(i, "saleQuantity").toString()));
            sale_detail.setPrice(Float.parseFloat((String) ("" + yimiaotable.getValue(i, "yushouPrice"))));
            sale_detail.setTotalprice(sale_detail.getQuantity() * sale_detail.getPrice());
            sale_detail.setStatus(0);
            list.add(sale_detail);
            total += sale_detail.getTotalprice();
        }
        sale.setDanjujine(total);
        yimiaoxiaoshou.setSale(sale);
        yimiaoxiaoshou.setSale_details(list);
        return new SubmitFormTask(yimiaoxiaoshou);

    }

    //    打印预览
    @Action
    public void print() {
        try {
            super.print(this.getTitle(),
                    new String[][]{{"单据编号", jTextFieldXiaoshouId.getText()},
                    {"制单日期", jTextFieldzhidanDate.getText()},
                    {"客户单位", jTextFieldXiaoshoudanwei.getText()},
                    {"供应方式", jTextFieldgongyingType.getText()},
                    {"收货地址", jTextFieldAddr.getText()},
                    {"收货电话", jTextFieldTel.getText()},
                    {"经办人", jTextFieldjingbanren.getText()},
                    {"部门", jTextFielddepartment.getText()},
                    {"备注", jTextArea1.getText()}},
                    jTableyimiao,
                    new String[][]{{"制单人", jTextFieldzhidanren.getText()},});
        } catch (DRException ex) {
            ex.printStackTrace();
            logger.error(ex);
        }
    }

    @Action
    public void exit() {
        this.dispose();
    }

    private class SubmitFormTask extends XiaoshouTask {

        SubmitFormTask(Sale_detail_tbFindEntity sale_detail) {
            super(sale_detail, isNew ? XiaoshouTask.ENTITY_SAVE : XiaoshouTask.ENTITY_UPDATE);
        }

        @Override
        public void onSucceeded(Object result) {
            if (result instanceof Exception) {
                Exception e = (Exception) result;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            AssetMessage.INFO("提交成功！", YiMiaoXiaoShouJDialog.this);
            exit();
            JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
            YiMiaoXiaoShouJDialog yiMiaoXiaoShouJDialog = new YiMiaoXiaoShouJDialog();
            yiMiaoXiaoShouJDialog.setLocationRelativeTo(mainFrame);
            yiMiaoXiaoShouJDialog.setAddOrUpdate(true);
            AssetClientApp.getApplication().show(yiMiaoXiaoShouJDialog);
        }
    }

    public YiMiaoXiaoShouJDialog(final JDialog parent, XiaoshoushenpixiangdanEntity yimiaoxiaoshouxiangdanEntity) {
        super();
        initComponents();
        this.yimiaoxiaoshouxiangdanEntity = yimiaoxiaoshouxiangdanEntity;
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

        jTextFieldXiaoshouId.setEditable(false);
        jTextFieldXiaoshouId.setText(yimiaoxiaoshouxiangdanEntity.getSaletb().getSaleId());
        jTextFieldzhidanDate.setText(DateHelper.format(yimiaoxiaoshouxiangdanEntity.getSaletb().getSaleDate(), "yyyy-MM-dd HH:mm:ss"));
        jTextFieldXiaoshoudanwei.setEditable(false);
        jTextFieldXiaoshoudanwei.setText("" + yimiaoxiaoshouxiangdanEntity.getKehudanwei().getKehudanweiName());
        jTextFieldgongyingType.setEditable(false);
        jTextFieldgongyingType.setText("" + yimiaoxiaoshouxiangdanEntity.getSaletb().getFahuotype());
        jTextFieldAddr.setEditable(false);
        jTextFieldAddr.setText("" + yimiaoxiaoshouxiangdanEntity.getKehudanwei().getKehudanweiAddr());
        jTextFieldTel.setEditable(false);
        jTextFieldTel.setText("" + yimiaoxiaoshouxiangdanEntity.getKehudanwei().getKehudanweiPhone());
        jTextFielddepartment.setEditable(false);
        jTextFielddepartment.setText("" + yimiaoxiaoshouxiangdanEntity.getUserAll().getDepartment().getDepartmentName());
        jTextFieldjingbanren.setEditable(false);
        jTextFieldjingbanren.setText("" + yimiaoxiaoshouxiangdanEntity.getUserAll().getUserName());
        jTextFieldzhidanren.setEditable(false);
        jTextFieldzhidanren.setText("" + yimiaoxiaoshouxiangdanEntity.getUserAll().getUserName());
        jTextArea1.setEditable(false);
        jTextArea1.setText("" + yimiaoxiaoshouxiangdanEntity.getSaletb().getRemark());
        totalPrice.setText("" + yimiaoxiaoshouxiangdanEntity.getSaletb().getDanjujine() + "元");

        setListTable(yimiaoxiaoshouxiangdanEntity.getResult());
    }

    public void setListTable(List<SaleyimiaoEntity> saleyimiaoEntityList) {

        int size = saleyimiaoEntityList.size();
        Object[][] o = new Object[size][11];
        for (int i = 0; i < size; i++) {
            Sale_detail_tb saledetailtb = saleyimiaoEntityList.get(i).getSale_detail_tb();
            YimiaoAll yimiaoAll = saleyimiaoEntityList.get(i).getYimiaoAll();
            Stockpiletb stockpile = saleyimiaoEntityList.get(i).getStockpile();
            o[i] = new Object[]{saledetailtb.getStockpileId(), yimiaoAll.getYimiaoName(), yimiaoAll.getYimiaoGuige(), yimiaoAll.getYimiaoJixing(), yimiaoAll.getYimiaoShengchanqiye(), stockpile.getPihao(), yimiaoAll.getUnitId(),
                new SimpleDateFormat("yyyy-MM-dd").format((Date) stockpile.getYouxiaodate()), saledetailtb.getQuantity(), saledetailtb.getPrice(), saledetailtb.getTotalprice()};
        }

        jTableyimiao.setModel(new javax.swing.table.DefaultTableModel(
                o,
                new String[]{
                    "库存编号", "疫苗名称", "规格", "剂型", "生产企业", "批号", "单位", "有效期", "数量", "售价", "合价"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false, false, false
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
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
    private javax.swing.JTextField jTextFieldAddr;
    private javax.swing.JTextField jTextFieldTel;
    private javax.swing.JTextField jTextFieldXiaoshouId;
    private javax.swing.JTextField jTextFieldXiaoshoudanwei;
    private javax.swing.JTextField jTextFielddepartment;
    private javax.swing.JTextField jTextFieldgongyingType;
    private javax.swing.JTextField jTextFieldjingbanren;
    private javax.swing.JTextField jTextFieldzhidanDate;
    private javax.swing.JTextField jTextFieldzhidanren;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel totalPrice;
    // End of variables declaration//GEN-END:variables
}
