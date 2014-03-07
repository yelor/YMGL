/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ckgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Baosuntb;
import com.jskj.asset.client.bean.entity.Yimiaobaosuntb;
import com.jskj.asset.client.bean.entity.YimiaobaosuntbFindEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.panel.ckgl.task.YimiaobaosunUpdateTask;
import com.jskj.asset.client.util.DanHao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author Administrator
 */
public class YiMiaoBaoSun extends javax.swing.JDialog {

    private Yimiaobaosuntb yimiaobaosun;
    private Baosuntb baosun;
    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private boolean isNew = true;
    private YimiaobaosuntbFindEntity yimiaobaosunEntity;

    /**
     * Creates new form GuDingZiChanRuKu
     */

    /**
     * Creates new form ymbs
     */
    public YiMiaoBaoSun(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        jTextFieldBaosunId.setText(DanHao.getDanHao("YMBS"));
        jTextFieldBaosunId.setEditable(false);
        jTextFieldzhidanDate.setText(dateformate.format(new Date()).toString());
        jTextFieldJingbanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFieldzhidanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());

//        经办人的popup
        ((BaseTextField) jTextFieldJingbanren).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "user";
            }

            public String getConditionSQL() {
                String sql = "";
                if (!jTextFieldJingbanren.getText().trim().equals("")) {
                    sql = "(user_name like \"%" + jTextFieldJingbanren.getText() + "%\"" + " or zujima like \"" + jTextFieldJingbanren.getText().toString().toLowerCase() + "%\")";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"userName", "用户名"}, {"departmentId", "部门编号"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    jTextFieldJingbanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
                    jTextFieldzhidanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());

                }
            }
        });
        //疫苗表中的内容
        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTableyimiao).createSingleEditModel(new String[][]{
            {"yimiaoId", "疫苗编号"}, {"yimiaoName", "疫苗名称"}, {"yimiaoGuige", "规格", "false"}, {"yimiaoJixing", "剂型", "false"},
            {"shengchanqiye", "生产企业", "false"}, {"unit", "单位", "false"}, {"youxiaoqi", "有效期至", "false"}, {"baosunQuantity", "数量", "true"}, {"price", "单价", "false"}, {"totalprice", "合价", "false"},
            {"xiaohuiAddr", "销毁地点", "true"}, {"xiaohuiDate", "销毁时间", "true"}, {"xiaohuiType", "销毁方式", "true"}, {"baosunReason", "报损原因", "true"}});

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
                    sql = "(yimiao_name like \"%" + newColumnObj.toString() + "%\"" + " or zujima like \"" + newColumnObj.toString().toLowerCase() + "%\")";
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
                    Object price = bindedMap.get("stockpilePrice");
                    Object youxiaoqi = bindedMap.get("youxiaodate");
                    

                    editTable.insertValue(0, yimiaoId);
                    editTable.insertValue(1, yimiaoName);
                    editTable.insertValue(2, yimiaoGuige);
                    editTable.insertValue(3, yimiaoJixing);
                    editTable.insertValue(4, shengchanqiye);
                    editTable.insertValue(5, unit);
                    editTable.insertValue(6, youxiaoqi);
                    editTable.insertValue(8, price);
                    editTable.insertValue(11, dateformate.format(new Date()).toString());
                    

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

        jLabel5 = new javax.swing.JLabel();
        jTextFieldCangku = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldzhidanDate = new javax.swing.JTextField();
        jTextFieldzhidanren = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldBaosunId = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableyimiao = new BaseTable(null);
        jLabel2 = new javax.swing.JLabel();
        jTextFieldJingbanren = new BaseTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoBaoSun.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jTextFieldCangku.setName("jTextFieldCangku"); // NOI18N

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoBaoSun.class, this);
        jButton2.setAction(actionMap.get("submitForm")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setOpaque(false);
        jToolBar1.add(jButton2);

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

        jButton5.setIcon(resourceMap.getIcon("jButton5.icon")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton5.setName("jButton5"); // NOI18N
        jButton5.setOpaque(false);
        jToolBar1.add(jButton5);

        jButton8.setAction(actionMap.get("exit")); // NOI18N
        jButton8.setIcon(resourceMap.getIcon("jButton8.icon")); // NOI18N
        jButton8.setText(resourceMap.getString("jButton8.text")); // NOI18N
        jButton8.setBorderPainted(false);
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton8.setName("jButton8"); // NOI18N
        jButton8.setOpaque(false);
        jToolBar1.add(jButton8);

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextFieldzhidanDate.setEditable(false);
        jTextFieldzhidanDate.setText(resourceMap.getString("jTextFieldzhidanDate.text")); // NOI18N
        jTextFieldzhidanDate.setName("jTextFieldzhidanDate"); // NOI18N
        jTextFieldzhidanDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldzhidanDateActionPerformed(evt);
            }
        });

        jTextFieldzhidanren.setName("jTextFieldzhidanren"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextFieldBaosunId.setEditable(false);
        jTextFieldBaosunId.setName("jTextFieldBaosunId"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTableyimiao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "疫苗编号", "疫苗名称", "规格", "剂型", "生产企业", "批号", "有效期至", "单位", "单价", "数量", "合价", "销毁地点", "销毁时间", "销毁方式", "报损原因"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableyimiao.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableyimiao.setName("jTableyimiao"); // NOI18N
        jScrollPane2.setViewportView(jTableyimiao);
        if (jTableyimiao.getColumnModel().getColumnCount() > 0) {
            jTableyimiao.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title0")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title1")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title2")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title14")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable1.columnModel.title3")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTable1.columnModel.title5")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title7")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTable1.columnModel.title6")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("jTable1.columnModel.title8")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(10).setHeaderValue(resourceMap.getString("jTable1.columnModel.title9")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(11).setHeaderValue(resourceMap.getString("jTable1.columnModel.title10")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(12).setHeaderValue(resourceMap.getString("jTable1.columnModel.title11")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(13).setHeaderValue(resourceMap.getString("jTable1.columnModel.title12")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(14).setHeaderValue(resourceMap.getString("jTable1.columnModel.title13")); // NOI18N
        }

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextFieldJingbanren.setEditable(false);
        jTextFieldJingbanren.setName("jTextFieldJingbanren"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldBaosunId, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldJingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldCangku, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldBaosunId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldJingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(40, 40, 40)
                            .addComponent(jTextFieldCangku, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel5))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            java.util.logging.Logger.getLogger(YiMiaoBaoSun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoBaoSun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoBaoSun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoBaoSun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                YiMiaoBaoSun dialog = new YiMiaoBaoSun(new javax.swing.JFrame(), true);
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
            this.setTitle("Ⅱ类疫苗销售单");
            baosun = new Baosuntb();
            yimiaobaosun = new Yimiaobaosuntb();
        } else {
            this.setTitle("Ⅱ类疫苗销售单");
        }
    }

    public void setUpdatedData(Yimiaobaosuntb yimiaobaosun) {
        if (yimiaobaosun == null) {
            return;
        }
        this.yimiaobaosun = yimiaobaosun;
        jTextFieldBaosunId.setText((yimiaobaosun.getBaosunId()).toString());

    }

    @Action
    public Task submitForm() throws ParseException {
        if (jTextFieldzhidanDate.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入制单日期!");
            return null;
        } else if (jTextFieldCangku.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入仓库!");
            return null;
        }
        yimiaobaosunEntity = new YimiaobaosuntbFindEntity();
        baosun = new Baosuntb();
        yimiaobaosun = new Yimiaobaosuntb();
        baosun.setBaosunId(jTextFieldBaosunId.getText());
        baosun.setBaosunDate(dateformate.parse(jTextFieldzhidanDate.getText()));
        baosun.setDeport("冻库");
        System.out.println(AssetClientApp.getSessionMap().getUsertb().getUserId());
        baosun.setZhidanren(AssetClientApp.getSessionMap().getUsertb().getUserName());
        baosun.setJingbanren(AssetClientApp.getSessionMap().getUsertb().getUserName());

        List<Yimiaobaosuntb> list = new ArrayList<Yimiaobaosuntb>();
        for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
            BaseTable yimiaotable = ((BaseTable) jTableyimiao);
            yimiaobaosun.setBaosunId(jTextFieldBaosunId.getText());
            yimiaobaosun.setKucunId(Integer.parseInt(yimiaotable.getValue(i, "yimiaoId").toString()));
            yimiaobaosun.setQuantity(Integer.parseInt(yimiaotable.getValue(i, "baosunQuantity").toString()));
            yimiaobaosun.setXiaohuiaddr((String) yimiaotable.getValue(i, "xiaohuiAddr"));
            yimiaobaosun.setXiaohuidate(dateformate.parse(jTextFieldzhidanDate.getText()));
            yimiaobaosun.setXiaohuireason((String) yimiaotable.getValue(i, "baosunReason"));
            yimiaobaosun.setXiaohuitype((String) yimiaotable.getValue(i, "xiaohuiType"));
            list.add(yimiaobaosun);
        }
        yimiaobaosunEntity.setBaosun(baosun);
        yimiaobaosunEntity.setResult(list);
        return new SubmitFormTask(yimiaobaosunEntity);

    }

    @Action
    public void exit() {
        this.dispose();
    }

    private class SubmitFormTask extends YimiaobaosunUpdateTask {

        SubmitFormTask(YimiaobaosuntbFindEntity yimiaobaosunEntiy) {
            super(yimiaobaosunEntiy, isNew ? YimiaobaosunUpdateTask.ENTITY_SAVE : YimiaobaosunUpdateTask.ENTITY_UPDATE);
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
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableyimiao;
    private javax.swing.JTextField jTextFieldBaosunId;
    private javax.swing.JTextField jTextFieldCangku;
    private javax.swing.JTextField jTextFieldJingbanren;
    private javax.swing.JTextField jTextFieldzhidanDate;
    private javax.swing.JTextField jTextFieldzhidanren;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
