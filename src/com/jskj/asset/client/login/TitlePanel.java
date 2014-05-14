/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.login;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 *
 * @author 305027939
 */
public class TitlePanel extends JPanel {

    int iWidth, iHeight;

    public TitlePanel(int iWidth, int iHeight) {
        this.iHeight = iHeight;
        this.iWidth = iWidth;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image icon = toolkit.getImage(getClass().getResource("/com/jskj/asset/client/login/resources/new_logo.jpg"));

        g.drawImage(icon, 0, 0, iWidth, iHeight, this);
    }
}
