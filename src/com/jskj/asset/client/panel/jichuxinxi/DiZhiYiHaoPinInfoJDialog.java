/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.jichuxinxi;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.DizhiyihaopinAll;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseFileChoose;
import com.jskj.asset.client.layout.BaseListModel;
import com.jskj.asset.client.layout.BasePanel;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.layout.ws.CommUpdateTask;
import com.jskj.asset.client.panel.FileTask;
import com.jskj.asset.client.panel.ImagePreview;
import com.jskj.asset.client.panel.ymgl.*;
import com.jskj.asset.client.util.DanHao;
import com.jskj.asset.client.util.PingYinUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author huiqi
 */
public class DiZhiYiHaoPinInfoJDialog extends BaseDialog {

    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DiZhiYiHaoPinInfoJDialog.class);

    private DizhiyihaopinAll appParam;
    private BasePanel parentPanel;
    private String barcode;

    /**
     * Creates new form YiMiaoDengJi1JDialog
     */
    public DiZhiYiHaoPinInfoJDialog(BasePanel parentPanel) {
        super();
        initComponents();
        this.parentPanel = parentPanel;
        this.setTitle("低值易耗品");

        dzyhpType.setModel(new javax.swing.DefaultComboBoxModel(AssetClientApp.getParamNamesByType("物品类别")));
//        
//        ((BaseTextField) depottb$depotName).registerPopup(new IPopupBuilder() {
//
//            @Override
//            public int getType() {
//                return IPopupBuilder.TYPE_POPUP_TEXT;
//            }
//
//            @Override
//            public String getWebServiceURI() {
//                return Constants.HTTP + Constants.APPID + "cangku/";
//            }
//
//            @Override
//            public String getConditionSQL() {
//                String sql = "";
//                if (!depottb$depotName.getText().trim().equals("")) {
//                    sql = "(depot_name like \"%" + depottb$depotName.getText() + "%\""+" or zujima like \"%"+depottb$depotName.getText().toLowerCase() + "%\")";
//                }
//                return sql;
//            }
//
//            @Override
//            public String[][] displayColumns() {
//                return new String[][]{{"depotName", "仓库名"}, {"depotArea", "面积"}};
//            }
//
//            @Override
//            public void setBindedMap(HashMap bindedMap) {
//                if (bindedMap != null) {
//                    depottb$depotName.setText(bindedMap.get("depotName").toString());
//                    jTextFieldDepotID.setText(bindedMap.get("depotId").toString());
//                }
//            }
//        });
//
//        ((BaseTextField) suppliertb$supplierName).registerPopup(new IPopupBuilder() {
//
//            @Override
//            public int getType() {
//                return IPopupBuilder.TYPE_POPUP_TEXT;
//            }
//
//            @Override
//            public String getWebServiceURI() {
//                return Constants.HTTP + Constants.APPID + "supplier/";
//            }
//
//            @Override
//            public String getConditionSQL() {
//                String sql = " supplier_type = 1 ";
//                if (!suppliertb$supplierName.getText().trim().equals("")) {
//                    sql += "and (supplier_name like \"%" + suppliertb$supplierName.getText() + "%\"" + " or supplier_zujima like \"%" +  suppliertb$supplierName.getText().trim().toLowerCase() + "%\")";
//                }
//                return sql;
//            }
//
//            @Override
//            public String[][] displayColumns() {
//                return new String[][]{{"supplierName", "供应商"}, {"supplierConstactperson", "联系人"}};
//            }
//
//            @Override
//            public void setBindedMap(HashMap bindedMap) {
//                if (bindedMap != null) {
//                    suppliertb$supplierName.setText(bindedMap.get("supplierName").toString());
//                    jTextFieldSupplier.setText(bindedMap.get("supplierId").toString());
//                }
//            }
//        });
    }

    public void setUpdatedData(DizhiyihaopinAll paramData) {

        this.appParam = paramData;
        if (paramData == null) {
            return;
        }
        jTextFieldDepotID.setText("");
        jTextFieldSupplier.setText("");
        //自动绑定所有的值
        super.bind(paramData, jPanel3);
        super.bind(paramData, jPanel4);
        barcode = appParam.getDzyhpBarcode();
        
        if(paramData.getZhijielingyong()!=null&&paramData.getZhijielingyong() == 0){
            jCheckBoxzjly.setSelected(true);
        }

        if (appParam.getDzyhpId() == null || appParam.getDzyhpId() <= 0) { //新建
            jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("基本信息")); // NOI18N
            //jCheckBox2.setSelected(false);
            jCheckBoxCont.setEnabled(true);
            jTextFieldDepotID.setText("");
            barcode = DanHao.getDanHao("DZ");
        } else {//更新
            jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("物品编号:" + appParam.getDzyhpId())); // NOI18N
            jCheckBoxCont.setSelected(false);
            jCheckBoxCont.setEnabled(false);

            if (appParam.getSupplierId() != null) {
                int supplierId = appParam.getSupplierId();
                if (supplierId > 0) {
                    jTextFieldSupplier.setText(String.valueOf(supplierId));
                }
            }
            if (appParam.getDeportId() != null) {
                int deportId = appParam.getDeportId();
                if (deportId > 0) {
                    jTextFieldDepotID.setText(String.valueOf(deportId));
                }
            }
        }

    }

    @Action
    public Task save() {

        if (dzyhpName.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入物品名称!");
            dzyhpName.grabFocus();
            return null;
        } 
        
        super.copyToBean(appParam, jPanel3);
        super.copyToBean(appParam, jPanel4);

        String depotId = jTextFieldDepotID.getText();
        String supplierId = jTextFieldSupplier.getText();

        if (!depotId.trim().equals("")) {
            appParam.setDeportId(Integer.parseInt(depotId));
        }
        if (!supplierId.trim().equals("")) {
            appParam.setSupplierId(Integer.parseInt(supplierId));
        }

        appParam.setUnitId(unitId.getText());
        if(jCheckBoxzjly.isSelected()){
            appParam.setZhijielingyong(0);
        }else {
            appParam.setZhijielingyong(1);
        }
        
        String zujima = PingYinUtil.getFirstSpell(dzyhpName.getText().trim());
        appParam.setZujima(zujima);
        
        //单独保存barcode
        appParam.setDzyhpBarcode(barcode);
        
        String serviceId = "dizhiyihaopin/add";
        if (appParam.getDzyhpId() != null && appParam.getDzyhpId() > 0) {
            serviceId = "dizhiyihaopin/update";
        }

        return new CommUpdateTask<DizhiyihaopinAll>(appParam, serviceId) {
            @Override
            public void responseResult(ComResponse<DizhiyihaopinAll> response) {
                if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                    parentPanel.reload().execute();
                    if (jCheckBoxCont.isSelected()) {
                        DiZhiYiHaoPinInfoJDialog diZhiYiHaoPinInfoJDialog = new DiZhiYiHaoPinInfoJDialog(parentPanel);
                        JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                        diZhiYiHaoPinInfoJDialog.setLocationRelativeTo(mainFrame);
                        diZhiYiHaoPinInfoJDialog.setUpdatedData(new DizhiyihaopinAll());
                        AssetClientApp.getApplication().show(diZhiYiHaoPinInfoJDialog);
                    }
                    AssetMessage.showMessageDialog(null, "保存成功！");
                    dispose();
                } else {
                    AssetMessage.ERROR(response.getErrorMessage(), DiZhiYiHaoPinInfoJDialog.this);
                }
            }

        };

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldDepotID = new javax.swing.JTextField();
        jTextFieldSupplier = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jCheckBoxCont = new javax.swing.JCheckBox();
        jCheckBoxzjly = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        dzyhpName = new javax.swing.JTextField();
        dzyhpXinghao = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        dzyhpPinpai = new javax.swing.JTextField();
        dzyhpType = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        dzyhpGuige = new javax.swing.JTextField();
        unitId = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dzyhpRemark = new javax.swing.JTextArea();

        jTextFieldDepotID.setName("jTextFieldDepotID"); // NOI18N

        jTextFieldSupplier.setName("jTextFieldSupplier"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(DiZhiYiHaoPinInfoJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setMaximumSize(null);
        setMinimumSize(null);
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel2.setName("jPanel2"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(DiZhiYiHaoPinInfoJDialog.class, this);
        jButton3.setAction(actionMap.get("save")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        jButton4.setAction(actionMap.get("close")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N

        jCheckBoxCont.setText(resourceMap.getString("jCheckBoxCont.text")); // NOI18N
        jCheckBoxCont.setName("jCheckBoxCont"); // NOI18N

        jCheckBoxzjly.setText(resourceMap.getString("jCheckBoxzjly.text")); // NOI18N
        jCheckBoxzjly.setName("jCheckBoxzjly"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBoxCont)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxzjly)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton3)
                .addComponent(jButton4))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jCheckBoxCont)
                .addComponent(jCheckBoxzjly))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel3.border.title"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        dzyhpName.setName("dzyhpName"); // NOI18N

        dzyhpXinghao.setName("dzyhpXinghao"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        dzyhpPinpai.setName("dzyhpPinpai"); // NOI18N

        dzyhpType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "低值易耗品", "高值易耗品" }));
        dzyhpType.setName("dzyhpType"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        dzyhpGuige.setName("dzyhpGuige"); // NOI18N

        unitId.setName("unitId"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dzyhpXinghao, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(unitId))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dzyhpGuige)
                            .addComponent(dzyhpPinpai)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(dzyhpName, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addGap(12, 12, 12)
                        .addComponent(dzyhpType, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(dzyhpName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(dzyhpType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dzyhpGuige, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(dzyhpXinghao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel18)
                    .addComponent(dzyhpPinpai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unitId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel4.border.title"))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        dzyhpRemark.setColumns(20);
        dzyhpRemark.setRows(2);
        dzyhpRemark.setName("dzyhpRemark"); // NOI18N
        jScrollPane1.setViewportView(dzyhpRemark);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.getAccessibleContext().setAccessibleName(resourceMap.getString("jPanel3.AccessibleContext.accessibleName")); // NOI18N

        getAccessibleContext().setAccessibleName(resourceMap.getString("Form.AccessibleContext.accessibleName")); // NOI18N

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
            java.util.logging.Logger.getLogger(YiMiaoDengJi2JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoDengJi2JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoDengJi2JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoDengJi2JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                YiMiaoDengJi2JDialog dialog = new YiMiaoDengJi2JDialog();
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
    public void close() {
        this.dispose();
    }

//    @Action
//    public void generatorBar() {
//        String barcode = dzyhpBarcode.getText();
//        String label = dzyhpName.getText();
//        if (barcode == null) {
//            return;
//        }
//        if (label.trim().equals("")) {
//            int result = AssetMessage.CONFIRM(this, "没有标签名，确定打印吗?");
//            if (result != AssetMessage.OK_OPTION) {
//                dzyhpName.grabFocus();
//                return;
//            }
//        }
//        DanHao.printBarCode128(label, barcode);
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField dzyhpGuige;
    private javax.swing.JTextField dzyhpName;
    private javax.swing.JTextField dzyhpPinpai;
    private javax.swing.JTextArea dzyhpRemark;
    private javax.swing.JComboBox dzyhpType;
    private javax.swing.JTextField dzyhpXinghao;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBoxCont;
    private javax.swing.JCheckBox jCheckBoxzjly;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldDepotID;
    private javax.swing.JTextField jTextFieldSupplier;
    private javax.swing.JTextField unitId;
    // End of variables declaration//GEN-END:variables
}
