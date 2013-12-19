/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import java.awt.Component;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author woderchen
 */
public class BaseBorder extends AbstractBorder {

    public Insets getBorderInsets(Component c) {
        return new Insets(3, 3, 3, 3);
    }
}
