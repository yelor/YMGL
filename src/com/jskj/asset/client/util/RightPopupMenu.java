/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

import com.jskj.asset.client.AssetClientApp;
import java.awt.Component;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.ActionMap;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import org.apache.log4j.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;

/**
 *
 * @author woderchen
 */
public class RightPopupMenu extends JPopupMenu {

    private static Logger logger = Logger.getLogger(RightPopupMenu.class);
    public static final String MENUITEM = "JMenuItem";
    public static final String SEPARATOR = "JSeparator";
    private ApplicationContext applicationContext = Application.getInstance(AssetClientApp.class).getContext();
    private int menuCount;
    private List<Integer> needEnableIndexs;

    public RightPopupMenu(Object actionsObject, Object actionsObject2) {
        super();
        needEnableIndexs = new ArrayList<Integer>();
        if (actionsObject2 != null) {
            build(actionsObject2);
            //addSeparator();
        }
        build(actionsObject);
    }

    public void enableRightButton(boolean status) {
        if (needEnableIndexs == null) {
            return;
        }
        MenuElement[] elements = getSubElements();
        for (int i = 0; i < elements.length; i++) {
            for (int index : needEnableIndexs) {
                if (i == index - 1) {
                    MenuElement element = elements[i];
                    Component component = element.getComponent();
                    component.setEnabled(status);
                    break;
                }
            }

        }
    }

    private void build(Object actionObject) {

        Method[] methods = actionObject.getClass().getMethods();
        List<Method> methodList = Arrays.asList(methods);

        Collections.sort(methodList, new Comparator<Method>() {

            public int compare(Method o1, Method o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        int i = 0;
        for (Method a : methodList) {

            if (a.isAnnotationPresent(RightItem.class)) {

                RightItem rightItem = a.getAnnotation(RightItem.class);
//                if (logger.isDebugEnabled()) {
//                    logger.debug("----add right menu item:" + a.getName());
//                    logger.debug("item name:" + rightItem.value());
//                    logger.debug("item type:" + rightItem.type());
//                }
                String type = rightItem.type();
                boolean hasSeparator = rightItem.hasSeparator();
                boolean enable = rightItem.enable();

                JMenuItem item = new JMenuItem();
                //item.setFont(Constants.GLOBAL_FONT);

                menuCount++;

                if (type.equals(MENUITEM)) {
                    String actionName = a.getName();
                    if (!actionName.trim().equals("")) {
                        ActionMap actionMap = applicationContext.getActionMap(actionObject);
                        item.setAction(actionMap.get(actionName));
                    }
                    item.setText(rightItem.value());
                    if (!enable) {
                        item.setEnabled(false);
                        needEnableIndexs.add(menuCount);
                    }

                }

                add(item);

                if (hasSeparator) {
                    addSeparator();
                }
            }
        }
    }
}
