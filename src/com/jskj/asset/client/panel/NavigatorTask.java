/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel;

import com.jskj.asset.client.layout.AssetArrayNodes;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.XMLHelper;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBException;

/**
 *
 * @author woderchen
 */
public class NavigatorTask extends BaseTask {

    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(NavigatorTask.class);
    private final String NAVIGATOR_XML = "com/jskj/asset/client/resources/Navigator.xml";

    public NavigatorTask() {
        super();
        
    }

    @Override
    public Object doBackgrounp() {
        AssetArrayNodes nodes = new AssetArrayNodes();
        XMLHelper<AssetArrayNodes> help = new XMLHelper<AssetArrayNodes>();
        try {
            nodes = help.read(this.getClass().getClassLoader().getResourceAsStream(NAVIGATOR_XML),nodes);
        } catch (JAXBException ex) {
            return ex;
        }
        return nodes;
    }

    @Override
    public void onSucceeded(Object object) {
        //left panel实现这个方法
    }

}
