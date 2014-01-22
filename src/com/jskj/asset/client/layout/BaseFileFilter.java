/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author woderchen
 */
public class BaseFileFilter extends FileFilter {

    private String[] suffixs;

    public BaseFileFilter(){
      super();
    }
    
    public BaseFileFilter(String[] suffixs) {
        this.suffixs = suffixs;
    }

    public boolean accept(File pathname) {
        String name = pathname.getName();

        if (pathname.isDirectory()||suffixs==null) {
            return true;
        }
        // find the last
        int idx = name.lastIndexOf(".");
        if (idx == -1) {
            return false;
        } else if (idx == name.length() - 1) {
            return false;
        } else {
            String houzui = name.substring(idx + 1);
            for(String suffix:suffixs){
               if(suffix.equalsIgnoreCase(houzui)){
                    return true;
               }
            }
        }

        return false;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
