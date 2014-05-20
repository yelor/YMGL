/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.constants.Constants;
import static com.jskj.asset.client.layout.ReportTemplates.viewer;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.SubreportBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;

/**
 *
 * @author 305027939
 */
public abstract class BaseDialog extends JDialog {

    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(BaseDialog.class);

    public BaseDialog() {
        super(AssetClientApp.getApplication().getMainFrame());
    }

    public BaseDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }

    private Object getObject(String paramater, Object bean) throws Exception {
        if (paramater != null && !paramater.equals("")) {
            String[] ps = paramater.split("\\$");
            String firstPara = ps[0];
            String getIs = "get";
            String tempTitle = getIs + firstPara.substring(0, 1).toUpperCase() + firstPara.substring(1);
            try {
                if (bean == null) {
                    return "";
                }
                Method method = bean.getClass().getMethod(tempTitle, new Class[0]);
                Object temp = method.invoke(bean, new Object[0]);
                if (ps.length == 1) {
                    return temp;
                }
                return getObject(paramater.substring(firstPara.length() + 1), temp);
            } catch (Exception ex) {
                throw ex;
            }
        }
        return "";
    }

    private Set<Field> getClassAllFields(Class clazz, Set<Field> allGenericFields) {

        // 如果clazz为空则直接返回    
        if (clazz == null) {
            return allGenericFields;
        }

        Object parent = clazz.getGenericSuperclass();
        // 如果有父类并且父类不是Object 则递归调用    
        if (parent != null && !((Class) parent).getName().equals("Object")) {
            getClassAllFields((Class) parent, allGenericFields);
        }

        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {// 如果clazz存在声明的属性    
            for (int i = 0; i < fields.length; i++) {
                allGenericFields.add(fields[i]);
            }
        }
        // 存在父类则递归调用    
        return allGenericFields;
    }

    private Set<Field> getClassAllFields(Class clazz) {
        Set<Field> allGenericFields = new HashSet<Field>();
//      List<Field> allGenericFields = new ArrayList<Field>();  
        return getClassAllFields(clazz, allGenericFields);
    }

    private void setObject(String paramater, Object bean, String value) throws Exception {
        if (paramater != null && !paramater.equals("")) {
            String[] ps = paramater.split("\\$");
            String firstPara = ps[0];
            String getIs = "set";
            if (ps.length > 1) {
                getIs = "get";
            }
            String tempTitle = getIs + firstPara.substring(0, 1).toUpperCase() + firstPara.substring(1);
            try {
                if (bean == null) {
                    return;
                }
                if (ps.length == 1) {
                    Set<Field> fields = getClassAllFields(bean.getClass());
                    for (Field field : fields) {
                        if (field.getName().equalsIgnoreCase(firstPara)) {
                            //Field field = bean.getClass().getField(firstPara);
                            Class[] paramClasses = new Class[1];
                            paramClasses[0] = field.getType();
                            Method method = bean.getClass().getMethod(tempTitle, paramClasses);
                            if (paramClasses[0] == String.class) {
                                method.invoke(bean, value);
                            } else if (paramClasses[0] == Integer.class) {
                                if (!value.trim().equals("")) {
                                    method.invoke(bean, Integer.parseInt(value));
                                }
                            } else if (paramClasses[0] == Double.class) {
                                if (!value.trim().equals("")) {
                                    method.invoke(bean, Double.parseDouble(value));
                                }
                            } else if (paramClasses[0] == Float.class) {
                                if (!value.trim().equals("")) {
                                    method.invoke(bean, Float.parseFloat(value));
                                }
                            } else if (paramClasses[0] == Boolean.class) {
                                if (!value.trim().equals("")) {
                                    method.invoke(bean, Boolean.parseBoolean(value));
                                }
                            } else if (paramClasses[0] == Long.class) {
                                if (!value.trim().equals("")) {
                                    method.invoke(bean, Long.parseLong(value));
                                }
                            } else if (paramClasses[0] == Long.class) {
                                if (!value.trim().equals("")) {
                                    method.invoke(bean, Long.parseLong(value));
                                }
                            }
                            break;
                        }
                    }
                } else {
                    Method method = bean.getClass().getMethod(tempTitle, new Class[0]);
                    Object temp = method.invoke(bean, new Object[0]);
                    if (temp != null) {
                        setObject(paramater.substring(firstPara.length() + 1), temp, value);
                    }
                }
            } catch (Exception ex) {
                throw ex;
            }
        }
    }

    public void copyToBean(Object targetBean, JPanel container) {

        Component[] coms = container.getComponents();
        for (Component c : coms) {
            if (c instanceof JPanel) {
                copyToBean(targetBean, (JPanel) c);
            } else if (c instanceof JScrollPane) {
                JScrollPane ccc = (JScrollPane) c;
                if (ccc.getViewport() != null) {
                    Component com = ccc.getViewport().getView();
                    if (com != null) {
                        if (com instanceof JTextArea) {
                            try {
                                String value = ((JTextArea) com).getText();
                                setObject(com.getName(), targetBean, value);
                            } catch (Exception ex) {
                                logger.error(ex);
                            }
                        } else if (com instanceof javax.swing.JList) {
                            try {
                                Object value = ((javax.swing.JList) com).getSelectedValue();
                                if (value != null) {
                                    setObject(com.getName(), targetBean, value.toString());
                                }
                            } catch (Exception ex) {
                                logger.error(ex);
                            }
                        }
                    }
                }
            } else if (c instanceof JTextField) {
                try {
                    String value = ((JTextField) c).getText();
                    setObject(c.getName(), targetBean, value);
                } catch (Exception ex) {
                    logger.error(ex);
                }

            } else if (c instanceof JComboBox) {
                try {
                    Object value = ((JComboBox) c).getSelectedItem();
                    if (value instanceof String) {
                        setObject(c.getName(), targetBean, value.toString());
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                }
            } else if (c instanceof JTextArea) {
                try {
                    String value = ((JTextArea) c).getText();
                    setObject(c.getName(), targetBean, value);
                } catch (Exception ex) {
                    logger.error(ex);
                }
            } else if (c instanceof JList) {
                try {
                    Object value = ((JList) c).getSelectedValue();
                    if (value instanceof String) {
                        setObject(c.getName(), targetBean, value.toString());
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                }
            }

        }

    }

    public void setDialogComponentValue(HashMap map, Container container, boolean canEdit) {
        Component[] coms = container.getComponents();
        for (Component c : coms) {
            if (c instanceof JPanel) {
                setDialogComponentValue(map, (JPanel) c, canEdit);
            } else if (c instanceof JScrollPane) {
                JScrollPane ccc = (JScrollPane) c;
                if (ccc.getViewport() != null) {
                    Component com = ccc.getViewport().getView();
                    if (com != null) {
                        if (com instanceof JTextArea) {
                            try {
                                String name = ((JTextArea) com).getName();
                                Object obj = map.get(name);
                                ((JTextArea) com).setEnabled(canEdit);
                                if (obj instanceof String) {
                                    ((JTextArea) com).setText(obj.toString());
                                }
                            } catch (Exception ex) {
                                logger.error(ex);
                            }
                        } else if (com instanceof javax.swing.JList) {
                            try {
                                String name = ((javax.swing.JList) com).getName();
                                Object obj = map.get(name);

                                ((javax.swing.JList) com).setSelectedValue(obj, true);
                                ((javax.swing.JList) com).setEnabled(canEdit);
                            } catch (Exception ex) {
                                logger.error(ex);
                            }
                        } else if (com instanceof javax.swing.JTable) {
                            try {
                                String name = ((javax.swing.JTable) com).getName();
                                Object obj = map.get(name);

                                //((javax.swing.JTable) com).setText(obj.toString()); //jtable的处理有点麻烦，后面再改？？
                                ((javax.swing.JTable) com).setEnabled(canEdit);

                            } catch (Exception ex) {
                                logger.error(ex);
                            }
                        }
                    }
                }
            } else if (c instanceof JTextField) {
                try {
                    String name = ((JTextField) c).getName();
                    Object obj = map.get(name);
                    ((JTextField) c).setEnabled(canEdit);
                    if (obj instanceof String) {
                        ((JTextField) c).setText(obj.toString());
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                }

            } else if (c instanceof JComboBox) {
                try {
                    String name = ((JComboBox) c).getName();
                    Object obj = map.get(name);
                    ((JComboBox) c).setEnabled(canEdit);
                    ((JComboBox) c).setSelectedItem(obj);

                } catch (Exception ex) {
                    logger.error(ex);
                }
            } else if (c instanceof JTextArea) {
                try {
                    String name = ((JTextArea) c).getName();
                    Object obj = map.get(name);
                    ((JTextArea) c).setEnabled(canEdit);
                    if (obj instanceof String) {
                        ((JTextArea) c).setText(obj.toString());
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                }
            } else if (c instanceof JList) {
                try {
                    String name = ((JList) c).getName();
                    Object obj = map.get(name);
                    ((JList) c).setEnabled(canEdit);
                    ((JList) c).setSelectedValue(obj, true);
                } catch (Exception ex) {
                    logger.error(ex);
                }
            }

        }
    }

    public void enableEnterFocus(Container container) {
        Component[] coms = container.getComponents();
        for (Component c : coms) {
            if (c instanceof JPanel) {
                enableEnterFocus((JPanel) c);
            } else if (c instanceof JScrollPane) {
                JScrollPane ccc = (JScrollPane) c;
                if (ccc.getViewport() != null) {
                    Component com = ccc.getViewport().getView();
                    if (com != null) {
                        if (com instanceof JTextArea) {

                        } else if (com instanceof javax.swing.JList) {

                        } else if (com instanceof javax.swing.JTable) {

                        }
                    }
                }
            } else if (c instanceof JTextField) {
                if (c instanceof BaseTextField) {
                } else {
                    c.addKeyListener(new KeyAdapter() {
                        public void keyPressed(KeyEvent evt) {
                            int key = evt.getKeyCode();
                            if (key == KeyEvent.VK_ENTER) {
                                transferFocus();
                            }
                        }
                    });
                }

            } else if (c instanceof JComboBox) {

            } else if (c instanceof JTextArea) {

            } else if (c instanceof JList) {

            }

        }
    }

    /**
     *
     * @param sourceBean
     * @param container
     */
    public void bind(Object sourceBean, JPanel container) {
        Component[] coms = container.getComponents();
        for (Component c : coms) {
            if (c instanceof JPanel) {
                bind(sourceBean, (JPanel) c);
            } else if (c instanceof JScrollPane) {
                JScrollPane ccc = (JScrollPane) c;
                if (ccc.getViewport() != null) {
                    Component com = ccc.getViewport().getView();
                    if (com != null) {
                        if (com instanceof JTextArea) {
                            try {
                                Object temp = getObject(com.getName(), sourceBean);
                                if (temp != null) {
                                    ((JTextArea) com).setText(temp.toString());
                                } else {
                                    ((JTextArea) com).setText("");
                                }
                            } catch (Exception ex) {
                                logger.error(ex);
                            }
                        } else if (com instanceof javax.swing.JList) {
                            try {
                                Object temp = getObject(com.getName(), sourceBean);
                                if (temp != null) {
                                    ((JList) com).setSelectedValue(temp, true);
                                } else {
                                    //((JList) c).setText("");
                                }
                            } catch (Exception ex) {
                                logger.error(ex);
                            }
                        }
                    }
                }
            } else if (c instanceof JTextField) {
                try {
                    Object temp = getObject(c.getName(), sourceBean);
                    if (temp != null) {
                        ((JTextField) c).setText(temp.toString());
                    } else {
                        ((JTextField) c).setText("");
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                    ((JTextField) c).setText("");
                }

            } else if (c instanceof JComboBox) {
                try {
                    Object temp = getObject(c.getName(), sourceBean);
                    if (temp != null) {
                        ((JComboBox) c).setSelectedItem(temp);
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                }
            } else if (c instanceof JTextArea) {
                try {
                    Object temp = getObject(c.getName(), sourceBean);
                    if (temp != null) {
                        ((JTextArea) c).setText(temp.toString());
                    } else {
                        ((JTextArea) c).setText("");
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                    ((JTextArea) c).setText("");
                }
            }
        }
    }

    protected void print(String title, String[][] topDisplayColumns, JTable[] table, String[][] bottomDisplayColumns) throws DRException {
        this.print(title, topDisplayColumns, table, bottomDisplayColumns, null);
    }

    protected void print(String title, String[][] topDisplayColumns, JTable table, String[][] bottomDisplayColumns) throws DRException {
        this.print(title, topDisplayColumns, table, bottomDisplayColumns, null);
    }

    protected void print(String title, String[][] topDisplayColumns, JTable table, String[][] bottomDisplayColumns, String sign) throws DRException {
        this.print(title, topDisplayColumns, new JTable[]{table}, bottomDisplayColumns, sign);
    }

    protected void print(String title, String[][] topDisplayColumns, JTable[] table, String[][] bottomDisplayColumns, String sign) throws DRException {
        List<String[]> leftArray = new ArrayList();
        List<String[]> rightArray = new ArrayList();
        List<String[]> bottomleftArray = new ArrayList();
        List<String[]> bottomrightArray = new ArrayList();
        List<String[]> singleRowArray = new ArrayList();
        if (topDisplayColumns != null) {
            for (int i = 1; i <= topDisplayColumns.length; i++) {
                if (topDisplayColumns[i - 1].length >= 3) {
                    String type = topDisplayColumns[i - 1][2];
                    if (type.equalsIgnoreCase("single")) {
                        singleRowArray.add(topDisplayColumns[i - 1]);
                        continue;
                    }
                }
                if (i % 2 == 0) {
                    rightArray.add(topDisplayColumns[i - 1]);
                } else {
                    leftArray.add(topDisplayColumns[i - 1]);
                }
            }
        }

        if (bottomDisplayColumns != null) {
            for (int i = 1; i <= bottomDisplayColumns.length; i++) {
                if (bottomDisplayColumns[i - 1].length >= 3) {
                    String type = bottomDisplayColumns[i - 1][2];
                    if (type.equalsIgnoreCase("single")) {
                        singleRowArray.add(bottomDisplayColumns[i - 1]);
                        continue;
                    }
                }
                if (i % 2 == 0) {
                    bottomrightArray.add(bottomDisplayColumns[i - 1]);
                } else {
                    bottomleftArray.add(bottomDisplayColumns[i - 1]);
                }
            }
        }

        HorizontalListBuilder subHbuilder = cmp.horizontalList().setStyle(stl.style(10)).setGap(50);

        HorizontalListBuilder bhuilder = subHbuilder.add(
                cmp.hListCell(createCustomerComponent(bottomleftArray)).heightFixedOnTop(),
                cmp.hListCell(createCustomerComponent(bottomrightArray)).heightFixedOnTop());

        if (singleRowArray.size() > 0) {
            subHbuilder.newRow().add(createCustomerComponent(singleRowArray));
        }
        
        if (sign != null && !sign.equals("")) {
            bhuilder.newRow().add(
                    cmp.text(sign + ":_____________").setHorizontalAlignment(HorizontalAlignment.RIGHT));
        }
        bhuilder.newRow().add(
                cmp.text("单位:" + Constants.DANWEINAME).setHorizontalAlignment(HorizontalAlignment.RIGHT));

        SubreportBuilder subreport = cmp.subreport(new SubreportExpression(table))
                .setDataSource(new SubreportDataSourceExpression(table));

        viewer(report().setTemplate(ReportTemplates.reportTemplate)
                .title(ReportTemplates.createTitleComponent(title),
                        cmp.horizontalList().setStyle(stl.style(10)).setGap(50).add(
                                cmp.hListCell(createCustomerComponent(leftArray)).heightFixedOnTop(),
                                cmp.hListCell(createCustomerComponent(rightArray)).heightFixedOnTop()),
                        cmp.verticalGap(10))
                .detail(subreport, cmp.verticalGap(20))
                .pageFooter(ReportTemplates.footerComponent)
                //.sortBy(asc(itemColumn), desc(unitPriceColumn))
                .setDataSource(new JREmptyDataSource(table.length))
                .summary(bhuilder));
    }

    private ComponentBuilder<?, ?> createCustomerComponent(List<String[]> array) {
        HorizontalListBuilder list = cmp.horizontalList().setBaseStyle(stl.style().setLeftPadding(10));
        for (String[] cus : array) {
            list.add(cmp.text(cus[0].trim().equals("") ? "" : (cus[0] + ":")).setFixedColumns(8).setStyle(ReportTemplates.boldStyle), cmp.text(cus.length > 1 ? cus[1] : "")).newRow();
        }
        return cmp.verticalList(list);
    }

    private DRDataSource getDatasourceByTable(JTable table, TextColumnBuilder[] itemColumns) {
        DRDataSource dataSource = null;
        if (table != null) {
            int rowCount = table.getRowCount();
            int columnCount = table.getColumnCount();
            String[] columnName = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                Object headerValue = table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue();
                columnName[i] = "A" + i;
                itemColumns[i] = col.column(headerValue.toString(), columnName[i], type.stringType());
            }

            dataSource = new DRDataSource(columnName);
            for (int i = 0; i < rowCount; i++) {
                Object[] values = new Object[columnCount];
                for (int j = 0; j < columnCount; j++) {
                    Object valuObj = table.getValueAt(i, j);
                    values[j] = valuObj.toString();

                }
                dataSource.add(values);
            }
        }
        return dataSource;
    }

    private class SubreportExpression extends AbstractSimpleExpression<JasperReportBuilder> {

        private static final long serialVersionUID = 1L;

        private JTable[] tables;

        public SubreportExpression(JTable[] tables) {
            this.tables = tables;
        }

        @Override
        public JasperReportBuilder evaluate(ReportParameters reportParameters) {
            int masterRowNumber = reportParameters.getReportRowNumber();
            JasperReportBuilder report = report();
            if (masterRowNumber <= tables.length) {
                report
                        .setTemplate(ReportTemplates.reportTemplate);

                int columnCount = tables[masterRowNumber - 1].getColumnCount();
                TextColumnBuilder[] itemColumns = new TextColumnBuilder[columnCount];
                DRDataSource dataSource = getDatasourceByTable(tables[masterRowNumber - 1], itemColumns);
                report.addColumn(itemColumns);
            }
            return report;
        }
    }

    private class SubreportDataSourceExpression extends AbstractSimpleExpression<JRDataSource> {

        private static final long serialVersionUID = 1L;

        private JTable[] tables;

        public SubreportDataSourceExpression(JTable[] tables) {
            this.tables = tables;
        }

        @Override
        public JRDataSource evaluate(ReportParameters reportParameters) {
            int masterRowNumber = reportParameters.getReportRowNumber();
            if (masterRowNumber <= tables.length) {
                int columnCount = tables[masterRowNumber - 1].getColumnCount();
                TextColumnBuilder[] itemColumns = new TextColumnBuilder[columnCount];
                DRDataSource dataSource = getDatasourceByTable(tables[masterRowNumber - 1], itemColumns);
                return dataSource;
            }
            return null;
        }
    }

}
