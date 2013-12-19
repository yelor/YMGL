/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.AssetClientView;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

/**
 *
 * @author woderchen
 */
public class AssetTab extends JPanel {

    private final JTabbedPane jTabBed;
    private final JPanel tabPane;
    private final ActionMap actionMap = Application.getInstance(AssetClientApp.class).getContext().getActionMap(AssetTab.class, this);
    private AssetClientView hosClientView = null;

    public AssetTab(JTabbedPane jTabBed, JPanel tabPane, AssetNode selectNode) {
        super();
        this.jTabBed = jTabBed;
        this.tabPane = tabPane;
        hosClientView = (AssetClientView) Application.getInstance(AssetClientApp.class).getMainView();
        buildTab(selectNode);
    }

    public AssetTab(JTabbedPane jTabBed, JPanel tabPane, String name) {
        super();
        this.jTabBed = jTabBed;
        this.tabPane = tabPane;
        hosClientView = (AssetClientView) Application.getInstance(AssetClientApp.class).getMainView();
        buildTab(name);
    }

    private void buildTab(AssetNode selectNode) {

        javax.swing.JToolBar jToolBar = new javax.swing.JToolBar();
        jToolBar.setFloatable(false);
        jToolBar.setRollover(true);
        Dimension d= new Dimension();
        d.setSize(22, 15);
        jToolBar.setPreferredSize(d);
        
        JButton tabCloseButton = new JButton();
        tabCloseButton.setAction(actionMap.get("close"));
        tabCloseButton.setText("×");
        jToolBar.add(tabCloseButton);

       // javax.swing.JLabel statusAnimationLabel = hosClientView.getStatusAnimationLabel();

        JLabel textlabel = new JLabel(selectNode.getNodeName());
       // this.add(statusAnimationLabel, BorderLayout.WEST);
        this.add(textlabel, BorderLayout.CENTER);
        this.add(jToolBar, BorderLayout.EAST);

    }

    private void buildTab(String name) {
        javax.swing.JToolBar jToolBar = new javax.swing.JToolBar();
        jToolBar.setFloatable(false);
        jToolBar.setRollover(true);
        Dimension d= new Dimension();
        d.setSize(22, 15);
        jToolBar.setPreferredSize(d);
        
        JButton tabCloseButton = new JButton();
        tabCloseButton.setAction(actionMap.get("close"));
        tabCloseButton.setText("×");
        jToolBar.add(tabCloseButton);

       // javax.swing.JLabel statusAnimationLabel = hosClientView.getStatusAnimationLabel();

        JLabel textlabel = new JLabel(name);
       // this.add(statusAnimationLabel, BorderLayout.WEST);
        this.add(textlabel, BorderLayout.CENTER);
        this.add(jToolBar, BorderLayout.EAST);




    }

    @Action
    public void close() {
        jTabBed.remove(tabPane);
        BaseTreePane.disTabCount.remove(tabPane.getName());
        System.out.println("------------->CLOSE:" + tabPane.getName());

    }
}
