/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author 305027939
 */
public class CrystalButton extends JButton {

    private float alpha = 1f; // 底色的透明度，默认为不透明  
    private boolean isMouseEntered = false;// 鼠标是否进入按钮  

    public CrystalButton() {
        initStyle();
    }

    public CrystalButton(String buttonText) {
        super(buttonText);
        initStyle();
        //添加鼠标监听  
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                //当鼠标进入时,鼠标进入状态改为TRUE，并重绘按钮  
                isMouseEntered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isMouseEntered = false;
                repaint();
            }
        });

    }

    /**
     * 初始化按钮样式
     */
    private void initStyle() {
        setOpaque(false);
        setBorder(null);
        setBorderPainted(false);
        setFocusable(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // 绘制渐变底色  
        if (this.isMouseEntered) {
            AlphaComposite composite = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, alpha);
            g2.setComposite(composite);
            drawButtonBackground(g2, this, new Color(253, 236, 219), new Color(
                    253, 223, 187), new Color(255, 206, 105), new Color(255,
                            255, 222));
        }
    }

    /**
     * 按钮底色绘制
     *
     * @param g2 画布
     * @param bt 面板
     * @param c1 渐变颜色1
     * @param c2 渐变颜色2
     * @param c3 渐变颜色3
     * @param c4 渐变颜色4
     * @param frameColor 边框颜色
     */
    private static void drawButtonBackground(Graphics2D g2, JButton bt,
            Color c1, Color c2, Color c3, Color c4, Color frameColor) {

        g2.setColor(frameColor);
        g2.setStroke(new BasicStroke(2));
        //g2.drawRoundRect(1, 1, bt.getWidth() - 4, bt.getHeight() - 2, 10, 10);

        g2.drawLine(1, bt.getHeight() - 2, bt.getWidth() - 4, bt.getHeight() - 2);
//        // 渐变背景  
////        g2.setPaint(new GradientPaint(2, 2, c1, 1, bt.getHeight() / 3, c2));
//        g2.drawRect(2, 2, bt.getWidth() - 5, bt.getHeight() / 3);
//        // 渐变二段  
////        g2.setPaint(new GradientPaint(1, bt.getHeight() / 3, c3, 1, bt
////                .getHeight(), c4));
//        g2.drawRect(2, bt.getHeight() / 3, bt.getWidth() - 5,
//                bt.getHeight() / 3 * 2 - 1);
    }

    private static void drawButtonBackground(Graphics2D g2, JButton bt,
            Color c1, Color c2, Color c3, Color c4) {
        drawButtonBackground(g2, bt, c1, c2, c3, c4, new Color(122,163,204));
    }
}
