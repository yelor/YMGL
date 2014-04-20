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
    public final static String TYPE_YIMIAOSB = "YMLQJH";
    public final static String TYPE_YIMIAOSG = "YMSGJH";
    public final static String TYPE_YIMIAOLY = "YMLQ";
    public final static String TYPE_YIMIAOCG = "YMSG";
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
    public final static String TYPE_QTSK = "QTSK";
    /**
     *
     * @param shenqingdanId
     * @return
     */
    public static String getUIClassByDanhaoType(String shenqingdanId) {
        String className = "";

        if (shenqingdanId.startsWith(TYPE_YIMIAOXF)) {
            className = "com.jskj.asset.client.panel.ymgl.YiMiaoXiaFaJDialog";
        } else if (shenqingdanId.startsWith(TYPE_YIMIAOXS)) {
            className = "com.jskj.asset.client.panel.ymgl.YiMiaoXiaoShouJDialog";
        } else if (shenqingdanId.startsWith(TYPE_YIMIAOSB)) {
            className = "com.jskj.asset.client.panel.ymgl.YiMiaoLingQuPlanJDialog";
        } else if (shenqingdanId.startsWith(TYPE_YIMIAOSG)) {
            className = "com.jskj.asset.client.panel.ymgl.YiMiaoSheGouPlanJDialog";
        } else if (shenqingdanId.startsWith(TYPE_YIMIAOLY)) {
            className = "com.jskj.asset.client.panel.ymgl.YiMiaoLingQuShenQingJDialog";
        } else if (shenqingdanId.startsWith(TYPE_YIMIAOCG)) {
            className = "com.jskj.asset.client.panel.ymgl.YiMiaoSheGouShenQingJDialog";
        } else if (shenqingdanId.startsWith(TYPE_YIMIAOTJ)) {
            className = "com.jskj.asset.client.panel.ymgl.YiMiaoTiaoJiaJDialog";
        } else if (shenqingdanId.startsWith(TYPE_YIMIAOBS)) {
            className = "com.jskj.asset.client.panel.ckgl.YiMiaoBaoSun";
        } else if (shenqingdanId.startsWith(TYPE_GDZC)) {
            className = "com.jskj.asset.client.panel.slgl.GuDingZiChanCaiGouShenQingJDialog";
        } else if (shenqingdanId.startsWith(TYPE_YHCG)) {
            className = "com.jskj.asset.client.panel.slgl.DiZhiYiHaoPinCaiGouShenQingJDialog";
        } else if (shenqingdanId.startsWith(TYPE_PTLY)) {
            className = "com.jskj.asset.client.panel.slgl.PTGuDingZiChanLingYongShenQingJDialog";
        } else if (shenqingdanId.startsWith(TYPE_ITLY)) {
            className = "com.jskj.asset.client.panel.slgl.ITGuDingZiChanLingYongShenQingJDialog";
        } else if (shenqingdanId.startsWith(TYPE_YHLY)) {
            className = "com.jskj.asset.client.panel.slgl.DiZhiYiHaoPinLingYongShenQingJDialog";
        } else if (shenqingdanId.startsWith(TYPE_WXSQ)) {
            className = "com.jskj.asset.client.panel.slgl.GuDingZiChanWeiXiuShenQingJDialog";
        } else if (shenqingdanId.startsWith(TYPE_FKDJ)) {
            className = "com.jskj.asset.client.panel.shjs.FuKuanDanJDialog";
        }else if (shenqingdanId.startsWith(TYPE_QTFK)) {
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
