/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;

/**
 *
 * @author 305027939
 */
public class BaseTextField extends JTextField implements KeyListener, FocusListener {

    private ImageIcon icon;

    private Popup pop;
    private boolean isShow;
    private BasePopup basePopup;
    private boolean hasRegister;

    public BaseTextField() {
        super();
        hasRegister = false;
        isShow = false;

    }

    /*为一个textfiled注册一个popup
     registerPopup需要IPopupBuilder
     */
    public void registerPopup(IPopupBuilder popBuilder) {
        switch (popBuilder.getType()) {
            case IPopupBuilder.TYPE_DATE_CLICK:
                icon = new ImageIcon(getClass().getResource(IPopupBuilder.ICON_DATE));
                break;
            case IPopupBuilder.TYPE_POPUP_TEXT:
                icon = new ImageIcon(getClass().getResource(IPopupBuilder.ICON_POPUP_TEXT));
                break;
            default:
                icon = new ImageIcon(getClass().getResource(IPopupBuilder.ICON_POPUP_TABLE));
        }

        Insets insets = new Insets(0, 20, 0, 0);
        this.setMargin(insets);

        addKeyListener(this);
        addFocusListener(this);
        basePopup = new BasePopup(popBuilder) {
            @Override
            public void closePopup() {
                hidePanel();
            }

        };
        hasRegister = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (!hasRegister) {
            return;
        }
        Insets insets = getInsets();
        super.paintComponent(g);
        int iconWidth = icon.getIconWidth();
        int iconHeight = icon.getIconHeight();
        int Height = this.getHeight();
        //在文本框中画上之前图片  
        icon.paintIcon(this, g, (insets.left - iconWidth) / 2, (Height - iconHeight) / 2);
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ENTER) {
            if (isShow) {
                hidePanel();
            } else {
                showPanel();
            }
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void hidePanel() {
        if (pop != null) {
            isShow = false;
            pop.hide();
            pop = null;
        }
    }

    private void showPanel() {
        if (pop != null) {
            pop.hide();
        }
//        Point show = new Point(0, this.getHeight());
//        SwingUtilities.convertPointToScreen(show, this);
//        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        Point p = this.getLocationOnScreen();

        int selectedX = p.x;
        int selectedY = p.y + getHeight();

//        Point mousepoint = MouseInfo.getPointerInfo().getLocation();
        pop = PopupFactory.getSharedInstance().getPopup(this, basePopup, selectedX, selectedY);
        basePopup.setKey(getText());
        pop.show();
        isShow = true;
    }

    public void focusGained(FocusEvent e) {

    }

    public void focusLost(FocusEvent e) {
        hidePanel();
    }

}
