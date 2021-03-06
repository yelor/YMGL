/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.AssetClientView;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import org.jdesktop.application.Application;

/**
 *
 * @author 305027939
 */
public class PopupButton extends JButton implements ActionListener {

    private Popup pop;
    private boolean isShow;
    private BasePanel scanBarPanel;
    private boolean hasRegister;

    public PopupButton() {
        super();
        hasRegister = false;
    }

    public void registerPopup(BasePanel panel) {

        scanBarPanel = panel;
        hasRegister = true;
        addActionListener(this);
    }

    public void hidePanel() {
        if (pop != null) {
            isShow = false;
            pop.hide();
            pop = null;
        }
    }

    public void showPanel() {
        if (pop != null) {
            pop.hide();
        }
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        Point p = this.getLocationOnScreen();

        int selectedX = p.x;
        int selectedY = p.y + getHeight();

        int popHeight = scanBarPanel.getPreferredSize().height;
        int popWitdh = scanBarPanel.getPreferredSize().width;

        if ((selectedY + popHeight) > size.getHeight()) {
            selectedY = p.y - scanBarPanel.getPreferredSize().height;
        }

        if ((selectedX + popWitdh) > size.getWidth()) {
            selectedX = p.x - scanBarPanel.getPreferredSize().width;
        }

        pop = PopupFactory.getSharedInstance().getPopup(((AssetClientView) Application.getInstance(AssetClientApp.class).getMainView()).getFrame(), scanBarPanel, selectedX, selectedY);
        pop.show();
        isShow = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (hasRegister) {
            if (!isShow) {
                showPanel();
            } else {
                hidePanel();
            }
        }
    }

}
