/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.ckgl;

import com.jskj.asset.client.AssetClientApp;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jdesktop.application.Action;

/**
 *
 * @author Administrator
 */
public class SelectYiMiaoChuRuKu extends javax.swing.JDialog {
    YiMiaoChuRuKu1 ymcrk1;
    YiMiaoChuRuKu2 ymcrk2;

    /**
     * Creates new form ymcrk
     */
    public SelectYiMiaoChuRuKu(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N
        setResizable(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(SelectYiMiaoChuRuKu.class, this);
        jButton1.setAction(actionMap.get("ck1_pop")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(SelectYiMiaoChuRuKu.class);
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jButton2.setAction(actionMap.get("ck2_pop")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jButton3.setAction(actionMap.get("rk1_pop")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        jButton4.setAction(actionMap.get("rk2_pop")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addGap(59, 59, 59))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(46, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(SelectYiMiaoChuRuKu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectYiMiaoChuRuKu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectYiMiaoChuRuKu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectYiMiaoChuRuKu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SelectYiMiaoChuRuKu dialog = new SelectYiMiaoChuRuKu(new javax.swing.JFrame(), true);
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
    public void ck1_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(ymcrk1==null){
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    ymcrk1 = new YiMiaoChuRuKu1(new javax.swing.JFrame(), true);
                    ymcrk1.setLocationRelativeTo(mainFrame);
                }
                ymcrk1.setCk();
                AssetClientApp.getApplication().show(ymcrk1);
            }
        });
    }
    
    @Action
    public void rk1_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(ymcrk1==null){
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    ymcrk1 = new YiMiaoChuRuKu1(new javax.swing.JFrame(), true);
                    ymcrk1.setLocationRelativeTo(mainFrame);
                }
                ymcrk1.setRk();
                AssetClientApp.getApplication().show(ymcrk1);
            }
        });
    }

    @Action
    public void ck2_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(ymcrk2==null){
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    ymcrk2 = new YiMiaoChuRuKu2(new javax.swing.JFrame(), true);
                    ymcrk2.setLocationRelativeTo(mainFrame);
                }
                ymcrk2.setCk();
                AssetClientApp.getApplication().show(ymcrk2);
            }
        });
    }
    
    @Action
    public void rk2_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(ymcrk2==null){
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    ymcrk2 = new YiMiaoChuRuKu2(new javax.swing.JFrame(), true);
                    ymcrk2.setLocationRelativeTo(mainFrame);
                }
                ymcrk2.setRk();
                AssetClientApp.getApplication().show(ymcrk2);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    // End of variables declaration//GEN-END:variables
}
