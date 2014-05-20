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
public class ScanButton extends JButton implements ActionListener {

    private Popup pop;
    private boolean isShow;
    private ScanBarPanel scanBarPanel;
    private boolean hasRegister;

    public ScanButton() {
        super();
        hasRegister = false;
    }

    public void registerPopup(IPopupBuilder popBuilder) {

        scanBarPanel = new ScanBarPanel(popBuilder) {

            @Override
            public void closePopup() {

            }
        };
        hasRegister = true;
        addActionListener(this);
    }

    private void hidePanel() {
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
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        Point p = this.getLocationOnScreen();

        int selectedX = p.x;
        int selectedY = p.y + getHeight();

        int popHeight = scanBarPanel.getHeight();
        int popWitdh = scanBarPanel.getWidth();

        if ((selectedY + popHeight) > size.getHeight()) {
            selectedY = p.y - scanBarPanel.getHeight();
        }

        if ((selectedX + popWitdh) > size.getWidth()) {
            selectedX = p.x - scanBarPanel.getWidth();
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
