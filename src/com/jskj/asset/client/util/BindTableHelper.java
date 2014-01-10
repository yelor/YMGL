/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultCellEditor;
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
    private final HashMap<Integer, Converter> savedCoverts;
    private final List<Integer> savedCanEdit;
    private final HashMap<String[], Class<?>> savedEmptyColumns;

    public BindTableHelper(JTable jtable, List<T> data) {
        bindingGroup = new BindingGroup();
        jTableFormat = new JTableFormat(jtable);
        this.data = data;
        bindObject = null;
        savedDateColumn = null;
        savedIntegerColumn = null;
        savedCoverts = new HashMap<Integer, Converter>();
        savedCanEdit = new ArrayList<Integer>();
        savedEmptyColumns = new HashMap<String[], Class<?>>();
    }

    public void createTable(Class bean) {
        try {
            jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, data, jTableFormat.getJtable());

            // System.out.println("data::"+data);
            Field[] f = bean.getDeclaredFields();
            int i = 0;
            for (Field a : f) {
                String name = a.getName();
                if (undisplayString.indexOf(name) > -1) {
                    continue;
                } else {
                    String temp = "${" + name + "}";
                    // System.out.println("bind object:"+temp);
                    jTableBinding.addColumnBinding(i, org.jdesktop.beansbinding.ELProperty.create(temp))
                            .setColumnName(name)
                            .setColumnClass(String.class).setEditable(false);
                    i++;
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
            if (bindObject != null && bindObject.length > 0) {
                for (int i = 0; i < bindObject.length; i++) {
                    String temp = bindObject[i][0];
                    String name = bindObject[i][1];
                    jTableBinding.addColumnBinding(i, org.jdesktop.beansbinding.ELProperty.create("${" + temp + "}"))
                            .setColumnName(name)
                            .setColumnClass(String.class).setEditable(false);
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
            int i = 0;
            for (Object key : keys) {
                if (key instanceof String) {
                    String name = key.toString();
                    String temp = "${" + name + "}";
                    // System.out.println("bind object:"+temp);
                    jTableBinding.addColumnBinding(i, org.jdesktop.beansbinding.ELProperty.create(temp))
                            .setColumnName(name)
                            .setColumnClass(String.class).setEditable(false);
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

    public void setColumnEditable(boolean editable, int startIndex, int endIndex) {

        for (int i = startIndex; i <= endIndex; i++) {
            jTableBinding.getColumnBinding(i - 1).setEditable(editable);
            if (editable) {
                savedCanEdit.add(i);
            }
        }
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

    public BindTableHelper addEmptyColumn(String[] headerName, Class<?> columnClass) {
        savedEmptyColumns.put(headerName, columnClass);
        jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${" + headerName[0] + "}")).setColumnName(headerName[1]).setColumnClass(columnClass).setEditable(true);
        return this;
    }

    /**
     * finish bind
     *
     * @return
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
                    jTableBinding.getColumnBinding(columnIndex - 1).setConverter(savedCoverts.get(columnIndex));
                }
            }
            if (savedCanEdit != null && savedCanEdit.size() > 0) {
                for (Integer index : savedCanEdit) {
                    jTableBinding.getColumnBinding(index - 1).setEditable(true);
                }
            }
            if (savedEmptyColumns != null && savedEmptyColumns.size() > 0) {
                Set<String[]> keys = savedEmptyColumns.keySet();
                for (String[] headerName : keys) {
                    jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${" + headerName[0] + "}")).setColumnName(headerName[1]).setColumnClass(savedEmptyColumns.get(headerName)).setEditable(true);
                }
            }
            jTableBinding.bind();

            if (jTableFormat.savedColumnWidth != null) {
                jTableFormat.setColumnWidth(jTableFormat.savedColumnWidth);
            }

            if (jTableFormat.savedRowHeight >= 0) {
                jTableFormat.setRowHeight(jTableFormat.savedRowHeight);
            }

            if (jTableFormat.savedCellEditor != null && jTableFormat.savedCellEditor.size() > 0) {
                Set<Integer> keys = jTableFormat.savedCellEditor.keySet();
                for (Integer columnIndex : keys) {
                    DefaultCellEditor celleditor = jTableFormat.savedCellEditor.get(columnIndex);
                    jTableFormat.getJtable().getColumnModel().getColumn(columnIndex).setCellEditor(celleditor);
                }
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

        private final JTable jtable;

        public int[][] savedColumnWidth;
        public int savedRowHeight;
        public HashMap<Integer, DefaultCellEditor> savedCellEditor;

        JTableFormat(JTable jtable) {
            this.jtable = jtable;
            savedColumnWidth = null;
            savedRowHeight = -1;
            savedCellEditor = new HashMap<Integer, DefaultCellEditor>();

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
