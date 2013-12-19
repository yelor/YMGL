/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import org.jdesktop.application.Task;

/**
 *
 * @author woderchen
 */
public abstract class BasePanel extends javax.swing.JPanel {

  
    public BasePanel() {
        super();
    }

    /*子类可以重写这个方法，当双击左边树的时候，这个方法会自动执行*/
    public abstract Task reload();

    /*子类可以重写这个方法，当双击左边树的时候，这个方法会自动执行*/
    public abstract Task reload(Object param);

}
