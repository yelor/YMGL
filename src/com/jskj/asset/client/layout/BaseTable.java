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
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
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
    HashMap<String, Integer> columnIdMap = null;

    public BaseTable(Object actionObject) {
        super();
        //右键支持
        rightMouseAdapter = new RightMouseAdapter(this, actionObject);
        addMouseListener(rightMouseAdapter);
        setRowHeight(25);
        singleEditRowTable = null;
        getTableHeader().setReorderingAllowed(false);

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
            // openCellPane();
        }
    }

    private void _mouseReleased(java.awt.event.MouseEvent evt) {
        if (!evt.isPopupTrigger()) {
            enableRightButton(true);
        }
    }

  
    public void addCellListener(final BaseCellFocusListener listener){
       this.getCellEditor(0, 0).addCellEditorListener(new CellEditorListener(){

            @Override
            public void editingStopped(ChangeEvent e) {
                int column = BaseTable.this.getSelectedColumn();
                int row = BaseTable.this.getSelectedRow();
                listener.editingStopped(row,column);
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
            }
        });
    }

    public class SingleEditRowTable implements TableModelListener, KeyListener {

        private Popup pop;
        private boolean isShow;
//        private BasePopup basePopup;
        private final BaseTable table;
        public int displayColumnPopupIndex = -1;
        public boolean hasRegister;
        private int selectedRow = -1;
        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(this);

        public HashMap<Integer, BasePopup> registerColumn;

        public SingleEditRowTable(BaseTable table) {
            this.table = table;
            hasRegister = false;
            registerColumn = new HashMap<Integer, BasePopup>();

            //table.setShowVerticalLines(false);
            table.addKeyListener(this);
            table.getModel().addTableModelListener(this);

            table.getTableHeader().setReorderingAllowed(false);
            //取消回车键
            ActionMap am = table.getActionMap();
            am.getParent().remove("selectNextRowCell");
            table.setActionMap(am);
        }

        public void registerPopup(final int columnPopupIndex, IPopupBuilder popBuilder) {

//            this.columnPopupIndex = columnPopupIndex;
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
            BasePopup basePopup = new BasePopup(popBuilder) {
                @Override
                public void closePopup() {
                    getCellEditor(selectedRow, columnPopupIndex).stopCellEditing();
                    hidePanel();

                    //如果是最后一行，编辑后，增加新行
                    if (table.getSelectedRow() == (table.getRowCount() - 1)) {
                        addNewRow();
                    }
                }
            };

            registerColumn.put(columnPopupIndex, basePopup);

            logger.debug("register popup for column:" + columnPopupIndex + ",row:" + selectedRow);
            table.getColumnModel().getColumn(columnPopupIndex).setCellRenderer(new RichTableCellRenderer(icon));
            hasRegister = true;

        }

        public void hidePanel() {
            if (pop != null) {
                logger.debug("hide popup panel,column:" + displayColumnPopupIndex + ",row:" + selectedRow);
                isShow = false;
                pop.hide();
                pop = null;
                table.getCellEditor(selectedRow, displayColumnPopupIndex).stopCellEditing();
            }
        }

        public void showPanel() {
            if (pop != null) {
                pop.hide();
            }
            int selectedColumn = getSelectedColumn();
            BasePopup basePopup = registerColumn.get(selectedColumn);
            if (basePopup == null) {
                return;
            }

            logger.debug("show popup panel,column:" + selectedColumn + ",row:" + selectedRow);
            Point p = table.getLocationOnScreen();

            selectedRow = getSelectedRow();

            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

            int selectedColumnX = p.x;
            int selectedColumnY = p.y + (selectedRow + 1) * table.getRowHeight();

            if (selectedColumn > 0) {
                for (int i = 0; i < selectedColumn; i++) {
                    selectedColumnX += table.getColumnModel().getColumn(i).getWidth();
                }
            }

            int popHeight = basePopup.getPreferredSize().height;
            int popWitdh = basePopup.getPreferredSize().width;

            if ((selectedColumnY + popHeight) > size.getHeight()) {
                selectedColumnY = selectedColumnY - basePopup.getPreferredSize().height - table.getRowHeight();
            }

            if ((selectedColumnX + popWitdh) > size.getWidth()) {
                selectedColumnX = selectedColumnX - basePopup.getPreferredSize().width;
            }

            pop = PopupFactory.getSharedInstance().getPopup(table, basePopup, selectedColumnX, selectedColumnY);
            basePopup.setKey(table.getValueAt(table.getSelectedRow(), selectedColumn).toString());
            basePopup.requestFocusInWindow();
            pop.show();
            displayColumnPopupIndex = selectedColumn;
            isShow = true;
        }

        public void insertValue(int columnIndex, Object obj) {
            logger.debug("insert new value to table,value:" + obj + ",row:" + selectedRow + ",column:" + columnIndex);
            if (selectedRow == -1 || columnIndex == -1) {
                return;
            }
            table.setValueAt(obj, selectedRow, columnIndex);
        }

        public void insertValue(int rowIndex, int columnIndex, Object obj) {
            logger.debug("insert new value to table,value:" + obj + ",row:" + rowIndex + ",column:" + columnIndex);
            if (rowIndex == -1 || columnIndex == -1) {
                return;
            }
            table.setValueAt(obj, rowIndex, columnIndex);
        }

        public void addNewRow() {
            AssetTableModel tableMode = (AssetTableModel) table.getModel();
            List newRow = new ArrayList();
            for (int i = 0; i < table.getColumnCount(); i++) {
                newRow.add("");
            }
            tableMode.getDataVector().add(newRow);
            table.updateUI();
        }

        public void tableChanged(TableModelEvent e) {
            if (e.getType() == TableModelEvent.UPDATE) {

            }

        }

        public void keyTyped(KeyEvent e) {

        }

        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            int selectedColumn = table.getSelectedColumn();
            if (registerColumn.containsKey(selectedColumn)) {
                BasePopup basePopup = registerColumn.get(selectedColumn);
                JTable popUptable = basePopup.getTable();
                //禁止掉上下键
                if (isShow) {
                    if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                        e.consume();
                    }
                }

                if (keyCode == KeyEvent.VK_ENTER) {
                    if (isShow) {
                        int selectedrow = popUptable.getSelectedRow();
                        if (selectedrow >= 0) {
                            basePopup.setPopValueToParent();
                        } else {
                            int selectedRow = table.getSelectedRow();
                            logger.debug("ENTER for column:" + selectedColumn + ",row:" + selectedRow);
                            if (registerColumn.containsKey(selectedColumn)) {

                                table.getCellEditor(selectedRow, selectedColumn).stopCellEditing();

                                if (isShow) {
                                    String value = table.getValueAt(table.getSelectedRow(), selectedColumn).toString();
                                    logger.debug("set new value for popup:" + value);
                                    registerColumn.get(selectedColumn).setKey(value);
                                }
                            }

                        }
                    } else {
                        showPanel();
                    }
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    if (isShow) {
                        int selectedrow = popUptable.getSelectedRow();
                        int upRow = selectedrow + 1;
                        //System.out.println("keyCodekeyCode:VK_DOWN:" + upRow);
                        if (upRow < popUptable.getRowCount()) {
                            popUptable.setRowSelectionInterval(upRow, upRow);
                        }
                    }
                } else if (keyCode == KeyEvent.VK_UP) {
                    if (isShow) {
                        int selectedrow = popUptable.getSelectedRow();
                        int upRow = selectedrow - 1;
                        // System.out.println("keyCodekeyCode:VK_UP:" + upRow);
                        if (upRow >= 0) {
                            popUptable.setRowSelectionInterval(upRow, upRow);
                        } else {
                            popUptable.clearSelection();
                        }
                    }
                } else {
                    if (isShow) {
                        popUptable.clearSelection();
                    }
                }

            }

        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            }
        }

    }

    @Override
    public void changeSelection(int rowIndex, int columnIndex,
            boolean toggle, boolean extend) {
        super.changeSelection(rowIndex, columnIndex, toggle, extend);
        super.editCellAt(rowIndex, columnIndex, null);
        //System.out.println("changeSelection,rowIndex:" + rowIndex + ",columnIndex:" + columnIndex + ",toggle:" + toggle + ",extend" + extend);
        if (singleEditRowTable != null && singleEditRowTable.hasRegister == true) {
            int selectedColumn = getSelectedColumn();
            int selectedRow = getSelectedRow();

            if (singleEditRowTable.registerColumn != null && singleEditRowTable.registerColumn.containsKey(selectedColumn)) {
                //getCellEditor(selectedRow, selectedColumn).stopCellEditing();
                singleEditRowTable.showPanel();
            } else {
                singleEditRowTable.hidePanel();
            }

        }
    }

    public SingleEditRowTable createSingleEditModel(String[][] beanColumns) {
        List columnNameArray = new ArrayList();
        columnIdMap = new HashMap< String, Integer>();
        List datas = new ArrayList();
        List firstRowEmptyData = new ArrayList();
        editColumn = new ArrayList<Integer>();
        int i = 0;
        for (String[] column : beanColumns) {
            columnNameArray.add(column[1]);
            columnIdMap.put(column[0], i);
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

    public Object getValue(int rowIndex, String columnId) {
        if (columnIdMap != null) {
            Object obj = getValueAt(rowIndex, columnIdMap.get(columnId));
            return obj;
        }
        return "";
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

    /**
     * 复制到剪切板
     */
    @RightItem(value = "删除行", enable = false, hasSeparator = false)
    @Action
    public void zzzzzzzCSZ() {
        TableModel tableMode = getModel();
        if (tableMode instanceof AssetTableModel) {
            List<List> data = ((AssetTableModel) tableMode).getDataVector();
            int selectedRow = getSelectedRow();

            if (data.size() > 1) {
                for (int i = 0; i < data.size(); i++) {
                    if (i == selectedRow) {
                        data.remove(i);
                    }
                }
//            ((AssetTableModel)tableMode).getDataVector().add(data);
                updateUI();
            }
        }
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
