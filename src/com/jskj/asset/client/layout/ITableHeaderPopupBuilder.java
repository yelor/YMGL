/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.layout;

import java.util.HashMap;
import org.jdesktop.application.Task;

/**
 *
 * @author 305027939
 */
public interface ITableHeaderPopupBuilder {
    
    public int[] getFilterColumnHeader();
    
    public Task filterData(HashMap<Integer, String> searchKeys);
    
}
