/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.baobiao.caigou;

import com.jskj.asset.client.bean.report.CaigouChartReport;
import com.jskj.asset.client.layout.ws.*;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.layout.ReportImageTemplates;
import com.jskj.asset.client.layout.ReportTemplates;
import com.jskj.asset.client.panel.FileTask;
import com.jskj.asset.client.util.BeanFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.constant.ImageType;
import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.grp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.sbt;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.chart.Bar3DChartBuilder;
import net.sf.dynamicreports.report.builder.chart.PieChartBuilder;
import net.sf.dynamicreports.report.builder.chart.TimeSeriesChartBuilder;
import net.sf.dynamicreports.report.builder.column.PercentageColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.Orientation;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author woderchen
 */
public abstract class ReportChartFindTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(ReportChartFindTask.class);
    private final String URI = Constants.HTTP + Constants.APPID;
    private final HashMap map;

    public ReportChartFindTask(HashMap map) {
        super();
        this.map = map;
    }

    @Override
    public Object doBackgrounp() {
        try {

            //使用Spring3 RESTful client来获取http数据
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);

            StringBuilder paramater = new StringBuilder();
            if (map.get("idflag") != null && !map.get("idflag").toString().trim().equals("")) {
                paramater.append("idflag=").append(map.get("idflag")).append("&");
            }
            if (map.get("startDate") != null && !map.get("startDate").toString().trim().equals("")) {
                paramater.append("startDate=").append(map.get("startDate")).append("&");
            }
            if (map.get("endDate") != null && !map.get("endDate").toString().trim().equals("")) {
                paramater.append("endDate=").append(map.get("endDate")).append("&");
            }
            paramater.deleteCharAt(paramater.length() - 1);

            logger.debug("parameter map:" + paramater);

            CommFindEntity<CaigouChartReport> chartResponse = restTemplate.exchange(URI + "report/caigou/chart?" + paramater,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CommFindEntity<CaigouChartReport>>() {
                    }).getBody();

            List<CaigouChartReport> chartDatas = chartResponse.getResult();

            DRDataSource dataSource = new DRDataSource("state", "item", "orderdate", "quantity", "unitprice");
            if (chartDatas != null && chartDatas.size() > 0) {
                for (CaigouChartReport reportbean : chartDatas) {
                    dataSource.add(reportbean.getState(), reportbean.getItem(), reportbean.getOrderdate(), reportbean.getQuantity(), new BigDecimal(reportbean.getPrice() == null ? 0 : reportbean.getPrice()));
                }

                JasperReportBuilder report = report();
                //init styles
                FontBuilder boldFont = stl.fontArialBold();

                //init columns
                TextColumnBuilder<String> stateColumn = col.column("资产类别", "state", type.stringType());
                TextColumnBuilder<String> itemColumn = col.column("条目", "item", type.stringType()).setPrintRepeatedDetailValues(false);
                TextColumnBuilder<Date> orderDateColumn = col.column("订单日期", "orderdate", type.dateType());
                TextColumnBuilder<Integer> quantityColumn = col.column("数量", "quantity", type.integerType());
                TextColumnBuilder<BigDecimal> unitPriceColumn = col.column("单价", "unitprice", ReportTemplates.currencyType);
                //price = unitPrice * quantity
                TextColumnBuilder<BigDecimal> priceColumn = unitPriceColumn.multiply(quantityColumn).setTitle("总价")
                        .setDataType(ReportTemplates.currencyType);
                PercentageColumnBuilder pricePercColumn = col.percentageColumn("比率 %", priceColumn);

                //init groups
                ColumnGroupBuilder stateGroup = grp.group(stateColumn);

                //init subtotals
                AggregationSubtotalBuilder<Number> priceAvg = sbt.avg(priceColumn)
                        .setValueFormatter(ReportTemplates.createCurrencyValueFormatter("avg = "));
                AggregationSubtotalBuilder<BigDecimal> unitPriceSum = sbt.sum(unitPriceColumn)
                        .setLabel("合计:")
                        .setLabelStyle(ReportTemplates.boldStyle);
                AggregationSubtotalBuilder<BigDecimal> priceSum = sbt.sum(priceColumn)
                        .setLabel("")
                        .setLabelStyle(ReportTemplates.boldStyle);

                //init charts
                Bar3DChartBuilder itemChart = cht.bar3DChart()
                        .setTitle("Application by item")
                        .setTitleFont(boldFont)
                        .setOrientation(Orientation.HORIZONTAL)
                        .setCategory(itemColumn)
                        .addSerie(
                                cht.serie(unitPriceColumn), cht.serie(priceColumn));
                TimeSeriesChartBuilder dateChart = cht.timeSeriesChart()
                        .setTitle("Application by date")
                        .setTitleFont(boldFont)
                        .setFixedHeight(150)
                        .setTimePeriod(orderDateColumn)
                        .addSerie(
                                cht.serie(unitPriceColumn), cht.serie(priceColumn));
                PieChartBuilder stateChart = cht.pieChart()
                        .setTitle("Application by asset")
                        .setTitleFont(boldFont)
                        .setFixedHeight(100)
                        .setShowLegend(false)
                        .setKey(stateColumn)
                        .addSerie(
                                cht.serie(priceColumn));

                //configure report
                report
                        .setTemplate(ReportImageTemplates.reportTemplate)
                        //columns
                        .columns(
                                stateColumn, itemColumn, orderDateColumn, quantityColumn, unitPriceColumn, priceColumn, pricePercColumn)
                        //groups
                        .groupBy(stateGroup)
                        //subtotals
                        .subtotalsAtFirstGroupFooter(
                                sbt.sum(unitPriceColumn), sbt.sum(priceColumn))
                        .subtotalsOfPercentageAtGroupFooter(stateGroup,
                                sbt.percentage(priceColumn).setShowInColumn(pricePercColumn))
                        .subtotalsAtSummary(
                                unitPriceSum, priceSum, priceAvg)
                        //band components
                        .title(
                                cmp.horizontalList(
                                        itemChart, cmp.verticalList(dateChart, stateChart)),
                                cmp.verticalGap(10))
                        .setDataSource(dataSource);
                return report;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {
        File imgfile = null;
        if (object == null) {
            clientView.setStatus("response data is null", AssetMessage.ERROR_MESSAGE);
        }
        if (object instanceof Exception) {
            Exception e = (Exception) object;
            clientView.setStatus(e.getMessage(), AssetMessage.ERROR_MESSAGE);
            logger.error(e);
        }

        if (object instanceof JasperReportBuilder) {
            JasperReportBuilder builder = (JasperReportBuilder) object;
            try {
                //builder.show(false);

                String imgPath = FileTask.tempAttchedPath + "caigou_" + map.get("idflag") + ".png";
                OutputStream outputStream = new FileOutputStream(imgPath);
                builder.toImage(outputStream, ImageType.PNG);
                outputStream.flush();
                outputStream.close();

                imgfile = new File(imgPath);
                if (imgfile.exists()) {
                    responseResult(imgfile);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error(ex);
            }
        }

        responseResult(imgfile);
    }

    public abstract void responseResult(File imgFile);
}
