/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ckgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Churukudantb;
import com.jskj.asset.client.bean.entity.Churukudanyimiaoliebiaotb;
import com.jskj.asset.client.bean.entity.YimiaochurukuEntity;
import com.jskj.asset.client.bean.entity.YimiaoshenqingliebiaoEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.layout.ws.CommFindEntity;
import com.jskj.asset.client.layout.ws.CommUpdateTask;
import com.jskj.asset.client.panel.ymgl.task.CancelYimiaoDengji;
import com.jskj.asset.client.panel.ymgl.task.WeidengjiyimiaoTask;
import static com.jskj.asset.client.panel.ymgl.task.WeidengjiyimiaoTask.logger;
import com.jskj.asset.client.util.DanHao;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author Administrator
 */
public class YiMiaoRuKu1JDialog extends BaseDialog {

    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat riqiformate = new SimpleDateFormat("yyyy-MM-dd");
    private Churukudantb churukudan;
    private List<Churukudanyimiaoliebiaotb> bindedMapyimiaoliebiaoList = new ArrayList<Churukudanyimiaoliebiaotb>();
    private List<YimiaoshenqingliebiaoEntity> list;

    /**
     * Creates new form ymcrk1
     */
    public YiMiaoRuKu1JDialog() {
        super();
        initComponents();
        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }

            @Override
            public void windowClosed(WindowEvent e) {
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

        churukudan = new Churukudantb();
        jTextFielddanjuNo.setText(DanHao.getDanHao("YMRK"));
        jTextFielddanjuNo.setEditable(false);
        jTextFieldzhidanDate.setText(dateformate.format(new Date()).toString());
        jTextFieldjingbanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());

        //库房的popup
        ((BaseTextField) jTextFieldkufang).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "cangku/finddepot";
            }

            public String getConditionSQL() {
                String sql = "";
                if (!jTextFieldkufang.getText().trim().equals("")) {
                    sql = "(depot_name like \"%" + jTextFieldkufang.getText() + "%\"" + " or zujima like \"%" + jTextFieldkufang.getText().trim().toLowerCase() + "%\")";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"depotId", "仓库编号"}, {"depotName", "仓库名称"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
//                    shenqingdan.setSupplierId((Integer) (bindedMap.get("supplierId")));
                    jTextFieldkufang.setText(bindedMap.get("depotName") == null ? "" : bindedMap.get("depotName").toString());
                }
            }
        });

        //疫苗表中的内容
        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTableyimiao).createSingleEditModel(new String[][]{
            {"yimiaoId", "疫苗编号", "false"}, {"yimiaoName", "疫苗名称", "true"}, {"source", "国产/出口", "false"}, {"tongguandanNo", "进口通关单编号", "false"}, {"quantity", "数量", "true"}, {"yimiaoGuige", "规格", "false"}, {"yimiaoJixing", "剂型", "false"},
            {"yimiaoShengchanqiye", "生产企业", "false"}, {"pihao", "批号", "false"}, {"youxiaodate", "有效期", "false"}, {"unitId", "单位", "false"},
            {"piqianfaNo", "批签发合格证编号", "false"}, {"yimiaoPizhunwenhao", "批准文号", "true"},
            {"jingbanren", "经办人", "true"}, {"gongyingdanwei", "供应单位", "true"}, {"duifangjingbanren", "对方经办人", "true"}});

        editTable.registerPopup(1, new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addyanshouyimiao";
            }

            public String getConditionSQL() {
                int selectedColumn = jTableyimiao.getSelectedColumn();
                int selectedRow = jTableyimiao.getSelectedRow();
                Object newColumnObj = jTableyimiao.getValueAt(selectedRow, selectedColumn);
                String sql = "";
                if (newColumnObj instanceof String && !newColumnObj.toString().trim().equals("")) {
                    sql += "xiangdan_id in (select distinct yimiaoshenqingdan.xiangdan_id from yimiaoshenqingdan,yimiao where yimiaoshenqingdan.danjuleixing_id =5 and yimiaoshenqingdan.is_completed = 1 and yimiaoshenqingdan.status = 2 and (yimiao.yimiao_name like \"%" + newColumnObj.toString() + "%\" or yimiao.zujima like \"%" + newColumnObj.toString().toLowerCase() + "%\")) ";
                } else {
                    sql += "xiangdan_id in (select distinct xiangdan_id from yimiaoshenqingdan where is_completed = 1 and status = 2 and danjuleixing_id =5)";
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
                    Object yimiaodengjimap = bindedMap.get("yimiaodengji");
                    HashMap yimiaodengji = (HashMap) yimiaodengjimap;
                    Object gongyingdanweimap = bindedMap.get("gongyingdanwei");
                    HashMap gongyingdanwei = (HashMap) gongyingdanweimap;

                    Object yimiaoId = yimiaoAll.get("yimiaoId");
                    Object yimiaoName = yimiaoAll.get("yimiaoName");
                    Object yimiaoGuige = yimiaoAll.get("yimiaoGuige");
                    Object yimiaoJixing = yimiaoAll.get("yimiaoJixing");
                    Object shengchanqiye = yimiaoAll.get("yimiaoShengchanqiye");
                    Object unit = yimiaoAll.get("unitId");
                    Object pihao;
                    try {
                        pihao = yimiaodengji.get("pihao");
                    } catch (Exception e) {
                        pihao = null;
                    }
                    Object source;
                    try {
                        source = yimiaodengji.get("source");
                    } catch (Exception e) {
                        source = null;
                    }
                    Object youxiaoqi;
                    try {
                        youxiaoqi = yimiaodengji.get("youxiaoqi");
                    } catch (Exception e) {
                        youxiaoqi = "";
                    }
                    Object piqianfahegezhenno;
                    try {
                        piqianfahegezhenno = yimiaodengji.get("piqianfahegezhenno");
                    } catch (Exception e) {
                        piqianfahegezhenno = null;
                    }
                    Object yimiaoPizhunwenhao = yimiaoAll.get("yimiaoPizhunwenhao");
                    Object tongguandanno;
                    try {
                        tongguandanno = yimiaodengji.get("tongguandanno");
                    } catch (Exception e) {
                        tongguandanno = null;
                    }
                    Object quantity = yimiaoshenqingdan.get("quantity");
                    Object gongyingdanweiName;
                    try {
                        gongyingdanweiName = gongyingdanwei.get("supplierName");
                    } catch (Exception e) {
                        gongyingdanweiName = "";
                    }
                    Object userName;
                    try {
                        userName = gongyingdanwei.get("supplierConstactperson");
                    } catch (Exception e) {
                        userName = "";
                    }

                    Churukudanyimiaoliebiaotb chukudan = new Churukudanyimiaoliebiaotb();
                    chukudan.setXiangdanId(Integer.parseInt((String) ("" + yimiaoshenqingdan.get("xiangdanId"))));
                    chukudan.setWanglaidanweiId(Integer.parseInt((String) ("" + gongyingdanwei.get("supplierId"))));
                    bindedMapyimiaoliebiaoList.add(chukudan);

                    editTable.insertValue(0, yimiaoId);
                    editTable.insertValue(1, yimiaoName);
                    editTable.insertValue(2, source);
                    editTable.insertValue(3, tongguandanno);
                    editTable.insertValue(4, quantity);
                    editTable.insertValue(5, yimiaoGuige);
                    editTable.insertValue(6, yimiaoJixing);
                    editTable.insertValue(7, shengchanqiye);
                    editTable.insertValue(8, pihao);
                    editTable.insertValue(9, youxiaoqi);
                    editTable.insertValue(10, unit);
                    editTable.insertValue(11, piqianfahegezhenno);
                    editTable.insertValue(12, yimiaoPizhunwenhao);
                    editTable.insertValue(13, AssetClientApp.getSessionMap().getUsertb().getUserName());
                    editTable.insertValue(14, gongyingdanweiName);
                    editTable.insertValue(15, userName);

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

        jToolBar1 = new javax.swing.JToolBar();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldzhidanDate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldjingbanren = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableyimiao = new BaseTable(null);
        jTextFielddanjuNo = new BaseTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldkufang = new BaseTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoRuKu1JDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoRuKu1JDialog.class, this);
        jButton7.setAction(actionMap.get("save")); // NOI18N
        jButton7.setIcon(resourceMap.getIcon("jButton7.icon")); // NOI18N
        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setBorderPainted(false);
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton7.setName("jButton7"); // NOI18N
        jButton7.setOpaque(false);
        jToolBar1.add(jButton7);

        jButton8.setIcon(resourceMap.getIcon("jButton8.icon")); // NOI18N
        jButton8.setText(resourceMap.getString("jButton8.text")); // NOI18N
        jButton8.setBorderPainted(false);
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton8.setName("jButton8"); // NOI18N
        jButton8.setOpaque(false);
        jToolBar1.add(jButton8);

        jButton11.setIcon(resourceMap.getIcon("jButton11.icon")); // NOI18N
        jButton11.setText(resourceMap.getString("jButton11.text")); // NOI18N
        jButton11.setBorderPainted(false);
        jButton11.setFocusable(false);
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton11.setName("jButton11"); // NOI18N
        jButton11.setOpaque(false);
        jToolBar1.add(jButton11);

        jButton12.setAction(actionMap.get("exit")); // NOI18N
        jButton12.setIcon(resourceMap.getIcon("jButton12.icon")); // NOI18N
        jButton12.setText(resourceMap.getString("jButton12.text")); // NOI18N
        jButton12.setBorderPainted(false);
        jButton12.setFocusable(false);
        jButton12.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton12.setName("jButton12"); // NOI18N
        jButton12.setOpaque(false);
        jToolBar1.add(jButton12);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextFieldzhidanDate.setEditable(false);
        jTextFieldzhidanDate.setText(resourceMap.getString("jTextFieldzhidanDate.text")); // NOI18N
        jTextFieldzhidanDate.setName("jTextFieldzhidanDate"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jTextFieldjingbanren.setEditable(false);
        jTextFieldjingbanren.setText(resourceMap.getString("jTextFieldjingbanren.text")); // NOI18N
        jTextFieldjingbanren.setName("jTextFieldjingbanren"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTableyimiao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "日期", "数量", "生产企业", "规格", "剂型", "批号", "有效期至", "批签合格证编号", "批准文号", "供应单位", "经办人", "对方单位经办人"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
            jTableyimiao.getColumnModel().getColumn(10).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title10")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(11).setPreferredWidth(100);
            jTableyimiao.getColumnModel().getColumn(11).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title11")); // NOI18N
        }

        jTextFielddanjuNo.setName("jTextFielddanjuNo"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(2);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane2.setViewportView(jTextArea1);

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextFieldkufang.setName("jTextFieldkufang"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFielddanjuNo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 438, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldkufang, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane2))))
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFielddanjuNo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldkufang, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            java.util.logging.Logger.getLogger(YiMiaoRuKu1JDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoRuKu1JDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoRuKu1JDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoRuKu1JDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                YiMiaoRuKu1JDialog dialog = new YiMiaoRuKu1JDialog();
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
        YimiaochurukuEntity yimiaorukuEntity = new YimiaochurukuEntity();

        churukudan.setChurukuId(DanHao.getDanHao("YMRK"));
        churukudan.setZhidandate(dateformate.parse(jTextFieldzhidanDate.getText()));
        if (jTextFieldkufang.getText().toString().trim().equals("")) {
            AssetMessage.ERRORSYS("请选择入库库房!");
            return null;
        }
        churukudan.setKufang(jTextFieldkufang.getText());
        churukudan.setZhidanren(AssetClientApp.getSessionMap().getUsertb().getUserId());
        yimiaorukuEntity.setChurukutb(churukudan);

        List<Churukudanyimiaoliebiaotb> list = new ArrayList<Churukudanyimiaoliebiaotb>();
        for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
            BaseTable yimiaotable = ((BaseTable) jTableyimiao);
            Churukudanyimiaoliebiaotb yimiaoliebiao = new Churukudanyimiaoliebiaotb();
            if (yimiaotable.getValue(i, "yimiaoName").toString().trim().equals("")) {
                AssetMessage.ERRORSYS("请输入入库疫苗!");
                return null;
            }
            yimiaoliebiao.setYimiaoId(Integer.parseInt("" + yimiaotable.getValue(i, "yimiaoId").toString()));
            yimiaoliebiao.setPihao((String) ("" + yimiaotable.getValue(i, "pihao")));
            yimiaoliebiao.setPiqianfahegeno((String) yimiaotable.getValue(i, "piqianfaNo"));
            yimiaoliebiao.setQuantity(Integer.parseInt((String) ("" + yimiaotable.getValue(i, "quantity"))));
            yimiaoliebiao.setSource((String) ("" + yimiaotable.getValue(i, "source")));
            yimiaoliebiao.setTongguandanno((String) ("" + yimiaotable.getValue(i, "tongguandanNo")));
            if (yimiaotable.getValue(i, "youxiaodate").toString().trim().equals("")) {
                yimiaoliebiao.setYouxiaoqi(null);
            } else {
                yimiaoliebiao.setYouxiaoqi(riqiformate.parse((String) ("" + yimiaotable.getValue(i, "youxiaodate"))));
            }
            yimiaoliebiao.setTotalprice(0f);
            yimiaoliebiao.setWanglaidanweiId(bindedMapyimiaoliebiaoList.get(i).getWanglaidanweiId());
            yimiaoliebiao.setXiangdanId(bindedMapyimiaoliebiaoList.get(i).getXiangdanId());
            list.add(yimiaoliebiao);
        }
        yimiaorukuEntity.setResult(list);

        String serviceId = "yimiaoruku/add";
        return new CommUpdateTask<YimiaochurukuEntity>(yimiaorukuEntity, serviceId) {

            @Override
            public void responseResult(ComResponse<YimiaochurukuEntity> response) {
                if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                    JOptionPane.showMessageDialog(null, "提交成功！");
                    exit();
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    YiMiaoRuKu1JDialog ymrk1 = new YiMiaoRuKu1JDialog();
                    ymrk1.setLocationRelativeTo(mainFrame);
                    AssetClientApp.getApplication().show(ymrk1);
                } else {
                    AssetMessage.ERROR(response.getErrorMessage(), YiMiaoRuKu1JDialog.this);
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
        String sql = " shenqingdan_id like \"YMLQ%\" and is_completed = 1 and status = 2";
        new CloseTask(sql).execute();
    }

    public void close() {
        this.dispose();
    }

    private class CloseTask extends WeidengjiyimiaoTask {

        public CloseTask(String sql) {
            super(sql, "普通");
        }

        @Override
        public void responseResult(CommFindEntity<YimiaoshenqingliebiaoEntity> response) {

            logger.debug("get current size:" + response.getResult().size());
            list = response.getResult();
            if (list != null && list.size() > 0) {
                StringBuilder string = new StringBuilder();
                for (YimiaoshenqingliebiaoEntity yimiao : list) {
                    string.append("单据").append(yimiao.getYimiaoshenqingdan().getShenqingdanId()).append("有未登记项【")
                            .append(yimiao.getYimiao().getYimiaoName()).append("】\n");
                }
                string.append("是否继续入库？选“否”或“取消”会要求输入原因，并不再入库以上所有疫苗");
                int result = AssetMessage.showConfirmDialog(null, string.toString(), "确认", JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                    return;
                }
                for (YimiaoshenqingliebiaoEntity lb : list) {
                    String reason = null;
                    while (reason == null || reason.isEmpty()) {
                        reason = AssetMessage.showInputDialog(null, "请输入取消入库疫苗【"
                                + lb.getYimiao().getYimiaoName() + "】的理由(必输)：");
                    }
                    lb.getYimiaoshenqingdan().setReason("【入库】" + reason);
                }
                new YiMiaoRuKu1JDialog.Cancel(list).execute();
            }
            close();
        }

    }

    private class Cancel extends CancelYimiaoDengji {

        public Cancel(List<YimiaoshenqingliebiaoEntity> zc) {
            super(zc);
        }

        @Override
        public void onSucceeded(Object object) {
            if (object instanceof Exception) {
                Exception e = (Exception) object;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            close();
            JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
            YiMiaoRuKu1JDialog yimiaoruku = new YiMiaoRuKu1JDialog();
            yimiaoruku.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(yimiaoruku);
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableyimiao;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFielddanjuNo;
    private javax.swing.JTextField jTextFieldjingbanren;
    private javax.swing.JTextField jTextFieldkufang;
    private javax.swing.JTextField jTextFieldzhidanDate;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
