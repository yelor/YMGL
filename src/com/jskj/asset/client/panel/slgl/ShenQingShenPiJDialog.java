/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.slgl;

import com.jskj.asset.client.panel.slgl.task.ChaXunTask;
import com.jskj.asset.client.panel.slgl.task.ShenPiTask;
import com.jskj.asset.client.panel.slgl.task.ShenqingDetailTask;
import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.CaiGouShenQingFindEntity;
import com.jskj.asset.client.bean.entity.CaigoushenqingDetailEntity;
import com.jskj.asset.client.bean.entity.ShenPiEntity;
import com.jskj.asset.client.bean.entity.Usertb;
import com.jskj.asset.client.bean.entity.Zichanshenpiliuchengtb;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.util.BindTableHelper;
import com.jskj.asset.client.util.DanHao;
import com.jskj.asset.client.util.DateHelper;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author tt
 */
public class ShenQingShenPiJDialog extends javax.swing.JDialog {

    private int pageIndex;

    private int count;

    private List<Zichanshenpiliuchengtb> cgsq;
    
    private ShenPiEntity shenPiEntity;
    
    private final int userId;
    
    private final String userDept;
    
    private final String userlevel;
    
    BindTableHelper<Zichanshenpiliuchengtb> bindTable;
    
    /**
     * Creates new form GuDingZiChanRuKu
     * @param parent
     * @param modal
     */
    public ShenQingShenPiJDialog(java.awt.Frame parent,boolean modal) {
        super(parent,modal);
        initComponents();
        userId = AssetClientApp.getSessionMap().getUsertb().getUserId();
        userDept = AssetClientApp.getSessionMap().getDepartment().getDepartmentName();
        userlevel = AssetClientApp.getSessionMap().getUsertb().getUserRoles();
        pageIndex = 1;
        count = 0;
        bindTable = new BindTableHelper<Zichanshenpiliuchengtb>(jSQTable, new ArrayList<Zichanshenpiliuchengtb>());
        bindTable.createTable(new String[][]{{"danjuId", "采购单号"},
            {"checkId1", "采购办"}, {"checkId2", "财务科"}, {"checkId3", "分管领导"},{"checkId4", "主要领导"}});
//        bindTable.setIntegerType(1);
//        bindTable.setDateType(3);
        bindTable.bind().setColumnWidth(new int[]{0, 150}).setRowHeight(30);
        new RefreshTask(0).execute();
    }
    
    @Action
    public void exit() {
        this.dispose();
    }
    
    @Action
    public void reload() {
        new RefreshTask(0).execute();
        this.repaint();
    }
    
    private class RefreshTask extends ChaXunTask {

        BindingGroup bindingGroup = new BindingGroup();

        RefreshTask(int pageIndex) {
            super(""+userId,pageIndex);
        }

        @Override
        public void onSucceeded(Object object) {

            if (object instanceof Exception) {
                Exception e = (Exception) object;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }

            CaiGouShenQingFindEntity sq = (CaiGouShenQingFindEntity) object;

            if (sq != null) {
                count = sq.getCount();
                jLabelTotal.setText(((pageIndex - 1) * ChaXunTask.pageSize + 1) + "/" + count);
                logger.debug("total:" + count + ",get shenqing size:" +  sq.getResult().size());

                //存下所有的数据
                cgsq =  sq.getResult();
                
                if (cgsq != null) {
                    for (Zichanshenpiliuchengtb liucheng : cgsq) {
                        if (liucheng.getCheckId1() != null) {
                            liucheng.setCheckId1(liucheng.getCheckId1() + "," + liucheng.getCheckUser1() + "," + DateHelper.formatTime(liucheng.getCheckTime1()));
                        }
                        if (liucheng.getCheckId2() != null) {
                            liucheng.setCheckId2(liucheng.getCheckId2() + "," + liucheng.getCheckUser2() + "," + DateHelper.formatTime(liucheng.getCheckTime2()));
                        }
                        if (liucheng.getCheckId3() != null) {
                            liucheng.setCheckId3(liucheng.getCheckId3() + "," + liucheng.getCheckUser3() + "," + DateHelper.formatTime(liucheng.getCheckTime3()));
                        }
                        if (liucheng.getCheckId4() != null) {
                            liucheng.setCheckId4(liucheng.getCheckId4() + "," + liucheng.getCheckUser4() + "," + DateHelper.formatTime(liucheng.getCheckTime4()));
                        }

                        //领用单财务科提示不需要审批
                        if(liucheng.getDanjuId().contains("LY") && liucheng.getCheckId1() != null){
                            liucheng.setCheckId2("此流程不经由财务科审批");
                        }
                    }
                }
                
                bindTable.refreshData(cgsq);
            }
        }
    }
    
     @Action
    public void pagePrev() {
        pageIndex = pageIndex - 1;
        pageIndex = pageIndex <= 0 ? 1 : pageIndex;
        new RefreshTask(pageIndex).execute();
    }

    @Action
    public void pageNext() {
        if (ChaXunTask.pageSize * (pageIndex) <= count) {
            pageIndex = pageIndex + 1;
        }
        new RefreshTask(pageIndex).execute();
    }
    
    @Action
    public void detail(){
        int n = jSQTable.getSelectedRow();
        if(n < 0 || cgsq.isEmpty()){
            AssetMessage.showMessageDialog(this, "请选择某个申请单!");
            return;
        }
        this.setVisible(false);
        Zichanshenpiliuchengtb cgsqdan = cgsq.get(n);
        new DetailTask(cgsqdan.getDanjuId()).execute();
    }
    
    public Zichanshenpiliuchengtb selectedDataFromTable() {
        if (jSQTable.getSelectedRow() >= 0) {
            if (cgsq != null) {
                return cgsq.get(jSQTable.getSelectedRow());
            }
        }
        return null;
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
    
    @Action
    public Task shenPiY(){
        int n = jSQTable.getSelectedRow();
        if(n < 0){
            AssetMessage.showMessageDialog(this, "请选择某个申请单!");
            return null;
        }
        
        String passwd = AssetMessage.CONFIRM_PASSWD(this);
        if (passwd == null) {
            return null;
        } else {
            Usertb usertb = AssetClientApp.getSessionMap().getUsertb();
            if (!passwd.equalsIgnoreCase(usertb.getUserPassword())) {
                AssetMessage.ERRORSYS("密码错误!");
                return null;
            }
        }
        
        Zichanshenpiliuchengtb cgsqdan = cgsq.get(n);
        shenPiEntity = new ShenPiEntity();
        shenPiEntity.setId(cgsqdan.getDanjuId().toString());
        shenPiEntity.setResult("同意");
        shenPiEntity.setUser(""+ userId);
        if(shenPiEntity.getId().contains("WXSQ") && userDept.equals("采购办")
                && userlevel.equals("直接领导")){
            String reason = AssetMessage.showInputDialog(this, "请输入预估维修费用！");
            if(reason.isEmpty()){
                return null;
            }
            shenPiEntity.setReason(reason);
        }
        cgsq.remove(jSQTable.getSelectedRow());
        return new SPTask(shenPiEntity);
    }
    
    @Action
    public Task shenPiN(){
        int n = jSQTable.getSelectedRow();
        if(n < 0){
            AssetMessage.showMessageDialog(this, "请选择某个申请单!");
            return null;
        }
        
        String passwd = AssetMessage.CONFIRM_PASSWD(this);
        if (passwd == null) {
            return null;
        } else {
            Usertb usertb = AssetClientApp.getSessionMap().getUsertb();
            if (!passwd.equalsIgnoreCase(usertb.getUserPassword())) {
                AssetMessage.ERRORSYS("密码错误!");
                return null;
            }
        }
        
        String reason;
        reason = AssetMessage.showInputDialog(this, "请输入拒绝理由");
        if(reason==null){
           return null;
        }
        Zichanshenpiliuchengtb cgsqdan = cgsq.get(n);
        shenPiEntity = new ShenPiEntity();
        shenPiEntity.setId(cgsqdan.getDanjuId().toString());
        shenPiEntity.setResult("拒绝");
        shenPiEntity.setUser(""+ userId);
        shenPiEntity.setReason(reason);
        cgsq.remove(jSQTable.getSelectedRow());
        return new SPTask(shenPiEntity);
    }
    
    private class SPTask extends ShenPiTask{

        public SPTask(ShenPiEntity sp) {
            super(sp);
        }
        
        @Override
        protected void succeeded(Object result){
            reload();
            AssetMessage.showMessageDialog(null, "审批完成！");
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

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jSQTable = new javax.swing.JTable();
        ctrlPane = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelTotal = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(ShenQingShenPiJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel2.setMinimumSize(new java.awt.Dimension(796, 577));
        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jSQTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jSQTable.setName("jSQTable"); // NOI18N
        jSQTable.setShowVerticalLines(false);
        jScrollPane1.setViewportView(jSQTable);
        jSQTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        ctrlPane.setName("ctrlPane"); // NOI18N
        ctrlPane.setPreferredSize(new java.awt.Dimension(796, 30));

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(ShenQingShenPiJDialog.class, this);
        jButton11.setAction(actionMap.get("shenPiN")); // NOI18N
        jButton11.setIcon(resourceMap.getIcon("jButton11.icon")); // NOI18N
        jButton11.setText(resourceMap.getString("jButton11.text")); // NOI18N
        jButton11.setBorderPainted(false);
        jButton11.setFocusable(false);
        jButton11.setName("jButton11"); // NOI18N
        jButton11.setOpaque(false);
        jToolBar1.add(jButton11);

        jButton12.setAction(actionMap.get("shenPiY")); // NOI18N
        jButton12.setIcon(resourceMap.getIcon("jButton12.icon")); // NOI18N
        jButton12.setText(resourceMap.getString("jButton12.text")); // NOI18N
        jButton12.setBorderPainted(false);
        jButton12.setFocusable(false);
        jButton12.setName("jButton12"); // NOI18N
        jButton12.setOpaque(false);
        jToolBar1.add(jButton12);

        jButton2.setAction(actionMap.get("detail")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.setIconTextGap(2);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setOpaque(false);
        jToolBar1.add(jButton2);

        jButton15.setAction(actionMap.get("exit")); // NOI18N
        jButton15.setIcon(resourceMap.getIcon("jButton15.icon")); // NOI18N
        jButton15.setText(resourceMap.getString("jButton15.text")); // NOI18N
        jButton15.setBorderPainted(false);
        jButton15.setFocusable(false);
        jButton15.setName("jButton15"); // NOI18N
        jButton15.setOpaque(false);
        jToolBar1.add(jButton15);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);

        jLabelTotal.setText(resourceMap.getString("jLabelTotal.text")); // NOI18N
        jLabelTotal.setName("jLabelTotal"); // NOI18N

        jButton1.setAction(actionMap.get("pagePrev")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jButton3.setAction(actionMap.get("pageNext")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(283, Short.MAX_VALUE)
                .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addGap(2, 2, 2))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout ctrlPaneLayout = new javax.swing.GroupLayout(ctrlPane);
        ctrlPane.setLayout(ctrlPaneLayout);
        ctrlPaneLayout.setHorizontalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctrlPaneLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        ctrlPaneLayout.setVerticalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctrlPaneLayout.createSequentialGroup()
                .addGroup(ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(79, 79, 79))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ctrlPane, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(ctrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 851, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 510, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
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
            java.util.logging.Logger.getLogger(ShenQingShenPiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShenQingShenPiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShenQingShenPiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShenQingShenPiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ShenQingShenPiJDialog dialog = new ShenQingShenPiJDialog(new javax.swing.JFrame(),true);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ctrlPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTable jSQTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
