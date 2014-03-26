/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

import com.jskj.asset.client.layout.ReportTemplates;
import java.util.Date;
import static net.sf.dynamicreports.report.builder.DynamicReports.bcode;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.template;
import net.sf.dynamicreports.report.builder.barcode.Code128BarcodeBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.log4j.Logger;

/**
 *
 * @author tt
 */
public class DanHao {

    private static final Logger logger = Logger.getLogger(DanHao.class);

    /**
     * ********************定义需要审批流程的单号***************
     */
    //疫苗相关
    public final static String TYPE_YIMIAOXF = "YMXF";
    public final static String TYPE_YIMIAOXS = "YMXS";
    public final static String TYPE_YIMIAOSB = "YMSB";
    public final static String TYPE_YIMIAOSG = "YMSG";
    public final static String TYPE_YIMIAOLY = "YMLY";
    public final static String TYPE_YIMIAOCG = "YMCG";
    public final static String TYPE_YIMIAOTJ = "YMTJ";
    public final static String TYPE_YIMIAOBS = "YMBS";

    //资产相关
    public final static String TYPE_GDZC = "GDZC";
    public final static String TYPE_YHCG = "YHCG";
    public final static String TYPE_PTLY = "PTLY";
    public final static String TYPE_ITLY = "ITLY";
    public final static String TYPE_YHLY = "YHLY";
    public final static String TYPE_WXSQ = "WXSQ";
    public final static String TYPE_FKDJ = "FKDJ";
    public final static String TYPE_QTFK = "QTFK";

    /**
     *************其他单号，不需要审批流程的单号*************
     */
    //疫苗相关
    //资产相关
    public final static String TYPE_SKDJ = "SKDJ";
    /**
     *
     * @param DANHAO_TYPE
     * @return
     */
    public static String getUIClassByDanhaoType(String DANHAO_TYPE) {
        String className = "";

        if (TYPE_YIMIAOXF.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.ymgl.YiMiaoXiaFaJDialog";
        } else if (TYPE_YIMIAOXS.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.ymgl.YiMiaoXiaoShouJDialog";
        } else if (TYPE_YIMIAOSB.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.ymgl.YiMiaoShenBaoPlanJDialog";
        } else if (TYPE_YIMIAOSG.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.ymgl.YiMiaoSheGouPlanJDialog";
        } else if (TYPE_YIMIAOLY.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.ymgl.YiMiaoLingYongShenQingJDialog";
        } else if (TYPE_YIMIAOCG.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.ymgl.YiMiaoCaiGouShenQingJDialog";
        } else if (TYPE_YIMIAOTJ.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.ymgl.YiMiaoTiaoJiaJDialog";
        } else if (TYPE_YIMIAOBS.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.ckgl.YiMiaoBaoSun";
        } else if (TYPE_GDZC.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.slgl.GuDingZiChanCaiGouShenQingJDialog";
        } else if (TYPE_YHCG.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.slgl.DiZhiYiHaoPinCaiGouShenQingJDialog";
        } else if (TYPE_PTLY.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.slgl.PTGuDingZiChanLingYongShenQingJDialog";
        } else if (TYPE_ITLY.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.slgl.ITGuDingZiChanLingYongShenQingJDialog";
        } else if (TYPE_YHLY.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.slgl.DiZhiYiHaoPinLingYongShenQingJDialog";
        } else if (TYPE_WXSQ.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.slgl.GuDingZiChanWeiXiuShenQingJDialog";
        } else if (TYPE_FKDJ.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.shjs.FuKuanDanJDialog";
        }else if (TYPE_QTFK.equals(DANHAO_TYPE)) {
            className = "com.jskj.asset.client.panel.shjs.OtherFuKuanDanJDialog";
        }

        return className;
    }

    public static String getDanHao(String type) {

        return type + (new Date().getTime());

    }

    public static void printBarCode128(String label, String barcode) {
        try {
            StyleBuilder bold14Style = stl.style(ReportTemplates.boldStyle).setFontSize(14);
            Code128BarcodeBuilder postalCode = bcode.code128(barcode)
                    .setModuleWidth(2.5)
                    .setStyle(bold14Style);

            report()
                    .setTemplate(template()).setPageFormat(PageType.A7, PageOrientation.LANDSCAPE)
                    .title(createBarcodeCellComponent(label, postalCode))
                    .print(true);
        } catch (DRException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    private static ComponentBuilder<?, ?> createBarcodeCellComponent(String label, ComponentBuilder<?, ?> content) {
        StyleBuilder bold14Style = stl.style(ReportTemplates.boldStyle).setFontSize(14);
        VerticalListBuilder cell = cmp.verticalList(
                cmp.text(label).setStyle(bold14Style),
                cmp.horizontalList(
                        cmp.horizontalGap(5),
                        content,
                        cmp.horizontalGap(5)));
        return cell;
    }

    //测试用main函数
//    public static void main(String[] args){
//        System.out.println(DanHao.getDanHao("cgsq"));
//    }
}
