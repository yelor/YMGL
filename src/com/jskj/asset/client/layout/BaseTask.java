/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.AssetClientView;
import com.jskj.asset.client.util.ProgressPanel;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JFrame;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author woderchen
 */
public abstract class BaseTask extends Task<Object, Void> {

    private ProgressPanel ipp;
    // private JFrame focusComponent;
    private boolean processDisplay = true;
    public static Lock lock = new ReentrantLock();
    
    public final static int STATUS_OK=0;
     public final static int STATUS_ERROR=-1;
    
    
    protected AssetClientView clientView = (AssetClientView) Application.getInstance(AssetClientApp.class).getMainView();

    public BaseTask(Application app) {
        super(app);
    }

    public BaseTask() {
        this(AssetClientApp.getApplication());
    }

    protected void startWaitingPage() {

//        if (ipp == null) {
//            ipp = new ProgressPanel("");
//        }
//
//        ipp.init();
//
//        mainView.getFrame().setGlassPane(ipp);
//
//        ipp.start();
    }

    public void stop(boolean booleann) {
        super.cancel(booleann);
        if (ipp != null) {
            ipp.stop();
        }
    }

    protected void stopWaitingPage(JFrame frame) {
//        if (ipp != null) {
//            ipp.stop();
//        }
    }

    @Override
    protected Object doInBackground() throws Exception {
        clientView.getProgressBar().setIndeterminate(true);
        // System.out.println("----------->doInBackground");
        if (processDisplay) {
            startWaitingPage();
        }

        return doBackgrounp();
    }

    @Override
    protected void succeeded(Object object) {

        if (processDisplay) {
            stopWaitingPage(Application.getInstance(AssetClientApp.class).getMainFrame());
        }
        onSucceeded(object);
        //System.out.println("--------------->succeeded");
        clientView.getProgressBar().setIndeterminate(false);
    }

    public abstract Object doBackgrounp();

    public abstract void onSucceeded(Object object);

    /**
     * @param processDisplay the processDisplay to set
     */
    public void setProcessDisplay(boolean processDisplay) {
        this.processDisplay = processDisplay;
    }
}
