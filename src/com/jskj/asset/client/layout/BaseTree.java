/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import com.jskj.asset.client.util.RightMouseAdapter;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 *
 * @author woderchen
 */
public class BaseTree extends JTree {

    private RightMouseAdapter rightMouseAdapter;
    private TreePath selectedTreePath;

    public BaseTree(Object actionObject) {
        super();
        //右键支持
        rightMouseAdapter = new RightMouseAdapter(this, actionObject);
        addMouseListener(rightMouseAdapter);

        addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                _mouseReleased(evt);
            }
        });
    }

    public BaseTree() {
        this(null);
    }

    private void _mouseReleased(java.awt.event.MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            selectedTreePath = getPathForLocation(evt.getX(), evt.getY());
            if (selectedTreePath != null) {
                setSelectionPath(selectedTreePath);
                rightMouseAdapter.enable(true);
            } else {
                rightMouseAdapter.enable(false);
            }
        }
    }

    /**
     * @return the selectedTreePath
     */
    public TreePath getPopupSelectedTreePath() {
        return selectedTreePath;
    }
}
