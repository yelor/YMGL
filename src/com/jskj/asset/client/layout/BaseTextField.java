/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.AssetClientView;
import com.jskj.asset.client.util.DateChooser;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import org.jdesktop.application.Application;

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

    private boolean openWhenClick;

    public BaseTextField() {
        super();
        hasRegister = false;
        isShow = false;
        openWhenClick = false;

        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!isShow && openWhenClick && isEnabled()) {
                    showPanel();
                }
            }

            public void mouseEntered(MouseEvent me) {
                if (isEnabled()) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            public void mouseExited(MouseEvent me) {
                if (isEnabled()) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });

        this.addAncestorListener(new AncestorListener() {
            public void ancestorAdded(AncestorEvent event) {

            }

            public void ancestorRemoved(AncestorEvent event) {
                hidePanel();
            }

            //只要祖先组件一移动,马上就让popup消失  
            public void ancestorMoved(AncestorEvent event) {
                hidePanel();
            }
        });

    }

    /**
     * 为一个textfiled注册一带有图标的POPUP_TYPE,PARAMETER为扩展参数
     * 1.如果是TYPE_DATE_CLICK：可以用这个参数来表示时间格式"yyyy-MM-dd" 2....
     *
     * @param POPUP_TYPE
     * @param PARAMETER
     */
    public void registerPopup(int POPUP_TYPE, String PARAMETER) {
        switch (POPUP_TYPE) {
            case IPopupBuilder.TYPE_DATE_CLICK:
                icon = new ImageIcon(getClass().getResource(IPopupBuilder.ICON_DATE));
                break;
            case IPopupBuilder.TYPE_POPUP_TEXT:
                icon = new ImageIcon(getClass().getResource(IPopupBuilder.ICON_POPUP_TEXT));
                break;
            default:
                icon = new ImageIcon(getClass().getResource(IPopupBuilder.ICON_POPUP_TABLE));
        }
        if (POPUP_TYPE == IPopupBuilder.TYPE_DATE_CLICK) {
            DateChooser dateChooser1 = DateChooser.getInstance(PARAMETER);
            dateChooser1.register(BaseTextField.this);
        }
        Insets insets = new Insets(0, 20, 0, 0);
        this.setMargin(insets);

        Border line = BorderFactory.createLineBorder(Color.DARK_GRAY);
        Border empty = new EmptyBorder(0, 20, 0, 0);
        CompoundBorder border = new CompoundBorder(line, empty);
        setBorder(border);

        hasRegister = true;
    }

    public void registerIcon(int POPUP_TYPE) {
        registerIcon(POPUP_TYPE, 20);
    }

    /**
     * 为一个textfiled注册一带有图标的POPUP_TYPE,PARAMETER为扩展参数
     * 1.如果是TYPE_DATE_CLICK：可以用这个参数来表示时间格式"yyyy-MM-dd" 2....
     *
     * @param POPUP_TYPE
     * @param PARAMETER
     */
    public void registerIcon(int POPUP_TYPE, int leftPos) {
        switch (POPUP_TYPE) {
            case IPopupBuilder.TYPE_DATE_CLICK:
                icon = new ImageIcon(getClass().getResource(IPopupBuilder.ICON_DATE));
                break;
            case IPopupBuilder.TYPE_POPUP_TEXT:
                icon = new ImageIcon(getClass().getResource(IPopupBuilder.ICON_POPUP_TEXT));
                break;
            case IPopupBuilder.TYPE_POPUP_SCAN:
                icon = new ImageIcon(getClass().getResource(IPopupBuilder.ICON_POPUP_SCAN));
                break;
            default:
                icon = new ImageIcon(getClass().getResource(IPopupBuilder.ICON_POPUP_TABLE));
        }
        Insets insets = new Insets(0, leftPos, 0, 0);
        this.setMargin(insets);

        Border line = BorderFactory.createLineBorder(Color.DARK_GRAY);
        Border empty = new EmptyBorder(0, leftPos, 0, 0);
        CompoundBorder border = new CompoundBorder(line, empty);
        setBorder(border);
        hasRegister = true;
    }

    /**
     * 为一个textfiled注册一个popup registerPopup需要IPopupBuilder
     *
     * @param popBuilder
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
        Border line = BorderFactory.createLineBorder(Color.DARK_GRAY);
        Border empty = new EmptyBorder(0, 20, 0, 0);
        CompoundBorder border = new CompoundBorder(line, empty);
        setBorder(border);

        addKeyListener(this);
        addFocusListener(this);
        basePopup = new BasePopup(popBuilder) {
            @Override
            public void closePopup() {
                hidePanel();
            }

        };
        hasRegister = true;
        openWhenClick = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (!hasRegister) {
            super.paintComponent(g);
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

                JTable table = basePopup.getTable();
                int selectedrow = table.getSelectedRow();
                if (selectedrow >= 0) {
                    basePopup.setPopValueToParent();
                } else {
                    hidePanel();
                }
            } else {
                showPanel();
            }
        } else if (keyCode == KeyEvent.VK_DOWN) {
            if (isShow) {
                JTable table = basePopup.getTable();
                int selectedrow = table.getSelectedRow();
                int upRow = selectedrow + 1;
                //System.out.println("keyCodekeyCode:VK_DOWN:" + upRow);
                if (upRow < table.getRowCount()) {
                    table.setRowSelectionInterval(upRow, upRow);
                }
            }
        } else if (keyCode == KeyEvent.VK_UP) {
            if (isShow) {
                JTable table = basePopup.getTable();
                int selectedrow = table.getSelectedRow();
                int upRow = selectedrow - 1;
                // System.out.println("keyCodekeyCode:VK_UP:" + upRow);
                if (upRow >= 0) {
                    table.setRowSelectionInterval(upRow, upRow);
                } else {
                    table.clearSelection();
                }

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode != KeyEvent.VK_ENTER) {
            if (!isShow) {
                showPanel();
            } else {
                basePopup.setKey(getText());
            }
        }
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
//        Point show = new Point(0, this.getHeight());
//        SwingUtilities.convertPointToScreen(show, this);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        Point p = this.getLocationOnScreen();

        int selectedX = p.x;
        int selectedY = p.y + getHeight();

        int popHeight = basePopup.getPreferredSize().height;
        int popWitdh = basePopup.getPreferredSize().width;

        if ((selectedY + popHeight) > size.getHeight()) {
            selectedY = p.y - basePopup.getPreferredSize().height;
        }

        if ((selectedX + popWitdh) > size.getWidth()) {
            selectedX = p.x - basePopup.getPreferredSize().width;
        }

//        Point mousepoint = MouseInfo.getPointerInfo().getLocation();
        pop = PopupFactory.getSharedInstance().getPopup(((AssetClientView) Application.getInstance(AssetClientApp.class).getMainView()).getFrame(), basePopup, selectedX, selectedY);
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
