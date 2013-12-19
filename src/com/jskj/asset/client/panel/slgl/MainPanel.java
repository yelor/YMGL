/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NoFoundPanel.java
 *
 * Created on Feb 21, 2010, 10:42:18 PM
 */
package com.jskj.asset.client.panel.slgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.panel.*;
import com.jskj.asset.client.layout.BasePanel;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author woderchen
 */
public class MainPanel extends BasePanel {

    /**
     * Creates new form NoFoundPane
     */
    public MainPanel() {
        super();
        initComponents();
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
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setName("Form"); // NOI18N

        ctrlPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ctrlPane.setName("ctrlPane"); // NOI18N

        javax.swing.GroupLayout ctrlPaneLayout = new javax.swing.GroupLayout(ctrlPane);
        ctrlPane.setLayout(ctrlPaneLayout);
        ctrlPaneLayout.setHorizontalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 506, Short.MAX_VALUE)
        );
        ctrlPaneLayout.setVerticalGroup(
            ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jPanel1.setName("jPanel1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(MainPanel.class);
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(MainPanel.class, this);
        jButton2.setAction(actionMap.get("selectLingYongDanAction")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jButton3.setAction(actionMap.get("selectYanShouDengJiDanAction")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        jButton4.setAction(actionMap.get("selectCaiGouDanAction")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N

        jButton5.setAction(actionMap.get("selecteWeiXiuDiaoBoDanAction")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setName("jButton5"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(78, 78, 78))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(177, 177, 177)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(51, 51, 51)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addGap(56, 56, 56))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ctrlPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ctrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ctrlPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    @Override
    public Task reload() {
        return null;
    }

    @Override
    public Task reload(Object param) {
        return null;
    }

    @Action
    public void selectCaiGouDanAction() {
        SwingUtilities.invokeLater(new Runnable() {
            private selectCaiGouDanJDialog selectCaiGouDanJDialog;

            @Override
            public void run() {
                if (selectCaiGouDanJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    selectCaiGouDanJDialog = new selectCaiGouDanJDialog(new javax.swing.JFrame(), true);
                    selectCaiGouDanJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(selectCaiGouDanJDialog);
            }
        });
    }

    @Action
    public void selectLingYongDanAction() {
        SwingUtilities.invokeLater(new Runnable() {
            private selectCaiGouDanJDialog selectCaiGouDanJDialog;
            private selectLingYongDanJDialog selectLingYongDanJDialog;

            @Override
            public void run() {
                if (selectLingYongDanJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    selectLingYongDanJDialog = new selectLingYongDanJDialog(new javax.swing.JFrame(), true);
                    selectLingYongDanJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(selectLingYongDanJDialog);
            }
        });
    }

    @Action
    public void selectYanShouDengJiDanAction() {
        SwingUtilities.invokeLater(new Runnable() {
            private selectYanShouDengJiDanJDialog selectYanShouDengJiDanJDialog;

            @Override
            public void run() {
                if (selectYanShouDengJiDanJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    selectYanShouDengJiDanJDialog = new selectYanShouDengJiDanJDialog(new javax.swing.JFrame(), true);
                    selectYanShouDengJiDanJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(selectYanShouDengJiDanJDialog);
            }
        });
    }

    @Action
    public void selecteWeiXiuDiaoBoDanAction() {
        SwingUtilities.invokeLater(new Runnable() {
            private selectWeiXiuDiaoBoDanJDialog selectWeiXiuDiaoBoDanJDialog;
            @Override
            public void run() {
                if (selectWeiXiuDiaoBoDanJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    selectWeiXiuDiaoBoDanJDialog = new selectWeiXiuDiaoBoDanJDialog(new javax.swing.JFrame(), true);
                    selectWeiXiuDiaoBoDanJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(selectWeiXiuDiaoBoDanJDialog);
            }
        });
    }
}
