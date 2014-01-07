/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import com.jskj.asset.client.AssetClientApp;
import javax.swing.JDialog;

/**
 *
 * @author 305027939
 */
public abstract class BaseDialog extends JDialog {


    public BaseDialog() {
        super(AssetClientApp.getApplication().getMainFrame());
    }


}
