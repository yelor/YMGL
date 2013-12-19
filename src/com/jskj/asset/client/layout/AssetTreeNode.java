/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 *
 * @author woderchen
 */
public class AssetTreeNode implements  MutableTreeNode, Serializable {

    protected MutableTreeNode parent;
    protected AssetNode userNode;
    protected Vector children;
    protected boolean allowsChildren;

    public AssetTreeNode(AssetNode userNode) {
        this(userNode,true);
    }

    public AssetNode getUserNode(){
      return userNode;
    }

    public AssetTreeNode(AssetNode userNode,boolean allowsChildren) {
        super();
        parent = null;
        this.allowsChildren = allowsChildren;
        this.userNode = userNode;

    }

    public void insert(MutableTreeNode newChild, int childIndex) {
        if (!allowsChildren) {
	    throw new IllegalStateException("node does not allow children");
	} else if (newChild == null) {
	    throw new IllegalArgumentException("new child is null");
	} else if (isNodeAncestor(newChild)) {
	    throw new IllegalArgumentException("new child is an ancestor");
	}

	    MutableTreeNode oldParent = (MutableTreeNode)newChild.getParent();

	    if (oldParent != null) {
		oldParent.remove(newChild);
	    }
	    newChild.setParent(this);
	    if (children == null) {
		children = new Vector();
	    }
	    children.insertElementAt(newChild, childIndex);
    }

    public void remove(int childIndex) {
       MutableTreeNode child = (MutableTreeNode)getChildAt(childIndex);
	children.removeElementAt(childIndex);
	child.setParent(null);
    }

    public void remove(MutableTreeNode node) {
        if (node == null) {
	    throw new IllegalArgumentException("argument is null");
	}

	if (!isNodeChild(node)) {
	    throw new IllegalArgumentException("argument is not a child");
	}
	remove(getIndex(node));	// linear search
    }

    public void setUserObject(Object object) {
        this.userNode = (AssetNode)object;
    }

    public void removeFromParent() {
       MutableTreeNode parent = (MutableTreeNode)getParent();
	if (parent != null) {
	    parent.remove(this);
	}
    }

    public void setParent(MutableTreeNode newParent) {
        parent = newParent;
    }

    public TreeNode getChildAt(int childIndex) {
       if (children == null) {
	    throw new ArrayIndexOutOfBoundsException("node has no children");
	}
	return (TreeNode)children.elementAt(childIndex);
    }

    public int getChildCount() {
        if (children == null) {
	    return 0;
	} else {
	    return children.size();
	}
    }

    public TreeNode getParent() {
        return parent;
    }

    public int getIndex(TreeNode node) {
       if (node == null) {
	    throw new IllegalArgumentException("argument is null");
	}

	if (!isNodeChild(node)) {
	    return -1;
	}
	return children.indexOf(node);	// linear search
    }

    public boolean getAllowsChildren() {
        return allowsChildren;
    }

    public boolean isLeaf() {
       return (getChildCount() == 0);
    }

    public Enumeration children() {
        if (children == null) {
	    return EMPTY_ENUMERATION;
	} else {
	    return children.elements();
	}
    }


    /**
     *
     * start
     *
     *
     **/
    static public final Enumeration<TreeNode> EMPTY_ENUMERATION
	= new Enumeration<TreeNode>() {
	    public boolean hasMoreElements() { return false; }
	    public TreeNode nextElement() {
		throw new NoSuchElementException("No more elements");
	    }
    };

     public void add(MutableTreeNode newChild) {
	if(newChild != null && newChild.getParent() == this)
	    insert(newChild, getChildCount() - 1);
	else
	    insert(newChild, getChildCount());
    }

    public boolean isNodeAncestor(TreeNode anotherNode) {
	if (anotherNode == null) {
	    return false;
	}

	TreeNode ancestor = this;

	do {
	    if (ancestor == anotherNode) {
		return true;
	    }
	} while((ancestor = ancestor.getParent()) != null);

	return false;
    }

       public boolean isNodeChild(TreeNode aNode) {
	boolean retval;

	if (aNode == null) {
	    retval = false;
	} else {
	    if (getChildCount() == 0) {
		retval = false;
	    } else {
		retval = (aNode.getParent() == this);
	    }
	}

	return retval;
    }

       /**
        * 树形菜单显示的文字
        * @return
        */
    @Override
     public String toString(){
      return userNode.getNodeName();
    }

}
