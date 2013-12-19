/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author woderchen
 */
public class ComboxHelper<T> {

    private HashMap KVMap = new HashMap();
    private JComboBox jComboBox;
    private List<T> beans;
    public static boolean NULLSELECT = true;

    public ComboxHelper(JComboBox jComboBox, List<T> beans) {
        this.jComboBox = jComboBox;
        this.beans = beans;
    }

    /**
     * assemble the value of select
     */
    public void assemble(String methodName, String methodId, boolean isnull) {
        List selects = new ArrayList();
        if (NULLSELECT) {
            selects.add(null);
        }
        if (beans != null) {
            for (T bean : beans) {
                Object name = getValueForMethod(bean, methodName);
                Object id = getValueForMethod(bean, methodId);

                selects.add(name);
                getKVMap().put(name, id);

            }
        }
        String[] temp = new String[selects.size()];
        selects.toArray(temp);
        jComboBox.setModel(new javax.swing.DefaultComboBoxModel(temp));


    }

    /**
     * assemble the value of select
     */
    public void assemble(String methodName, String methodId) {
        List selects = new ArrayList();
        for (T bean : beans) {
            Object name = getValueForMethod(bean, methodName);
            Object id = getValueForMethod(bean, methodId);

            selects.add(name);
            getKVMap().put(name, id);

        }
        String[] temp = new String[selects.size()];
        selects.toArray(temp);
        jComboBox.setModel(new javax.swing.DefaultComboBoxModel(temp));


    }

    private Object getValueForMethod(T bean, String methodString) {
        try {
            Method name = bean.getClass().getDeclaredMethod(methodString);
            return name.invoke(bean);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }

    }

    public static void main(String[] arg) {
//        try {
//            LoginUserVO vo = new LoginUserVO();
//            vo.setUserId(1111);
//            vo.setUserName("testtest");
//
//            Method m = LoginUserVO.class.getDeclaredMethod("getUserName");
//            System.out.println(m.invoke(vo));
//        } catch (Exception ex) {
//            Logger.getLogger(ComboxHelper.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    /**
     * @return the KVMap
     */
    public HashMap getKVMap() {
        return KVMap;
    }
}
