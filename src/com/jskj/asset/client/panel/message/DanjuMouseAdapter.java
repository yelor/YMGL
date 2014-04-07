/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.message;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.CaigoushenqingDetailEntity;
import com.jskj.asset.client.bean.entity.FukuanshenqingDetailEntity;
import com.jskj.asset.client.bean.entity.XiaoshoushenpixiangdanEntity;
import com.jskj.asset.client.bean.entity.YimiaobaosunxiangdanEntity;
import com.jskj.asset.client.bean.entity.YimiaocaigouxiangdanEntity;
import com.jskj.asset.client.bean.entity.YimiaotiaojiaxiangdanEntity;
import com.jskj.asset.client.layout.BaseDialog;
import static com.jskj.asset.client.panel.slgl.task.ShenQingTask.logger;
import com.jskj.asset.client.panel.slgl.task.ShenqingDetailTask;
import com.jskj.asset.client.panel.ymgl.task.YimiaoXiaoshouXiangdanTask;
import com.jskj.asset.client.util.ClassHelper;
import com.jskj.asset.client.util.DanHao;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author 305027939
 */
public abstract class DanjuMouseAdapter extends MouseAdapter {

    boolean isDoubleClick;

    public DanjuMouseAdapter() {
        this(false);
    }

    public DanjuMouseAdapter(boolean isDoubleClick) {
        this.isDoubleClick = isDoubleClick;
    }


    public abstract String getShenqingdanID();
    
    @Override
    public void mouseReleased(MouseEvent e) {

        if (isDoubleClick) {
            if (e.getClickCount() != 2) {
                return;
            }
        }

        final String shenqingdan = getShenqingdanID();
        if (shenqingdan != null && shenqingdan.length() > 4) {

            final String className = DanHao.getUIClassByDanhaoType(shenqingdan);
            if (!className.equals("")) {

                if (shenqingdan.startsWith("YM")) {//疫苗相关
                    new YimiaoXiaoshouXiangdanTask(shenqingdan) {
                        @Override
                        protected void succeeded(Object result) {
                            if (result != null) {
                                if (shenqingdan.startsWith(DanHao.TYPE_YIMIAOXF) || shenqingdan.startsWith(DanHao.TYPE_YIMIAOXS)) {
                                    openDialog(className, result, XiaoshoushenpixiangdanEntity.class);
                                } else if (shenqingdan.startsWith(DanHao.TYPE_YIMIAOSB) || shenqingdan.startsWith(DanHao.TYPE_YIMIAOLY)
                                        || shenqingdan.startsWith(DanHao.TYPE_YIMIAOSG) || shenqingdan.startsWith(DanHao.TYPE_YIMIAOCG)) {
                                    openDialog(className, result, YimiaocaigouxiangdanEntity.class);
                                } else if (shenqingdan.startsWith(DanHao.TYPE_YIMIAOBS)) {
                                    openDialog(className, result, YimiaobaosunxiangdanEntity.class);
                                } else if (shenqingdan.startsWith(DanHao.TYPE_YIMIAOTJ)) {
                                    openDialog(className, result, YimiaotiaojiaxiangdanEntity.class);
                                }

                            } else {
                                logger.error("response result is null.");
                            }
                        }
                    }.execute();

                } else if (!shenqingdan.startsWith(DanHao.TYPE_FKDJ) && !shenqingdan.startsWith(DanHao.TYPE_QTFK)) {//资产相关
                    new ShenqingDetailTask(shenqingdan) {
                        @Override
                        protected void succeeded(Object result) {
                            if (result != null) {
                                openDialog(className, result, CaigoushenqingDetailEntity.class);
                            } else {
                                logger.error("response result is null.");
                            }
                        }
                    }.execute();
                } else {//付款单据

                    new com.jskj.asset.client.panel.shjs.task.FkShenqingDetailTask(shenqingdan) {
                        @Override
                        protected void succeeded(Object result) {
                            if (result != null) {
                                openDialog(className, result, FukuanshenqingDetailEntity.class);
                            } else {
                                logger.error("response result is null.");
                            }
                        }
                    }.execute();

                }

            }
        }
    }

    private void openDialog(String className, Object entity, Class entityClassType) {
        try {
            ClassHelper<BaseDialog> helper = new ClassHelper<BaseDialog>(className, JDialog.class, entityClassType);
            JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
            BaseDialog jdialog = helper.newInstance(null, entity);
            jdialog.setLocationRelativeTo(mainFrame);
            //AssetClientApp.getApplication().show(yimiaoShenPiJDialog);
            jdialog.setVisible(true);
        } catch (Exception ex) {
            logger.error("StaticDialog:" + ex);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().setBackground(Color.WHITE);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        e.getComponent().setBackground(null);
    }

}
