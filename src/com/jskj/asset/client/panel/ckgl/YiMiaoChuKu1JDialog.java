/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ckgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Churukudantb;
import com.jskj.asset.client.bean.entity.Churukudanyimiaoliebiaotb;
import com.jskj.asset.client.bean.entity.Kehudanweitb;
import com.jskj.asset.client.bean.entity.Sale_detail_tb;
import com.jskj.asset.client.bean.entity.SaleyimiaoEntity;
import com.jskj.asset.client.bean.entity.YimiaochurukuEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.layout.ws.CommFindEntity;
import com.jskj.asset.client.layout.ws.CommUpdateTask;
import com.jskj.asset.client.panel.ckgl.task.CancelChuKu;
import com.jskj.asset.client.panel.ckgl.task.WeiChuKuYimiaoTask;
import static com.jskj.asset.client.panel.slgl.task.ShenQingTask.logger;
import com.jskj.asset.client.util.DanHao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.dynamicreports.report.exception.DRException;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author Administrator
 */
public class YiMiaoChuKu1JDialog extends BaseDialog {

    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat riqiformate = new SimpleDateFormat("yyyy-MM-dd");
    private Churukudantb churukudan;
    private List<SaleyimiaoEntity> list;
    private boolean isNew;
    private Map saledetailIdmap;
    private Map saleIdmap;
    private Map kehudanweiIdmap;

    public YiMiaoChuKu1JDialog() {
        super();
        initComponents();
        churukudan = new Churukudantb();

        jTextFielddanjuNo.setText(DanHao.getDanHao("YMCK"));
        jTextFielddanjuNo.setEditable(false);
        jTextFieldzhidanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFieldzhidanDate.setText(dateformate.format(new Date()).toString());

        saledetailIdmap = new HashMap();
        saleIdmap = new HashMap();
        kehudanweiIdmap = new HashMap();

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
            {"xiangdanId", "详单编号", "false"},{"stockpileId", "库存编号", "false"}, {"yimiaoName", "疫苗名称", "true"}, {"source", "国产/出口", "false"}, {"tongguandanNo", "进口通关单编号", "false"}, {"quantity", "数量", "true"}, {"yimiaoGuige", "规格", "false"}, {"yimiaoJixing", "剂型", "false"},
            {"yimiaoShengchanqiye", "生产企业", "false"}, {"pihao", "批号", "false"}, {"youxiaodate", "有效期", "false"}, {"unitId", "单位", "false"},
            {"piqianfaNo", "批签发合格证编号", "false"}, {"yimiaoPizhunwenhao", "批准文号", "true"},
            {"jingbanren", "经办人", "true"}, {"gongyingdanwei", "收货单位", "true"}, {"duifangjingbanren", "对方经办人", "true"}});
        editTable.registerPopup(2, new IPopupBuilder() {
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
                    sql += "sale_detail_id in (select distinct sale_detail.sale_detail_id from sale_detail,yimiao where sale_detail.is_completed = 1 and sale_detail.status = 0 and sale_detail.price=0 and (yimiao.yimiao_name like \"%" + newColumnObj.toString() + "%\" or yimiao.zujima like \"%" + newColumnObj.toString().toLowerCase() + "%\")) ";
                } else {
                    sql += "sale_detail_id in (select distinct sale_detail_id from sale_detail where is_completed = 1 and status = 0 and price=0 )";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"saletb.saleId", "源单单号"}, {"saletb.saleDate", "申报日期"}, {"yimiaoAll.yimiaoName", "疫苗名称"},
                {"yimiaoAll.yimiaoJixing", "剂型"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object yimiaomap = bindedMap.get("yimiaoAll");
                    Object stockpilemap = bindedMap.get("stockpile");
                    Object sale_detail_tbmap = bindedMap.get("sale_detail_tb");
                    Object kehudanweimap = bindedMap.get("kehudanwei");
                    HashMap yimiaoAll = (HashMap) yimiaomap;
                    HashMap stockpile = (HashMap) stockpilemap;
                    HashMap sale_detail_tb = (HashMap) sale_detail_tbmap;
                    HashMap kehudanwei = (HashMap) kehudanweimap;



                    Object xiangdanId = sale_detail_tb.get("saleDetailId");
                    Object saleId = sale_detail_tb.get("saleId");
                    Object kehudanweiId = kehudanwei.get("kehudanweiId");
                    Object yimiaoId = stockpile.get("stockpileId");
                    Object yimiaoName = yimiaoAll.get("yimiaoName");
                    Object yimiaoGuige = yimiaoAll.get("yimiaoGuige");
                    Object yimiaoJixing = yimiaoAll.get("yimiaoJixing");
                    Object shengchanqiye = yimiaoAll.get("yimiaoShengchanqiye");
                    Object unit = yimiaoAll.get("unitId");
                    Object pihao = stockpile.get("pihao");
                    Object youxiaoqi = stockpile.get("youxiaodate").toString().subSequence(0, 10);
                    Object piqianfaNo = stockpile.get("piqianfano");
                    Object pizhunwenhao = yimiaoAll.get("yimiaoPizhunwenhao");
                    Object source = stockpile.get("source");
                    Object tongguandanNo = yimiaoAll.get("jinkoutongguanno");
                    Object quantity = sale_detail_tb.get("quantity");
                    Object kehudanweiName = kehudanwei.get("kehudanweiName");
                    Object duifangjinbangren = kehudanwei.get("kehudanweiConstactperson");

                    editTable.insertValue(0, xiangdanId);
                    editTable.insertValue(1, yimiaoId);
                    editTable.insertValue(2, yimiaoName);
                    editTable.insertValue(3, source);
                    editTable.insertValue(4, tongguandanNo);
                    editTable.insertValue(5, quantity);
                    editTable.insertValue(6, yimiaoGuige);
                    editTable.insertValue(7, yimiaoJixing);
                    editTable.insertValue(8, shengchanqiye);
                    editTable.insertValue(9, pihao);
                    editTable.insertValue(10, youxiaoqi);
                    editTable.insertValue(11, unit);
                    editTable.insertValue(12, piqianfaNo);
                    editTable.insertValue(13, pizhunwenhao);
                    editTable.insertValue(14, AssetClientApp.getSessionMap().getUsertb().getUserName());
                    editTable.insertValue(15, kehudanweiName);
                    editTable.insertValue(16, duifangjinbangren);

                    
                    saledetailIdmap.put(xiangdanId, xiangdanId);
                    saleIdmap.put(xiangdanId, saleId);
                    kehudanweiIdmap.put(xiangdanId, kehudanweiId);
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
        jButton1 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldzhidanDate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldzhidanren = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableyimiao = new BaseTable(null);
        jTextFielddanjuNo = new BaseTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextFieldkufang = new BaseTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoChuKu1JDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoChuKu1JDialog.class, this);
        jButton7.setAction(actionMap.get("save")); // NOI18N
        jButton7.setIcon(resourceMap.getIcon("jButton7.icon")); // NOI18N
        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setBorderPainted(false);
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton7.setName("jButton7"); // NOI18N
        jButton7.setOpaque(false);
        jToolBar1.add(jButton7);

        jButton1.setAction(actionMap.get("buhege")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setName("jButton1"); // NOI18N
        jToolBar1.add(jButton1);

        jButton8.setAction(actionMap.get("print")); // NOI18N
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

        jTextFieldzhidanren.setEditable(false);
        jTextFieldzhidanren.setText(resourceMap.getString("jTextFieldzhidanren.text")); // NOI18N
        jTextFieldzhidanren.setName("jTextFieldzhidanren"); // NOI18N

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

        jTextFielddanjuNo.setEditable(false);
        jTextFielddanjuNo.setName("jTextFielddanjuNo"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(2);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane2.setViewportView(jTextArea1);

        jTextFieldkufang.setName("jTextFieldkufang"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextFielddanjuNo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2)))
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldkufang, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFielddanjuNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldkufang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(YiMiaoChuKu1JDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu1JDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu1JDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu1JDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu1JDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu1JDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu1JDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu1JDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                YiMiaoChuKu1JDialog dialog = new YiMiaoChuKu1JDialog();
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

    //疫苗取消入库情况
    @Action
    public Task buhege() {
        if (jTableyimiao.getRowCount() < 1) {
            AssetMessage.ERRORSYS("请选择要取消出库的疫苗！", this);
            return null;
        }
        List<SaleyimiaoEntity> lst = new ArrayList<SaleyimiaoEntity>();
        for (int i = 0; i < jTableyimiao.getRowCount()-1; i++) {
            SaleyimiaoEntity lb = new SaleyimiaoEntity();
            Sale_detail_tb saledetail = new Sale_detail_tb();
            saledetail.setSaleDetailId(Integer.parseInt(saledetailIdmap.get(jTableyimiao.getValueAt(i, 0)).toString()));
            saledetail.setSaleId(saleIdmap.get(jTableyimiao.getValueAt(i, 0)).toString());
            lb.setSale_detail_tb(saledetail);
            String reason = "";
            while (reason.isEmpty()) {
                reason = AssetMessage.showInputDialog(null, "请输入取消出库疫苗【"
                        + jTableyimiao.getValueAt(i, 1) + "】的理由(必输)：");
                if (reason == null) {
                    return null;
                }
            }
            lb.getSale_detail_tb().setReason("【出库】" + reason);
            lst.add(lb);
        }
        return new Cancel(lst);
    }

    private class BuhegeTask extends org.jdesktop.application.Task<Object, Void> {

        BuhegeTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to BuhegeTask fields, here.
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
    public Task save() throws ParseException {
        YimiaochurukuEntity yimiaochukuEntity = new YimiaochurukuEntity();
        if (jTextFielddanjuNo.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入出库疫苗!");
            return null;
        }
        if (jTextFieldkufang.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请选择出库库房!");
            return null;
        }
        churukudan.setKufang(jTextFieldkufang.getText());
        churukudan = new Churukudantb();
        churukudan.setChurukuId(jTextFielddanjuNo.getText());
        churukudan.setZhidandate(dateformate.parse(jTextFieldzhidanDate.getText()));
        churukudan.setZhidanren(AssetClientApp.getSessionMap().getUsertb().getUserId());

        yimiaochukuEntity.setChurukutb(churukudan);
        List<Churukudanyimiaoliebiaotb> list = new ArrayList<Churukudanyimiaoliebiaotb>();

        for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
            BaseTable yimiaotable = ((BaseTable) jTableyimiao);
            Churukudanyimiaoliebiaotb yimiaoliebiao = new Churukudanyimiaoliebiaotb();
            yimiaoliebiao.setChurukuId(jTextFielddanjuNo.getText());
            yimiaoliebiao.setZhidandate(dateformate.parse(jTextFieldzhidanDate.getText()));
            yimiaoliebiao.setPihao((String) yimiaotable.getValue(i, "pihao"));
            yimiaoliebiao.setPiqianfahegeno((String) yimiaotable.getValue(i, "piqianfaNo"));
            yimiaoliebiao.setPrice(0f);
            yimiaoliebiao.setSource((String) ("" + yimiaotable.getValue(i, "source")));
            yimiaoliebiao.setTongguandanno((String) ("" + yimiaotable.getValue(i, "tongguandanNo")));
            yimiaoliebiao.setYouxiaoqi(riqiformate.parse((String) ("" + yimiaotable.getValue(i, "youxiaodate"))));
            yimiaoliebiao.setKucunId(Integer.parseInt(yimiaotable.getValue(i, "stockpileId").toString()));
            yimiaoliebiao.setChukuQuantity(Integer.parseInt((String) ("" + yimiaotable.getValue(i, "quantity"))));
            yimiaoliebiao.setTotalprice(yimiaoliebiao.getPrice() * yimiaoliebiao.getPrice());
            yimiaoliebiao.setXiangdanId(Integer.parseInt(saledetailIdmap.get(jTableyimiao.getValueAt(i, 0)).toString()));
            yimiaoliebiao.setWanglaidanweiId(Integer.parseInt(kehudanweiIdmap.get(jTableyimiao.getValueAt(i, 0)).toString()));
            list.add(yimiaoliebiao);
        }
        yimiaochukuEntity.setResult(list);

        String serviceId = "yimiaochuku/add";
        return new CommUpdateTask<YimiaochurukuEntity>(yimiaochukuEntity, serviceId) {

            @Override
            public void responseResult(ComResponse<YimiaochurukuEntity> response) {
                if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                    JOptionPane.showMessageDialog(null, "提交成功！");
                    close();
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    YiMiaoChuKu1JDialog ymck1 = new YiMiaoChuKu1JDialog();
                    ymck1.setLocationRelativeTo(mainFrame);
                    AssetClientApp.getApplication().show(ymck1);
                } else {
                    AssetMessage.ERROR(response.getErrorMessage(), YiMiaoChuKu1JDialog.this);
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

    public void setNew() {
        isNew = true;
    }

    @Action
    public void print() {
        try {
            super.print(this.getTitle(),
                    new String[][]{{"单据编号", jTextFielddanjuNo.getText()},
                    {"制单日期", jTextFieldzhidanDate.getText()},
                    {"经办人", jTextFieldzhidanren.getText()},
                    {"仓库", jTextFieldkufang.getText()},
                    {"备注", jTextArea1.getText()}},
                    jTableyimiao,
                    new String[][]{{"", ""},});
        } catch (DRException ex) {
            ex.printStackTrace();
            logger.error(ex);
        }
    }

    @Action
    public void exit() {
        if (isNew) {
            close();
            return;
        }
        String sql = " (sale_id like \"YMXF%\") and is_completed = 1 and status = 0";
        new CloseTask(sql).execute();
    }

    public void close() {
        this.dispose();
    }

    private class CloseTask extends WeiChuKuYimiaoTask {

        public CloseTask(String sql) {
            super(sql);
        }

        @Override
        public void responseResult(CommFindEntity<SaleyimiaoEntity> response) {

            logger.debug("get current size:" + response.getResult().size());
            list = response.getResult();
            if (list != null && list.size() > 0) {
                StringBuilder string = new StringBuilder();
                for (SaleyimiaoEntity zc : list) {
                    string.append("单据").append(zc.getSale_detail_tb().getSaleId()).append("有未出库项【")
                            .append(zc.getYimiaoAll().getYimiaoName()).append("】\n");
                }
                string.append("是否继续出库？选“否”会要求输入原因，并不再出库以上所有疫苗");
                int result = AssetMessage.showConfirmDialog(null, string.toString(),
                        "确认", JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                    return;
                }
                for (SaleyimiaoEntity lb : list) {
                    String reason = "";
                    while (reason.isEmpty()) {
                        reason = AssetMessage.showInputDialog(null, "请输入取消出库疫苗【"
                                + lb.getYimiaoAll().getYimiaoName() + "】的理由(必输)：");
                        if (reason == null) {
                            return;
                        }
                    }
                    lb.getSale_detail_tb().setReason("【出库】" + reason);
                }
                new Cancel(list).execute();
            }
            close();
        }

    }

    private class Cancel extends CancelChuKu {

        public Cancel(List<SaleyimiaoEntity> zc) {
            super(zc);
        }

        @Override
        public void onSucceeded(Object object) {
            if (object instanceof Exception) {
                Exception e = (Exception) object;
                AssetMessage.ERRORSYS(e.getMessage());
                WeiChuKuYimiaoTask.logger.error(e);
                return;
            }
            close();
            JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
            YiMiaoChuKu1JDialog yimiaochuku = new YiMiaoChuKu1JDialog();
            yimiaochuku.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(yimiaochuku);
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
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
    private javax.swing.JTextField jTextFieldkufang;
    private javax.swing.JTextField jTextFieldzhidanDate;
    private javax.swing.JTextField jTextFieldzhidanren;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
