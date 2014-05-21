/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ckgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.panel.slgl.GuDingZiChanChuKuJDialog;
import com.jskj.asset.client.panel.slgl.GuDingZiChanRuKuJDialog;
import com.jskj.asset.client.panel.slgl.GuDingZiChanTuiKuJDialog;
import com.jskj.asset.client.panel.slgl.YihaopinChuKuJDialog;
import com.jskj.asset.client.panel.slgl.YihaopinRuKuJDialog;
import com.jskj.asset.client.panel.slgl.YihaopinTuiKuJDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jdesktop.application.Action;

/**
 *
 * @author Administrator
 */
public class SelectYiMiaoChuRuKuJDialog extends BaseDialog {

    YiMiaoRuKu2JDialog ymcrk2;

    /**
     * Creates new form ymcrk
     */
    public SelectYiMiaoChuRuKuJDialog() {
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

        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(SelectYiMiaoChuRuKuJDialog.class, this);
        jButton9.setAction(actionMap.get("zichantuiku_pop")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(SelectYiMiaoChuRuKuJDialog.class);
        jButton9.setIcon(resourceMap.getIcon("jButton9.icon")); // NOI18N
        jButton9.setText(resourceMap.getString("jButton9.text")); // NOI18N
        jButton9.setName("jButton9"); // NOI18N

        jButton10.setAction(actionMap.get("yihaopintuiku_pop")); // NOI18N
        jButton10.setIcon(resourceMap.getIcon("jButton10.icon")); // NOI18N
        jButton10.setText(resourceMap.getString("jButton10.text")); // NOI18N
        jButton10.setName("jButton10"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jButton1.setAction(actionMap.get("ck1_pop")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jButton2.setAction(actionMap.get("ck2_pop")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jButton3.setAction(actionMap.get("rk1_pop")); // NOI18N
        jButton3.setIcon(resourceMap.getIcon("jButton3.icon")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        jButton4.setAction(actionMap.get("rk2_pop")); // NOI18N
        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N

        jButton5.setAction(actionMap.get("zichanruku_pop")); // NOI18N
        jButton5.setIcon(resourceMap.getIcon("jButton5.icon")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setName("jButton5"); // NOI18N

        jButton6.setAction(actionMap.get("zichanchuku_pop")); // NOI18N
        jButton6.setIcon(resourceMap.getIcon("jButton6.icon")); // NOI18N
        jButton6.setText(resourceMap.getString("jButton6.text")); // NOI18N
        jButton6.setName("jButton6"); // NOI18N

        jButton7.setAction(actionMap.get("yihaopinruku_pop")); // NOI18N
        jButton7.setIcon(resourceMap.getIcon("jButton7.icon")); // NOI18N
        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setName("jButton7"); // NOI18N

        jButton8.setAction(actionMap.get("yihaopinchuku_pop")); // NOI18N
        jButton8.setIcon(resourceMap.getIcon("jButton8.icon")); // NOI18N
        jButton8.setText(resourceMap.getString("jButton8.text")); // NOI18N
        jButton8.setName("jButton8"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addContainerGap(19, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(SelectYiMiaoChuRuKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectYiMiaoChuRuKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectYiMiaoChuRuKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectYiMiaoChuRuKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SelectYiMiaoChuRuKuJDialog dialog = new SelectYiMiaoChuRuKuJDialog();
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
                dispose();
                JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                YiMiaoChuKu1JDialog ymck1 = new YiMiaoChuKu1JDialog();
                ymck1.setNew();
                ymck1.setLocationRelativeTo(mainFrame);
                AssetClientApp.getApplication().show(ymck1);
            }
        });
    }

    @Action
    public void rk1_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dispose();
                JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                YiMiaoRuKu1JDialog ymrk1 = new YiMiaoRuKu1JDialog();
                ymrk1.setNew();
                ymrk1.setLocationRelativeTo(mainFrame);
                AssetClientApp.getApplication().show(ymrk1);
            }
        });
    }

    @Action
    public void ck2_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dispose();
                JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                YiMiaoChuKu2JDialog ymck2 = new YiMiaoChuKu2JDialog();
                ymck2.setNew();
                ymck2.setLocationRelativeTo(mainFrame);
                AssetClientApp.getApplication().show(ymck2);
            }
        });
    }

    @Action
    public void rk2_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dispose();
                JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                YiMiaoRuKu2JDialog ymrk2 = new YiMiaoRuKu2JDialog();
                ymrk2.setNew();
                ymrk2.setLocationRelativeTo(mainFrame);
                AssetClientApp.getApplication().show(ymrk2);
            }
        });
    }
    
    @Action
    public void zichanruku_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dispose();
                JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                GuDingZiChanRuKuJDialog zichanruku = new GuDingZiChanRuKuJDialog();
                zichanruku.setLocationRelativeTo(mainFrame);
                zichanruku.setNew();
                AssetClientApp.getApplication().show(zichanruku);
            }
        });
    }
    
    @Action
    public void zichanchuku_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dispose();
                JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                GuDingZiChanChuKuJDialog zichanchuku = new GuDingZiChanChuKuJDialog();
                zichanchuku.setLocationRelativeTo(mainFrame);
                zichanchuku.setNew();
                AssetClientApp.getApplication().show(zichanchuku);
            }
        });
    }
    
    @Action
    public void yihaopinruku_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dispose();
                JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                YihaopinRuKuJDialog zichanruku = new YihaopinRuKuJDialog();
                zichanruku.setLocationRelativeTo(mainFrame);
                zichanruku.setNew();
                AssetClientApp.getApplication().show(zichanruku);
            }
        });
    }
    
    @Action
    public void yihaopinchuku_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dispose();
                JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                YihaopinChuKuJDialog zichanchuku = new YihaopinChuKuJDialog(new javax.swing.JFrame(), true);
                zichanchuku.setLocationRelativeTo(mainFrame);
                zichanchuku.setNew();
                AssetClientApp.getApplication().show(zichanchuku);
            }
        });
    }
    
    @Action
    public void zichantuiku_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dispose();
                JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                GuDingZiChanTuiKuJDialog zichanruku = new GuDingZiChanTuiKuJDialog();
                zichanruku.setLocationRelativeTo(mainFrame);
                AssetClientApp.getApplication().show(zichanruku);
            }
        });
    }
    
    @Action
    public void yihaopintuiku_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dispose();
                JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                YihaopinTuiKuJDialog zichanruku = new YihaopinTuiKuJDialog();
                zichanruku.setLocationRelativeTo(mainFrame);
                AssetClientApp.getApplication().show(zichanruku);
            }
        });
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    // End of variables declaration//GEN-END:variables
}
