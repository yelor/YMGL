/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.panel.DisplayCellPane;
import com.jskj.asset.client.util.RightItem;
import com.jskj.asset.client.util.RightMouseAdapter;
import com.jskj.asset.client.util.UIRunnable;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;

/**
 *
 * @author woderchen
 */
public class BaseTable extends JTable {

    private static Logger logger = Logger.getLogger(BaseTable.class);
    private int[] selects;
    private DisplayCellPane displayCellPane;
    private RightMouseAdapter rightMouseAdapter;
    private List<List> tableData;
    private final Clipboard clipboard = getToolkit().getSystemClipboard();//定义剪切板对象
    private List<Integer> editColumn = null;
    private SingleEditRowTable singleEditRowTable;

    public BaseTable(Object actionObject) {
        super();
        //右键支持
        rightMouseAdapter = new RightMouseAdapter(this, actionObject);
        addMouseListener(rightMouseAdapter);
        setRowHeight(25);
        singleEditRowTable = null;

        addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _mouseClicked(evt);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                _mouseReleased(evt);
            }
        });

    }

    private void _mouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) { //是双//击事件。
            openCellPane();
        }
    }

    private void _mouseReleased(java.awt.event.MouseEvent evt) {
        if (!evt.isPopupTrigger()) {
            enableRightButton(true);
        }
    }

    public class SingleEditRowTable implements TableModelListener, KeyListener {

        private Popup pop;
        private boolean isShow;
        private BasePopup basePopup;
        private BaseTable table;
        public int columnPopupIndex = -1;
        public boolean hasRegister;
        private int selectedRow = -1;
        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(this);

        public SingleEditRowTable(BaseTable table) {
            this.table = table;
            hasRegister = false;
        }

        public void registerPopup(final int columnPopupIndex, IPopupBuilder popBuilder) {

            this.columnPopupIndex = columnPopupIndex;
            ImageIcon icon = null;
            switch (popBuilder.getType()) {
                case IPopupBuilder.TYPE_DATE_CLICK:
                    icon = new ImageIcon(getClass().getResource(IPopupBuilder.ICON_DATE));
                    break;
                case IPopupBuilder.TYPE_POPUP_TEXT:
                    icon = new ImageIcon(getClass().getResource(IPopupBuilder.ICON_POPUP_TEXT));
                    break;
                default:
                    icon = new ImageIcon(getClass().getResource(IPopupBuilder.ICON_POPUP_TABLE));
            }
            basePopup = new BasePopup(popBuilder) {
                @Override
                public void closePopup() {
                    hidePanel();
                }
            };
            logger.debug("register popup for column:" + columnPopupIndex + ",row:" + selectedRow);
            //table.setShowVerticalLines(false);
            table.addKeyListener(this);
            table.getModel().addTableModelListener(this);
            table.getColumnModel().getColumn(columnPopupIndex).setCellRenderer(new RichTableCellRenderer(icon));
            table.getTableHeader().setReorderingAllowed(false);
            //取消回车键
            ActionMap am = table.getActionMap();
            am.getParent().remove("selectNextRowCell");
            table.setActionMap(am);

            hasRegister = true;

        }

        public void hidePanel() {
            if (pop != null) {
                logger.debug("hide popup panel,column:" + columnPopupIndex + ",row:" + selectedRow);
                isShow = false;
                pop.hide();
                pop = null;
                table.getCellEditor(selectedRow, columnPopupIndex).stopCellEditing();
            }
        }

        public void showPanel() {
            if (pop != null) {
                pop.hide();
            }
            logger.debug("show popup panel,column:" + columnPopupIndex + ",row:" + selectedRow);
            Point p = table.getLocationOnScreen();

            int selectedColumn = getSelectedColumn();
            selectedRow = getSelectedRow();

            int selectedColumnX = p.x;
            int selectedColumnY = p.y + (selectedRow + 1) * table.getRowHeight();

            if (selectedColumn > 0) {
                for (int i = 0; i < selectedColumn; i++) {
                    selectedColumnX += table.getColumnModel().getColumn(i).getWidth();
                }
            }

            pop = PopupFactory.getSharedInstance().getPopup(table, basePopup, selectedColumnX, selectedColumnY);
            basePopup.setKey(table.getValueAt(table.getSelectedRow(), columnPopupIndex).toString());
            basePopup.requestFocusInWindow();
            pop.show();
            isShow = true;
        }

        public void insertValue(int columnIndex, Object obj) {
            logger.debug("insert new value to table,value:" + obj + ",row:" + selectedRow + ",column:" + columnIndex);
            if (selectedRow == -1 || columnIndex == -1) {
                return;
            }
            table.setValueAt(obj, selectedRow, columnIndex);
        }

        public void tableChanged(TableModelEvent e) {
            if (e.getType() == TableModelEvent.UPDATE) {

            }

        }

        public void keyTyped(KeyEvent e) {

        }

        public void keyPressed(KeyEvent e) {

        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                int selectedColumn = table.getSelectedColumn();
                int selectedRow = table.getSelectedRow();

                logger.debug("ENTER for column:" + selectedColumn + ",row:" + selectedRow);

                //如果是最后一行，编辑后，增加新行
                if (table.getSelectedRow() == (table.getRowCount() - 1)) {
                    AssetTableModel tableMode = (AssetTableModel) table.getModel();
                    List newRow = new ArrayList();
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        newRow.add("");
                    }
                    tableMode.getDataVector().add(newRow);
                }

                if (selectedColumn == columnPopupIndex) {
                    table.getCellEditor(selectedRow, selectedColumn).stopCellEditing();

                    if (isShow) {
                        String value = table.getValueAt(table.getSelectedRow(), columnPopupIndex).toString();
                        logger.debug("set new value for popup:" + value);
                        basePopup.setKey(value);
                    }
                }
            }
        }

    }

    @Override
    public void changeSelection(int rowIndex, int columnIndex,
            boolean toggle, boolean extend) {
        super.changeSelection(rowIndex, columnIndex, toggle, extend);
        super.editCellAt(rowIndex, columnIndex, null);
        // System.out.println("changeSelection,rowIndex:" + rowIndex + ",columnIndex:" + columnIndex + ",toggle:" + toggle + ",extend" + extend);
        if (singleEditRowTable != null && singleEditRowTable.hasRegister == true) {
            int selectedColumn = getSelectedColumn();
            int selectedRow = getSelectedRow();
            if (selectedColumn == singleEditRowTable.columnPopupIndex) {
                singleEditRowTable.showPanel();
            } else {
                singleEditRowTable.hidePanel();
            }

        }
    }

    public SingleEditRowTable createSingleEditModel(String[][] beanColumns) {
        List columnNameArray = new ArrayList();
        List columnIdArray = new ArrayList();
        List datas = new ArrayList();
        List firstRowEmptyData = new ArrayList();
        editColumn = new ArrayList<Integer>();
        int i = 0;
        for (String[] column : beanColumns) {
            columnNameArray.add(column[1]);
            columnIdArray.add(column[0]);
            firstRowEmptyData.add("");
            if (column.length > 2 && column[2].equalsIgnoreCase("true")) {
                editColumn.add(i);
            }
            i++;

        }
        datas.add(firstRowEmptyData);
        setModel(new AssetTableModel(datas, columnNameArray, true, editColumn));
        getModel().addTableModelListener(this);
        logger.debug("create single row table:" + beanColumns.toString());
        singleEditRowTable = new SingleEditRowTable(this);

        return singleEditRowTable;
    }

    /**
     * 设置table数据
     *
     * @param model
     */
    public void setModel(AssetTableModel model) {
        this.tableData = model.getDataVector();
        List columnList = model.getColumnIdentifiers();
        enableRightButton(false);
        super.setModel(model);
    }

    /**
     * 选择状态下的值
     *
     * @param flag
     */
    private void enableRightButton(boolean flag) {
        if (flag) {
            selects = getSelectedRows();
        } else {
            selects = null;
        }
        rightMouseAdapter.enable(flag);
    }

    private List getSelectedData() {
        List ls = new ArrayList();
        if (selects != null && selects.length > 0) {

            if (tableData != null) {

                for (int num : selects) {
                    //Object obj = jTableResult.getValueAt(num, 0);
                    ls.add(tableData.get(num));
                    // logger.debug("select object:" + ((List) tableData.get(num)).get(0));
                }
            }
        }

        return ls;
    }

    @Action
    private void openCellPane() {

        if (getModel().isCellEditable(getSelectedRow(), getSelectedColumn()) == true) {
            return;
        }
        Object selectValue = getValueAt(getSelectedRow(), getSelectedColumn());
        if (selectValue != null) {

            SwingUtilities.invokeLater(new UIRunnable<Object>(selectValue) {

                @Override
                public void run() {
                    if (displayCellPane == null) {
                        JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                        displayCellPane = new DisplayCellPane(mainFrame, true);
                        displayCellPane.setLocationRelativeTo(mainFrame);
                    }
                    displayCellPane.setCellValue(getValue());
                    AssetClientApp.getApplication().show(displayCellPane);
                }
            });

        }
    }

    /**
     * 复制到剪切板
     */
    @RightItem(value = "复制数据", enable = false)
    @Action
    public void zzzzzzzCST() {
        Object selectValue = getValueAt(getSelectedRow(), getSelectedColumn());
        if (selectValue != null) {
            clipboard.setContents(new StringSelection(selectValue.toString()), null);
        }
    }

    /**
     * 复制到剪切板
     */
    @RightItem(value = "复制行数据", enable = false, hasSeparator = true)
    @Action
    public void zzzzzzzCSU() {
        List<List> data = getSelectedData();

        StringBuilder allTemp = new StringBuilder();
        for (List columnData : data) {
            StringBuilder temp = new StringBuilder();
            int columnLength = columnData.size();
            for (int i = 0; i < columnLength; i++) {//写入行
                Object value = columnData.get(i);
                if (i == columnLength - 1) {
                    temp.append(value == null ? "" : value).append("\r\n");

                } else {
                    temp.append(value == null ? "" : value).append(",");
                }

            }
            allTemp.append(temp);
        }
        clipboard.setContents(new StringSelection(allTemp.toString()), null);
    }

    class RichTableCellRenderer extends DefaultTableCellRenderer {

        ImageIcon icon;

        public RichTableCellRenderer(ImageIcon icon) {
            this.icon = icon;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setIcon(null);
            setBorder(null);
            if (icon != null) {
                setIcon(icon);
            }

            return this;
        }

    }

}
