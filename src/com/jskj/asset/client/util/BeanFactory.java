package com.jskj.asset.client.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author woderchen@gmail.com
 */
public class BeanFactory {

    private static final Log log = LogFactory.getLog(BeanFactory.class);
    private static ApplicationContext application;
    private static BeanFactory beanfactory;
    private final static String classPath= "com/jskj/asset/client/resources/applicationContext-common.xml";

    public static BeanFactory instance() {
        if (beanfactory == null) {
            beanfactory = new BeanFactory();
         
            if (log.isDebugEnabled()) {
                log.debug("application classpath:" + classPath);
            }
            application = new ClassPathXmlApplicationContext(classPath);
        }
        return beanfactory;
    }

    public Object createBean(Class classMapper) {
        return application.getBean(classMapper);
    }

    public Object createBean(String beanName) {
        return application.getBean(beanName);
    }
}
