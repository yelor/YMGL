/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.shjs;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.CaigoushenqingDetailEntity;
import com.jskj.asset.client.bean.entity.FukuanshenqingDetailEntity;
import com.jskj.asset.client.bean.entity.Yingfukuandanjutb;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.ws.CommFindEntity;
import com.jskj.asset.client.panel.shjs.task.FkShenqingDetailTask;
import com.jskj.asset.client.panel.shjs.task.YingfuliebiaoFindTask;
import com.jskj.asset.client.panel.slgl.DiZhiYiHaoPinCaiGouShenQingJDialog;
import com.jskj.asset.client.panel.slgl.DiZhiYiHaoPinLingYongShenQingJDialog;
import com.jskj.asset.client.panel.slgl.GuDingZiChanCaiGouShenQingJDialog;
import com.jskj.asset.client.panel.slgl.GuDingZiChanWeiXiuShenQingJDialog;
import com.jskj.asset.client.panel.slgl.ITGuDingZiChanLingYongShenQingJDialog;
import com.jskj.asset.client.panel.slgl.PTGuDingZiChanLingYongShenQingJDialog;
import com.jskj.asset.client.panel.slgl.task.ShenqingDetailTask;
import com.jskj.asset.client.util.BindTableHelper;
import com.jskj.asset.client.util.DanHao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;

/**
 *
 * @author huiqi
 */
public class DanweiyingfuJDialog extends BaseDialog {

    private final static Logger logger = Logger.getLogger(DanweiyingfuJDialog.class);
    private final SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd");
    private List<Yingfukuandanjutb> currentPageData;
    private final BindTableHelper<Yingfukuandanjutb> bindTable;
    private final HashMap parameterMap;
    private final String conditionSql;
    

    /**
     * Creates new form FKDJDialog
     * @param map
     */
    public DanweiyingfuJDialog(HashMap map) {
        super();
        initComponents();

        startDate.setText(dateformate.format(map.get("startDate")).toString());
        endDate.setText(dateformate.format(map.get("endDate")).toString());
        supplier.setText(map.get("supplierName").toString());
        yingfu.setText("0.0");
        
        conditionSql = "";
        parameterMap = new HashMap();
        parameterMap.put("conditionSql", conditionSql);
        parameterMap.put("startDate", map.get("startDate"));
        parameterMap.put("endDate", map.get("endDate"));
        parameterMap.put("supplierId", map.get("supplierId"));
        parameterMap.put("serviceId", "yflist");

        bindTable = new BindTableHelper<Yingfukuandanjutb>(jTable1, new ArrayList<Yingfukuandanjutb>());
        bindTable.createTable(new String[][]{{"yuandanId", "源单编号"}, {"zhidandate", "制单日期"},{"yuandantype", "源单类型"}
                , {"danjujine", "单据金额"}, {"increase", "增加金额"}, {"decrease", "减少金额"}, {"yingfu", "应付金额"}, {"remark", "备注"}});
        bindTable.setColumnType(Date.class, 2);
        //bindTable.setColumnType(Float.class, 8, 9);
        bindTable.bind().setColumnWidth(new int[]{0, 150}, new int[]{1, 100}, new int[]{2, 100}).setRowHeight(25);
        
        new RefreshTask().execute();
    }

    @Action
    public void exit() {
        this.dispose();
    }
    
    private class RefreshTask extends YingfuliebiaoFindTask{

        RefreshTask() {
            super(parameterMap);
        }

        @Override
        public void responseResult(CommFindEntity<Yingfukuandanjutb> response) {
            
            logger.debug("get current size:" + response.getResult().size());

            //存下所有的数据
            currentPageData = response.getResult();

            float total = 0;
            for(int i = 0; i < currentPageData.size(); i++){
                total += currentPageData.get(i).getYingfu();
                if(i > 0){
                    currentPageData.get(i).setYingfu(currentPageData.get(i - 1).getYingfu() +
                            currentPageData.get(i).getYingfu());
                }
            }
            yingfu.setText("" + total);
            bindTable.refreshData(currentPageData);
        }
        
    }

    @Action
    public void detail(){
        int n = jTable1.getSelectedRow();
        if(n < 0){
            AssetMessage.showMessageDialog(this, "请选择某个申请单!");
            return;
        }
        Yingfukuandanjutb fksqdan = currentPageData.get(n);
        if(fksqdan.getYuandantype().equals("期初应付")){
            AssetMessage.showMessageDialog(this, "请选择某个申请单!");
            return;
        }
        if(fksqdan.getYuandanId().contains("FKDJ") || fksqdan.getYuandanId().contains("QTFK")){
            new FKDetailTask(fksqdan.getYuandanId()).execute();
        } else if(fksqdan.getYuandanId().contains("GDZC") || fksqdan.getYuandanId().contains("YHCG") 
                || fksqdan.getYuandanId().contains("YMCG")){
            new DetailTask(fksqdan.getYuandanId()).execute();
        }
        
        this.setVisible(false);
    }
    
    private class FKDetailTask extends FkShenqingDetailTask{

        public FKDetailTask(String id) {
            super(id);
        }
        
        @Override
        public void onSucceeded(Object object) {
            if (object instanceof Exception) {
                Exception e = (Exception) object;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            if(object == null){
                AssetMessage.showMessageDialog(null, "未获取到申请单详细信息。");
                return;
            }
            FukuanshenqingDetailEntity cgsq = (FukuanshenqingDetailEntity)object;
            openFKShenqingdan(cgsq);
        }
    }
    
    public void openFKShenqingdan(FukuanshenqingDetailEntity fksq) {
        JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
        if(fksq.getFukuandanId().contains(DanHao.TYPE_FKDJ)){
            FuKuanDanJDialog fkdJDialog = new FuKuanDanJDialog(this, fksq);
            fkdJDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(fkdJDialog);
        }else if(fksq.getFukuandanId().contains(DanHao.TYPE_QTFK)){
            OtherFuKuanDanJDialog fkdJDialog = new OtherFuKuanDanJDialog(this, fksq);
            fkdJDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(fkdJDialog);
        }
    }
    
    private class DetailTask extends ShenqingDetailTask{

        public DetailTask(String id) {
            super(id);
        }
        
        @Override
        public void onSucceeded(Object object) {
            if (object instanceof Exception) {
                Exception e = (Exception) object;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            if(object == null){
                AssetMessage.showMessageDialog(null, "未获取到申请单详细信息。");
                return;
            }
            CaigoushenqingDetailEntity cgsq = (CaigoushenqingDetailEntity)object;
            openShenqingdan(cgsq);
        }
        
    }

    public void openShenqingdan(CaigoushenqingDetailEntity cgsq) {
        JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
        if (cgsq.getCgsqId().contains(DanHao.TYPE_GDZC)) {
            GuDingZiChanCaiGouShenQingJDialog guDingZiChanCaiGouSQSHJDialog = new GuDingZiChanCaiGouShenQingJDialog(this, cgsq);
            guDingZiChanCaiGouSQSHJDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(guDingZiChanCaiGouSQSHJDialog);
        } else if (cgsq.getCgsqId().contains(DanHao.TYPE_YHCG)) {
            DiZhiYiHaoPinCaiGouShenQingJDialog lingyongshenqing = new DiZhiYiHaoPinCaiGouShenQingJDialog(this, cgsq);
            lingyongshenqing.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(lingyongshenqing);
        } else if (cgsq.getCgsqId().contains(DanHao.TYPE_PTLY)) {
            PTGuDingZiChanLingYongShenQingJDialog lingyongshenqing = new PTGuDingZiChanLingYongShenQingJDialog(this, cgsq);
            lingyongshenqing.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(lingyongshenqing);
        } else if (cgsq.getCgsqId().contains(DanHao.TYPE_ITLY)) {
            ITGuDingZiChanLingYongShenQingJDialog lingyongshenqing = new ITGuDingZiChanLingYongShenQingJDialog(this, cgsq);
            lingyongshenqing.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(lingyongshenqing);
        } else if (cgsq.getCgsqId().contains(DanHao.TYPE_YHLY)) {
            DiZhiYiHaoPinLingYongShenQingJDialog lingyongshenqing = new DiZhiYiHaoPinLingYongShenQingJDialog(this, cgsq);
            lingyongshenqing.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(lingyongshenqing);
        } else if (cgsq.getCgsqId().contains(DanHao.TYPE_WXSQ)) {
            GuDingZiChanWeiXiuShenQingJDialog weixiushenqing = new GuDingZiChanWeiXiuShenQingJDialog(this, cgsq);
            weixiushenqing.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(weixiushenqing);
        }
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
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        supplier = new BaseTextField();
        jLabel6 = new javax.swing.JLabel();
        startDate = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new BaseTable(null);
        jLabel7 = new javax.swing.JLabel();
        yingfu = new BaseTextField();
        jLabel9 = new javax.swing.JLabel();
        endDate = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(DanweiyingfuJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(DanweiyingfuJDialog.class, this);
        jButton1.setAction(actionMap.get("detail")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jToolBar1.add(jButton1);

        jButton4.setAction(actionMap.get("print")); // NOI18N
        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setOpaque(false);
        jToolBar1.add(jButton4);

        jButton6.setAction(actionMap.get("exit")); // NOI18N
        jButton6.setIcon(resourceMap.getIcon("jButton6.icon")); // NOI18N
        jButton6.setText(resourceMap.getString("jButton6.text")); // NOI18N
        jButton6.setBorderPainted(false);
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton6.setName("jButton6"); // NOI18N
        jButton6.setOpaque(false);
        jToolBar1.add(jButton6);

        jPanel1.setName("jPanel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        supplier.setEditable(false);
        supplier.setText(resourceMap.getString("supplier.text")); // NOI18N
        supplier.setName("supplier"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        startDate.setEditable(false);
        startDate.setText(resourceMap.getString("startDate.text")); // NOI18N
        startDate.setName("startDate"); // NOI18N

        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "源单编号", "制单日期", "源单类型", "单据金额", "增加金额", "减少金额", "应付金额", "备注"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
            jTable1.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title1")); // NOI18N
            jTable1.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
            jTable1.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title3")); // NOI18N
            jTable1.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable1.columnModel.title5")); // NOI18N
            jTable1.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable1.columnModel.title6")); // NOI18N
            jTable1.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTable1.columnModel.title7")); // NOI18N
            jTable1.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE))
        );

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        yingfu.setEditable(false);
        yingfu.setName("yingfu"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        endDate.setEditable(false);
        endDate.setName("endDate"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(yingfu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 246, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(yingfu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            java.util.logging.Logger.getLogger(DanweiyingfuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DanweiyingfuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DanweiyingfuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DanweiyingfuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    }

    @Action
    public void print() {
        try {
            super.print(this.getTitle(),
                    new String[][]{
                    {"供应单位", supplier.getText()},
                    {"起始日期", startDate.getText()},
                    {"应付总金额", yingfu.getText()},
                    {"结束日期", endDate.getText()}}, 
                    jTable1,
                    new String[][]{
                    });
        } catch (DRException ex) {
            ex.printStackTrace();
            logger.error(ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField endDate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField startDate;
    private javax.swing.JTextField supplier;
    private javax.swing.JTextField yingfu;
    // End of variables declaration//GEN-END:variables
}
