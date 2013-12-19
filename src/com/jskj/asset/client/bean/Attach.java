/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.bean;

/**
 *
 * @author woderchen
 */
public class Attach {
    private String queryTablesString;
    private String columnName;
    private String coumnType;
    private String columnClassName;
    private int rowNum;
    private int dbKey;

    /**
     * @return the queryTablesString
     */
    public String getQueryTablesString() {
        return queryTablesString;
    }

    /**
     * @param queryTablesString the queryTablesString to set
     */
    public void setQueryTablesString(String queryTablesString) {
        this.queryTablesString = queryTablesString;
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
     * @return the coumnType
     */
    public String getCoumnType() {
        return coumnType;
    }

    /**
     * @param coumnType the coumnType to set
     */
    public void setCoumnType(String coumnType) {
        this.coumnType = coumnType;
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
     * @return the rowNum
     */
    public int getRowNum() {
        return rowNum;
    }

    /**
     * @param rowNum the rowNum to set
     */
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    /**
     * @return the dbKey
     */
    public int getDbKey() {
        return dbKey;
    }

    /**
     * @param dbKey the dbKey to set
     */
    public void setDbKey(int dbKey) {
        this.dbKey = dbKey;
    }

    

}
