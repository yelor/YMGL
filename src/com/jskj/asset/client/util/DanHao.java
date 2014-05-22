/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
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
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.Orientation;
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
            className = "com.jskj.asset.client.panel.ckgl.YiMiaoBaoSunJDialog";
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
        } else if (shenqingdanId.startsWith(TYPE_QTFK)) {
            className = "com.jskj.asset.client.panel.shjs.OtherFuKuanDanJDialog";
        }

        return className;
    }

    public static String getDanHao(String type) {

        return type + (new Date().getTime());

    }

    private final static StyleBuilder font4Style = stl.style(ReportTemplates.columnStyle).setFontSize(4);

    private final static StyleBuilder font10Style = stl.style(ReportTemplates.columnStyle).setFontSize(10);

    private final static StyleBuilder font15Style = stl.style(ReportTemplates.columnStyle).setFontSize(15).setHorizontalAlignment(HorizontalAlignment.CENTER);

    private final static StyleBuilder font12Style = stl.style(ReportTemplates.columnStyle).setFontSize(12).setHorizontalAlignment(HorizontalAlignment.CENTER);

    public static void printBarCode128(String label, String barcode) {
        try {
            StyleBuilder bold14Style = stl.style(ReportTemplates.boldStyle).setFontSize(14);
            Code128BarcodeBuilder postalCode = bcode.code128(barcode)
                    .setModuleWidth(1d)
                    .setStyle(bold14Style);

            report()
                    .setTemplate(template().setBarcodeWidth(179).setBarcodeHeight(50)
                    .setPrintOrder(Orientation.HORIZONTAL)).setIgnorePageWidth(Boolean.TRUE).setIgnorePagination(Boolean.TRUE)
                    .title(createBarcodeCellComponent(label, postalCode))
                    .print(false);
            AssetMessage.INFO("打印完成.");
        } catch (DRException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    public static void printBarCode128(String label, String barcode, int total) {
        for (int i = 0; i < total; i++) {
            printBarCode128(label, barcode + (i+1));
        }
    }

    public static void printBarCode128ForAsset(String[] labelAndbarcode, String[][] parameters, int total) {
        for (int i = 0; i < total; i++) {
            if (labelAndbarcode == null || labelAndbarcode.length <= 1) {
                return;
            }
            labelAndbarcode[1]+=(i+1);
            printBarCode128ForAsset(labelAndbarcode, parameters);
        }
    }

    public static void printBarCode128ForAsset(String[] labelAndbarcode, String[][] parameters) {

        if (labelAndbarcode == null || labelAndbarcode.length <= 1) {
            return;
        }

        try {
            StyleBuilder textStyle = stl.style().setFontSize(12);

            Code128BarcodeBuilder shippingContainerCode = bcode.code128(labelAndbarcode[1])
                    .setModuleWidth(1d)
                    .setStyle(font10Style);
            report().setTemplate(template().setBarcodeWidth(130).setBarcodeHeight(35).setPrintOrder(Orientation.HORIZONTAL))
                    .setPageFormat(PageType.C7).setIgnorePageWidth(Boolean.TRUE).setIgnorePagination(Boolean.TRUE)
                    .setTextStyle(textStyle)
                    .title(
                            cmp.horizontalList(
                                    createCustomerComponent(Constants.DANWEINAME),
                                    createCellComponent( shippingContainerCode)),
                            cmp.horizontalList(
                                                    createCellComponentLeft(returnNotNullValue(0, 0, parameters)),
                                                    createCellComponent(returnNotNullValue(0, 1, parameters))),
//                            cmp.horizontalList(
//                                    cmp.verticalList(
//                                            cmp.horizontalList(
//                                                    createCellComponent(returnNotNullValue(1, 0, parameters), cmp.text(returnNotNullValue(1, 1, parameters))),
//                                                    createCellComponent(returnNotNullValue(2, 0, parameters), cmp.text(returnNotNullValue(2, 1, parameters)))),
//                                            cmp.horizontalList(
//                                                    createCellComponent(returnNotNullValue(3, 0, parameters), cmp.text(returnNotNullValue(3, 1, parameters))),
//                                                    createCellComponent(returnNotNullValue(4, 0, parameters), cmp.text(returnNotNullValue(4, 1, parameters)))),
                                            cmp.horizontalList(
                                                    createCellComponentLeft(returnNotNullValue(1, 0, parameters)),
                                                    createCellComponent(returnNotNullValue(1, 1, parameters))),
                                            cmp.horizontalList(
                                                    createCellComponentLeft(returnNotNullValue(2, 0, parameters)),
                                                    createCellComponent(returnNotNullValue(2, 1, parameters))),
                                            cmp.horizontalList(
                                                    createCellComponentLeft(returnNotNullValue(3, 0, parameters)),
                                                    createCellComponent(returnNotNullValue(3, 1, parameters))),
                                            cmp.horizontalList(
                                                    createCellComponentLeft(returnNotNullValue(4, 0, parameters)),
                                                    createCellComponent(returnNotNullValue(4, 1, parameters))),
                                            cmp.horizontalList(
                                                    createCellComponentLeft(returnNotNullValue(5, 0, parameters)),
                                                    createCellComponent(returnNotNullValue(5, 1, parameters)))
//                                    ))
                            ).show(false);
            AssetMessage.INFO("打印完成.");
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    private static String returnNotNullValue(int a, int b, String[][] parameters) {
        if (parameters == null || parameters.length < (a + 1) || parameters[a].length < (b + 1)) {
            return "";
        }

        return parameters[a][b];
    }

    private static ComponentBuilder<?, ?> createBarcodeCellComponent(String label, ComponentBuilder<?, ?> content) {

        VerticalListBuilder cell = cmp.verticalList(
                cmp.text(label).setStyle(font10Style),
                cmp.horizontalList(
                        cmp.horizontalGap(5),
                        content,
                        cmp.horizontalGap(5)));
        return cell;
    }

    private static ComponentBuilder<?, ?> createCustomerComponent(String label, String content) {
        VerticalListBuilder contentBuilder = cmp.verticalList(
                cmp.text(content));
        return createCellComponent(label, contentBuilder);
    }

    private static ComponentBuilder<?, ?> createCustomerComponent(String content) {
        VerticalListBuilder contentBuilder = cmp.verticalList(
                cmp.text(content).setStyle(font12Style).setFixedWidth(100));
        return createCellComponent(contentBuilder);
    }

    private static ComponentBuilder<?, ?> createCellComponent(String label, ComponentBuilder<?, ?> content) {
        VerticalListBuilder cell = cmp.verticalList(
                cmp.text(label).setStyle(font4Style),
                cmp.horizontalList(
//                        cmp.horizontalGap(5),
                        content
//                        ,cmp.horizontalGap(5)
                ));
//        cell.setStyle(stl.style(stl.pen2Point()));
        return cell;
    }

    private static ComponentBuilder<?, ?> createCellComponent(ComponentBuilder<?, ?> content) {
        VerticalListBuilder cell = cmp.verticalList(
                cmp.horizontalList(
//                        cmp.horizontalGap(5),
                        content
//                        ,cmp.horizontalGap(10)
                ));
//        cell.setStyle(stl.style(stl.pen2Point()));
        return cell;
    }

    private static ComponentBuilder<?, ?> createCellComponentLeft(String label) {
        VerticalListBuilder cell = cmp.verticalList(
                cmp.text(label).setStyle(font10Style)).setFixedWidth(50);
        cell.setStyle(stl.style(stl.pen2Point()));
        return cell;
    }

    private static ComponentBuilder<?, ?> createCellComponent(String label) {
        VerticalListBuilder cell = cmp.verticalList(
                cmp.text(label).setStyle(font10Style));
        cell.setStyle(stl.style(stl.pen2Point()));
        return cell;
    }

    //测试用main函数
    public static void main(String[] args) {
        System.out.println(DanHao.getDanHao("cgsq"));
        DanHao.printBarCode128("", DanHao.getDanHao("cgsq") + 88);
//        DanHao.printBarCode128ForAsset(new String[]{"编号",DanHao.getDanHao("cgsq")+88},
//                new String[][]{
//                {"资产名","12313"},
//                {"资产类别","345"},
//                {"序列号","54hrfgh"},
//                {"购置日期","fghwe"},
//                {"保修期至","wr324"},
//                {"登记人","234234"}});
    }
}
