package com.netcracker.edu.java.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeNodeImpl implements TreeNode {

    private TreeNode parent;
    private List<TreeNode> children = new ArrayList<>();
    private boolean expanded = false;
    private Object data;

    public TreeNodeImpl() {}

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    @Override
    public TreeNode getRoot() {
        TreeNode root = getParent();
        if (root == null) {
            return null;
        }
        while(root.getParent() != null) {
            root = root.getParent();
        }
        return root;
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public Iterator<TreeNode> getChildrenIterator() {
        return children.iterator();
    }

    @Override
    public void addChild(TreeNode child) {
        child.setParent(this);
        children.add(child);
    }

    @Override
    public boolean removeChild(TreeNode child) {
        boolean result = children.remove(child);
        if (result) {
            child.setParent(null);
        }
        return result;
    }

    @Override
    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        for (TreeNode child : children) {
            child.setExpanded(expanded);
        }
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String getTreePath() {
        String delimiter = "->";
        String path = getData() == null ? "empty" : getData().toString();
        if (getParent() != null) {
            path = getParent().getTreePath() + delimiter + path;
        }
        return path;
    }

    @Override
    public TreeNode findParent(Object data) {
        if (getData().equals(data)) {
            return this;
        }
        return getParent().findParent(data);
    }

    @Override
    public TreeNode findChild(Object data) {
        if (children == null)
            return null;
        for(TreeNode child: children){
            if ( getData() != null && data.equals(child.getData()))
                return child;
            if ( getData() == null && child.getData() == null){
                return child;
            }
        }
        for(TreeNode child: children)
            return child.findChild(data);
        return null;
    }
}
