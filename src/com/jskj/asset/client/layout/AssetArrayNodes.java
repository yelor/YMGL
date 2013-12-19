/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author woderchen
 */
@XmlRootElement(name = "AssetArrayNodes")
@XmlAccessorType(XmlAccessType.FIELD)
public class AssetArrayNodes {

    @XmlElement(name = "RootNode")
    private AssetNode topNode;
    @XmlElement(name = "Node")
    private List<AssetNode> nodes;

    /**
     * @return the topNodeName
     */
    /**
     * @return the nodes
     */
    public List<AssetNode> getNodes() {
        return nodes;
    }

    /**
     * @param nodes the nodes to set
     */
    public void setNodes(List<AssetNode> nodes) {
        this.nodes = nodes;
    }

    /**
     * @return the topAssetNode
     */
    public AssetNode getTopNode() {
        return topNode;
    }

    /**
     * @param topNode the topAssetNode to set
     */
    public void setTopNode(AssetNode topNode) {
        this.topNode = topNode;
    }

}
