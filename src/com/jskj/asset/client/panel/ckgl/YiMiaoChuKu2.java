/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ckgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Churukudantb;
import com.jskj.asset.client.bean.entity.StockpiletbAll;
import com.jskj.asset.client.bean.entity.YiMiaotb;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.layout.ws.CommUpdateTask;
import com.jskj.asset.client.util.BindTableHelper;
import com.jskj.asset.client.util.DanHao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author Administrator
 */
public class YiMiaoChuKu2 extends javax.swing.JDialog {

    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd");
//    private List<StockpiletbAll> chukuyimiaolist;
//    private BindTableHelper<StockpiletbAll> bindTable;
    private Churukudantb churukudan;

    /**
     * Creates new form ymcrk1
     */
    public YiMiaoChuKu2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        churukudan = new Churukudantb();
        
        jTextFielddanjuNo.setText(DanHao.getDanHao("YMCK"));
        jTextFielddanjuNo.setEditable(false);
        jTextFieldzhidanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFieldzhidanDate.setText(dateformate.format(new Date()).toString());
        
        //疫苗表中的内容
        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTableyimiao).createSingleEditModel(new String[][]{
            {"yimiaoId", "疫苗编号", "false"},{"yimiaoName", "疫苗名称", "true"},{"source", "国产/出口", "false"},{"tongguandanNo", "进口通关单编号", "false"}, {"quantity", "数量", "true"}, {"yimiaoGuige", "规格", "false"}, {"yimiaoJixing", "剂型", "false"},
            {"yimiaoShengchanqiye", "生产企业", "false"}, {"pihao", "批号", "false"}, {"youxiaodate", "有效期", "false"}, {"unitId", "单位", "false"},
            {"piqianfaNo", "批签发合格证编号", "false"}, {"yimiaoPizhunwenhao", "批准文号", "true"}, {"stockpilePrice", "单价", "true"}, {"totalPrice", "合价", "true"},
            {"jingbanren", "经办人", "true"}, {"gongyingdanwei", "供应单位", "true"}, {"duifangjingbanren", "对方经办人", "true"}});
        editTable.registerPopup(1, new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addxiafayimiao";
            }

            public String getConditionSQL() {
                int selectedColumn = jTableyimiao.getSelectedColumn();
                int selectedRow = jTableyimiao.getSelectedRow();
                Object newColumnObj = jTableyimiao.getValueAt(selectedRow, selectedColumn);
                String sql = "";
                if (newColumnObj instanceof String && !newColumnObj.toString().trim().equals("")) {
                    sql += "yimiao_name like \"%" + newColumnObj.toString()  + "%\""
                            + "from yimiao where yimiao_id in (select distinct yimiao_id from stockpile where stockPile_price=0)";
                } else {
                    sql += "yimiao_id in (select distinct yimiao_id from stockpile where stockPile_price=0)";
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
                    
                    Object yimiaoId = yimiaoAll.get("yimiaoId");
                    Object yimiaoName = yimiaoAll.get("yimiaoName");
                    Object yimiaoGuige = yimiaoAll.get("yimiaoGuige");
                    Object yimiaoJixing = yimiaoAll.get("yimiaoJixing");
                    Object shengchanqiye = yimiaoAll.get("yimiaoShengchanqiye");
                    Object unit = yimiaoAll.get("unitId");
                    Object quantity = yimiaoshenqingdan.get("quantity");
                    

                    editTable.insertValue(0, yimiaoId);
                    editTable.insertValue(1, yimiaoName);
                    editTable.insertValue(2, yimiaoGuige);
                    editTable.insertValue(3, yimiaoJixing);
                    editTable.insertValue(4, shengchanqiye);
                    editTable.insertValue(5, unit);
                    editTable.insertValue(6, quantity);

                }

            }
        });

//        bindTable = new BindTableHelper<StockpiletbAll>(jTableyimiao, new ArrayList<StockpiletbAll>());
//        bindTable.createTable(new String[][]{
//            {"date", "日期", "true"}, {"quantity", "数量", "true"}, {"yimiao.yimiaoGuige", "规格", "false"}, {"yimiao.yimiaoJixing", "剂型", "false"},
//            {"yimiao.yimiaoShengchanqiye", "生产企业", "false"}, {"pihao", "批号", "false"}, {"youxiaoqi", "有效期", "false"}, {"yimiao.unitId", "单位", "false"},
//            {"piqianfaNo", "批签发合格证编号", "false"}, {"yimiao.yimiaoPizhunwenhao", "批准文号", "true"}, {"stockpilePrice", "单价", "true"}, {"totalPrice", "合价", "true"},
//            {"jingbanren", "经办人", "true"}, {"gongyingdanwei", "供应单位", "true"}, {"duifangjingbanren", "对方经办人", "true"}});
//
//        //        疫苗的popup
//        ((BaseTextField) jTextFielddanjuNo).registerPopup(new IPopupBuilder() {
//            public int getType() {
//                return IPopupBuilder.TYPE_POPUP_TEXT;
//            }
//
//            public String getWebServiceURI() {
//                return Constants.HTTP + Constants.APPID + "addkucunyimiao";
//            }
//
//            public String getConditionSQL() {
//                String sql = "";
//                sql += "yimiao_id in (select distinct yimiao_id from stockpile where stockPile_price>0)";
//                if (!jTextFielddanjuNo.getText().trim().equals("")) {
//                    sql = "and yimiao_name like \"%" + jTextFielddanjuNo.getText() + "%\"";
//                }
//                return sql;
//            }
//
//            public String[][] displayColumns() {
//                return new String[][]{{"yimiaoId", "疫苗编号"}, {"yimiao.yimiaoName", "疫苗名称"}};
//            }
//
//            public void setBindedMap(HashMap bindedMap) {
//                if (bindedMap != null) {
//                    Object yimiaomap = bindedMap.get("yimiao");
//                    HashMap yimiao = (HashMap) yimiaomap;
//                    jTextFielddanjuNo.setText((String) (yimiao.get("yimiaoName") == null ? "" : yimiao.get("yimiaoName")));
//                    jTextFieldzhidanDate.setText(bindedMap.get("source") == null ? "" : bindedMap.get("source").toString());
//                    jTextFieldzhidanren.setText(bindedMap.get("jinkoutongguanno") == null ? "" : bindedMap.get("jinkoutongguanno").toString());
//                    churukudan.setSource(bindedMap.get("source").toString());
//                    churukudan.setTongguandanno(bindedMap.get("jinkoutongguanno").toString());
//                    churukudan.setYouxiaoqi((Date) bindedMap.get("youxiaoqi"));
//
//                    churukudan.setPiqianfahegeno((String) bindedMap.get("piqianfano"));
//                    System.out.println(yimiaomap);
//                    System.out.println(bindedMap);
////                    System.out.println(yimiao.get("yimiaoName"));
//                    YiMiaotb yimiaoentity = new YiMiaotb();
//                    yimiaoentity.setSupplierId((Integer) yimiao.get("supplierId"));
//                    yimiaoentity.setUnitId((String) yimiao.get("unitId"));
//                    yimiaoentity.setYimiaoChufangbiaoji((String) yimiao.get("yimiaoChufangbiaoji"));
//                    yimiaoentity.setYimiaoFuzhuunit((String) yimiao.get("yimiaoFuzhuunit"));
//                    yimiaoentity.setYimiaoGuige((String) yimiao.get("yimiaoGuige"));
//                    yimiaoentity.setYimiaoId((Integer) yimiao.get("yimiaoId"));
//                    churukudan.setYimiaoId((Integer) yimiao.get("yimiaoId"));
//                    yimiaoentity.setYimiaoJixing((String) yimiao.get("yimiaoJixing"));
//                    yimiaoentity.setYimiaoMorencangku((Integer) yimiao.get("yimiaoMorencangku"));
//                    yimiaoentity.setYimiaoName((String) yimiao.get("yimiaoName"));
//                    yimiaoentity.setYimiaoPicture((String) yimiao.get("yimiaoPicture"));
//                    yimiaoentity.setYimiaoPizhunwenhao((String) yimiao.get("yimiaoPizhunwenhao"));
//                    yimiaoentity.setYimiaoRemark((String) yimiao.get("yimiaoRemark"));
//                    yimiaoentity.setYimiaoShengchanqiye((String) yimiao.get("yimiaoShengchanqiye"));
//                    yimiaoentity.setYimiaoStockdown((Integer) yimiao.get("yimiaoStockdown"));
//                    yimiaoentity.setYimiaoStockup((Integer) yimiao.get("yimiaoStockup"));
//                    yimiaoentity.setYimiaoTiaoxingma((String) yimiao.get("yimiaoTiaoxingma"));
//                    yimiaoentity.setYimiaoType((String) yimiao.get("yimiaoType"));
////                    yimiaoentity.setYimiaoYushoujia((Float) yimiao.get("yimiaoYushoujia"));
//                    StockpiletbAll chukumiao = new StockpiletbAll();
//                    chukumiao.setYimiao(yimiaoentity);
//                    chukumiao.setPiqianfano((String) bindedMap.get("piqianfano"));
//                    try {
//                        chukumiao.setYouxiaodate(dateformate.parse((String) bindedMap.get("youxiaodate")));
//                    } catch (ParseException ex) {
//                        Logger.getLogger(YiMiaoChuKu2.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    System.out.println(bindedMap.get("stockpilePrice"));
//                    Float stockpilePrice = Float.parseFloat(""+bindedMap.get("stockpilePrice")) ;
//                    chukumiao.setStockpilePrice(stockpilePrice);
//
//                    chukuyimiaolist = new ArrayList<StockpiletbAll>();
//                    chukuyimiaolist.add(chukumiao);
//                    bindTable.refreshData(chukuyimiaolist);
//                }
//            }
//
//        }
//        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton7 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldzhidanDate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldzhidanren = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableyimiao = new BaseTable(null);
        jTextFielddanjuNo = new BaseTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoChuKu2.class);
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoChuKu2.class, this);
        jButton7.setAction(actionMap.get("save")); // NOI18N
        jButton7.setIcon(resourceMap.getIcon("jButton12.icon")); // NOI18N
        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton7.setName("jButton7"); // NOI18N
        jButton7.setOpaque(false);
        jToolBar1.add(jButton7);

        jButton14.setIcon(resourceMap.getIcon("jButton14.icon")); // NOI18N
        jButton14.setText(resourceMap.getString("jButton14.text")); // NOI18N
        jButton14.setFocusable(false);
        jButton14.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton14.setName("jButton14"); // NOI18N
        jButton14.setOpaque(false);
        jToolBar1.add(jButton14);

        jButton15.setIcon(resourceMap.getIcon("jButton15.icon")); // NOI18N
        jButton15.setText(resourceMap.getString("jButton15.text")); // NOI18N
        jButton15.setFocusable(false);
        jButton15.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton15.setName("jButton15"); // NOI18N
        jButton15.setOpaque(false);
        jToolBar1.add(jButton15);

        jButton16.setIcon(resourceMap.getIcon("jButton16.icon")); // NOI18N
        jButton16.setText(resourceMap.getString("jButton16.text")); // NOI18N
        jButton16.setFocusable(false);
        jButton16.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton16.setName("jButton16"); // NOI18N
        jButton16.setOpaque(false);
        jToolBar1.add(jButton16);

        jButton17.setIcon(resourceMap.getIcon("jButton17.icon")); // NOI18N
        jButton17.setText(resourceMap.getString("jButton17.text")); // NOI18N
        jButton17.setFocusable(false);
        jButton17.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton17.setName("jButton17"); // NOI18N
        jButton17.setOpaque(false);
        jToolBar1.add(jButton17);

        jButton18.setIcon(resourceMap.getIcon("jButton18.icon")); // NOI18N
        jButton18.setText(resourceMap.getString("jButton18.text")); // NOI18N
        jButton18.setFocusable(false);
        jButton18.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton18.setName("jButton18"); // NOI18N
        jButton18.setOpaque(false);
        jToolBar1.add(jButton18);

        jButton19.setAction(actionMap.get("exit")); // NOI18N
        jButton19.setIcon(resourceMap.getIcon("jButton19.icon")); // NOI18N
        jButton19.setText(resourceMap.getString("jButton19.text")); // NOI18N
        jButton19.setFocusable(false);
        jButton19.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton19.setName("jButton19"); // NOI18N
        jButton19.setOpaque(false);
        jToolBar1.add(jButton19);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextFieldzhidanDate.setEditable(false);
        jTextFieldzhidanDate.setText(resourceMap.getString("jTextFieldzhidanDate.text")); // NOI18N
        jTextFieldzhidanDate.setName("jTextFieldzhidanDate"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jTextFieldzhidanren.setEditable(false);
        jTextFieldzhidanren.setText(resourceMap.getString("jTextFieldzhidanren.text")); // NOI18N
        jTextFieldzhidanren.setName("jTextFieldzhidanren"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTableyimiao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "日期", "数量", "生产企业", "规格", "剂型", "批号", "有效期至", "批签合格证编号", "批准文号", "供应单位", "单价", "合价", "经办人", "对方单位经办人"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableyimiao.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableyimiao.setName("jTableyimiao"); // NOI18N
        jScrollPane1.setViewportView(jTableyimiao);
        if (jTableyimiao.getColumnModel().getColumnCount() > 0) {
            jTableyimiao.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title0")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title1")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title2")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable1.columnModel.title3")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title5")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title6")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(7).setPreferredWidth(100);
            jTableyimiao.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title7")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title8")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title9")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(10).setHeaderValue(resourceMap.getString("jTable1.columnModel.title12")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(11).setHeaderValue(resourceMap.getString("jTable1.columnModel.title13")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(12).setHeaderValue(resourceMap.getString("jTable1.columnModel.title10")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(13).setPreferredWidth(100);
            jTableyimiao.getColumnModel().getColumn(13).setHeaderValue(resourceMap.getString("jTable1.columnModel.title11")); // NOI18N
        }

        jTextFielddanjuNo.setName("jTextFielddanjuNo"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(2);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextFielddanjuNo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFielddanjuNo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(YiMiaoChuKu2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                YiMiaoChuKu2 dialog = new YiMiaoChuKu2(new javax.swing.JFrame(), true);
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

    @Action
    public Task save() throws ParseException {
        if (jTextFielddanjuNo.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入出库疫苗!");
            return null;
        }

        churukudan.setChurukuId(DanHao.getDanHao("YMCK"));
        churukudan.setZhidandate(dateformate.parse(jTextFieldzhidanDate.getText()));
        churukudan.setZhidanren(AssetClientApp.getSessionMap().getUsertb().getUserId());

        churukudan.setJingbanren(AssetClientApp.getSessionMap().getUsertb().getUserId());
        churukudan.setGongyingdanwei(1);
        churukudan.setQuantity(22);

        String serviceId = "yimiaochuku/add";
        return new CommUpdateTask<Churukudantb>(churukudan, serviceId) {

            @Override
            public void responseResult(ComResponse<Churukudantb> response) {
                if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                    JOptionPane.showMessageDialog(null, "提交成功！");
                    exit();
                } else {
                    AssetMessage.ERROR(response.getErrorMessage(), YiMiaoChuKu2.this);
                }
            }

        };

    }

    private class SaveTask extends org.jdesktop.application.Task<Object, Void> {
        SaveTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to SaveTask fields, here.
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
    public void exit() {
        this.dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableyimiao;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFielddanjuNo;
    private javax.swing.JTextField jTextFieldzhidanDate;
    private javax.swing.JTextField jTextFieldzhidanren;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
