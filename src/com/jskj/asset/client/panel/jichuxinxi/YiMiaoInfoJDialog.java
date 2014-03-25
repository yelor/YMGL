/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.jichuxinxi;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.YimiaoAll;
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
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author huiqi
 */
public class YiMiaoInfoJDialog extends BaseDialog {

    private static final Logger logger = Logger.getLogger(DanWeiInfoJDialog.class);
    private YimiaoAll appParam;
    private BasePanel parentPanel;

    /**
     * Creates new form YiMiaoDengJi1JDialog
     *
     * @param parentPanel
     */
    public YiMiaoInfoJDialog(BasePanel parentPanel) {
        super();
        initComponents();
        this.parentPanel = parentPanel;
        setTitle("疫苗信息");

        yimiaoType.setModel(new javax.swing.DefaultComboBoxModel(AssetClientApp.getParamNamesByType("疫苗类别")));
        yimiaoJixing.setModel(new javax.swing.DefaultComboBoxModel(AssetClientApp.getParamNamesByType("剂型")));
        unitId.setModel(new javax.swing.DefaultComboBoxModel(AssetClientApp.getParamNamesByType("单位")));
        yimiaoFuzhuunit.setModel(new javax.swing.DefaultComboBoxModel(AssetClientApp.getParamNamesByType("辅助单位")));

        ((BaseTextField) depottb$depotName).registerPopup(new IPopupBuilder() {

            @Override
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            @Override
            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "cangku/";
            }

            @Override
            public String getConditionSQL() {
                String sql = "";
                if (!depottb$depotName.getText().trim().equals("")) {
                    sql = "(depot_name like \"%" + depottb$depotName.getText() + "%\""+" or zujima like \""+depottb$depotName.getText().toLowerCase() + "%\")";
                }
                return sql;
            }

            @Override
            public String[][] displayColumns() {
                return new String[][]{{"depotName", "仓库名"}, {"depotArea", "面积"}};
            }

            @Override
            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    depottb$depotName.setText(bindedMap.get("depotName").toString());
                    jTextFieldDepotID.setText(bindedMap.get("depotId").toString());
                }
            }
        });

        ((BaseTextField) suppliertb$supplierName).registerPopup(new IPopupBuilder() {

            @Override
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            @Override
            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "supplier/";
            }

            @Override
            public String getConditionSQL() {
                String sql = "";
                if (!suppliertb$supplierName.getText().trim().equals("")) {
                    sql = "(supplier_name like \"%" + suppliertb$supplierName.getText() + "%\"" + " or supplier_zujima like \"" +  suppliertb$supplierName.getText().trim().toLowerCase() + "%\")";
                }
                return sql;
            }

            @Override
            public String[][] displayColumns() {
                return new String[][]{{"supplierName", "供应商"}, {"supplierConstactperson", "联系人"}};
            }

            @Override
            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    suppliertb$supplierName.setText(bindedMap.get("supplierName").toString());
                    jTextFieldSupplier.setText(bindedMap.get("supplierId").toString());
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

        jTextFieldDepotID = new javax.swing.JTextField();
        jTextFieldSupplier = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        yimiaoName = new javax.swing.JTextField();
        yimiaoGuige = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        yimiaoYushoujia = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        yimiaoType = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        yimiaoJixing = new javax.swing.JComboBox();
        unitId = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        yimiaoShengchanqiye = new javax.swing.JTextField();
        yimiaoPizhunwenhao = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        yimiaoPicture = new javax.swing.JList();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        yimiaoStockdown = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        yimiaoStockup = new javax.swing.JTextField();
        depottb$depotName = new BaseTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        suppliertb$supplierName = new BaseTextField();
        jButton5 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        yimiaoRemark = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        yimiaoChufangbiaoji = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        yimiaoTiaoxingma = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        yimiaoFuzhuunit = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        yimiaoHuansuanlv = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jCheckBoxCont = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoInfoJDialog.class);
        jTextFieldDepotID.setText(resourceMap.getString("jTextFieldDepotID.text")); // NOI18N
        jTextFieldDepotID.setName("jTextFieldDepotID"); // NOI18N

        jTextFieldSupplier.setText(resourceMap.getString("jTextFieldSupplier.text")); // NOI18N
        jTextFieldSupplier.setName("jTextFieldSupplier"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel3.border.title"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        yimiaoName.setName("yimiaoName"); // NOI18N

        yimiaoGuige.setName("yimiaoGuige"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        yimiaoYushoujia.setName("yimiaoYushoujia"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoInfoJDialog.class, this);
        jButton6.setAction(actionMap.get("deletePic")); // NOI18N
        jButton6.setText(resourceMap.getString("jButton6.text")); // NOI18N
        jButton6.setName("jButton6"); // NOI18N

        jButton7.setAction(actionMap.get("uploadPic")); // NOI18N
        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setName("jButton7"); // NOI18N

        yimiaoType.setName("yimiaoType"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        yimiaoJixing.setName("yimiaoJixing"); // NOI18N

        unitId.setName("unitId"); // NOI18N

        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        yimiaoShengchanqiye.setName("yimiaoShengchanqiye"); // NOI18N

        yimiaoPizhunwenhao.setText(resourceMap.getString("yimiaoPizhunwenhao.text")); // NOI18N
        yimiaoPizhunwenhao.setName("yimiaoPizhunwenhao"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jButton1.setAction(actionMap.get("imagePreview")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        yimiaoPicture.setName("yimiaoPicture"); // NOI18N
        jScrollPane3.setViewportView(yimiaoPicture);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(yimiaoShengchanqiye, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(unitId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(yimiaoGuige, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel10)
                            .addGap(18, 18, 18)
                            .addComponent(yimiaoJixing, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(yimiaoName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(yimiaoPizhunwenhao, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(yimiaoYushoujia))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7)
                            .addComponent(jButton6)
                            .addComponent(jButton1)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yimiaoType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(yimiaoName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(yimiaoType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(yimiaoGuige, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel10)
                            .addComponent(yimiaoJixing, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yimiaoPizhunwenhao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(unitId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(yimiaoYushoujia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(yimiaoShengchanqiye, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addGap(3, 3, 3)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel4.border.title"))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        yimiaoStockdown.setName("yimiaoStockdown"); // NOI18N

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        yimiaoStockup.setName("yimiaoStockup"); // NOI18N

        depottb$depotName.setName("depottb$depotName"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        suppliertb$supplierName.setName("suppliertb$supplierName"); // NOI18N

        jButton5.setAction(actionMap.get("generatorBar")); // NOI18N
        jButton5.setIcon(resourceMap.getIcon("jButton5.icon")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setName("jButton5"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        yimiaoRemark.setColumns(20);
        yimiaoRemark.setRows(2);
        yimiaoRemark.setName("yimiaoRemark"); // NOI18N
        jScrollPane1.setViewportView(yimiaoRemark);

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        yimiaoChufangbiaoji.setName("yimiaoChufangbiaoji"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        yimiaoTiaoxingma.setEnabled(false);
        yimiaoTiaoxingma.setName("yimiaoTiaoxingma"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(depottb$depotName)
                            .addComponent(suppliertb$supplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yimiaoChufangbiaoji, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yimiaoTiaoxingma))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yimiaoStockdown, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yimiaoStockup, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(depottb$depotName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel15)
                    .addComponent(yimiaoStockdown, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(yimiaoStockup, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(suppliertb$supplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6)
                    .addComponent(yimiaoChufangbiaoji, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(yimiaoTiaoxingma, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel5.border.title"))); // NOI18N
        jPanel5.setName("jPanel5"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        yimiaoFuzhuunit.setName("yimiaoFuzhuunit"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        yimiaoHuansuanlv.setName("yimiaoHuansuanlv"); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(yimiaoFuzhuunit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(yimiaoHuansuanlv, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(yimiaoFuzhuunit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(yimiaoHuansuanlv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setName("jPanel2"); // NOI18N

        jButton4.setAction(actionMap.get("close")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N

        jCheckBoxCont.setText(resourceMap.getString("jCheckBoxCont.text")); // NOI18N
        jCheckBoxCont.setName("jCheckBoxCont"); // NOI18N

        jButton3.setAction(actionMap.get("submitForm")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBoxCont)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 634, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jCheckBoxCont)
                .addComponent(jButton3)
                .addComponent(jButton4))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(2, 2, 2))
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                YiMiaoDengJi2JDialog dialog = new YiMiaoDengJi2JDialog(new javax.swing.JFrame(), true);
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

    public void setUpdatedData(YimiaoAll paramData) {
        this.appParam = paramData;
        if (paramData == null) {
            return;
        }

        yimiaoPicture.setModel(new BaseListModel<String>(new ArrayList(), ""));
        jTextFieldDepotID.setText("");
        jTextFieldSupplier.setText("");
        //自动帮定所有的值
        super.bind(paramData, jPanel3);
        super.bind(paramData, jPanel4);
        super.bind(paramData, jPanel5);

        if (appParam.getYimiaoId() == null || appParam.getYimiaoId() <= 0) { //新建
            jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("疫苗信息")); // NOI18N
            //jCheckBox2.setSelected(false);
            jCheckBoxCont.setEnabled(true);
            yimiaoTiaoxingma.setText(DanHao.getDanHao("YM"));
        } else {//更新
            jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("修改疫苗:" + appParam.getYimiaoId())); // NOI18N
            jCheckBoxCont.setSelected(false);
            jCheckBoxCont.setEnabled(false);

            if (appParam.getSupplierId() != null) {
                int supplierId = appParam.getSupplierId();
                if (supplierId > 0) {
                    jTextFieldSupplier.setText(String.valueOf(supplierId));
                }
            }
            if (appParam.getYimiaoId() != null) {
                int deportId = appParam.getYimiaoId();
                if (deportId > 0) {
                    jTextFieldDepotID.setText(String.valueOf(deportId));
                }
            }

            String imagePaths = paramData.getYimiaoPicture();
            if (imagePaths != null && !imagePaths.trim().equals("")) {
                BaseListModel<String> mode = (BaseListModel<String>) yimiaoPicture.getModel();
                List source = mode.getSource();
                String[] images = imagePaths.split(";");
                for (String m : images) {
                    source.add(m);
                }
                BaseListModel<String> newMode = new BaseListModel<String>(source, "");
                yimiaoPicture.setModel(newMode);
            }
        }

    }

    @Action
    public Task submitForm() {
        if (yimiaoName.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入疫苗名称!");
            return null;
        } else if (yimiaoGuige.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入疫苗规格!");
            return null;
        } else if (yimiaoShengchanqiye.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入生产企业!");
            return null;
        } else if (yimiaoJixing.getSelectedItem().toString().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入疫苗剂型!");
            return null;
        }

        String zujima = PingYinUtil.getFirstSpell(yimiaoName.getText().trim());
        appParam.setZujima(zujima);

        super.copyToBean(appParam, jPanel3);
        super.copyToBean(appParam, jPanel4);
        super.copyToBean(appParam, jPanel5);

        String depotId = jTextFieldDepotID.getText();
        String supplierId = jTextFieldSupplier.getText();

        if (!depotId.trim().equals("")) {
            appParam.setYimiaoMorencangku(Integer.parseInt(depotId));
        }
        if (!supplierId.trim().equals("")) {
            appParam.setSupplierId(Integer.parseInt(supplierId));
        }

        /*得到图片路径*/
        BaseListModel<String> mode = (BaseListModel<String>) yimiaoPicture.getModel();
        List source = mode.getSource();
        String imgPaths = "";
        for (int i = 0; i < source.size(); i++) {
            if (i == (source.size() - 1)) {
                imgPaths += source.get(i).toString();
            } else {
                imgPaths += source.get(i) + ";";
            }
        }
        appParam.setYimiaoPicture(imgPaths);

        String serviceId = "yimiao/add";
        if (appParam.getYimiaoId() != null && appParam.getYimiaoId() > 0) {
            serviceId = "yimiao/update";
        }

        return new CommUpdateTask<YimiaoAll>(appParam, serviceId) {
            @Override
            public void responseResult(ComResponse<YimiaoAll> response) {
                if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                    parentPanel.reload().execute();
                    if (!jCheckBoxCont.isSelected()) {
                        dispose();
                    } else {
                        BaseListModel<String> mode = (BaseListModel<String>) yimiaoPicture.getModel();
                        List<String> source = mode.getSource();
                        source.clear();
                        BaseListModel<String> newMode = new BaseListModel<String>(source, "");
                        yimiaoPicture.setModel(newMode);
                    }
                } else {
                    AssetMessage.ERROR(response.getErrorMessage(), YiMiaoInfoJDialog.this);
                }
            }

        };
    }

    @Action
    public Task uploadPic() {
        BaseFileChoose fileChoose = new BaseFileChoose(new String[]{"png", "jpg", "gif", "bmp"}, this);
        String selectedPath = fileChoose.openDialog();
        if (!selectedPath.trim().equals("")) {
            addObjectToList("uploading...");
            return new FileTask(FileTask.TYPE_UPLOAD, selectedPath, "yimiao") {
                @Override
                public void responseResult(String file) {
                    removeObjectFromList("uploading...");
                    BaseListModel<String> mode = (BaseListModel<String>) yimiaoPicture.getModel();
                    List source = mode.getSource();
                    if (source.contains(file)) {
                        return;
                    }
                    source.add(file);
                    BaseListModel<String> newMode = new BaseListModel<String>(source, "");
                    yimiaoPicture.setModel(newMode);
                }
            };
        }
        return null;
    }

    private void addObjectToList(String name) {

        BaseListModel<String> mode = (BaseListModel<String>) yimiaoPicture.getModel();
        List source = mode.getSource();
        if (source.contains(name)) {
            return;
        }
        source.add(name);
        BaseListModel<String> newMode = new BaseListModel<String>(source, "");
        yimiaoPicture.setModel(newMode);
    }

    private void removeObjectFromList(String name) {
        BaseListModel<String> mode = (BaseListModel<String>) yimiaoPicture.getModel();
        List<String> source = mode.getSource();
        source.remove(name);
        BaseListModel<String> newMode = new BaseListModel<String>(source, "");
        yimiaoPicture.setModel(newMode);
    }

    @Action
    public Task deletePic() {
        Object selectedValue = yimiaoPicture.getSelectedValue();
        if (selectedValue == null) {
            return null;
        }
        removeObjectFromList(selectedValue.toString());
        if (!selectedValue.toString().equals("")) {
            return new FileTask(FileTask.TYPE_DELETE, selectedValue.toString(), "yimiao") {
                @Override
                public void responseResult(String file) {
                }
            };
        }
        return null;
    }

    @Action
    public void close() {
        this.dispose();

        BaseListModel<String> mode = (BaseListModel<String>) yimiaoPicture.getModel();
        List<String> source = mode.getSource();
        if (source.size() > 0) {
            for (int i = 0; i < source.size(); i++) {
                if (!source.get(i).equals("")) {
                    FileTask task = new FileTask(FileTask.TYPE_DELETE, source.get(i), "yimiao") {
                        @Override
                        public void responseResult(String file) {
                            removeObjectFromList(file);
                        }
                    };
                    task.execute();
                }
            }
        }
    }

    @Action
    public Task imagePreview() {
        final Object obj = yimiaoPicture.getSelectedValue();
        if (obj != null) {
            return new FileTask(FileTask.TYPE_DOWNLOAD, obj.toString(), "yimiao") {
                @Override
                public void responseResult(final String file) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                            ImagePreview imagePreview = new ImagePreview(file, true);
                            imagePreview.setLocationRelativeTo(mainFrame);
                            AssetClientApp.getApplication().show(imagePreview);
                        }
                    });
                }
            };
        }
        return null;
    }

    @Action
    public void generatorBar() {
        String barcode = yimiaoTiaoxingma.getText();
        String label = yimiaoName.getText();
        if (barcode == null) {
            return;
        }
        if (label.trim().equals("")) {
            int result = AssetMessage.CONFIRM(this, "没有标签名，确定打印吗?");
            if (result != AssetMessage.OK_OPTION) {
                yimiaoName.grabFocus();
                return;
            }
        }
        DanHao.printBarCode128(label, barcode);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField depottb$depotName;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBoxCont;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextFieldDepotID;
    private javax.swing.JTextField jTextFieldSupplier;
    private javax.swing.JTextField suppliertb$supplierName;
    private javax.swing.JComboBox unitId;
    private javax.swing.JTextField yimiaoChufangbiaoji;
    private javax.swing.JComboBox yimiaoFuzhuunit;
    private javax.swing.JTextField yimiaoGuige;
    private javax.swing.JTextField yimiaoHuansuanlv;
    private javax.swing.JComboBox yimiaoJixing;
    private javax.swing.JTextField yimiaoName;
    private javax.swing.JList yimiaoPicture;
    private javax.swing.JTextField yimiaoPizhunwenhao;
    private javax.swing.JTextArea yimiaoRemark;
    private javax.swing.JTextField yimiaoShengchanqiye;
    private javax.swing.JTextField yimiaoStockdown;
    private javax.swing.JTextField yimiaoStockup;
    private javax.swing.JTextField yimiaoTiaoxingma;
    private javax.swing.JComboBox yimiaoType;
    private javax.swing.JTextField yimiaoYushoujia;
    // End of variables declaration//GEN-END:variables
}
