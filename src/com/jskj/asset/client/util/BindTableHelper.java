/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.layout.ReportTemplates;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.swing.JTable;
import javax.xml.crypto.Data;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Converter;

import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;
import org.jdesktop.application.Task;
import org.jdesktop.swingbinding.JTableBinding.ColumnBinding;

/**
 *
 * @author woderchen
 */
public class BindTableHelper<T> {

    private final static Logger logger = Logger.getLogger(BindTableHelper.class);
    private final BindingGroup bindingGroup;
    private org.jdesktop.swingbinding.JTableBinding jTableBinding;
    private final String undisplayString = "serialVersionUID";
    private final JTableFormat jTableFormat;
    private List<T> data;
    private String[][] bindObject;

    private int[] savedDateColumn;
    private int[] savedIntegerColumn;
    private HashMap<Class, List<Integer>> savedColumnType;
    private HashMap<Integer, Converter> savedCoverts;

    public BindTableHelper(JTable jtable, List<T> data) {
        bindingGroup = new BindingGroup();
        jTableFormat = new JTableFormat(jtable);
        this.data = data;
        bindObject = null;
        savedDateColumn = null;
        savedIntegerColumn = null;
        savedColumnType = new HashMap<Class, List<Integer>>();
        savedCoverts = new HashMap<Integer, Converter>();
    }

    public void createTable(Class bean) {
        try {
            jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, data, jTableFormat.getJtable());

            // System.out.println("data::"+data);
            Field[] f = bean.getDeclaredFields();
            org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = null;

            bindObject = new String[f.length][2];
            int i = 0;
            for (Field a : f) {
                String name = a.getName();
                String temp = "${" + name + "}";
                bindObject[i][0] = name;
                bindObject[i][1] = name;
                // System.out.println("bind object:"+temp);
                columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create(temp));
                columnBinding.setColumnName(name);
                columnBinding.setColumnClass(String.class);
                i++;

            }

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    public void createTable(String[][] bindObj) {
        try {
            bindObject = bindObj;
            jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, data, jTableFormat.getJtable());
            // System.out.println("data::"+data);

            org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = null;

            if (bindObject != null && bindObject.length > 0) {
                for (int i = 0; i < bindObject.length; i++) {
                    String temp = bindObject[i][0];
                    String name = bindObject[i][1];
                    columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${" + temp + "}"));
                    columnBinding.setColumnName(name);
                    columnBinding.setColumnClass(String.class);
                }

            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, data, jTableFormat.getJtable());

            T rowData = data.get(0);
            if (rowData == null) {
                return;
            }
            //Pojo转成hashmap
            HashMap map = (HashMap) rowData;
            Set keys = map.keySet();
            org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = null;
            bindObject = new String[keys.size()][2];
            int i = 0;
            for (Object key : keys) {
                if (key instanceof String) {
                    String name = key.toString();
                    String temp = "${" + name + "}";
                    bindObject[i][0] = name;
                    bindObject[i][1] = name;
                    // System.out.println("bind object:"+temp);
                    columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create(temp));
                    columnBinding.setColumnName(name);
                    columnBinding.setColumnClass(String.class);
                    i++;
                }
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    /**
     * for conversion
     *
     * @param index
     * @param converter
     */
    public void setConvert(int index, Converter converter) {
        savedCoverts.put(index, converter);
        jTableBinding.getColumnBinding(index - 1).setConverter(converter);
    }

    /**
     * integer
     *
     * @param objects
     */
    public void setIntegerType(int... objects) {
        savedIntegerColumn = objects;
        for (int temp : objects) {
            jTableBinding.getColumnBinding(temp - 1).setColumnClass(Integer.class);
        }
    }

    /**
     *
     * @param classType
     * @param objects
     */
    public void setColumnType(Class<?> classType, int... objects) {
        if (objects != null) {
            List<Integer> tempArray = new ArrayList<Integer>();
            for (int temp : objects) {
                tempArray.add(temp);
                jTableBinding.getColumnBinding(temp - 1).setColumnClass(classType);
            }
            savedColumnType.put(classType, tempArray);
        }
    }

    /**
     * for Date
     *
     * @param objects
     */
    public void setDateType(int... objects) {
        savedDateColumn = objects;
        for (int temp : objects) {
            jTableBinding.getColumnBinding(temp - 1).setColumnClass(Date.class);
        }
    }

    /**
     * finish bind
     */
    public JTableFormat bind() {
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        bindingGroup.bind();
        return jTableFormat;
    }

    /**
     * 刷新数据
     *
     * @param data
     */
    public void refreshData(List<T> data) {
        this.data = data;
        if (bindObject != null) {
            createTable(bindObject);
            if (savedDateColumn != null) {
                setDateType(savedDateColumn);
            }
            if (savedIntegerColumn != null) {
                setIntegerType(savedIntegerColumn);
            }
            if (savedCoverts != null && savedCoverts.size() > 0) {
                Set<Integer> keys = savedCoverts.keySet();
                for (Integer columnIndex : keys) {
                    setConvert(columnIndex, savedCoverts.get(columnIndex));
                }
            }
            if (savedColumnType != null && savedColumnType.size() > 0) {
                Set<Class> keys = savedColumnType.keySet();
                for (Class columnIndex : keys) {
                    List<Integer> columnsIndex = savedColumnType.get(columnIndex);
                    int[] columnsArray = new int[columnsIndex.size()];
                    for (int i = 0; i < columnsIndex.size(); i++) {
                        columnsArray[i] = columnsIndex.get(i).intValue();
                    }
                    setColumnType(columnIndex, columnsArray);
                }
            }
            jTableBinding.bind();

            if (jTableFormat.savedColumnWidth != null) {
                jTableFormat.setColumnWidth(jTableFormat.savedColumnWidth);
            }

            if (jTableFormat.savedRowHeight >= 0) {
                jTableFormat.setRowHeight(jTableFormat.savedRowHeight);
            }
        }

    }

    /**
     * singel data
     *
     * @return
     */
    public T getSelectedBean() {
        JTable jtable = jTableFormat.getJtable();
        int row = jtable.getSelectedRow();
        if (row > -1) {
            return data.get(row);
        }

        return null;
    }

    /**
     * all data for table
     *
     * @return
     */
    public List<T> getTableData() {
        return data;
    }

    public Printer createPrinter(String reportName) {
        return new Printer(reportName, getTableData());
    }

    public Printer createPrinter(String reportName, List sourceData) {
        return new Printer(reportName, sourceData);
    }

    public class Printer {

        List<T> sourceData;
        String[] columnString;
        String[] columnParameter;
        String reportName;

        public Printer(String reportName, List<T> data) {
            this.reportName = reportName;
            this.sourceData = data;
            columnString = null;
            columnParameter = null;
        }

        private JRDataSource createDataSource() {
            DRDataSource dataSource = null;
            if (bindObject != null && bindObject.length > 0) {
                columnString = new String[bindObject.length];
                columnParameter = new String[bindObject.length];
                for (int i = 0; i < bindObject.length; i++) {
                    columnParameter[i] = bindObject[i][0];
                    columnString[i] = bindObject[i][1];
                }
                dataSource = new DRDataSource(columnParameter);

                if (sourceData != null && sourceData.size() > 0) {
                    for (int i = 0; i < sourceData.size(); i++) {
                        Object[] values = new Object[columnParameter.length];
                        T bean = sourceData.get(i);
                        for (int j = 0; j < columnParameter.length; j++) {
                            try {
                                Object temp = getObject(columnParameter[j], bean, jTableBinding.getColumnBinding(j).getColumnClass());
                                if (temp == null) {
                                    ColumnBinding binder = jTableBinding.getColumnBinding(i);
                                    if (binder.getColumnClass() == String.class) {
                                        temp = "";
                                    } else if (binder.getColumnClass() == Data.class) {
                                        temp = new Date();
                                    }  else if (binder.getColumnClass() == Boolean.class) {
                                        temp = false;
                                    }else {
                                        temp = -1;
                                    }
                                }
                                values[j] = temp;
                            } catch (Exception ex) {
                                logger.error(ex);
                                values[j] = "[error:" + columnParameter[j] + "]";
                            }
                        }
                        dataSource.add(values);
                    }
                }
            }
            return dataSource;
        }

        private Object getObject(String paramater, Object bean, Class finalClass) throws Exception {
            if (paramater != null && !paramater.equals("")) {
                String[] ps = paramater.split("\\.");
                String firstPara = ps[0];
                String getIs = "get";
                if (ps.length == 1 && finalClass == Boolean.class) {
                    getIs = "is";
                }
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
                    return getObject(paramater.substring(firstPara.length() + 1), temp, finalClass);
                } catch (Exception ex) {
                    throw ex;
                }
            }
            return "";
        }

        public void build() throws Exception {

            JRDataSource dataSource = createDataSource();

            if (columnParameter == null || columnParameter.length <= 0) {
                return;
            }
            logger.debug("start to create report");

            TextColumnBuilder[] itemColumns = new TextColumnBuilder[columnParameter.length];
            for (int i = 0; i < columnParameter.length; i++) {
                ColumnBinding binder = jTableBinding.getColumnBinding(i);
                if (binder.getColumnClass() == String.class) {
                    TextColumnBuilder<String> itemColumn = col.column(columnString[i], columnParameter[i], type.stringType());
                    itemColumns[i] = itemColumn;
                } else if (binder.getColumnClass() == Data.class) {
                    TextColumnBuilder<Date> itemColumn = col.column(columnString[i], columnParameter[i], type.dateType());
                    itemColumns[i] = itemColumn;
                } else if (binder.getColumnClass() == BigDecimal.class) {
                    TextColumnBuilder<BigDecimal> itemColumn = col.column(columnString[i], columnParameter[i], type.bigDecimalType());
                    itemColumns[i] = itemColumn;
                } else if (binder.getColumnClass() == Integer.class) {
                    TextColumnBuilder<Integer> itemColumn = col.column(columnString[i], columnParameter[i], type.integerType());
                    itemColumns[i] = itemColumn;
                } else if (binder.getColumnClass() == Double.class) {
                    TextColumnBuilder<Double> itemColumn = col.column(columnString[i], columnParameter[i], type.doubleType());
                    itemColumns[i] = itemColumn;
                } else if (binder.getColumnClass() == Float.class) {
                    TextColumnBuilder<Float> itemColumn = col.column(columnString[i], columnParameter[i], type.floatType());
                    itemColumns[i] = itemColumn;
                } else if (binder.getColumnClass() == Boolean.class) {
                    TextColumnBuilder<Boolean> itemColumn = col.column(columnString[i], columnParameter[i], type.booleanType());
                    itemColumns[i] = itemColumn;
                } else if (binder.getColumnClass() == Long.class) {
                    TextColumnBuilder<Long> itemColumn = col.column(columnString[i], columnParameter[i], type.longType());
                    itemColumns[i] = itemColumn;
                } else {
                    TextColumnBuilder itemColumn = col.column(columnString[i], columnParameter[i], type.stringType());
                    itemColumns[i] = itemColumn;
                }
            }
            report().setTemplate(ReportTemplates.reportTemplate)
                    .columns(itemColumns)
                    .title(ReportTemplates.createTitleComponent(reportName))
                    .pageFooter(ReportTemplates.footerComponent)
                    //.sortBy(asc(itemColumn), desc(unitPriceColumn))
                    .setDataSource(dataSource)
                    .show(false);

            logger.debug("show report:" + reportName);

        }

        class PrintTask extends BaseTask {

            @Override
            public Object doBackgrounp() {
                try {
                    build();
                    return BaseTask.STATUS_OK;
                } catch (Exception ex) {
                    return ex;
                }
            }

            @Override
            public void onSucceeded(Object object) {
                if (object instanceof Exception) {
                    Exception e = (Exception) object;
                    logger.error(e);
                }
            }
        }

        public Task buildInBackgound() {
            return new PrintTask();
        }

    }

    /**
     * 格式化JTABLE
     */
    public class JTableFormat {

        private JTable jtable;

        public int[][] savedColumnWidth;
        public int savedRowHeight;

        JTableFormat(JTable jtable) {
            this.jtable = jtable;
            savedColumnWidth = null;
            savedRowHeight = -1;
        }

        public JTableFormat setColumnWidth(int[]  
            ... columnIndex_widths) {
            savedColumnWidth = columnIndex_widths;
            for (int[] columnIndex_width : columnIndex_widths) {
                getJtable().getColumnModel().getColumn(columnIndex_width[0]).setMaxWidth(columnIndex_width[1]);
                getJtable().getColumnModel().getColumn(columnIndex_width[0]).setMinWidth(columnIndex_width[1]);
            }
            return this;
        }

        public JTableFormat setRowHeight(int rowHeight) {
            savedRowHeight = rowHeight;
            getJtable().setRowHeight(rowHeight);
            return this;
        }

        /**
         * @return the jtable
         */
        public JTable getJtable() {
            return jtable;
        }
    }
}
