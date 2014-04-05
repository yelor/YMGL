/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UserPanel.java
 *
 * Created on Feb 21, 2010, 10:42:18 PM
 */
package com.jskj.asset.client.panel.user;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Usertb;
import com.jskj.asset.client.layout.BasePanel;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.layout.ws.CommUpdateTask;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author woderchen
 */
public final class SelfPanel extends BasePanel {
    
    private final static Logger logger = Logger.getLogger(SelfPanel.class);

    /**
     * Creates new form NoFoundPane
     */
    public SelfPanel() {
        super();
        initComponents();
    }
    
    @Action
    @Override
    public Task reload() {
        return null;
    }
    
    @Override
    public Task reload(Object param) {
        return null;
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ctrlPane = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jLabelTotal = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPasswordFieldOld = new javax.swing.JPasswordField();
        jPasswordFieldNew = new javax.swing.JPasswordField();
        jPasswordFieldRenew = new javax.swing.JPasswordField();
        jLabelMsg = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        ctrlPane.setName("ctrlPane"); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(SelfPanel.class, this);
        jButton1.setAction(actionMap.get("updateUser")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(SelfPanel.class);
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jToolBar1.add(jButton1);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setBorderPainted(false);
        jToolBar2.setName("jToolBar2"); // NOI18N
        jToolBar2.setOpaque(false);

        jLabelTotal.setForeground(resourceMap.getColor("jLabelTotal.foreground")); // NOI18N
        jLabelTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTotal.setText(resourceMap.getString("jLabelTotal.text")); // NOI18N
        jLabelTotal.setName("jLabelTotal"); // NOI18N

        javax.swing.GroupLayout ctrlPaneLayout = new javax.swing.GroupLayout(ctrlPane);
        ctrlPane.setLayout(ctrlPaneLayout);
        ctrlPaneLayout.setHorizontalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctrlPaneLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        ctrlPaneLayout.setVerticalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jLabelTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jPasswordFieldOld.setText(resourceMap.getString("jPasswordFieldOld.text")); // NOI18N
        jPasswordFieldOld.setName("jPasswordFieldOld"); // NOI18N

        jPasswordFieldNew.setText(resourceMap.getString("jPasswordFieldNew.text")); // NOI18N
        jPasswordFieldNew.setName("jPasswordFieldNew"); // NOI18N

        jPasswordFieldRenew.setText(resourceMap.getString("jPasswordFieldRenew.text")); // NOI18N
        jPasswordFieldRenew.setName("jPasswordFieldRenew"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPasswordFieldNew, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordFieldOld, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordFieldRenew, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jPasswordFieldOld, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jPasswordFieldNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordFieldRenew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelMsg.setForeground(resourceMap.getColor("jLabelMsg.foreground")); // NOI18N
        jLabelMsg.setText(resourceMap.getString("jLabelMsg.text")); // NOI18N
        jLabelMsg.setName("jLabelMsg"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ctrlPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelMsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ctrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(215, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public Task updateUser() {
        jLabelMsg.setText("");
        String passwordold = String.valueOf(jPasswordFieldOld.getPassword());
        String passwordnew = String.valueOf(jPasswordFieldNew.getPassword());
        String passwordrenew = String.valueOf(jPasswordFieldRenew.getPassword());
        Usertb usertb = AssetClientApp.getSessionMap().getUsertb();
        if (!passwordold.equals(usertb.getUserPassword())) {
            jLabelMsg.setText("旧密码输入错误.");
            jPasswordFieldOld.grabFocus();
            return null;
        }
        
        if (!passwordnew.equals(passwordrenew)) {
            jLabelMsg.setText("二次密码输入不一致.");
            jPasswordFieldNew.grabFocus();
            return null;
        }
        
        usertb.setUserPassword(passwordnew);
        
        return new CommUpdateTask<Usertb>(usertb, "user/update") {
            @Override
            public void responseResult(ComResponse<Usertb> response) {
                if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                    jLabelMsg.setText("<html><font color=green>密码修改完毕.下次登录请使用正确的密码.</font></html>");
                } else {
                    jLabelMsg.setText("ERROR" + response.getErrorMessage());
                }
            }
            
        };
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ctrlPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelMsg;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordFieldNew;
    private javax.swing.JPasswordField jPasswordFieldOld;
    private javax.swing.JPasswordField jPasswordFieldRenew;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables
}
