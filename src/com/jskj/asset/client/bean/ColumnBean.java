/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author woderchen
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ColumnBean implements Cloneable{

    @XmlAttribute
    private String columnName;
    @XmlAttribute
    private String columnClassName;
    @XmlAttribute
    private String columnType;
    @XmlAttribute
    private String length;
    @XmlAttribute
    private boolean autoIncrement;
    @XmlAttribute
    private boolean nullable;
    @XmlAttribute
    private boolean primaryKey;
    @XmlAttribute
    private boolean lazy;


    @Override
    public ColumnBean clone() {
        try {
            ColumnBean clone = (ColumnBean) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return the columnClassName
     */
    public String getColumnClassName() {
        return columnClassName;
    }

    /**
     * @param columnClassName the columnClassName to set
     */
    public void setColumnClassName(String columnClassName) {
        this.columnClassName = columnClassName;
    }

    /**
     * @return the columnType
     */
    public String getColumnType() {
        return columnType;
    }

    /**
     * @param columnType the columnType to set
     */
    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    /**
     * @return the length
     */
    public String getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(String length) {
        this.length = length;
    }

    /**
     * @return the autoIncrement
     */
    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    /**
     * @param autoIncrement the autoIncrement to set
     */
    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    /**
     * @return the nullable
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * @param nullable the nullable to set
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    /**
     * @return the lazy
     */
    public boolean isLazy() {
        return lazy;
    }

    /**
     * @param lazy the lazy to set
     */
    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    /**
     * @return the primaryKey
     */
    public boolean isPrimaryKey() {
        return primaryKey;
    }

    /**
     * @param primaryKey the primaryKey to set
     */
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
}
