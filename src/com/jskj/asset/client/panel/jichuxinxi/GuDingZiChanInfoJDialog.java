/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.jichuxinxi;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.GudingzichanAll;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BasePanel;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.layout.ws.CommUpdateTask;
import com.jskj.asset.client.panel.ymgl.*;
import com.jskj.asset.client.util.DanHao;
import com.jskj.asset.client.util.PingYinUtil;
import javax.swing.JFrame;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author huiqi
 */
public class GuDingZiChanInfoJDialog extends BaseDialog {

    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GuDingZiChanInfoJDialog.class);

    private GudingzichanAll appParam;
    private BasePanel parentPanel;
    private String barcode;

    /**
     * Creates new form YiMiaoDengJi1JDialog
     *
     * @param parentPanel
     */
    public GuDingZiChanInfoJDialog(BasePanel parentPanel) {
        super();
        initComponents();
        this.parentPanel = parentPanel;

        gdzcType.setModel(new javax.swing.DefaultComboBoxModel(AssetClientApp.getParamNamesByType("资产类别")));

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
//                    sql += " and (supplier_name like \"%" + suppliertb$supplierName.getText() + "%\"" + " or supplier_zujima like \"%" +  suppliertb$supplierName.getText().trim().toLowerCase() + "%\")";
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

    public void setUpdatedData(GudingzichanAll paramData) {

        this.appParam = paramData;
        if (paramData == null) {
            return;
        }
//        gdzcPhoto.setModel(new BaseListModel<String>(new ArrayList(), ""));
        jTextFieldDepotID.setText("");
        jTextFieldSupplier.setText("");
        //自动帮定所有的值
        super.bind(paramData, jPanel3);
        super.bind(paramData, jPanel4);
        barcode = appParam.getGdzcBarcode();

        if (appParam.getGdzcId() == null || appParam.getGdzcId() <= 0) { //新建
            jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("基本信息")); // NOI18N
            //jCheckBox2.setSelected(false);
            jCheckBoxCont.setEnabled(true);
            barcode = DanHao.getDanHao("ZC");
        } else {//更新
            jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("资产编号:" + appParam.getGdzcId())); // NOI18N
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

//            String imagePaths = paramData.getGdzcPhoto();
//            if (imagePaths != null && !imagePaths.trim().equals("")) {
//                BaseListModel<String> mode = (BaseListModel<String>) gdzcPhoto.getModel();
//                List source = mode.getSource();
//                String[] images = imagePaths.split(";");
//                for (String m : images) {
//                    source.add(m);
//                }
//                BaseListModel<String> newMode = new BaseListModel<String>(source, "");
//                gdzcPhoto.setModel(newMode);
//            }
        }

    }

    @Action
    public Task save() {

        if (gdzcName.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入资产名称!");
            gdzcName.grabFocus();
            return null;
        }

        String zujima = PingYinUtil.getFirstSpell(gdzcName.getText().trim());
        appParam.setZujima(zujima);

        super.copyToBean(appParam, jPanel3);
        super.copyToBean(appParam, jPanel4);

        //单独保存barcode
        appParam.setGdzcBarcode(barcode);
        
        String depotId = jTextFieldDepotID.getText();
        String supplierId = jTextFieldSupplier.getText();

        if (!depotId.trim().equals("")) {
            appParam.setDeportId(Integer.parseInt(depotId));
        }
        if (!supplierId.trim().equals("")) {
            appParam.setSupplierId(Integer.parseInt(supplierId));
//            appParam.setSupplier(suppliertb$supplierName.getText());
        }
//        /*得到图片路径*/
//        BaseListModel<String> mode = (BaseListModel<String>) gdzcPhoto.getModel();
//        List source = mode.getSource();
//        String imgPaths = "";
//        for (int i = 0; i < source.size(); i++) {
//            if (i == (source.size() - 1)) {
//                imgPaths += source.get(i).toString();
//            } else {
//                imgPaths += source.get(i) + ";";
//            }
//        }
//        appParam.setGdzcPhoto(imgPaths);

        String serviceId = "gdzc/add";
        if (appParam.getGdzcId() != null && appParam.getGdzcId() > 0) {
            serviceId = "gdzc/update";
        }

        return new CommUpdateTask<GudingzichanAll>(appParam, serviceId) {
            @Override
            public void responseResult(ComResponse<GudingzichanAll> response) {
                if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                    parentPanel.reload().execute();
                    if (jCheckBoxCont.isSelected()) {
                        JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                        GuDingZiChanInfoJDialog guDingZhiChanInfoJDialog = new GuDingZiChanInfoJDialog(parentPanel);
                        guDingZhiChanInfoJDialog.setLocationRelativeTo(mainFrame);
                        guDingZhiChanInfoJDialog.setUpdatedData(new GudingzichanAll());
                        AssetClientApp.getApplication().show(guDingZhiChanInfoJDialog);
                    }
                    AssetMessage.showMessageDialog(null, "保存成功！");
                    dispose();
                } else {
                    AssetMessage.ERROR(response.getErrorMessage(), GuDingZiChanInfoJDialog.this);
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
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        gdzcName = new javax.swing.JTextField();
        gdzcXinghao = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        unitId = new javax.swing.JTextField();
        gdzcType = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        gdzcGuige = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        gdzcRemark = new javax.swing.JTextArea();

        jTextFieldDepotID.setName("jTextFieldDepotID"); // NOI18N

        jTextFieldSupplier.setName("jTextFieldSupplier"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(GuDingZiChanInfoJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setMaximumSize(null);
        setMinimumSize(null);
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel2.setName("jPanel2"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(GuDingZiChanInfoJDialog.class, this);
        jButton3.setAction(actionMap.get("save")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        jButton4.setAction(actionMap.get("close")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N

        jCheckBoxCont.setText(resourceMap.getString("jCheckBoxCont.text")); // NOI18N
        jCheckBoxCont.setName("jCheckBoxCont"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBoxCont)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(jButton4))
                    .addComponent(jCheckBoxCont))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel3.border.title"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        gdzcName.setName("gdzcName"); // NOI18N

        gdzcXinghao.setName("gdzcXinghao"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        unitId.setName("unitId"); // NOI18N

        gdzcType.setName("gdzcType"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        gdzcGuige.setName("gdzcGuige"); // NOI18N

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(gdzcXinghao))
                    .addComponent(gdzcName)
                    .addComponent(unitId, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(gdzcGuige)
                    .addComponent(gdzcType, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(gdzcName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(gdzcType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gdzcGuige, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(gdzcXinghao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(unitId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel4.border.title"))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        gdzcRemark.setColumns(20);
        gdzcRemark.setRows(2);
        gdzcRemark.setName("gdzcRemark"); // NOI18N
        jScrollPane1.setViewportView(gdzcRemark);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

//    @Action
//    public Task uploadPic() {
//        BaseFileChoose fileChoose = new BaseFileChoose(new String[]{"png", "jpg", "gif", "bmp"}, this);
//        String selectedPath = fileChoose.openDialog();
//        if (!selectedPath.trim().equals("")) {
//            addObjectToList("uploading...");
//            return new FileTask(FileTask.TYPE_UPLOAD, selectedPath, "gudingzhichan") {
//                @Override
//                public void responseResult(String file) {
//                    removeObjectFromList("uploading...");
//                    BaseListModel<String> mode = (BaseListModel<String>) gdzcPhoto.getModel();
//                    List source = mode.getSource();
//                    if (source.contains(file)) {
//                        return;
//                    }
//                    source.add(file);
//                    BaseListModel<String> newMode = new BaseListModel<String>(source, "");
//                    gdzcPhoto.setModel(newMode);
//                }
//            };
//        }
//        return null;
//    }
//
//    private void addObjectToList(String name) {
//
//        BaseListModel<String> mode = (BaseListModel<String>) gdzcPhoto.getModel();
//        List source = mode.getSource();
//        if (source.contains(name)) {
//            return;
//        }
//        source.add(name);
//        BaseListModel<String> newMode = new BaseListModel<String>(source, "");
//        gdzcPhoto.setModel(newMode);
//    }
//
//    private void removeObjectFromList(String name) {
//        BaseListModel<String> mode = (BaseListModel<String>) gdzcPhoto.getModel();
//        List<String> source = mode.getSource();
//        source.remove(name);
//        BaseListModel<String> newMode = new BaseListModel<String>(source, "");
//        gdzcPhoto.setModel(newMode);
//    }
//
//    @Action
//    public Task deletePic() {
//        Object selectedValue = gdzcPhoto.getSelectedValue();
//        if (selectedValue == null) {
//            return null;
//        }
//        removeObjectFromList(selectedValue.toString());
//        if (!selectedValue.toString().equals("")) {
//            return new FileTask(FileTask.TYPE_DELETE, selectedValue.toString(), "gudingzhichan") {
//                @Override
//                public void responseResult(String file) {
//
//                }
//            };
//        }
//        return null;
//    }

    @Action
    public void close() {
        this.dispose();

//        BaseListModel<String> mode = (BaseListModel<String>) gdzcPhoto.getModel();
//        List<String> source = mode.getSource();
//        if (source.size() > 0) {
//            for (int i = 0; i < source.size(); i++) {
//                if (!source.get(i).equals("")) {
//                    FileTask task = new FileTask(FileTask.TYPE_DELETE, source.get(i), "gudingzhichan") {
//                        @Override
//                        public void responseResult(String file) {
//                            removeObjectFromList(file);
//                        }
//                    };
//                    task.execute();
//                }
//            }
//        }
    }

//    @Action
//    public Task imagePreview() {
//        final Object obj = gdzcPhoto.getSelectedValue();
//        if (obj != null) {
//            return new FileTask(FileTask.TYPE_DOWNLOAD, obj.toString(), "gudingzhichan") {
//                @Override
//                public void responseResult(final String file) {
//                    SwingUtilities.invokeLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
//                            ImagePreview imagePreview = new ImagePreview(file, true);
//                            imagePreview.setLocationRelativeTo(mainFrame);
//                            AssetClientApp.getApplication().show(imagePreview);
//                        }
//                    });
//                }
//            };
//        }
//        return null;
//    }

//    @Action
//    public void generatorBar() {
//        String barcode = gdzcSequence.getText();
//        String label = gdzcName.getText();
//        if (barcode == null) {
//            return;
//        }
//        if (label.trim().equals("")) {
//            int result = AssetMessage.CONFIRM(this, "没有标签名，确定打印吗?");
//            if (result != AssetMessage.OK_OPTION) {
//                gdzcName.grabFocus();
//                return;
//            }
//        }
//        DanHao.printBarCode128(label, barcode);
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField gdzcGuige;
    private javax.swing.JTextField gdzcName;
    private javax.swing.JTextArea gdzcRemark;
    private javax.swing.JComboBox gdzcType;
    private javax.swing.JTextField gdzcXinghao;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBoxCont;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
