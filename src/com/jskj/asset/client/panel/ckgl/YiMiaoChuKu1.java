/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ckgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Churukudantb;
import com.jskj.asset.client.bean.entity.StockpiletbAll;
import com.jskj.asset.client.bean.entity.StockpiletbFindEntity;
import com.jskj.asset.client.bean.entity.YanshouyimiaoEntity;
import com.jskj.asset.client.bean.entity.YanshouyimiaoFindEntity;
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
public class YiMiaoChuKu1 extends javax.swing.JDialog {

    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd");
    private BindTableHelper<StockpiletbAll> bindTable;
    private List<StockpiletbAll> chukuyimiaolist;
    private Churukudantb churukudan;

    public YiMiaoChuKu1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        churukudan = new Churukudantb();

        jTextFieldzhidanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFieldzhidanDate.setText(dateformate.format(new Date()).toString());

        bindTable = new BindTableHelper<StockpiletbAll>(jTableyimiao, new ArrayList<StockpiletbAll>());
        bindTable.createTable(new String[][]{
            {"date", "日期", "true"}, {"quantity", "数量", "true"}, {"yimiao.yimiaoGuige", "规格", "false"}, {"yimiao.yimiaoJixing", "剂型", "false"},
            {"yimiao.yimiaoShengchanqiye", "生产企业", "false"}, {"pihao", "批号", "false"}, {"youxiaodate", "有效期", "false"}, {"yimiao.unitId", "单位", "false"},
            {"piqianfaNo", "批签发合格证编号", "false"}, {"yimiao.yimiaoPizhunwenhao", "批准文号", "true"},
            {"jingbanren", "经办人", "true"}, {"gongyingdanwei", "供应单位", "true"}, {"duifangjingbanren", "对方经办人", "true"}});
        bindTable.setColumnType(Date.class, 7);
        bindTable.refreshData(null);
        //        疫苗的popup
        ((BaseTextField) jTextFieldyimiaoName).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addkucunyimiao";
            }

            public String getConditionSQL() {
                String sql = "";
                if (!jTextFieldyimiaoName.getText().trim().equals("")) {
                    sql += "yimiao_name like \"%" + jTextFieldyimiaoName.getText() + "%\""
                            + "from yimiao where yimiao_id in (select distinct yimiao_id from stockpile where stockPile_price=0)";
                } else {
                    sql += "yimiao_id in (select distinct yimiao_id from stockpile where stockPile_price=0)";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"yimiaoId", "疫苗编号"}, {"yimiao.yimiaoName", "疫苗名称"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object yimiaomap = bindedMap.get("yimiao");
                    HashMap yimiao = (HashMap) yimiaomap;
                    jTextFieldyimiaoName.setText((String) (yimiao.get("yimiaoName") == null ? "" : yimiao.get("yimiaoName")));
                    jTextFieldsource.setText(bindedMap.get("source") == null ? "" : bindedMap.get("source").toString());
                    jTextFieldtongguandanNo.setText(bindedMap.get("jinkoutongguanno") == null ? "" : bindedMap.get("jinkoutongguanno").toString());
                    churukudan.setSource(bindedMap.get("source").toString());
                    churukudan.setTongguandanno(bindedMap.get("jinkoutongguanno").toString());
                    churukudan.setYouxiaoqi((Date) bindedMap.get("youxiaoqi"));

                    churukudan.setPiqianfahegeno((String) bindedMap.get("piqianfano"));
                    System.out.println(yimiaomap);
                    System.out.println(bindedMap);
//                    System.out.println(yimiao.get("yimiaoName"));
                    YiMiaotb yimiaoentity = new YiMiaotb();
                    yimiaoentity.setSupplierId((Integer) yimiao.get("supplierId"));
                    yimiaoentity.setUnitId((String) yimiao.get("unitId"));
                    yimiaoentity.setYimiaoChufangbiaoji((String) yimiao.get("yimiaoChufangbiaoji"));
                    yimiaoentity.setYimiaoFuzhuunit((String) yimiao.get("yimiaoFuzhuunit"));
                    yimiaoentity.setYimiaoGuige((String) yimiao.get("yimiaoGuige"));
                    yimiaoentity.setYimiaoId((Integer) yimiao.get("yimiaoId"));
                    churukudan.setYimiaoId((Integer) yimiao.get("yimiaoId"));
                    yimiaoentity.setYimiaoJixing((String) yimiao.get("yimiaoJixing"));
                    yimiaoentity.setYimiaoMorencangku((Integer) yimiao.get("yimiaoMorencangku"));
                    yimiaoentity.setYimiaoName((String) yimiao.get("yimiaoName"));
                    yimiaoentity.setYimiaoPicture((String) yimiao.get("yimiaoPicture"));
                    yimiaoentity.setYimiaoPizhunwenhao((String) yimiao.get("yimiaoPizhunwenhao"));
                    yimiaoentity.setYimiaoRemark((String) yimiao.get("yimiaoRemark"));
                    yimiaoentity.setYimiaoShengchanqiye((String) yimiao.get("yimiaoShengchanqiye"));
                    yimiaoentity.setYimiaoStockdown((Integer) yimiao.get("yimiaoStockdown"));
                    yimiaoentity.setYimiaoStockup((Integer) yimiao.get("yimiaoStockup"));
                    yimiaoentity.setYimiaoTiaoxingma((String) yimiao.get("yimiaoTiaoxingma"));
                    yimiaoentity.setYimiaoType((String) yimiao.get("yimiaoType"));
//                    yimiaoentity.setYimiaoYushoujia((Float) yimiao.get("yimiaoYushoujia"));
                    StockpiletbAll chukumiao = new StockpiletbAll();
                    chukumiao.setYimiao(yimiaoentity);
                    chukumiao.setPiqianfano((String) bindedMap.get("piqianfano"));
                    try {
                        chukumiao.setYouxiaodate(dateformate.parse((String) bindedMap.get("youxiaodate")));
                    } catch (ParseException ex) {
                        Logger.getLogger(YiMiaoChuKu2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(bindedMap.get("stockpilePrice"));
//                    chukumiao.setStockpilePrice(Float.parseFloat((String) bindedMap.get("stockpilePrice")));

                    chukuyimiaolist = new ArrayList<StockpiletbAll>();
                    chukuyimiaolist.add(chukumiao);
                    bindTable.refreshData(chukuyimiaolist);
                }
            }

        }
        );
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
        jButton9 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldsource = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldtongguandanNo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableyimiao = new BaseTable(null);
        jLabel4 = new javax.swing.JLabel();
        jTextFieldzhidanren = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldzhidanDate = new javax.swing.JTextField();
        jTextFieldyimiaoName = new BaseTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoChuKu1.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoChuKu1.class, this);
        jButton7.setAction(actionMap.get("save")); // NOI18N
        jButton7.setIcon(resourceMap.getIcon("jButton7.icon")); // NOI18N
        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton7.setName("jButton7"); // NOI18N
        jButton7.setOpaque(false);
        jToolBar1.add(jButton7);

        jButton9.setIcon(resourceMap.getIcon("jButton9.icon")); // NOI18N
        jButton9.setText(resourceMap.getString("jButton9.text")); // NOI18N
        jButton9.setFocusable(false);
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton9.setName("jButton9"); // NOI18N
        jButton9.setOpaque(false);
        jToolBar1.add(jButton9);

        jButton8.setIcon(resourceMap.getIcon("jButton8.icon")); // NOI18N
        jButton8.setText(resourceMap.getString("jButton8.text")); // NOI18N
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton8.setName("jButton8"); // NOI18N
        jButton8.setOpaque(false);
        jToolBar1.add(jButton8);

        jButton11.setIcon(resourceMap.getIcon("jButton11.icon")); // NOI18N
        jButton11.setText(resourceMap.getString("jButton11.text")); // NOI18N
        jButton11.setFocusable(false);
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton11.setName("jButton11"); // NOI18N
        jButton11.setOpaque(false);
        jToolBar1.add(jButton11);

        jButton12.setAction(actionMap.get("exit")); // NOI18N
        jButton12.setIcon(resourceMap.getIcon("jButton12.icon")); // NOI18N
        jButton12.setText(resourceMap.getString("jButton12.text")); // NOI18N
        jButton12.setFocusable(false);
        jButton12.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton12.setName("jButton12"); // NOI18N
        jButton12.setOpaque(false);
        jToolBar1.add(jButton12);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextFieldsource.setText(resourceMap.getString("jTextFieldsource.text")); // NOI18N
        jTextFieldsource.setName("jTextFieldsource"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jTextFieldtongguandanNo.setText(resourceMap.getString("jTextFieldtongguandanNo.text")); // NOI18N
        jTextFieldtongguandanNo.setName("jTextFieldtongguandanNo"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTableyimiao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableyimiao.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableyimiao.setName("jTableyimiao"); // NOI18N
        jScrollPane1.setViewportView(jTableyimiao);

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextFieldzhidanren.setEditable(false);
        jTextFieldzhidanren.setText(resourceMap.getString("jTextFieldzhidanren.text")); // NOI18N
        jTextFieldzhidanren.setName("jTextFieldzhidanren"); // NOI18N
        jTextFieldzhidanren.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldzhidanrenActionPerformed(evt);
            }
        });

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jTextFieldzhidanDate.setEditable(false);
        jTextFieldzhidanDate.setText(resourceMap.getString("jTextFieldzhidanDate.text")); // NOI18N
        jTextFieldzhidanDate.setName("jTextFieldzhidanDate"); // NOI18N
        jTextFieldzhidanDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldzhidanDateActionPerformed(evt);
            }
        });

        jTextFieldyimiaoName.setName("jTextFieldyimiaoName"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldyimiaoName, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldsource, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldtongguandanNo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 107, Short.MAX_VALUE)))
                .addContainerGap())
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
                        .addComponent(jTextFieldyimiaoName, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                        .addComponent(jTextFieldtongguandanNo, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                    .addComponent(jTextFieldsource, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldzhidanrenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldzhidanrenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldzhidanrenActionPerformed

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
            java.util.logging.Logger.getLogger(YiMiaoChuKu1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                YiMiaoChuKu1 dialog = new YiMiaoChuKu1(new javax.swing.JFrame(), true);
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
        if (jTextFieldyimiaoName.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入出库疫苗!");
            return null;
        }

        churukudan.setChurukuId(DanHao.getDanHao("YMCK"));
        churukudan.setZhidandate(dateformate.parse(jTextFieldzhidanDate.getText()));
        churukudan.setZhidanren(AssetClientApp.getSessionMap().getUsertb().getUserId());

        churukudan.setJingbanren(AssetClientApp.getSessionMap().getUsertb().getUserId());
        churukudan.setGongyingdanwei(2);
        churukudan.setQuantity(22);

        String serviceId = "yimiaochuku/add";
        return new CommUpdateTask<Churukudantb>(churukudan, serviceId) {

            @Override
            public void responseResult(ComResponse<Churukudantb> response) {
                if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                    JOptionPane.showMessageDialog(null, "提交成功！");
                    exit();
                } else {
                    AssetMessage.ERROR(response.getErrorMessage(), YiMiaoChuKu1.this);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableyimiao;
    private javax.swing.JTextField jTextFieldsource;
    private javax.swing.JTextField jTextFieldtongguandanNo;
    private javax.swing.JTextField jTextFieldyimiaoName;
    private javax.swing.JTextField jTextFieldzhidanDate;
    private javax.swing.JTextField jTextFieldzhidanren;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
