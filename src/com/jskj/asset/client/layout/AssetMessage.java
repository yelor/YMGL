/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.AssetClientView;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import org.jdesktop.application.Application;

/**
 *
 * @author woderchen
 */
public class AssetMessage extends JOptionPane {

    public static final int ERROR_MESSAGE = 0;

    public static final int INFO_MESSAGE = 1;

    public static final int WARN_MESSAGE = 2;

    private static final javax.swing.JTextArea contentArea;

    public AssetMessage() {
        Dimension d = new Dimension(106, 300);
        this.setPreferredSize(d);
    }

    static {
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        contentArea = new javax.swing.JTextArea();
        jScrollPane1.setName("jScrollPane1"); // NOI18N
        contentArea.setName("jEditorPane1"); // NOI18N
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(contentArea);

        Dimension d = new Dimension(106, 300);
        jScrollPane1.setMinimumSize(d);
        jScrollPane1.setMaximumSize(d);
        jScrollPane1.setAutoscrolls(true);
    }

    public static void ERROR(String text) {
        AssetClientView clientView = (AssetClientView) Application.getInstance(AssetClientApp.class).getMainView();
        clientView.setStatus(text, ERROR_MESSAGE);
        contentArea.setText(text);
        showMessageDialog(AssetClientApp.getApplication().getMainFrame(), contentArea, "错误", JOptionPane.ERROR_MESSAGE);
    }

    public static void ERROR(String text, Component parentComponent) {
        AssetClientView clientView = (AssetClientView) Application.getInstance(AssetClientApp.class).getMainView();
        clientView.setStatus(text, ERROR_MESSAGE);
        contentArea.setText(text);
        showMessageDialog(parentComponent, contentArea, "错误", JOptionPane.ERROR_MESSAGE);

    }

    public static void ERRORSYS(String text) {
        AssetClientView clientView = (AssetClientView) Application.getInstance(AssetClientApp.class).getMainView();
        clientView.setStatus(text, ERROR_MESSAGE);
        contentArea.setText(text);
        showMessageDialog(AssetClientApp.getApplication().getMainFrame(), contentArea, "错误", JOptionPane.ERROR_MESSAGE);

    }

    public static void ERRORSYS(String text, Component parentComponent) {
        AssetClientView clientView = (AssetClientView) Application.getInstance(AssetClientApp.class).getMainView();
        clientView.setStatus(text, ERROR_MESSAGE);
        contentArea.setText(text);
        showMessageDialog(parentComponent, contentArea, "错误", JOptionPane.ERROR_MESSAGE);

    }

    public static void INFO(String text) {
        AssetClientView clientView = (AssetClientView) Application.getInstance(AssetClientApp.class).getMainView();
        clientView.setStatus(text, INFO_MESSAGE);
        showMessageDialog(AssetClientApp.getApplication().getMainFrame(), text, "信息", JOptionPane.INFORMATION_MESSAGE);

    }

    public static void INFO(String text, Component parentComponent) {
        AssetClientView clientView = (AssetClientView) Application.getInstance(AssetClientApp.class).getMainView();
        clientView.setStatus(text, INFO_MESSAGE);
        showMessageDialog(parentComponent, text, "信息", JOptionPane.INFORMATION_MESSAGE);

    }

    public static int CONFIRM(String text) {
        return showConfirmDialog(AssetClientApp.getApplication().getMainFrame(), text, "确认", JOptionPane.OK_CANCEL_OPTION);
    }

    public static int CONFIRM(Component parentComponent, String text) {
        return showConfirmDialog(parentComponent, text, "确认", JOptionPane.OK_CANCEL_OPTION);
    }

}
