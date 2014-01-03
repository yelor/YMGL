/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.AssetClientView;
import javax.swing.JOptionPane;
import org.jdesktop.application.Application;

/**
 *
 * @author woderchen
 */
public class AssetMessage {

    public static final int ERROR_MESSAGE = 0;

    public static final int INFO_MESSAGE = 1;

    public static final int WARN_MESSAGE = 2;

    public static void ERROR(String text) {
       // JOptionPane.showMessageDialog(AssetClientApp.getApplication().getMainFrame(), text, "错误", JOptionPane.ERROR_MESSAGE);
        AssetClientView clientView = (AssetClientView) Application.getInstance(AssetClientApp.class).getMainView();
        clientView.setStatus(text, ERROR_MESSAGE);
    }

    public static void ERRORSYS(String text) {
       // JOptionPane.showMessageDialog(AssetClientApp.getApplication().getMainFrame(), text, "错误", JOptionPane.ERROR_MESSAGE);
        AssetClientView clientView = (AssetClientView) Application.getInstance(AssetClientApp.class).getMainView();
        clientView.setStatus(text, ERROR_MESSAGE);
    }

    public static void INFO(String text) {
        JOptionPane.showMessageDialog(AssetClientApp.getApplication().getMainFrame(), text, "信息", JOptionPane.INFORMATION_MESSAGE);
        AssetClientView clientView = (AssetClientView) Application.getInstance(AssetClientApp.class).getMainView();
        clientView.setStatus(text, INFO_MESSAGE);
    }

    public static int CONFIRM(String text) {
        return JOptionPane.showConfirmDialog(AssetClientApp.getApplication().getMainFrame(), text, "确认", JOptionPane.OK_CANCEL_OPTION);
    }

}
