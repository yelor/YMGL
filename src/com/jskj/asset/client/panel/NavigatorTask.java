/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel;

import com.jskj.asset.client.layout.AssetArrayNodes;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.AssetNode;
import com.jskj.asset.client.layout.AssetTreeModel;
import com.jskj.asset.client.layout.AssetTreeNode;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.XMLHelper;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author woderchen
 */
public class NavigatorTask extends BaseTask {

    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(NavigatorTask.class);
    private final String NAVIGATOR_XML = "/com/jskj/asset/client/resources/Navigator.xml";

    public NavigatorTask() {
        super();
        
    }

    @Override
    public Object doBackgrounp() {
        AssetArrayNodes nodes = new AssetArrayNodes();
        XMLHelper<AssetArrayNodes> help = new XMLHelper<AssetArrayNodes>(this.getClass().getResource(NAVIGATOR_XML).getPath(), nodes);
        try {
            nodes = help.read();
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
