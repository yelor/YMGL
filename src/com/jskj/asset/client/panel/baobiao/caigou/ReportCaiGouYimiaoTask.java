/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.baobiao.caigou;

import com.jskj.asset.client.bean.report.CaiGouYimiaoReport;
import com.jskj.asset.client.bean.report.CaigouReport;
import com.jskj.asset.client.layout.ws.*;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.layout.ReportTemplates;
import com.jskj.asset.client.util.BeanFactory;
import java.math.BigDecimal;
import java.util.List;
import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author woderchen
 */
public class ReportCaiGouYimiaoTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(ReportCaiGouYimiaoTask.class);
    private final String URI = Constants.HTTP + Constants.APPID;
    private final String serviceId = "report/yimiao";
    private final CaigouReport selectedData;

    public ReportCaiGouYimiaoTask(CaigouReport selectedData) {
        super();
        this.selectedData = selectedData;
    }

    @Override
    public Object doBackgrounp() {
        try {
            logger.debug("find  id:" + selectedData.getShenqingdanId());
            //使用Spring3 RESTful client来获取http数据
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);
            CommFindEntity<CaiGouYimiaoReport> response = restTemplate.exchange(URI + serviceId + "/" + selectedData.getShenqingdanId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CommFindEntity<CaiGouYimiaoReport>>() {
                    }).getBody();

            if (response != null) {
                List<CaiGouYimiaoReport> caigouYimiaos = response.getResult();
                /*开始制作报表*/
                DRDataSource dataSource = new DRDataSource("item", "quantity", "unitprice", "totalprice");
                for (CaiGouYimiaoReport caigouYimiao : caigouYimiaos) {
                    dataSource.add(caigouYimiao.getYimiaotb().getYimiaoName(), 
                            caigouYimiao.getQuantity() == null ? 0 : caigouYimiao.getQuantity(),
                            new BigDecimal(caigouYimiao.getSaleprice() == null ? 0 : caigouYimiao.getSaleprice()),
                            new BigDecimal(caigouYimiao.getTotalprice() == null ? 0 : caigouYimiao.getTotalprice()));
                }

                FontBuilder boldFont = stl.fontArialBold().setFontSize(12);

                TextColumnBuilder<String> itemColumn = col.column("疫苗", "item", type.stringType());
                TextColumnBuilder<Integer> quantityColumn = col.column("数量", "quantity", type.integerType());
                TextColumnBuilder<BigDecimal> unitPriceColumn = col.column("单价", "unitprice", type.bigDecimalType());
                TextColumnBuilder<BigDecimal> totalPriceColumn = col.column("总价", "totalprice", type.bigDecimalType());

                try {
                    report()
                            .setTemplate(ReportTemplates.reportTemplate)
                            .columns(itemColumn, quantityColumn, unitPriceColumn,totalPriceColumn)
                            .summary(
                                    cht.pie3DChart()
                                    .setTitle(selectedData.getShenqingdanId())
                                    .setTitleFont(boldFont)
                                    .setKey(itemColumn)
                                    .series(
                                            cht.serie(totalPriceColumn)))
                            .pageFooter(ReportTemplates.footerComponent)
                            .setDataSource(dataSource)
                            .show(false);
                } catch (DRException e) {
                    e.printStackTrace();
                    throw e;
                }

            } else {
                clientView.setStatus("response data is null", AssetMessage.ERROR_MESSAGE);
            }

            return "";

        } catch (Exception e) {
            logger.error(e);
            return e;
        }
    }

    @Override
    public void onSucceeded(Object object) {

        if (object instanceof Exception) {
            Exception e = (Exception) object;
            clientView.setStatus(e.getMessage(), AssetMessage.ERROR_MESSAGE);
            logger.error(e);
            return;
        }

    }

}
