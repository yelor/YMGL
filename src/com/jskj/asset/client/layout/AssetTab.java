/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.AssetClientView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
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
    private AssetClientView clientView = null;

    public AssetTab(JTabbedPane jTabBed, JPanel tabPane, AssetNode selectNode) {
        this(jTabBed, tabPane, selectNode.getNodeName());
    }

    public AssetTab(JTabbedPane jTabBed, JPanel tabPane, String name) {
        super();
        this.jTabBed = jTabBed;
        this.tabPane = tabPane;
        clientView = (AssetClientView) Application.getInstance(AssetClientApp.class).getMainView();
        buildTab(name);
        setOpaque(false);
    }

    private void buildTab(String name) {
//        javax.swing.JToolBar jToolBar = new javax.swing.JToolBar("closetool");
//        jToolBar.setFloatable(true);
//        jToolBar.setRollover(false);
//        jToolBar.setOpaque(true);
//        jToolBar.setBorderPainted(false);
//        Dimension d = new Dimension();
//        d.setSize(30, 20);
//        jToolBar.setPreferredSize(d);
//        jToolBar.setMargin(new Insets(0, 0, 0, 0));
//        
//        jToolBar.setBackground(Color.WHITE);  /*这里是目前使用的UI的一个bug？？？？？，必须要有背景色才能透明？？*/

        JButton tabCloseButton = new JButton("close");

        GridBagConstraints gbc_tabButton = new GridBagConstraints();
        
//        gbc_tabButton.anchor = GridBagConstraints.EAST;
//        gbc_tabButton.insets = new Insets(0, 0, 0, 0);
//        gbc_tabButton.fill = GridBagConstraints.BOTH;
//        gbc_tabButton.gridx = 0;
//        gbc_tabButton.gridy = 0;
//        gbc_tabButton.gridwidth = 5;

        tabCloseButton.setAction(actionMap.get("close"));
        tabCloseButton.setText("×");
        tabCloseButton.setBorderPainted(false);
        tabCloseButton.setOpaque(false);

        // javax.swing.JLabel statusAnimationLabel = hosClientView.getStatusAnimationLabel();
        JLabel textlabel = new JLabel(name);
        // this.add(statusAnimationLabel, BorderLayout.WEST);
//        this.add(textlabel, BorderLayout.CENTER);
//        this.add(jToolBar, BorderLayout.EAST);

        javax.swing.GroupLayout ctrlPaneLayout = new javax.swing.GroupLayout(this);
        this.setLayout(ctrlPaneLayout);
        ctrlPaneLayout.setHorizontalGroup(
                ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ctrlPaneLayout.createSequentialGroup()
                        .addComponent(textlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabCloseButton, 20, 20, 20)
                        ));

        ctrlPaneLayout.setVerticalGroup(
                ctrlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(textlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabCloseButton, javax.swing.GroupLayout.Alignment.CENTER,16, 16, 16)
        );

    }

    @Action
    public void close() {
        jTabBed.remove(tabPane);
        BaseTreePane.disTabCount.remove(tabPane.getName());
        System.out.println("------------->CLOSE:" + tabPane.getName());

    }
}
