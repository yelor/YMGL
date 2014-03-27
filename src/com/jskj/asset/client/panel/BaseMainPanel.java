/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NoFoundPanel.java
 *
 * Created on Feb 21, 2010, 10:42:18 PM
 */
package com.jskj.asset.client.panel;

import com.jskj.asset.client.panel.ymgl.*;
import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.layout.BasePanel;
import com.jskj.asset.client.panel.baobiao.caigou.YimiaoyunshujiluPanel;
import com.jskj.asset.client.panel.baobiao.caiwubaobiao.DanweiyingshouyingfuPanel;
import com.jskj.asset.client.panel.baobiao.kucun.KucunchaxunPanel;
import com.jskj.asset.client.panel.ckgl.SelectKucunchaxun;
import com.jskj.asset.client.panel.ckgl.SelectYiMiaoChuRuKu;
import com.jskj.asset.client.panel.ckgl.SelectYiMiaoZuZhuangChaiXie;
import com.jskj.asset.client.panel.ckgl.SelectYiMiaochurukujilu;
import com.jskj.asset.client.panel.ckgl.Selectebaosun;
import com.jskj.asset.client.panel.shjs.FukuanShenPiJDialog;
import com.jskj.asset.client.panel.shjs.SelectDanweiJDialog;
import com.jskj.asset.client.panel.shjs.SelectPandianJDialog;
import com.jskj.asset.client.panel.shjs.SelectShoufukuanJDialog;
import com.jskj.asset.client.panel.slgl.ShenQingShenPiJDialog;
import com.jskj.asset.client.panel.slgl.selectCaiGouDanJDialog;
import com.jskj.asset.client.panel.slgl.selectLingYongDanJDialog;
import com.jskj.asset.client.panel.slgl.selectWeiXiuDiaoBoDanJDialog;
import com.jskj.asset.client.panel.slgl.selectYanShouDengJiDanJDialog;
import com.jskj.asset.client.panel.spcx.JinxiaochaxunJDialog;
import com.jskj.asset.client.panel.spcx.LiChengZaiXianChaXunJDialog;
import com.jskj.asset.client.panel.spcx.YeWuLiuChengChaXunJDialog;
import com.jskj.asset.client.panel.spcx.selecteTongJiInvoiceJDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author woderchen
 */
public class BaseMainPanel extends BasePanel {

    private final static Logger logger = Logger.getLogger(BaseMainPanel.class);
    private selecteInvoiceJDialog selecteInvoiceJDialog;
    private selecteInvoiceJDialog2 selecteInvoiceJDialog2;
    private selecteInvoiceJDialog3 selecteInvoiceJDialog3;

    /*仓库管理*/
    private SelectYiMiaoZuZhuangChaiXie ymzzcx;
    private SelectYiMiaochurukujilu ymcrkjl;
    private SelectYiMiaoChuRuKu ymcrk;
    private Selectebaosun selectebaosun;
    private SelectKucunchaxun kccx;

    /*审核结算*/
    private SelectShoufukuanJDialog selecteSKDJDialog;
    private SelectPandianJDialog selectePDDJDialog;
    private SelectDanweiJDialog selectDanweiJDialog;

    /*申领管理*/
    private selectCaiGouDanJDialog selectCaiGouDanJDialog;
    private selectLingYongDanJDialog selectLingYongDanJDialog;
    private selectYanShouDengJiDanJDialog selectYanShouDengJiDanJDialog;
    private selectWeiXiuDiaoBoDanJDialog selectWeiXiuDiaoBoDanJDialog;

    public final static int TYPE_CK = 0;
    public final static int TYPE_SH = 1;
    public final static int TYPE_SL = 2;
    public final static int TYPE_SP = 3;
    public final static int TYPE_YM = 4;

    /**
     * Creates new form NoFoundPane
     *
     * @param type
     */
    public BaseMainPanel(int type) {
        super();
        initComponents();
        initCorrectButton(type);
    }

    private void initCorrectButton(int type) {
        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(BaseMainPanel.class, this);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(BaseMainPanel.class);

        switch (type) {
            case TYPE_CK:
                jButton_center.setAction(actionMap.get(resourceMap.getString("jButton_center_ck.action"))); // NOI18N
                jButton_center.setIcon(resourceMap.getIcon("jButton_center_ck.icon")); // NOI18N
                jButton_center.setText(resourceMap.getString("jButton_center_ck.text")); // NOI18N

                jButton_topleft.setAction(actionMap.get(resourceMap.getString("jButton_topleft_ck.action"))); // NOI18N
                jButton_topleft.setIcon(resourceMap.getIcon("jButton_topleft_ck.icon")); // NOI18N
                jButton_topleft.setText(resourceMap.getString("jButton_topleft_ck.text")); // NOI18N

                jButton_topright.setAction(actionMap.get(resourceMap.getString("jButton_topright_ck.action"))); // NOI18N
                jButton_topright.setIcon(resourceMap.getIcon("jButton_topright_ck.icon")); // NOI18N
                jButton_topright.setText(resourceMap.getString("jButton_topright_ck.text")); // NOI18N

                jButton_bottomleft.setAction(actionMap.get(resourceMap.getString("jButton_bottomleft_ck.action"))); // NOI18N
                jButton_bottomleft.setIcon(resourceMap.getIcon("jButton_bottomleft_ck.icon")); // NOI18N
                jButton_bottomleft.setText(resourceMap.getString("jButton_bottomleft_ck.text")); // NOI18N

                jButton_bottomright.setAction(actionMap.get(resourceMap.getString("jButton_bottomright_ck.action"))); // NOI18N
                jButton_bottomright.setIcon(resourceMap.getIcon("jButton_bottomright_ck.icon")); // NOI18N
                jButton_bottomright.setText(resourceMap.getString("jButton_bottomright_ck.text")); // NOI18N
                break;

            case TYPE_SH:
                jButton_center.setAction(actionMap.get(resourceMap.getString("jButton_center_sh.action"))); // NOI18N
                jButton_center.setIcon(resourceMap.getIcon("jButton_center_sh.icon")); // NOI18N
                jButton_center.setText(resourceMap.getString("jButton_center_sh.text")); // NOI18N

                jButton_topleft.setAction(actionMap.get(resourceMap.getString("jButton_topleft_sh.action"))); // NOI18N
                jButton_topleft.setIcon(resourceMap.getIcon("jButton_topleft_sh.icon")); // NOI18N
                jButton_topleft.setText(resourceMap.getString("jButton_topleft_sh.text")); // NOI18N

                jButton_topright.setAction(actionMap.get(resourceMap.getString("jButton_topright_sh.action"))); // NOI18N
                jButton_topright.setIcon(resourceMap.getIcon("jButton_topright_sh.icon")); // NOI18N
                jButton_topright.setText(resourceMap.getString("jButton_topright_sh.text")); // NOI18N

                jButton_bottomleft.setAction(actionMap.get(resourceMap.getString("jButton_bottomleft_sh.action"))); // NOI18N
                jButton_bottomleft.setIcon(resourceMap.getIcon("jButton_bottomleft_sh.icon")); // NOI18N
                jButton_bottomleft.setText(resourceMap.getString("jButton_bottomleft_sh.text")); // NOI18N

                jButton_bottomright.setAction(actionMap.get(resourceMap.getString("jButton_bottomright_sh.action"))); // NOI18N
                jButton_bottomright.setIcon(resourceMap.getIcon("jButton_bottomright_sh.icon")); // NOI18N
                jButton_bottomright.setText(resourceMap.getString("jButton_bottomright_sh.text")); // NOI18N
                break;

            case TYPE_SL:
                jButton_center.setAction(actionMap.get(resourceMap.getString("jButton_center_sl.action"))); // NOI18N
                jButton_center.setIcon(resourceMap.getIcon("jButton_center_sl.icon")); // NOI18N
                jButton_center.setText(resourceMap.getString("jButton_center_sl.text")); // NOI18N

                jButton_topleft.setAction(actionMap.get(resourceMap.getString("jButton_topleft_sl.action"))); // NOI18N
                jButton_topleft.setIcon(resourceMap.getIcon("jButton_topleft_sl.icon")); // NOI18N
                jButton_topleft.setText(resourceMap.getString("jButton_topleft_sl.text")); // NOI18N

                jButton_topright.setAction(actionMap.get(resourceMap.getString("jButton_topright_sl.action"))); // NOI18N
                jButton_topright.setIcon(resourceMap.getIcon("jButton_topright_sl.icon")); // NOI18N
                jButton_topright.setText(resourceMap.getString("jButton_topright_sl.text")); // NOI18N

                jButton_bottomleft.setAction(actionMap.get(resourceMap.getString("jButton_bottomleft_sl.action"))); // NOI18N
                jButton_bottomleft.setIcon(resourceMap.getIcon("jButton_bottomleft_sl.icon")); // NOI18N
                jButton_bottomleft.setText(resourceMap.getString("jButton_bottomleft_sl.text")); // NOI18N

                jButton_bottomright.setAction(actionMap.get(resourceMap.getString("jButton_bottomright_sl.action"))); // NOI18N
                jButton_bottomright.setIcon(resourceMap.getIcon("jButton_bottomright_sl.icon")); // NOI18N
                jButton_bottomright.setText(resourceMap.getString("jButton_bottomright_sl.text")); // NOI18N
                break;

            case TYPE_SP:
                jButton_center.setAction(actionMap.get(resourceMap.getString("jButton_center_sp.action"))); // NOI18N
                jButton_center.setIcon(resourceMap.getIcon("jButton_center_sp.icon")); // NOI18N
                jButton_center.setText(resourceMap.getString("jButton_center_sp.text")); // NOI18N

                jButton_topleft.setAction(actionMap.get(resourceMap.getString("jButton_topleft_sp.action"))); // NOI18N
                jButton_topleft.setIcon(resourceMap.getIcon("jButton_topleft_sp.icon")); // NOI18N
                jButton_topleft.setText(resourceMap.getString("jButton_topleft_sp.text")); // NOI18N

                jButton_topright.setAction(actionMap.get(resourceMap.getString("jButton_topright_sp.action"))); // NOI18N
                jButton_topright.setIcon(resourceMap.getIcon("jButton_topright_sp.icon")); // NOI18N
                jButton_topright.setText(resourceMap.getString("jButton_topright_sp.text")); // NOI18N

                jButton_bottomleft.setAction(actionMap.get(resourceMap.getString("jButton_bottomleft_sp.action"))); // NOI18N
                jButton_bottomleft.setIcon(resourceMap.getIcon("jButton_bottomleft_sp.icon")); // NOI18N
                jButton_bottomleft.setText(resourceMap.getString("jButton_bottomleft_sp.text")); // NOI18N

                jButton_bottomright.setAction(actionMap.get(resourceMap.getString("jButton_bottomright_sp.action"))); // NOI18N
                jButton_bottomright.setIcon(resourceMap.getIcon("jButton_bottomright_sp.icon")); // NOI18N
                jButton_bottomright.setText(resourceMap.getString("jButton_bottomright_sp.text")); // NOI18N
                break;

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

        jPanel1 = new javax.swing.JPanel();
        jButton_center = new javax.swing.JButton();
        jButton_topleft = new javax.swing.JButton();
        jButton_topright = new javax.swing.JButton();
        jButton_bottomleft = new javax.swing.JButton();
        jButton_bottomright = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(BaseMainPanel.class);
        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(BaseMainPanel.class, this);
        jButton_center.setAction(actionMap.get("selecteInvoice3Action")); // NOI18N
        jButton_center.setIcon(resourceMap.getIcon("jButton_center.icon")); // NOI18N
        jButton_center.setText(resourceMap.getString("jButton_center.text")); // NOI18N
        jButton_center.setBorder(null);
        jButton_center.setBorderPainted(false);
        jButton_center.setContentAreaFilled(false);
        jButton_center.setName("jButton_center"); // NOI18N

        jButton_topleft.setAction(actionMap.get("selecteInvoiceAction")); // NOI18N
        jButton_topleft.setIcon(resourceMap.getIcon("jButton_topleft.icon")); // NOI18N
        jButton_topleft.setText(resourceMap.getString("jButton_topleft.text")); // NOI18N
        jButton_topleft.setBorder(null);
        jButton_topleft.setBorderPainted(false);
        jButton_topleft.setContentAreaFilled(false);
        jButton_topleft.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton_topleft.setName("jButton_topleft"); // NOI18N

        jButton_topright.setAction(actionMap.get("selecteInvoice2Action")); // NOI18N
        jButton_topright.setIcon(resourceMap.getIcon("jButton_topright.icon")); // NOI18N
        jButton_topright.setText(resourceMap.getString("jButton_topright.text")); // NOI18N
        jButton_topright.setBorder(null);
        jButton_topright.setBorderPainted(false);
        jButton_topright.setContentAreaFilled(false);
        jButton_topright.setName("jButton_topright"); // NOI18N

        jButton_bottomleft.setAction(actionMap.get("yiMiaoYanShouAction")); // NOI18N
        jButton_bottomleft.setIcon(resourceMap.getIcon("jButton_bottomleft.icon")); // NOI18N
        jButton_bottomleft.setText(resourceMap.getString("jButton_bottomleft.text")); // NOI18N
        jButton_bottomleft.setBorder(null);
        jButton_bottomleft.setBorderPainted(false);
        jButton_bottomleft.setContentAreaFilled(false);
        jButton_bottomleft.setName("jButton_bottomleft"); // NOI18N

        jButton_bottomright.setAction(actionMap.get("selectYimiaoShenPiDanAction")); // NOI18N
        jButton_bottomright.setIcon(resourceMap.getIcon("jButton_bottomright.icon")); // NOI18N
        jButton_bottomright.setText(resourceMap.getString("jButton_bottomright.text")); // NOI18N
        jButton_bottomright.setBorder(null);
        jButton_bottomright.setBorderPainted(false);
        jButton_bottomright.setContentAreaFilled(false);
        jButton_bottomright.setName("jButton_bottomright"); // NOI18N

        jLabel1.setIcon(resourceMap.getIcon("jLabel1.icon")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_bottomleft, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                    .addComponent(jButton_topleft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 321, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_topright, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_bottomright, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50))
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_center)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_topleft)
                    .addComponent(jButton_topright))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(jButton_center)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_bottomleft)
                    .addComponent(jButton_bottomright))
                .addGap(56, 56, 56))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void selecteInvoiceAction() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (selecteInvoiceJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    selecteInvoiceJDialog = new selecteInvoiceJDialog(new javax.swing.JFrame(), true);
                    selecteInvoiceJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(selecteInvoiceJDialog);
            }
        });
    }

    @Action

    public void selecteInvoice2Action() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (selecteInvoiceJDialog2 == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    selecteInvoiceJDialog2 = new selecteInvoiceJDialog2(new javax.swing.JFrame(), true);
                    selecteInvoiceJDialog2.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(selecteInvoiceJDialog2);
            }
        });

    }

    @Action
    public void selecteInvoice3Action() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (selecteInvoiceJDialog3 == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    selecteInvoiceJDialog3 = new selecteInvoiceJDialog3(new javax.swing.JFrame(), true);
                    selecteInvoiceJDialog3.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(selecteInvoiceJDialog3);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_bottomleft;
    private javax.swing.JButton jButton_bottomright;
    private javax.swing.JButton jButton_center;
    private javax.swing.JButton jButton_topleft;
    private javax.swing.JButton jButton_topright;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
   @Override
    public Task reload() {
        return null;
    }

    @Override
    public Task reload(Object param) {
//        logger.info("init message panel");
//        MessagePanel ctrlPane = new MessagePanel();
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
//        this.setLayout(layout);
//        layout.setHorizontalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                .addComponent(ctrlPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//        );
//        layout.setVerticalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                .addGroup(layout.createSequentialGroup()
//                        .addComponent(ctrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                        .addContainerGap())
//        );
        return null;
    }

    @Action
    public void yiMiaoYanShouAction() {
        SwingUtilities.invokeLater(new Runnable() {
            private YiMiaoYanShouDanJDialog yiMiaoYanShouJDialog;

            public void run() {
                if (yiMiaoYanShouJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    yiMiaoYanShouJDialog = new YiMiaoYanShouDanJDialog(new javax.swing.JFrame(), true);
                    yiMiaoYanShouJDialog.setLocationRelativeTo(mainFrame);
                }
                yiMiaoYanShouJDialog.setAddOrUpdate(true);
                AssetClientApp.getApplication().show(yiMiaoYanShouJDialog);
            }
        });

    }

    /**
     * ***************************仓库管理********************
     */
    @Action
    public void ymzzcx_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (ymzzcx == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    ymzzcx = new SelectYiMiaoZuZhuangChaiXie(new javax.swing.JFrame(), true);
                    ymzzcx.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(ymzzcx);
            }
        });
    }

    @Action
    public void ymcrkjl_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (ymcrkjl == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    ymcrkjl = new SelectYiMiaochurukujilu(new javax.swing.JFrame(), true);
                    ymcrkjl.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(ymcrkjl);
            }
        });
    }

    @Action
    public void kucunchaxun_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (kccx == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    kccx = new SelectKucunchaxun(new javax.swing.JFrame(), true);
                    kccx.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(kccx);
            }
        });
    }

    @Action
    public void ymcrk_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (ymcrk == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    ymcrk = new SelectYiMiaoChuRuKu(new javax.swing.JFrame(), true);
                    ymcrk.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(ymcrk);
            }
        });
    }

    @Action
    public void selectebaosun_pop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (selectebaosun == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    selectebaosun = new Selectebaosun();
                    selectebaosun.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(selectebaosun);
            }
        });
    }

    @Action
    public Task showYimiaoyunshujilu() {
        return new OpenTabTask("报表-疫苗运输记录", new YimiaoyunshujiluPanel(), false);
    }
    
    @Action
    public Task showKucunzhuangkuang() {
        return new OpenTabTask("报表-库存状况", new KucunchaxunPanel(), false);
    }

    /**
     * ***************************仓库管理完毕********************
     */
    /**
     * ***************************审核结算开始********************
     */
    @Action
    public void selecteInvoice1Action() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (selectDanweiJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    selectDanweiJDialog = new SelectDanweiJDialog();
                    selectDanweiJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(selectDanweiJDialog);
            }
        });
    }

    @Action
    public void selecteSHInvoice2Action() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (selecteSKDJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    selecteSKDJDialog = new SelectShoufukuanJDialog(new javax.swing.JFrame(), true);
                    selecteSKDJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(selecteSKDJDialog);
            }
        });
    }

    @Action
    public void selecteShenpiAction() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                FukuanShenPiJDialog fkdJDialog = new FukuanShenPiJDialog(new javax.swing.JFrame(), true);
                fkdJDialog.setLocationRelativeTo(mainFrame);
                AssetClientApp.getApplication().show(fkdJDialog);
            }
        });
    }

    @Action
    public void selecteInvoice5Action() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (selectePDDJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    selectePDDJDialog = new SelectPandianJDialog(new javax.swing.JFrame(), true);
                    selectePDDJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(selectePDDJDialog);
            }
        });
    }

    /**
     * ***************************审核结算完毕********************
     */
    /**
     * ***************************申领管理开始********************
     */
    @Action
    public void selectCaiGouDanAction() {
        SwingUtilities.invokeLater(new Runnable() {
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

    @Action
    public void selectShenPiDanAction() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                if (selectShenHeDanJDialog == null) {
//                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
//                    selectShenHeDanJDialog = new selectShenHeDanJDialog(new javax.swing.JFrame(), true);
//                    selectShenHeDanJDialog.setLocationRelativeTo(mainFrame);
//                }
//                AssetClientApp.getApplication().show(selectShenHeDanJDialog);
                JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                ShenQingShenPiJDialog sqsp = new ShenQingShenPiJDialog(new javax.swing.JFrame(), true);
                sqsp.setLocationRelativeTo(mainFrame);
                AssetClientApp.getApplication().show(sqsp);
            }
        });
    }

    /**
     * ***************************申领管理结束********************
     */
    /**
     * ***************************审批查询开始********************
     */
    @Action
    public void YeWuLiuChengChaXunAction() {
        SwingUtilities.invokeLater(new Runnable() {
            private YeWuLiuChengChaXunJDialog yeWuLiuChengChaXunJDialog;

            public void run() {
                if (yeWuLiuChengChaXunJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    yeWuLiuChengChaXunJDialog = new YeWuLiuChengChaXunJDialog(new javax.swing.JFrame(), true);
                    yeWuLiuChengChaXunJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(yeWuLiuChengChaXunJDialog);
            }

        });
    }

    @Action
    public void LiChengZaiXianChaXunAction() {
        SwingUtilities.invokeLater(new Runnable() {
            private LiChengZaiXianChaXunJDialog liChengZaiXianChaXunJDialog;

            public void run() {
                if (liChengZaiXianChaXunJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    liChengZaiXianChaXunJDialog = new LiChengZaiXianChaXunJDialog();
                    liChengZaiXianChaXunJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(liChengZaiXianChaXunJDialog);
            }

        });
    }

    @Action
    public void JinxiaoChaXunAction() {
        SwingUtilities.invokeLater(new Runnable() {
            private JinxiaochaxunJDialog jinxiaochaxunJDialog;

            public void run() {
                if (jinxiaochaxunJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    jinxiaochaxunJDialog = new JinxiaochaxunJDialog(new javax.swing.JFrame(), true);
                    jinxiaochaxunJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(jinxiaochaxunJDialog);
            }

        });
    }
    


    @Action
    public void TongJiChaXunAction() {
        SwingUtilities.invokeLater(new Runnable() {
            private selecteTongJiInvoiceJDialog selecteTongJiInvoiceJDialog;

            public void run() {
                if (selecteTongJiInvoiceJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    selecteTongJiInvoiceJDialog = new selecteTongJiInvoiceJDialog(new javax.swing.JFrame(), true);
                    selecteTongJiInvoiceJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(selecteTongJiInvoiceJDialog);
            }

        });
    }

    @Action
    public void selectYimiaoShenPiDanAction() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                YimiaoCaigouShenPiJDialog yimiaoShenPiJDialog = new YimiaoCaigouShenPiJDialog(new javax.swing.JFrame(), true);
                yimiaoShenPiJDialog.setLocationRelativeTo(mainFrame);
                AssetClientApp.getApplication().show(yimiaoShenPiJDialog);
            }
        });
    }
    /**
     * ***************************审批查询结束********************
     */
}
