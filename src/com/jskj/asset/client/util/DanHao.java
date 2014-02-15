/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

import com.jskj.asset.client.layout.ReportTemplates;
import com.jskj.asset.client.panel.jichuxinxi.DanWeiInfoJDialog;
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
