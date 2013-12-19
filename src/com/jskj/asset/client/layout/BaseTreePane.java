/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import java.util.ArrayList;

/**
 *
 * @author woderchen
 */
public abstract class BaseTreePane extends BasePanel {

    public static ArrayList disTabCount = new ArrayList();

    public BaseTreePane() {
        super();
    }

    /**
     * @return the disTabCount
     */
    public ArrayList getDisTabCount() {
        return disTabCount;
    }

    /**
     * @param aDisTabCount the disTabCount to set
     */
    public void setDisTabCount(ArrayList aDisTabCount) {
        disTabCount = aDisTabCount;
    }
}
