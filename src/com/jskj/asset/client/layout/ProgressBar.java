/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.layout;

import javax.swing.JProgressBar;

/**
 *
 * @author 305027939
 */
public class ProgressBar extends JProgressBar implements Runnable{

    public ProgressBar(){
     setIndeterminate(true);
    }
    
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
