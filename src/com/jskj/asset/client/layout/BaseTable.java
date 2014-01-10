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
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
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

    public BaseTable(Object actionObject) {
        super();
        //右键支持
        rightMouseAdapter = new RightMouseAdapter(this, actionObject);
        addMouseListener(rightMouseAdapter);
        setRowHeight(25);

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

    /**
     * 设置table数据
     *
     * @param data
     * @param columnList
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

}
