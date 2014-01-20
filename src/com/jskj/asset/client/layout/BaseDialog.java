/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import com.jskj.asset.client.AssetClientApp;
import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author 305027939
 */
public abstract class BaseDialog extends JDialog {

    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(BaseDialog.class);

    public BaseDialog() {
        super(AssetClientApp.getApplication().getMainFrame());
    }

    private Object getObject(String paramater, Object bean) throws Exception {
        if (paramater != null && !paramater.equals("")) {
            String[] ps = paramater.split("\\$");
            String firstPara = ps[0];
            String getIs = "get";
            String tempTitle = getIs + firstPara.substring(0, 1).toUpperCase() + firstPara.substring(1);
            try {
                if (bean == null) {
                    return "";
                }
                Method method = bean.getClass().getMethod(tempTitle, new Class[0]);
                Object temp = method.invoke(bean, new Object[0]);
                if (ps.length == 1) {
                    return temp;
                }
                return getObject(paramater.substring(firstPara.length() + 1), temp);
            } catch (Exception ex) {
                throw ex;
            }
        }
        return "";
    }

    private void setObject(String paramater, Object bean, String value) throws Exception {
        if (paramater != null && !paramater.equals("")) {
            String[] ps = paramater.split("\\$");
            String firstPara = ps[0];
            String getIs = "set";
            if (ps.length > 1) {
                getIs = "get";
            }
            String tempTitle = getIs + firstPara.substring(0, 1).toUpperCase() + firstPara.substring(1);
            try {
                if (bean == null) {
                    return;
                }
                if (ps.length == 1) {
                    Field field = bean.getClass().getDeclaredField(firstPara);
                    Class[] paramClasses = new Class[1];
                    paramClasses[0] = field.getType();
                    Method method = bean.getClass().getMethod(tempTitle, paramClasses);
                    if (paramClasses[0] == String.class) {
                        method.invoke(bean, value);
                    } else if (paramClasses[0] == Integer.class) {
                        if (!value.trim().equals("")) {
                            method.invoke(bean, Integer.parseInt(value));
                        }
                    } else if (paramClasses[0] == Double.class) {
                        if (!value.trim().equals("")) {
                            method.invoke(bean, Double.parseDouble(value));
                        }
                    }else if (paramClasses[0] == Float.class) {
                        if (!value.trim().equals("")) {
                            method.invoke(bean, Float.parseFloat(value));
                        }
                    }else if (paramClasses[0] == Boolean.class) {
                        if (!value.trim().equals("")) {
                            method.invoke(bean, Boolean.parseBoolean(value));
                        }
                    }else if (paramClasses[0] == Long.class) {
                        if (!value.trim().equals("")) {
                            method.invoke(bean, Long.parseLong(value));
                        }
                    }else if (paramClasses[0] == Long.class) {
                        if (!value.trim().equals("")) {
                            method.invoke(bean, Long.parseLong(value));
                        }
                    }
                } else {
                    Method method = bean.getClass().getMethod(tempTitle, new Class[0]);
                    Object temp = method.invoke(bean, new Object[0]);
                    if (temp != null) {
                        setObject(paramater.substring(firstPara.length() + 1), temp, value);
                    }
                }
            } catch (Exception ex) {
                throw ex;
            }
        }
    }

    public void copyToBean(Object targetBean, JPanel container) {

        Component[] coms = container.getComponents();
        for (Component c : coms) {
            if (c instanceof JPanel) {
                copyToBean(targetBean, (JPanel) c);
            } else if (c instanceof JTextField) {
                try {
                    String value = ((JTextField) c).getText();
                    setObject(c.getName(), targetBean, value);
                } catch (Exception ex) {
                    logger.error(ex);
                }

            } else if (c instanceof JComboBox) {
                try {
                    Object value = ((JComboBox) c).getSelectedItem();
                    if (value instanceof String) {
                        setObject(c.getName(), targetBean, value.toString());
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                }
            } else if (c instanceof JTextArea) {
                try {
                    String value = ((JTextArea) c).getText();
                    setObject(c.getName(), targetBean, value);
                } catch (Exception ex) {
                    logger.error(ex);
                }
            } else if (c instanceof JList) {
                try {
                    Object value = ((JList) c).getSelectedValue();
                    if (value instanceof String) {
                        setObject(c.getName(), targetBean, value.toString());
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                }
            }
        }

    }

    /**
     *
     * @param sourceBean
     * @param container
     */
    public void bind(Object sourceBean, JPanel container) {
        Component[] coms = container.getComponents();
        for (Component c : coms) {
            if (c instanceof JPanel) {
                bind(sourceBean, (JPanel) c);
            } else if (c instanceof JTextField) {
                try {
                    Object temp = getObject(c.getName(), sourceBean);
                    if (temp != null) {
                        ((JTextField) c).setText(temp.toString());
                    } else {
                        ((JTextField) c).setText("");
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                    ((JTextField) c).setText("");
                }

            } else if (c instanceof JComboBox) {
                try {
                    Object temp = getObject(c.getName(), sourceBean);
                    if (temp != null) {
                        ((JComboBox) c).setSelectedItem(temp);
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                }
            } else if (c instanceof JTextArea) {
                try {
                    Object temp = getObject(c.getName(), sourceBean);
                    if (temp != null) {
                        ((JTextArea) c).setText(temp.toString());
                    } else {
                        ((JTextArea) c).setText("");
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                    ((JTextArea) c).setText("");
                }
            }
        }
    }

}
