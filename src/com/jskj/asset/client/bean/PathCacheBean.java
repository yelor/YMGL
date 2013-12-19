/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.bean;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author woderchen
 */
@XmlRootElement(name = "PathCache")
@XmlAccessorType(XmlAccessType.FIELD)
public class PathCacheBean {

    @XmlElement
    private List<PathBean> paths;

    /**
     * @return the paths
     */
    public List<PathBean> getPaths() {
        return paths;
    }

    /**
     * @param paths the paths to set
     */
    public void setPaths(List<PathBean> paths) {
        this.paths = paths;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class PathBean {

        @XmlAttribute
        private String key;
        @XmlValue
        private String value;

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }

        /**
         * @param value the value to set
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * @param key the key to set
         */
        public void setKey(String key) {
            this.key = key;
        }
    }
}
