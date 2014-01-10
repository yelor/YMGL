/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.swing.JTable;
import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Converter;

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
    private HashMap<Integer, Converter> savedCoverts;

    public BindTableHelper(JTable jtable, List<T> data) {
        bindingGroup = new BindingGroup();
        jTableFormat = new JTableFormat(jtable);
        this.data = data;
        bindObject = null;
        savedDateColumn = null;
        savedIntegerColumn = null;
        savedCoverts = null;
    }

    public void createTable(Class bean) {
        try {
            jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, data, jTableFormat.getJtable());

            // System.out.println("data::"+data);
            Field[] f = bean.getDeclaredFields();
            org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = null;

            for (Field a : f) {
                String name = a.getName();
                if (undisplayString.indexOf(name) > -1) {
                    continue;
                } else {
                    String temp = "${" + name + "}";
                    // System.out.println("bind object:"+temp);
                    columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create(temp));
                    columnBinding.setColumnName(name);
                    columnBinding.setColumnClass(String.class);
                }
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
            for (Object key : keys) {
                if (key instanceof String) {
                    String name = key.toString();
                    String temp = "${" + name + "}";
                    // System.out.println("bind object:"+temp);
                    columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create(temp));
                    columnBinding.setColumnName(name);
                    columnBinding.setColumnClass(String.class);
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
        savedCoverts = new HashMap<Integer, Converter>();
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
