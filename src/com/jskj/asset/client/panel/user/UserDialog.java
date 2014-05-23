/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.user;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Appparam;
import com.jskj.asset.client.bean.entity.DepartmentFindEntity;
import com.jskj.asset.client.bean.entity.DepartmenttbAll;
import com.jskj.asset.client.bean.entity.Usertb;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseListModel;
import com.jskj.asset.client.layout.BasePanel;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.layout.ws.CommUpdateTask;
import com.jskj.asset.client.panel.jichuxinxi.task.BuMenTask;
import com.jskj.asset.client.util.DateHelper;
import com.jskj.asset.client.util.IdcardUtils;
import com.jskj.asset.client.util.PingYinUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author 305027939
 */
public class UserDialog extends BaseDialog {

    private static final Logger logger = Logger.getLogger(UserDialog.class);
    private final BasePanel parentPanel;
    private Usertb usertb;
    private boolean isNew;
    private List<DepartmenttbAll> departments;

    /**
     * Creates new form UserDialog
     *
     * @param parentPanel
     */
    public UserDialog(BasePanel parentPanel) {
        super();
        initComponents();
        this.parentPanel = parentPanel;
        isNew = true;
    }

    public void setAddOrUpdate(boolean isAdd) {
        isNew = isAdd;
//        jListRoles.setModel(new BaseListModel<String>(new ArrayList(), ""));
        //得到部门和角色
        new BumenTask().execute();
        jComboBoxRole.setModel(new javax.swing.DefaultComboBoxModel(AssetClientApp.getParamNamesByType("角色")));
        if (isNew) {
            this.setTitle("新建用户");
            jTextFieldUserName.setText("");
            jTextFieldPasswd.setText("");
            jTextFieldPhone.setText("");
            jRadioButton1.setSelected(true);
            jTextFieldIDCard.setText("");
            jTextFieldAddress.setText("");
            jTextFieldEmail.setText("");
            jTextAreaDesc.setText("");
            jCheckBox1.setEnabled(true);
            jCheckBox1.setSelected(false);
            usertb = new Usertb();
            jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("新建用户")); // NOI18N
        } else {
            this.setTitle("更新用户:10");
            jCheckBox1.setEnabled(false);
            jCheckBox1.setSelected(false);
        }
    }

    public void setUpdatedData(Usertb usertb) {
        if (usertb == null) {
            return;
        }
        this.usertb = usertb;
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("更新用户:" + usertb.getUserId())); // NOI18N
        jTextFieldUserName.setText(usertb.getUserName());
        jTextFieldPasswd.setText(usertb.getUserPassword());
        jTextFieldPhone.setText(usertb.getUserPhone());
        jRadioButton1.setSelected(usertb.getUserSex().equals("男"));
        jTextFieldIDCard.setText(usertb.getUserIdentitycard());
        jTextFieldAddress.setText(usertb.getUserPosition());

        String rolesStr = usertb.getUserRoles();
        jComboBoxRole.setSelectedItem(rolesStr);
//
//        if (rolesStr != null && !rolesStr.equals("")) {
//            String[] rolesArr = rolesStr.split(",");
//            List arrayList = new ArrayList(Arrays.asList(rolesArr));
////            jListRoles.setModel(new BaseListModel<String>(arrayList, ""));
//        }

    }

    private class BumenTask extends BuMenTask {

        @Override
        public void onSucceeded(Object object) {
            if (object instanceof Exception) {
                Exception e = (Exception) object;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            if (object != null && object instanceof DepartmentFindEntity) {
                DepartmentFindEntity depart = (DepartmentFindEntity) object;
                departments = depart.getResult();
                jListBumen.setModel(new BaseListModel<DepartmenttbAll>(departments, "getDepartmentName"));

                if (usertb != null && usertb.getDepartmentId() != null && usertb.getDepartmentId() > 0) {
                    jListBumen.setSelectedValue(getNameByDepartmentID(usertb.getDepartmentId()), true);
                }

            } else {
                logger.error("BumenTask returns null or not a DepartmentFindEntity object.");
            }
        }
    }

    public String getNameByDepartmentID(int depID) {
        if (departments != null) {
            for (DepartmenttbAll dep : departments) {
                if (dep.getDepartmentId() == depID) {
                    return dep.getDepartmentName();
                }
            }
        }
        return "";
    }

    public int getIDByDepartmentName(String name) {
        if (departments != null) {
            for (DepartmenttbAll dep : departments) {
                if (dep.getDepartmentName().equalsIgnoreCase(name)) {
                    return dep.getDepartmentId();
                }
            }
        }
        return 0;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldUserName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldPasswd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldIDCard = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldPhone = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldAddress = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDesc = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListBumen = new javax.swing.JList();
        jLabel10 = new javax.swing.JLabel();
        jComboBoxRole = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N
        setResizable(false);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(UserDialog.class);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextFieldUserName.setText(resourceMap.getString("jTextFieldUserName.text")); // NOI18N
        jTextFieldUserName.setName("jTextFieldUserName"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextFieldPasswd.setText(resourceMap.getString("jTextFieldPasswd.text")); // NOI18N
        jTextFieldPasswd.setName("jTextFieldPasswd"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText(resourceMap.getString("jRadioButton1.text")); // NOI18N
        jRadioButton1.setName("jRadioButton1"); // NOI18N

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText(resourceMap.getString("jRadioButton2.text")); // NOI18N
        jRadioButton2.setName("jRadioButton2"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextFieldIDCard.setText(resourceMap.getString("jTextFieldIDCard.text")); // NOI18N
        jTextFieldIDCard.setName("jTextFieldIDCard"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jTextFieldPhone.setText(resourceMap.getString("jTextFieldPhone.text")); // NOI18N
        jTextFieldPhone.setName("jTextFieldPhone"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jTextFieldEmail.setText(resourceMap.getString("jTextFieldEmail.text")); // NOI18N
        jTextFieldEmail.setName("jTextFieldEmail"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jTextFieldAddress.setText(resourceMap.getString("jTextFieldAddress.text")); // NOI18N
        jTextFieldAddress.setName("jTextFieldAddress"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextAreaDesc.setColumns(20);
        jTextAreaDesc.setRows(5);
        jTextAreaDesc.setName("jTextAreaDesc"); // NOI18N
        jScrollPane1.setViewportView(jTextAreaDesc);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel8)
                    .add(jLabel7)
                    .add(jLabel5)
                    .add(jLabel3)
                    .add(jLabel1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jTextFieldPhone, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabel6)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldEmail))
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jPanel1Layout.createSequentialGroup()
                            .add(jRadioButton1)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                            .add(jRadioButton2)
                            .add(18, 18, 18)
                            .add(jLabel4)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jTextFieldIDCard, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 217, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jPanel1Layout.createSequentialGroup()
                            .add(jTextFieldUserName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 186, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jLabel2)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jTextFieldPasswd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jScrollPane1)
                    .add(jTextFieldAddress))
                .add(0, 10, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jTextFieldUserName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2)
                    .add(jTextFieldPasswd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(jRadioButton1)
                    .add(jRadioButton2)
                    .add(jLabel4)
                    .add(jTextFieldIDCard, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(jTextFieldPhone, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel6)
                    .add(jTextFieldEmail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel7)
                    .add(jTextFieldAddress, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel8)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(UserDialog.class, this);
        jButton3.setAction(actionMap.get("exit")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        jButton4.setAction(actionMap.get("submitForm")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel2.border.title"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jListBumen.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListBumen.setName("jListBumen"); // NOI18N
        jScrollPane2.setViewportView(jListBumen);

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jComboBoxRole.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "普通用户", "审核权", "审批权", "管理权" }));
        jComboBoxRole.setName("jComboBoxRole"); // NOI18N

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabel10)
                            .add(jLabel9))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jComboBoxRole, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 103, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 260, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(22, 22, 22)
                        .add(jButton1)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel9))
                .add(18, 18, 18)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jComboBoxRole, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel10))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton1)
                .add(0, 0, Short.MAX_VALUE))
        );

        jCheckBox1.setText(resourceMap.getString("jCheckBox1.text")); // NOI18N
        jCheckBox1.setName("jCheckBox1"); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jCheckBox1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jButton4)
                .add(18, 18, 18)
                .add(jButton3)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jButton3)
                        .add(jButton4))
                    .add(jCheckBox1))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(UserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                UserDialog dialog = new UserDialog(new javax.swing.JFrame());
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
    }

    @Action
    public void exit() {
        this.dispose();
    }

    @Action
    public Task submitForm() {
        if (jTextFieldUserName.getText().trim().equals("")
                || jTextFieldPasswd.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入用户名和密码.");
            return null;
        }
        
        String zujima = PingYinUtil.getFirstSpell(jTextFieldUserName.getText().trim());
        usertb.setZujima(zujima);

        String idcard = jTextFieldIDCard.getText();
        if (!idcard.trim().equals("")) {
            if (IdcardUtils.validateCard(idcard)) {
                usertb.setUserIdentitycard(idcard);
               // usertb.setUserBirthday(DateHelper.getStringtoDate(IdcardUtils.getBirthByIdCard(idcard), "yyyyMMdd"));
            } else {
                AssetMessage.ERRORSYS("身份证号码有误，请检查.");
                return null;
            }
        } 

        Object selectObj = jListBumen.getSelectedValue();
        if (selectObj != null) {
            usertb.setDepartmentId(getIDByDepartmentName(selectObj.toString()));
        } else {
            AssetMessage.ERRORSYS("请选择部门.");
            return null;
        }

        usertb.setUserName(jTextFieldUserName.getText());
        usertb.setUserPassword(jTextFieldPasswd.getText());
        usertb.setUserPhone(jTextFieldPhone.getText());
        usertb.setUserSex(jRadioButton1.isSelected() ? "男" : "女");
        usertb.setUserIdentitycard(jTextFieldIDCard.getText());
        usertb.setUserEmail(jTextFieldEmail.getText());
        if (isNew) {
            usertb.setUserEntrydate(new Date());
        }
        usertb.setUserPosition(jTextFieldAddress.getText());

        
        usertb.setUserRoles(jComboBoxRole.getSelectedItem().toString());

        String serviceId = "user/add";
        if (usertb.getUserId() != null && usertb.getUserId() > 0) {
            serviceId = "user/update";
        }

        return new CommUpdateTask<Usertb>(usertb, serviceId) {
            @Override
            public void responseResult(ComResponse<Usertb> response) {
                if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                    parentPanel.reload().execute();
                    exit();
                    AssetMessage.showMessageDialog(null, "保存成功！");
                    if (jCheckBox1.isSelected()) {
                        JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                        UserDialog userDialog = new UserDialog(parentPanel);
                        userDialog.setLocationRelativeTo(mainFrame);
                        userDialog.setAddOrUpdate(true);
                        AssetClientApp.getApplication().show(userDialog);
                    }
                } else {
                    AssetMessage.ERROR(response.getErrorMessage(), UserDialog.this);
                }
            }

        };
    }

//    private class SubmitFormTask extends UserUpdateTask {
//
//        SubmitFormTask(Usertb usertb) {
//            super(usertb, isNew ? UserUpdateTask.ENTITY_SAVE : UserUpdateTask.ENTITY_UPDATE);
//        }
//
//        @Override
//        protected void succeeded(Object result) {
//            if (result instanceof Exception) {
//                Exception e = (Exception) result;
//                AssetMessage.ERRORSYS(e.getMessage());
//                logger.error(e);
//                return;
//            }
//
//            parentPanel.reload().execute();
//            if (!jCheckBox1.isSelected()) {
//                exit();
//            } else {
//                jTextFieldUserName.setText("");
//                jTextFieldIDCard.setText("");
//            }
//        }
//    }

//    @Action
//    public void addRole() {
//        Object item = jComboBoxRole.getSelectedItem();
//        if (item != null) {
//            BaseListModel<String> mode = (BaseListModel<String>) jListRoles.getModel();
//            List source = mode.getSource();
//
//            if (source.contains(item)) {
//                return;
//            }
//
//            source.add(item);
//            BaseListModel<String> newMode = new BaseListModel<String>(source, "");
//            jListRoles.setModel(newMode);
//        }
//    }
//
//    @Action
//    public void removeRole() {
//        Object selectedValue = jListRoles.getSelectedValue();
//        if (selectedValue == null) {
//            return;
//        }
//        BaseListModel<String> mode = (BaseListModel<String>) jListRoles.getModel();
//        List<String> source = mode.getSource();
//        source.remove(selectedValue.toString());
//        BaseListModel<String> newMode = new BaseListModel<String>(source, "");
//        jListRoles.setModel(newMode);
//    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBoxRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jListBumen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaDesc;
    private javax.swing.JTextField jTextFieldAddress;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldIDCard;
    private javax.swing.JTextField jTextFieldPasswd;
    private javax.swing.JTextField jTextFieldPhone;
    private javax.swing.JTextField jTextFieldUserName;
    // End of variables declaration//GEN-END:variables
}
