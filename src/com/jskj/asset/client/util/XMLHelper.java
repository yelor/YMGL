/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

import java.io.File;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;

/**
 *
 * @author woderchen
 */
public class XMLHelper<T> {
private final static Logger logger = Logger.getLogger(XMLHelper.class);
    private String xmlPath;
    private T xmlBean;

    public XMLHelper(String xmlPath, T xmlBean) {
        this.xmlPath = xmlPath;
        this.xmlBean = xmlBean;
        logger.debug(xmlPath);
    }

    public XMLHelper(T xmlBean) {
        this(System.getProperty("java.io.tmpdir") + "AssetClient" + File.separator + xmlBean.getClass().getSimpleName() + ".xml", xmlBean);
    }
    public XMLHelper() {
    }

    public T read(InputStream inputStream, T xmlBean) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(new Class[]{xmlBean.getClass()});
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        T newxmllBean = (T) unmarshaller.unmarshal(inputStream);
        return newxmllBean;
    }

    private File file(String xmlPath) {
        File temp = new File(xmlPath);
        File parentFile = new File(temp.getParent());
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return temp;
    }

    public T read() throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(new Class[]{xmlBean.getClass()});
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        xmlBean = (T) unmarshaller.unmarshal(file(xmlPath));
        return xmlBean;
    }

    public void write() throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(new Class[]{xmlBean.getClass()});
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(xmlBean, file(xmlPath));
    }

    public static void main(String[] str) {
//        try {
//            LoginVO t = new LoginVO();
//            t.setUri("www.sina.com");
//            t.setPasswd("12345");
//
//            List d=  new ArrayList();
//
//            LoginDepartVO departVO  = new LoginDepartVO();
//            departVO.setDepartId(1003);
//            departVO.setDepartName("开发部");
//            d.add(departVO);
//
//            LoginDepartVO departVO1  = new LoginDepartVO();
//            departVO1.setDepartId(1003);
//            departVO1.setDepartName("开发部23");
//            d.add(departVO1);
//
//
//            t.setDepartVO(d);
//
//            List u=  new ArrayList();
//            LoginUserVO userVO =new LoginUserVO();
//            userVO.setUserId(1);
//            userVO.setUserName("强人");
//            u.add(userVO);
//
//            LoginUserVO userVO1 =new LoginUserVO();
//            userVO1.setUserId(1);
//            userVO1.setUserName("强人23324");
//            u.add(userVO1);
//
//
//            t.setUserVO(u);
//
//
//            XMLHelper<LoginVO> login = new XMLHelper<LoginVO>( t);
//            login.write();
//
////            XMLHelper<LoginVO> login = new XMLHelper<LoginVO>("c:\\test.xml", t);
////            t = login.read();
////            System.out.println("@@@@@@@:" + t.getUri());
////            System.out.println("@@@@@@@:" + t.getDepartVO().getDepartName());
//
//        } catch (JAXBException ex) {
//            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }
}
