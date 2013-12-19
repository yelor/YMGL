/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.layout;

import com.jskj.asset.client.AssetClientApp;
import javax.swing.JOptionPane;

/**
 *
 * @author woderchen
 */
public class AssetMessage {

    public static void ERROR(String text){
       JOptionPane.showMessageDialog(AssetClientApp.getApplication().getMainFrame(), text, "错误", JOptionPane.ERROR_MESSAGE);
    }

    public static void ERRORSYS(String text){
       JOptionPane.showMessageDialog(AssetClientApp.getApplication().getMainFrame(), text, "错误", JOptionPane.ERROR_MESSAGE);
    }

    public static void INFO(String text){
       JOptionPane.showMessageDialog(AssetClientApp.getApplication().getMainFrame(), text, "信息", JOptionPane.INFORMATION_MESSAGE);
    }

     public static int CONFIRM(String text){
      return JOptionPane.showConfirmDialog(AssetClientApp.getApplication().getMainFrame(), text,"确认",JOptionPane.OK_CANCEL_OPTION);
    }

}
