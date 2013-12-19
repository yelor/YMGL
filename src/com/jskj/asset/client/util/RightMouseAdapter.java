/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author woderchen
 */
public class RightMouseAdapter extends MouseAdapter {

    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 200;
    private RightPopupMenu popup;

    public RightMouseAdapter(Object actionObject, Object actionObject2) {
        super();
        this.popup = new RightPopupMenu(actionObject, actionObject2);
    }

    public RightMouseAdapter(Object actionObject) {
        this(actionObject, null);
    }

    public void enable(boolean status) {
        popup.enableRightButton(status);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (event.isPopupTrigger()) {
            popup.show(event.getComponent(),
                    event.getX(), event.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (event.isPopupTrigger()) {
            popup.show(event.getComponent(),
                    event.getX(), event.getY());
        }
    }
}
