/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author woderchen
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AssetNode {

    @XmlElement
    private String nodeNo;
    @XmlElement
    private String nodeName;
    @XmlElement
    private String nodeIcon;
    @XmlElement
    private String pressedIcon;
    @XmlElement
    private String rolloverIcon;
    @XmlElement
    private String linkObject;
    @XmlElement
    private boolean isLastNode;
    @XmlElement
    private List<AssetNode> childNode;
    @XmlElement
    private AssetNode patientNode;
    @XmlElement
    private Object remoteData;

    /**
     * @return the nodeNo
     */
    public String getNodeNo() {
        return nodeNo;
    }

    /**
     * @param nodeNo the nodeNo to set
     */
    public void setNodeNo(String nodeNo) {
        this.nodeNo = nodeNo;
    }

    /**
     * @return the nodeName
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * @param nodeName the nodeName to set
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * @return the nodeIcon
     */
    public String getNodeIcon() {
        return nodeIcon;
    }

    /**
     * @param nodeIcon the nodeIcon to set
     */
    public void setNodeIcon(String nodeIcon) {
        this.nodeIcon = nodeIcon;
    }

    /**
     * @return the linkObject
     */
    public String getLinkObject() {
        return linkObject;
    }

    /**
     * @param linkObject the linkObject to set
     */
    public void setLinkObject(String linkObject) {
        this.linkObject = linkObject;
    }

    /**
     * @return the isLastAssetNode
     */
    public boolean isIsLastNode() {
        return isLastNode;
    }

    /**
     * @param isLastNode the isLastAssetNode to set
     */
    public void setIsLastNode(boolean isLastNode) {
        this.isLastNode = isLastNode;
    }

    /**
     * @return the childAssetNode
     */
    public List<AssetNode> getChildNode() {
        return childNode;
    }

    /**
     * @param childNode the childAssetNode to set
     */
    public void setChildNode(List<AssetNode> childNode) {
        this.childNode = childNode;
    }

    /**
     * @return the remoteData
     */
    public Object getRemoteData() {
        return remoteData;
    }

    /**
     * @param remoteData the remoteData to set
     */
    public void setRemoteData(Object remoteData) {
        this.remoteData = remoteData;
    }

    /**
     * @return the patientAssetNode
     */
    public AssetNode getPatientNode() {
        return patientNode;
    }

    /**
     * @param patientNode the patientAssetNode to set
     */
    public void setPatientNode(AssetNode patientNode) {
        this.patientNode = patientNode;
    }

    /**
     * @return the pressedIcon
     */
    public String getPressedIcon() {
        return pressedIcon;
    }

    /**
     * @param pressedIcon the pressedIcon to set
     */
    public void setPressedIcon(String pressedIcon) {
        this.pressedIcon = pressedIcon;
    }

    /**
     * @return the rolloverIcon
     */
    public String getRolloverIcon() {
        return rolloverIcon;
    }

    /**
     * @param rolloverIcon the rolloverIcon to set
     */
    public void setRolloverIcon(String rolloverIcon) {
        this.rolloverIcon = rolloverIcon;
    }

}
