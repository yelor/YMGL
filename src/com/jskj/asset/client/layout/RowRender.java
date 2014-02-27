/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author 305027939
 */
public abstract class RowRender extends DefaultTableCellRenderer {

    private String tableType;

    public RowRender(String tableType) {
        super();
        this.tableType = tableType;
    }

    public RowRender() {
        this("");
    }

    private String dateToString(Date date, String format) {
        try {
            if (date != null) {
                return new SimpleDateFormat(format).format(date);
            }
        } catch (Exception e) {
        }
        return "";
    }

    public abstract void setRender(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column);
    
    @Override
    public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        setRender( t,  value,  isSelected,  hasFocus,  row,  column);
        
        return super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
    }

    class CellImageObserver
            implements ImageObserver {

        JTable table;
        int row;
        int col;

        CellImageObserver(JTable table, int row, int col) {
            this.table = table;
            this.row = row;
            this.col = col;
        }

        public boolean imageUpdate(Image img, int flags, int x, int y, int w, int h) {
            if ((flags & 0x30) != 0) {
                Rectangle rect = this.table.getCellRect(this.row, this.col, false);
                this.table.repaint(rect);
            }
            return (flags & 0xA0) == 0;
        }
    }
}
