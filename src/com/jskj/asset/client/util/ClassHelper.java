/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

import com.jskj.asset.client.bean.ColumnBean;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;

/**
 *
 * @author woderchen
 */
public class ClassHelper<T> {

    private static Logger logger = Logger.getLogger(ClassHelper.class);
    T bean;
    String className;
    Constructor constructor;

    public ClassHelper(String className, Class<?>... parameterTypes) throws Exception {
        this.className = className;
        constructor = getConstructor(parameterTypes);
    }

    private Constructor getConstructor(Class<?>... parameterTypes) throws Exception {
        Class superPane = Class.forName(className);
        Constructor cons = superPane.getConstructor(parameterTypes);
        return cons;
    }

    public T newInstance(Object... initargs) throws Exception {
        if (constructor != null) {
            bean = (T) constructor.newInstance(initargs);
        }
        return bean;
    }

    public Object executeMethod(String name) throws Exception {
        Method method = bean.getClass().getDeclaredMethod(name);
        return method.invoke(bean);

    }

    public Object executeMethod(String name, Object parameterValue, Class<?> parameterTypes) throws Exception {
        Method method = bean.getClass().getMethod(name, parameterTypes);
        return method.invoke(bean, parameterTypes.cast(parameterValue));

    }

}
