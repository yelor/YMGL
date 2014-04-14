/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ckgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Yimiaozuzhuangchaixie;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.layout.ws.CommUpdateTask;
import com.jskj.asset.client.util.DanHao;
import com.jskj.asset.client.util.DateChooser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author Administrator
 */
public class YiMiaoZuZhuangChaiXieJDialog extends javax.swing.JDialog {

    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    boolean zz;
    private Yimiaozuzhuangchaixie yimiaozuzhuangchaixie;

    /**
     * Creates new form ymzzcx
     */
    public YiMiaoZuZhuangChaiXieJDialog(java.awt.Frame parent, boolean modal, boolean zzz) {
        super(parent, modal);
        init();
        initComponents();
        if (zzz == true) {
            setZz();
            jTextFieldDanjuId.setText(DanHao.getDanHao("YMZZ"));
            jTextFieldDanjuId.setEditable(false);
        } else {
            setCx();
            jTextFieldDanjuId.setText(DanHao.getDanHao("YMCX"));
            jTextFieldDanjuId.setEditable(false);
        }
        jTextFieldzhidanDate.setText(dateformate.format(new Date()).toString());
        jTextFieldjingbanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFieldzhidanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFieldbumen.setText(AssetClientApp.getSessionMap().getDepartment().getDepartmentName());

        //疫苗表1中的内容
        final BaseTable.SingleEditRowTable editTable1 = ((BaseTable) jTableyimiao1).createSingleEditModel(new String[][]{
            {"yimiaoId", "疫苗编号"}, {"yimiaoName", "疫苗名称", "true"}, {"yimiaoGuige", "规格", "false"}, {"yimiaoJixing", "剂型", "false"}, {"cangku", "仓库", "true"},
            {"shengchanqiye", "生产企业", "false"}, {"unit", "单位", "false"}, {"quantity", "数量", "true"}, {"remark", "备注", "false"}});

        editTable1.registerPopup(1, new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addkucunyimiao";
            }

            public String getConditionSQL() {
                int selectedColumn = jTableyimiao1.getSelectedColumn();
                int selectedRow = jTableyimiao1.getSelectedRow();
                Object newColumnObj = jTableyimiao1.getValueAt(selectedRow, selectedColumn);
                String sql = "";
                if (newColumnObj instanceof String && !newColumnObj.toString().trim().equals("")) {
                    sql = "(yimiao_name like \"%" + newColumnObj.toString() + "%\"" + " or zujima like \"%" + newColumnObj.toString().toLowerCase() + "%\")";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"yimiaoId", "疫苗编号"}, {"yimiao.yimiaoName", "疫苗名称"}, {"yimiao.yimiaoGuige", "规格"},
                {"yimiao.yimiaoJixing", "剂型"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object yimiaomap = bindedMap.get("yimiao");
                    HashMap yimiao = (HashMap) yimiaomap;
                    Object yimiaoId = bindedMap.get("yimiaoId");
                    Object yimiaoName = yimiao.get("yimiaoName");
                    Object yimiaoGuige = yimiao.get("yimiaoGuige");
                    Object yimiaoJixing = yimiao.get("yimiaoJixing");
                    Object shengchanqiye = yimiao.get("yimiaoShengchanqiye");
                    Object unit = yimiao.get("unitId");

                    editTable1.insertValue(0, yimiaoId);
                    editTable1.insertValue(1, yimiaoName);
                    editTable1.insertValue(2, yimiaoGuige);
                    editTable1.insertValue(3, yimiaoJixing);
                    editTable1.insertValue(5, shengchanqiye);
                    editTable1.insertValue(6, unit);

                }

            }
        });

        //疫苗表2中的内容
        final BaseTable.SingleEditRowTable editTable2 = ((BaseTable) jTableyimiao2).createSingleEditModel(new String[][]{
            {"yimiaoId", "疫苗编号"}, {"yimiaoName", "疫苗名称", "true"}, {"yimiaoGuige", "规格", "false"}, {"yimiaoJixing", "剂型", "false"}, {"cangku", "仓库", "true"},
            {"shengchanqiye", "生产企业", "false"}, {"unit", "单位", "false"}, {"quantity", "数量", "true"}, {"remark", "备注", "false"}});

        editTable2.registerPopup(1, new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addyimiao";
            }

            public String getConditionSQL() {
                int selectedColumn = jTableyimiao2.getSelectedColumn();
                int selectedRow = jTableyimiao2.getSelectedRow();
                Object newColumnObj = jTableyimiao2.getValueAt(selectedRow, selectedColumn);
                String sql = "";
                if (newColumnObj instanceof String && !newColumnObj.toString().trim().equals("")) {
                    sql = "(yimiao_name like \"%" + newColumnObj.toString() + "%\"" + " or zujima like \"%" + newColumnObj.toString().toLowerCase() + "%\")";
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

                    editTable2.insertValue(0, yimiaoId);
                    editTable2.insertValue(1, yimiaoName);
                    editTable2.insertValue(2, yimiaoGuige);
                    editTable2.insertValue(3, yimiaoJixing);
                    editTable2.insertValue(5, shengchanqiye);
                    editTable2.insertValue(6, unit);

                }

            }
        });
    }

    public void setZz() {
        setTitle("疫苗组装");
        jLabel16.setText("组装费用");
        yimiaozuzhuangchaixie = new Yimiaozuzhuangchaixie();
        zz = true;
    }

    public void setCx() {
        setTitle("疫苗拆卸");
        jLabel16.setText("拆卸费用");
        yimiaozuzhuangchaixie = new Yimiaozuzhuangchaixie();
        zz = false;
    }

    DateChooser dateChooser1;
    JTextField regTextField;

    private void init() {
        regTextField = new JTextField();
        dateChooser1 = DateChooser.getInstance("yyyy-MM-dd");
        dateChooser1.register(regTextField);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField9 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jToolBar3 = new javax.swing.JToolBar();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldjingbanren = new javax.swing.JTextField();
        jTextFieldDanjuId = new javax.swing.JTextField();
        jTextFieldzhidanren = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldzuzhuangfeiyong = new javax.swing.JTextField();
        jTextFieldzhidanDate = regTextField;
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableyimiao1 = new BaseTable(null);
        jLabel19 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaRemark = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextFieldfeiyongType = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jTextFieldbumen = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableyimiao2 = new BaseTable(null);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoZuZhuangChaiXieJDialog.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextField10.setName("jTextField10"); // NOI18N

        jTextField2.setName("jTextField2"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoZuZhuangChaiXieJDialog.class, this);
        jButton1.setAction(actionMap.get("quit")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jTextField9.setName("jTextField9"); // NOI18N

        jTextField1.setName("jTextField1"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jTextField7.setName("jTextField7"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextField8.setName("jTextField8"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jTextField3.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField3.setName("jTextField3"); // NOI18N

        jTextField4.setName("jTextField4"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
                "疫苗编码", "资产名称", "条形码", "规格型号", "仓库", "辅助属性", "批号", "单位", "数量", "备注"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane2.setViewportView(jTable1);

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jTextField6.setName("jTextField6"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton2);

        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton3);

        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton4);

        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setName("jButton5"); // NOI18N
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton5);

        jTextField5.setName("jTextField5"); // NOI18N

        jToolBar3.setRollover(true);
        jToolBar3.setName("jToolBar3"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N
        setResizable(false);

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jTextFieldjingbanren.setEditable(false);
        jTextFieldjingbanren.setName("jTextFieldjingbanren"); // NOI18N

        jTextFieldDanjuId.setEditable(false);
        jTextFieldDanjuId.setName("jTextFieldDanjuId"); // NOI18N

        jTextFieldzhidanren.setEditable(false);
        jTextFieldzhidanren.setName("jTextFieldzhidanren"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jTextFieldzuzhuangfeiyong.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextFieldzuzhuangfeiyong.setName("jTextFieldzuzhuangfeiyong"); // NOI18N

        jTextFieldzhidanDate.setEditable(false);
        jTextFieldzhidanDate.setName("jTextFieldzhidanDate"); // NOI18N

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTableyimiao1.setModel(new javax.swing.table.DefaultTableModel(
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
                "疫苗编号", "疫苗名称", "规格", "剂型", "仓库", "辅助属性", "批号", "单位", "数量", "备注"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableyimiao1.setName("jTableyimiao1"); // NOI18N
        jScrollPane3.setViewportView(jTableyimiao1);
        if (jTableyimiao1.getColumnModel().getColumnCount() > 0) {
            jTableyimiao1.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTableyimiao1.columnModel.title0")); // NOI18N
            jTableyimiao1.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTableyimiao1.columnModel.title1")); // NOI18N
            jTableyimiao1.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable2.columnModel.title3")); // NOI18N
            jTableyimiao1.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable2.columnModel.title2")); // NOI18N
            jTableyimiao1.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTableyimiao1.columnModel.title4")); // NOI18N
            jTableyimiao1.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTableyimiao1.columnModel.title5")); // NOI18N
            jTableyimiao1.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTableyimiao1.columnModel.title6")); // NOI18N
            jTableyimiao1.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTableyimiao1.columnModel.title7")); // NOI18N
            jTableyimiao1.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTableyimiao1.columnModel.title8")); // NOI18N
            jTableyimiao1.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("jTableyimiao1.columnModel.title9")); // NOI18N
        }

        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jTextAreaRemark.setColumns(20);
        jTextAreaRemark.setRows(2);
        jTextAreaRemark.setName("jTextAreaRemark"); // NOI18N
        jScrollPane4.setViewportView(jTextAreaRemark);

        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N

        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N

        jTextFieldfeiyongType.setName("jTextFieldfeiyongType"); // NOI18N

        jLabel22.setText(resourceMap.getString("jLabel22.text")); // NOI18N
        jLabel22.setName("jLabel22"); // NOI18N

        jToolBar2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setBorderPainted(false);
        jToolBar2.setName("jToolBar2"); // NOI18N

        jButton7.setAction(actionMap.get("save")); // NOI18N
        jButton7.setIcon(resourceMap.getIcon("jButton7.icon")); // NOI18N
        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setBorderPainted(false);
        jButton7.setFocusPainted(false);
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton7.setName("jButton7"); // NOI18N
        jButton7.setOpaque(false);
        jToolBar2.add(jButton7);

        jButton8.setIcon(resourceMap.getIcon("jButton8.icon")); // NOI18N
        jButton8.setText(resourceMap.getString("jButton8.text")); // NOI18N
        jButton8.setBorderPainted(false);
        jButton8.setFocusPainted(false);
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton8.setName("jButton8"); // NOI18N
        jButton8.setOpaque(false);
        jToolBar2.add(jButton8);

        jButton11.setIcon(resourceMap.getIcon("jButton11.icon")); // NOI18N
        jButton11.setText(resourceMap.getString("jButton11.text")); // NOI18N
        jButton11.setBorderPainted(false);
        jButton11.setFocusPainted(false);
        jButton11.setFocusable(false);
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton11.setName("jButton11"); // NOI18N
        jButton11.setOpaque(false);
        jToolBar2.add(jButton11);

        jButton12.setAction(actionMap.get("exit")); // NOI18N
        jButton12.setIcon(resourceMap.getIcon("jButton12.icon")); // NOI18N
        jButton12.setText(resourceMap.getString("jButton12.text")); // NOI18N
        jButton12.setBorderPainted(false);
        jButton12.setFocusPainted(false);
        jButton12.setFocusable(false);
        jButton12.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton12.setName("jButton12"); // NOI18N
        jButton12.setOpaque(false);
        jToolBar2.add(jButton12);

        jTextFieldbumen.setEditable(false);
        jTextFieldbumen.setName("jTextFieldbumen"); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTableyimiao2.setBackground(resourceMap.getColor("jTableyimiao2.background")); // NOI18N
        jTableyimiao2.setModel(new javax.swing.table.DefaultTableModel(
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
                "疫苗编号", "疫苗名称", "规格", "剂型", "仓库", "辅助属性", "批号", "单位", "数量", "备注"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableyimiao2.setName("jTableyimiao2"); // NOI18N
        jTableyimiao2.setShowHorizontalLines(false);
        jTableyimiao2.setShowVerticalLines(false);
        jScrollPane5.setViewportView(jTableyimiao2);
        if (jTableyimiao2.getColumnModel().getColumnCount() > 0) {
            jTableyimiao2.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTableyimiao2.columnModel.title0")); // NOI18N
            jTableyimiao2.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTableyimiao2.columnModel.title1")); // NOI18N
            jTableyimiao2.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable3.columnModel.title3")); // NOI18N
            jTableyimiao2.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable3.columnModel.title2")); // NOI18N
            jTableyimiao2.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTableyimiao2.columnModel.title4")); // NOI18N
            jTableyimiao2.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTableyimiao2.columnModel.title5")); // NOI18N
            jTableyimiao2.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTableyimiao2.columnModel.title6")); // NOI18N
            jTableyimiao2.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTableyimiao2.columnModel.title7")); // NOI18N
            jTableyimiao2.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTableyimiao2.columnModel.title8")); // NOI18N
            jTableyimiao2.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("jTableyimiao2.columnModel.title9")); // NOI18N
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel16)
                            .addComponent(jLabel19)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldzuzhuangfeiyong, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldDanjuId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldfeiyongType, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel21)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldbumen, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)))
                    .addComponent(jScrollPane5))
                .addContainerGap())
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldDanjuId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldzuzhuangfeiyong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldbumen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldfeiyongType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            java.util.logging.Logger.getLogger(YiMiaoZuZhuangChaiXieJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoZuZhuangChaiXieJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoZuZhuangChaiXieJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoZuZhuangChaiXieJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                /*ymzzcx dialog = new ymzzcx(new javax.swing.JFrame(), true, zz);
                 dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                 @Override
                 public void windowClosing(java.awt.event.WindowEvent e) {
                 System.exit(0);
                 }
                 });
                 dialog.setVisible(true);*/
            }
        });
    }

    @Action
    public Task save() throws ParseException {
        jTableyimiao1.getCellEditor(jTableyimiao1.getSelectedRow(),
                jTableyimiao1.getSelectedColumn()).stopCellEditing();
        jTableyimiao2.getCellEditor(jTableyimiao2.getSelectedRow(),
                jTableyimiao2.getSelectedColumn()).stopCellEditing();
        if (jTextFieldzuzhuangfeiyong.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入组装费用!");
            return null;
        } else if (jTextFieldfeiyongType.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入费用类型!");
            return null;
        }

        yimiaozuzhuangchaixie.setZuzhuangchaixieId(jTextFieldDanjuId.getText());
        yimiaozuzhuangchaixie.setZhidandate(dateformate.parse(jTextFieldzhidanDate.getText()));
        yimiaozuzhuangchaixie.setJingbanrenId(AssetClientApp.getSessionMap().getUsertb().getUserId());
        yimiaozuzhuangchaixie.setZuzhuangfeiyong(Double.parseDouble(jTextFieldzuzhuangfeiyong.getText()));
        yimiaozuzhuangchaixie.setFeiyongtype(jTextFieldfeiyongType.getText());
        yimiaozuzhuangchaixie.setRemark(jTextAreaRemark.getText());
        BaseTable yimiaotable1 = ((BaseTable) jTableyimiao1);
        BaseTable yimiaotable2 = ((BaseTable) jTableyimiao2);
        yimiaozuzhuangchaixie.setYimiao1Id(Integer.parseInt(yimiaotable1.getValue(0, "yimiaoId").toString()));
        if (((String) yimiaotable1.getValue(0, "cangku")).trim().equals("")) {
            AssetMessage.ERRORSYS("请输入仓库1!");
            return null;
        }
        yimiaozuzhuangchaixie.setCangku1((String) yimiaotable1.getValue(0, "cangku"));
        System.out.println(yimiaotable1.getValue(0, "quantity"));
        yimiaozuzhuangchaixie.setQuantity1(Integer.parseInt((String) yimiaotable1.getValue(0, "quantity")));
        yimiaozuzhuangchaixie.setYimiao2Id(Integer.parseInt(yimiaotable2.getValue(0, "yimiaoId").toString()));
        if (((String) yimiaotable2.getValue(0, "cangku")).trim().equals("")) {
            AssetMessage.ERRORSYS("请输入仓库2!");
            return null;
        }
        yimiaozuzhuangchaixie.setCangku2((String) yimiaotable2.getValue(0, "cangku"));
        yimiaozuzhuangchaixie.setQuantity2(Integer.parseInt((String) yimiaotable2.getValue(0, "quantity")));

        String serviceId = "yimiaozuzhuangchaixie/add";
        if (yimiaozuzhuangchaixie.getId() != null && yimiaozuzhuangchaixie.getId() > 0) {
            serviceId = "yimiaozuzhuangchaixie/update";
        }
        return new CommUpdateTask<Yimiaozuzhuangchaixie>(yimiaozuzhuangchaixie, serviceId) {

            @Override
            public void responseResult(ComResponse<Yimiaozuzhuangchaixie> response) {
                if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                    JOptionPane.showMessageDialog(null, "提交成功！");
                    exit();
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    YiMiaoZuZhuangChaiXieJDialog ymzzcx = new YiMiaoZuZhuangChaiXieJDialog(new javax.swing.JFrame(), true, zz);
                    ymzzcx.setLocationRelativeTo(mainFrame);
                    AssetClientApp.getApplication().show(ymzzcx);
                } else {
                    AssetMessage.ERROR(response.getErrorMessage(), YiMiaoZuZhuangChaiXieJDialog.this);
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
    public void exit() {
        this.dispose();
    }

    @Action
    public void switchZzCx() {
        if (zz) {
            setCx();
        } else {
            setZz();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableyimiao1;
    private javax.swing.JTable jTableyimiao2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextAreaRemark;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField jTextFieldDanjuId;
    private javax.swing.JTextField jTextFieldbumen;
    private javax.swing.JTextField jTextFieldfeiyongType;
    private javax.swing.JTextField jTextFieldjingbanren;
    private javax.swing.JTextField jTextFieldzhidanDate;
    private javax.swing.JTextField jTextFieldzhidanren;
    private javax.swing.JTextField jTextFieldzuzhuangfeiyong;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    // End of variables declaration//GEN-END:variables
}
