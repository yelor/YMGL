/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

import java.awt.Color;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

/**
 *
 * @author woderchen
 */
public class LogPaneAppender extends AppenderSkeleton {

    private final static int idealLine = 150;
    private final static int maxExcess = 30;
    private javax.swing.JTextPane logPane;

    public LogPaneAppender() {
        super();
    }

    @Override
    protected void append(LoggingEvent le) {
        try {
            Document doc = getLogPane().getDocument();
            int length = doc.getLength();

            if (le.getLevel().equals(Level.ERROR)) {
                setDocs(getMessage(le), Color.RED);
            } else if (le.getLevel().equals(Level.WARN)) {
                setDocs(getMessage(le), Color.PINK);
            } else {
                setDocs(getMessage(le),null);
            }
            getLogPane().setCaretPosition(length);

            int excess = getLineCount() - idealLine;
            if (excess >= maxExcess) {
                replaceRange("", 0, getLineStartOffset(excess));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private String getMessage(LoggingEvent le) {
        String oldMes = le.getMessage().toString() + "\r\n";
        String message = DateHelper.getDate(le.getTimeStamp()) + " [" + le.getLevel().toString() + "] " + oldMes;

        return message;
    }

    public void replaceRange(String str, int start, int end) {
        if (end < start) {
            throw new IllegalArgumentException("end before start");
        }
        Document doc = getLogPane().getDocument();
        if (doc != null) {
            try {
                if (doc instanceof AbstractDocument) {
                    ((AbstractDocument) doc).replace(start, end - start, str,
                            null);
                } else {
                    doc.remove(start, end - start);
                    doc.insertString(start, str, null);
                }
            } catch (BadLocationException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    public int getLineStartOffset(int line) {
        Element lineElement = getLogPane().getDocument().getDefaultRootElement().getElement(line);
        if (lineElement == null) {
            return -1;
        } else {
            return lineElement.getStartOffset();
        }
    }

    public void insert(String str, AttributeSet attrSet) {
        Document doc = getLogPane().getDocument();
        try {
            doc.insertString(doc.getLength(), str, attrSet);
        } catch (BadLocationException e) {
            System.out.println("BadLocationException:   " + e);
        }
    }

    public void setDocs(String str, Color col) {
        if (col != null) {
            SimpleAttributeSet attrSet = new SimpleAttributeSet();
            StyleConstants.setForeground(attrSet, col);
            //颜色
//        if (bold == true) {
//            StyleConstants.setBold(attrSet, true);
//        }//字体类型
            // StyleConstants.setFontSize(attrSet, fontSize);
            //字体大小
            insert(str, attrSet);
        }else{
            insert(str, null);
        }

    }

    public final int getLineCount() {
        return getLogPane().getDocument().getDefaultRootElement().getElementCount();
    }

    public void close() {
    }

    public boolean requiresLayout() {
        return false;
    }

    /**
     * @return the logPane
     */
    public javax.swing.JTextPane getLogPane() {
        return logPane;
    }

    /**
     * @param logPane the logPane to set
     */
    public void setLogPane(javax.swing.JTextPane logPane) {
        this.logPane = logPane;
    }
}
