/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import java.lang.reflect.Method;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author 305027939
 */
public class BaseListModel<T> extends AbstractListModel {

    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(BaseListModel.class);
    List<T> source;
    String needsDisplayPojoMethod;

    public BaseListModel(List<T> source, String needsDisplayPojoMethod) {
        this.source = source;
        this.needsDisplayPojoMethod = needsDisplayPojoMethod;
    }

    @Override
    public int getSize() {
        return source==null?0:source.size();
    }

    @Override
    public Object getElementAt(int index) {
        T bean = source.get(index);
        if (bean instanceof String) {
            return bean;
        }
        try {
            Method method = bean.getClass().getMethod(needsDisplayPojoMethod, new Class[0]);
            return method.invoke(bean, new Object[0]);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return bean.toString();
    }

    public List<T> getSource() {
        return source;
    }

}
