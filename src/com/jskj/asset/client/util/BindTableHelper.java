/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author woderchen
 */
public class BindTableHelper<T> {

    private T bean;
    private BindingGroup bindingGroup;
    private org.jdesktop.swingbinding.JTableBinding jTableBinding;
    private String undisplayString = "serialVersionUID";
    private JTableFormat jTableFormat;
    private List<T> data;

    public BindTableHelper(JTable jtable, List<T> data, T bean) {
        bindingGroup = new BindingGroup();
        jTableFormat = new JTableFormat(jtable);
        this.data = data;
        this.bean = bean;
    }

    public BindTableHelper(JTable jtable, List<T> data) {
        this(jtable, data, null);
    }

//    public void columFilter(String undisplayStr){
//       this.undisplayString = undisplayString+","+undisplayStr;
//    }
    public void createTable() {
        try {
            jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, data, jTableFormat.getJtable());

            // System.out.println("data::"+data);
            Field[] f = bean.getClass().getDeclaredFields();
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
            e.printStackTrace();
        }
    }

    public void createTable(String[][] bindObject) {
        try {
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
        jTableBinding.getColumnBinding(index - 1).setConverter(converter);
    }

    /**
     * integer
     *
     * @param index
     */
    public void setIntegerType(int... objects) {
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

        JTableFormat(JTable jtable) {
            this.jtable = jtable;
        }

        public JTableFormat setColumnWidth(int[]  
            ... columnIndex_widths) {
            for (int[] columnIndex_width : columnIndex_widths) {
                getJtable().getColumnModel().getColumn(columnIndex_width[0]).setMaxWidth(columnIndex_width[1]);
                getJtable().getColumnModel().getColumn(columnIndex_width[0]).setMinWidth(columnIndex_width[1]);
            }
            return this;
        }

        public JTableFormat setRowHeight(int rowHeight) {
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
