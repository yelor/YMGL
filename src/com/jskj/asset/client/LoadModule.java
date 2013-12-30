/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client;

import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.panel.OpenTabTask;
import com.jskj.asset.client.panel.ToppagePane;
import com.jskj.asset.client.panel.jichuxinxi.BuMenJDialog;
import com.jskj.asset.client.panel.jichuxinxi.DanJuLeiXingJDialog;
import com.jskj.asset.client.panel.jichuxinxi.DanWeiJDialog;
import com.jskj.asset.client.panel.jichuxinxi.DiZhiYiHaoPinJDialog;
import com.jskj.asset.client.panel.jichuxinxi.GongYingDanWeiJDialog;
import com.jskj.asset.client.panel.jichuxinxi.GuDingZiChanJDialog;
import com.jskj.asset.client.panel.jichuxinxi.JianShaoFangShiJDialog;
import com.jskj.asset.client.panel.jichuxinxi.KeHuDanWeiJDialog;
import com.jskj.asset.client.panel.jichuxinxi.YiMiaoJDialog;
import com.jskj.asset.client.panel.jichuxinxi.ZhiYuanJDialog;
import com.jskj.asset.client.panel.user.UserPanel;
import com.jskj.asset.client.util.LogPaneAppender;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;

/**
 *
 * @author 305027939
 */
public class LoadModule extends BaseTask {

    private final static Logger logger = Logger.getLogger(LoadModule.class);
    private JMenuBar menuBar;
    private LogDialog logBox;
    private JDialog aboutBox;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenuDW;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenu jichuMenu;
    private javax.swing.JMenu baobiaoMenu;

    public LoadModule(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    @Override
    public Object doBackgrounp() {

        logger.info("加载顶部菜单项");

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(LoadModule.class);
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jichuMenu = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        baobiaoMenu = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        jMenuDW = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jichuMenu.setText(resourceMap.getString("jichuMenu.text")); // NOI18N
        jichuMenu.setName("jichuMenu"); // NOI18N

        jMenuItem6.setAction(actionMap.get("showYiMiao")); // NOI18N
        jMenuItem6.setText(resourceMap.getString("jMenuItem6.text")); // NOI18N
        jMenuItem6.setName("jMenuItem6"); // NOI18N
        jichuMenu.add(jMenuItem6);

        jMenuItem7.setAction(actionMap.get("showGuDingZiChan")); // NOI18N
        jMenuItem7.setText(resourceMap.getString("jMenuItem7.text")); // NOI18N
        jMenuItem7.setName("jMenuItem7"); // NOI18N
        jichuMenu.add(jMenuItem7);

        jMenuItem8.setAction(actionMap.get("showDiZhiYiHaoPin")); // NOI18N
        jMenuItem8.setText(resourceMap.getString("jMenuItem8.text")); // NOI18N
        jMenuItem8.setName("jMenuItem8"); // NOI18N
        jichuMenu.add(jMenuItem8);

        jMenu2.setText(resourceMap.getString("jMenu2.text")); // NOI18N
        jMenu2.setName("jMenu2"); // NOI18N

        jMenuItem9.setAction(actionMap.get("showGongYingDanWei")); // NOI18N
        jMenuItem9.setText(resourceMap.getString("jMenuItem9.text")); // NOI18N
        jMenuItem9.setName("jMenuItem9"); // NOI18N
        jMenu2.add(jMenuItem9);

        jMenuItem10.setAction(actionMap.get("showKeHuDanWei")); // NOI18N
        jMenuItem10.setText(resourceMap.getString("jMenuItem10.text")); // NOI18N
        jMenuItem10.setName("jMenuItem10"); // NOI18N
        jMenu2.add(jMenuItem10);

        jichuMenu.add(jMenu2);

        jMenuItem11.setAction(actionMap.get("showZhiYuan")); // NOI18N
        jMenuItem11.setText(resourceMap.getString("jMenuItem11.text")); // NOI18N
        jMenuItem11.setName("jMenuItem11"); // NOI18N
        jichuMenu.add(jMenuItem11);

        jMenuItem12.setAction(actionMap.get("showDanJuLeiXing")); // NOI18N
        jMenuItem12.setText(resourceMap.getString("jMenuItem12.text")); // NOI18N
        jMenuItem12.setName("jMenuItem12"); // NOI18N
        jichuMenu.add(jMenuItem12);

        jMenuItem13.setAction(actionMap.get("showDanWei")); // NOI18N
        jMenuItem13.setText(resourceMap.getString("jMenuItem13.text")); // NOI18N
        jMenuItem13.setName("jMenuItem13"); // NOI18N
        jichuMenu.add(jMenuItem13);

        jMenuItem14.setAction(actionMap.get("showJianShaoFangShi")); // NOI18N
        jMenuItem14.setText(resourceMap.getString("jMenuItem14.text")); // NOI18N
        jMenuItem14.setName("jMenuItem14"); // NOI18N
        jichuMenu.add(jMenuItem14);

        menuBar.add(jichuMenu);

        baobiaoMenu.setText(resourceMap.getString("baobiaoMenu.text")); // NOI18N
        baobiaoMenu.setName("baobiaoMenu"); // NOI18N

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem15.setText(resourceMap.getString("jMenuItem15.text")); // NOI18N
        jMenuItem15.setName("jMenuItem15"); // NOI18N
        jMenu1.add(jMenuItem15);

        jMenuItem16.setText(resourceMap.getString("jMenuItem16.text")); // NOI18N
        jMenuItem16.setName("jMenuItem16"); // NOI18N
        jMenu1.add(jMenuItem16);

        jMenuItem17.setText(resourceMap.getString("jMenuItem17.text")); // NOI18N
        jMenuItem17.setName("jMenuItem17"); // NOI18N
        jMenu1.add(jMenuItem17);

        baobiaoMenu.add(jMenu1);

        jMenu3.setText(resourceMap.getString("jMenu3.text")); // NOI18N
        jMenu3.setName("jMenu3"); // NOI18N

        jMenuItem18.setText(resourceMap.getString("jMenuItem18.text")); // NOI18N
        jMenuItem18.setName("jMenuItem18"); // NOI18N
        jMenu3.add(jMenuItem18);

        jMenuItem19.setText(resourceMap.getString("jMenuItem19.text")); // NOI18N
        jMenuItem19.setName("jMenuItem19"); // NOI18N
        jMenu3.add(jMenuItem19);

        baobiaoMenu.add(jMenu3);

        jMenu4.setText(resourceMap.getString("jMenu4.text")); // NOI18N
        jMenu4.setName("jMenu4"); // NOI18N

        jMenuItem20.setText(resourceMap.getString("jMenuItem20.text")); // NOI18N
        jMenuItem20.setName("jMenuItem20"); // NOI18N
        jMenu4.add(jMenuItem20);

        jMenuItem21.setText(resourceMap.getString("jMenuItem21.text")); // NOI18N
        jMenuItem21.setName("jMenuItem21"); // NOI18N
        jMenu4.add(jMenuItem21);

        jMenuItem22.setText(resourceMap.getString("jMenuItem22.text")); // NOI18N
        jMenuItem22.setName("jMenuItem22"); // NOI18N
        jMenu4.add(jMenuItem22);

        baobiaoMenu.add(jMenu4);

        jMenu5.setText(resourceMap.getString("jMenu5.text")); // NOI18N
        jMenu5.setName("jMenu5"); // NOI18N

        jMenuItem23.setText(resourceMap.getString("jMenuItem23.text")); // NOI18N
        jMenuItem23.setName("jMenuItem23"); // NOI18N
        jMenu5.add(jMenuItem23);

        jMenuItem24.setText(resourceMap.getString("jMenuItem24.text")); // NOI18N
        jMenuItem24.setName("jMenuItem24"); // NOI18N
        jMenu5.add(jMenuItem24);

        baobiaoMenu.add(jMenu5);

        menuBar.add(baobiaoMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        jMenuItem5.setAction(actionMap.get("showTopPage")); // NOI18N
        jMenuItem5.setText(resourceMap.getString("jMenuItem5.text")); // NOI18N
        jMenuItem5.setName("jMenuItem5"); // NOI18N
        helpMenu.add(jMenuItem5);

        jMenuItem1.setAction(actionMap.get("showLogBox")); // NOI18N
        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        helpMenu.add(jMenuItem1);

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        jMenuDW.setText(resourceMap.getString("jMenuDW.text")); // NOI18N
        jMenuDW.setName("jMenuDW"); // NOI18N

        jMenuItem2.setAction(actionMap.get("showBuMen")); // NOI18N
        jMenuItem2.setText(resourceMap.getString("jMenuItem2.text")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenuDW.add(jMenuItem2);

        jMenuItem3.setText(resourceMap.getString("jMenuItem3.text")); // NOI18N
        jMenuItem3.setName("jMenuItem3"); // NOI18N
        jMenuDW.add(jMenuItem3);

        jMenuItem4.setAction(actionMap.get("showUserAdmin")); // NOI18N
        jMenuItem4.setText(resourceMap.getString("jMenuItem4.text")); // NOI18N
        jMenuItem4.setName("jMenuItem4"); // NOI18N
        jMenuDW.add(jMenuItem4);

        menuBar.add(jMenuDW);

        //加载日志分析器
        logger.info("加载日志分析器");
        initLogAppender();

        //加载主要工作区
        clientView.displayMainView();

        return STATUS_OK;
    }

    @Override
    public void onSucceeded(Object object) {
        logger.info("模块加载完成");
    }

    /**
     * 日志分析
     */
    private void initLogAppender() {
        if (logBox == null) {
            JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
            logBox = new LogDialog(mainFrame);
            logBox.setLocationRelativeTo(mainFrame);
        }

        LogPaneAppender loghelper = new LogPaneAppender();
        org.apache.log4j.varia.LevelRangeFilter filter = new org.apache.log4j.varia.LevelRangeFilter();
        filter.setLevelMin(Level.DEBUG);
        loghelper.addFilter(filter);
        loghelper.setLogPane(logBox.getLogTextPane());
        Logger loggers = Logger.getRootLogger();
        loggers.addAppender(loghelper);
    }

    @Action
    public void showLogBox() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AssetClientApp.getApplication().show(logBox);
            }
        });
    }

    @Action
    public void showAboutBox() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (aboutBox == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    aboutBox = new AboutBox(mainFrame);
                    aboutBox.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(aboutBox);
            }
        });

    }

    @Action
    public void showUserAdmin() {
        OpenTabTask task = new OpenTabTask("用户管理", new UserPanel(), false);
        task.execute();
    }

    @Action
    public void showTopPage() {
        OpenTabTask task = new OpenTabTask("首页", new ToppagePane(), false);
        task.execute();
    }

    @Action
    public void showYiMiao() {
        SwingUtilities.invokeLater(new Runnable() {
            private YiMiaoJDialog yiMiaoJDialog;

            @Override
            public void run() {
                if (yiMiaoJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    yiMiaoJDialog = new YiMiaoJDialog(new javax.swing.JFrame(), true);
                    yiMiaoJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(yiMiaoJDialog);
            }
        });
    }

    @Action
    public void showGuDingZiChan() {
        SwingUtilities.invokeLater(new Runnable() {
            private GuDingZiChanJDialog guDingZiChanJDialog;

            @Override
            public void run() {
                if (guDingZiChanJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    guDingZiChanJDialog = new GuDingZiChanJDialog(new javax.swing.JFrame(), true);
                    guDingZiChanJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(guDingZiChanJDialog);
            }
        });
    }

    @Action
    public void showDiZhiYiHaoPin() {
        SwingUtilities.invokeLater(new Runnable() {
            private DiZhiYiHaoPinJDialog diZhiYiHaoPinJDialog;

            @Override
            public void run() {
                if (diZhiYiHaoPinJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    diZhiYiHaoPinJDialog = new DiZhiYiHaoPinJDialog(new javax.swing.JFrame(), true);
                    diZhiYiHaoPinJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(diZhiYiHaoPinJDialog);
            }
        });
    }

    @Action
    public void showGongYingDanWei() {
        SwingUtilities.invokeLater(new Runnable() {
            private GongYingDanWeiJDialog gongYingDanWeiJDialog;

            @Override
            public void run() {
                if (gongYingDanWeiJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    gongYingDanWeiJDialog = new GongYingDanWeiJDialog(new javax.swing.JFrame(), true);
                    gongYingDanWeiJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(gongYingDanWeiJDialog);
            }
        });
    }

    @Action
    public void showKeHuDanWei() {
        SwingUtilities.invokeLater(new Runnable() {
            private KeHuDanWeiJDialog keHuDanWeiJDialog;

            @Override
            public void run() {
                if (keHuDanWeiJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    keHuDanWeiJDialog = new KeHuDanWeiJDialog(new javax.swing.JFrame(), true);
                    keHuDanWeiJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(keHuDanWeiJDialog);
            }
        });
    }

    @Action
    public void showBuMen() {

        SwingUtilities.invokeLater(new Runnable() {
            private BuMenJDialog buMenJDialog;

            @Override
            public void run() {
                if (buMenJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    buMenJDialog = new BuMenJDialog(new javax.swing.JFrame(), true);
                    buMenJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(buMenJDialog);
            }
        });
    }

    @Action
    public void showZhiYuan() {
        SwingUtilities.invokeLater(new Runnable() {
            private ZhiYuanJDialog zhiYuanJDialog;

            @Override
            public void run() {
                if (zhiYuanJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    zhiYuanJDialog = new ZhiYuanJDialog(new javax.swing.JFrame(), true);
                    zhiYuanJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(zhiYuanJDialog);
            }
        });
    }

    @Action
    public void showDanJuLeiXing() {
        SwingUtilities.invokeLater(new Runnable() {
            private DanJuLeiXingJDialog danJuLeiXingJDialog;

            @Override
            public void run() {
                if (danJuLeiXingJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    danJuLeiXingJDialog = new DanJuLeiXingJDialog(new javax.swing.JFrame(), true);
                    danJuLeiXingJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(danJuLeiXingJDialog);
            }
        });
    }

    @Action
    public void showDanWei() {
        SwingUtilities.invokeLater(new Runnable() {
            private DanWeiJDialog danWeiJDialog;

            @Override
            public void run() {
                if (danWeiJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    danWeiJDialog = new DanWeiJDialog(new javax.swing.JFrame(), true);
                    danWeiJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(danWeiJDialog);
            }
        });
    }

    @Action
    public void showJianShaoFangShi() {
        SwingUtilities.invokeLater(new Runnable() {
            private JianShaoFangShiJDialog jianShaoFangShiJDialog;

            @Override
            public void run() {
                if (jianShaoFangShiJDialog == null) {
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    jianShaoFangShiJDialog = new JianShaoFangShiJDialog(new javax.swing.JFrame(), true);
                    jianShaoFangShiJDialog.setLocationRelativeTo(mainFrame);
                }
                AssetClientApp.getApplication().show(jianShaoFangShiJDialog);
            }
        });

    }
}