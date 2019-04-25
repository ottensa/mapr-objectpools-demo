package com.mapr.objectpools.model;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private String name;
    private String parent;
    private Double value;
    private String type;
    private List<TreeNode> children;
    private String id;

    public TreeNode(String name) {
        this(name, null, null);
    }

    public TreeNode(String name, Double value, String type) {
        this.name = name;
        this.parent = null;
        this.value = value;
        this.type = type;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode child) {
        child.parent = this.name;
        this.children.add(child);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TreeNode treeNode = (TreeNode) o;

        if (name != null ? !name.equals(treeNode.name) : treeNode.name != null) return false;
        if (parent != null ? !parent.equals(treeNode.parent) : treeNode.parent != null) return false;
        if (value != null ? !value.equals(treeNode.value) : treeNode.value != null) return false;
        if (type != null ? !type.equals(treeNode.type) : treeNode.type != null) return false;
        return children != null ? children.equals(treeNode.children) : treeNode.children == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }
}
