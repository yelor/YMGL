/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import com.jskj.asset.client.AssetClientApp;
import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

    private Set<Field> getClassAllFields(Class clazz, Set<Field> allGenericFields) {

        // 如果clazz为空则直接返回    
        if (clazz == null) {
            return allGenericFields;
        }

        Object parent = clazz.getGenericSuperclass();
        // 如果有父类并且父类不是Object 则递归调用    
        if (parent != null && !((Class) parent).getName().equals("Object")) {
            getClassAllFields((Class) parent, allGenericFields);
        }

        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {// 如果clazz存在声明的属性    
            for (int i = 0; i < fields.length; i++) {
                allGenericFields.add(fields[i]);
            }
        }
        // 存在父类则递归调用    
        return allGenericFields;
    }

    private Set<Field> getClassAllFields(Class clazz) {
        Set<Field> allGenericFields = new HashSet<Field>();
//      List<Field> allGenericFields = new ArrayList<Field>();  
        return getClassAllFields(clazz, allGenericFields);
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
                    Set<Field> fields = getClassAllFields(bean.getClass());
                    for (Field field : fields) {
                        if (field.getName().equalsIgnoreCase(firstPara)) {
                            //Field field = bean.getClass().getField(firstPara);
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
                            } else if (paramClasses[0] == Float.class) {
                                if (!value.trim().equals("")) {
                                    method.invoke(bean, Float.parseFloat(value));
                                }
                            } else if (paramClasses[0] == Boolean.class) {
                                if (!value.trim().equals("")) {
                                    method.invoke(bean, Boolean.parseBoolean(value));
                                }
                            } else if (paramClasses[0] == Long.class) {
                                if (!value.trim().equals("")) {
                                    method.invoke(bean, Long.parseLong(value));
                                }
                            } else if (paramClasses[0] == Long.class) {
                                if (!value.trim().equals("")) {
                                    method.invoke(bean, Long.parseLong(value));
                                }
                            }
                            break;
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
            } else if (c instanceof JScrollPane) {
                JScrollPane ccc = (JScrollPane) c;
                if (ccc.getViewport() != null) {
                    Component com = ccc.getViewport().getView();
                    if (com != null) {
                        if (com instanceof JTextArea) {
                            try {
                                String value = ((JTextArea) com).getText();
                                setObject(com.getName(), targetBean, value);
                            } catch (Exception ex) {
                                logger.error(ex);
                            }
                        } else if (com instanceof javax.swing.JList) {
                            try {
                                Object value = ((javax.swing.JList) com).getSelectedValue();
                                if (value != null) {
                                    setObject(com.getName(), targetBean, value.toString());
                                }
                            } catch (Exception ex) {
                                logger.error(ex);
                            }
                        }
                    }
                }
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

    public void setDialogComponentValue(HashMap map, Container container, boolean canEdit) {
        Component[] coms = container.getComponents();
        for (Component c : coms) {
            if (c instanceof JPanel) {
                setDialogComponentValue(map, (JPanel) c,canEdit);
            } else if (c instanceof JScrollPane) {
                JScrollPane ccc = (JScrollPane) c;
                if (ccc.getViewport() != null) {
                    Component com = ccc.getViewport().getView();
                    if (com != null) {
                        if (com instanceof JTextArea) {
                            try {
                                String name = ((JTextArea) com).getName();
                                Object obj = map.get(name);
                                ((JTextArea) com).setEnabled(canEdit);
                                if (obj instanceof String) {
                                    ((JTextArea) com).setText(obj.toString());
                                }
                            } catch (Exception ex) {
                                logger.error(ex);
                            }
                        } else if (com instanceof javax.swing.JList) {
                            try {
                                String name = ((javax.swing.JList) com).getName();
                                Object obj = map.get(name);

                                ((javax.swing.JList) com).setSelectedValue(obj, true);
                                ((javax.swing.JList) com).setEnabled(canEdit);
                            } catch (Exception ex) {
                                logger.error(ex);
                            }
                        } else if (com instanceof javax.swing.JTable) {
                            try {
                                String name = ((javax.swing.JTable) com).getName();
                                Object obj = map.get(name);
                                
                                //((javax.swing.JTable) com).setText(obj.toString()); //jtable的处理有点麻烦，后面再改？？
                                ((javax.swing.JTable) com).setEnabled(canEdit);
                                
                            } catch (Exception ex) {
                                logger.error(ex);
                            }
                        }
                    }
                }
            } else if (c instanceof JTextField) {
                try {
                    String name = ((JTextField) c).getName();
                    Object obj = map.get(name);
                    ((JTextField) c).setEnabled(canEdit);
                    if (obj instanceof String) {
                        ((JTextField) c).setText(obj.toString());
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                }

            } else if (c instanceof JComboBox) {
                try {
                    String name = ((JComboBox) c).getName();
                    Object obj = map.get(name);
                    ((JComboBox) c).setEnabled(canEdit);
                    ((JComboBox) c).setSelectedItem(obj);

                } catch (Exception ex) {
                    logger.error(ex);
                }
            } else if (c instanceof JTextArea) {
                try {
                    String name = ((JTextArea) c).getName();
                    Object obj = map.get(name);
                    ((JTextArea) c).setEnabled(canEdit);
                    if (obj instanceof String) {
                        ((JTextArea) c).setText(obj.toString());
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                }
            } else if (c instanceof JList) {
                try {
                    String name = ((JList) c).getName();
                    Object obj = map.get(name);
                    ((JList) c).setEnabled(canEdit);
                    ((JList) c).setSelectedValue(obj, true);
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
            } else if (c instanceof JScrollPane) {
                JScrollPane ccc = (JScrollPane) c;
                if (ccc.getViewport() != null) {
                    Component com = ccc.getViewport().getView();
                    if (com != null) {
                        if (com instanceof JTextArea) {
                            try {
                                Object temp = getObject(com.getName(), sourceBean);
                                if (temp != null) {
                                    ((JTextArea) com).setText(temp.toString());
                                } else {
                                    ((JTextArea) com).setText("");
                                }
                            } catch (Exception ex) {
                                logger.error(ex);
                            }
                        } else if (com instanceof javax.swing.JList) {
                            try {
                                Object temp = getObject(com.getName(), sourceBean);
                                if (temp != null) {
                                    ((JList) com).setSelectedValue(temp, true);
                                } else {
                                    //((JList) c).setText("");
                                }
                            } catch (Exception ex) {
                                logger.error(ex);
                            }
                        }
                    }
                }
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
