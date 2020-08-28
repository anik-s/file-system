package xyz.aniksarker.fs;

import xyz.aniksarker.fs.exception.InvalidOperationException;
import xyz.aniksarker.fs.exception.NameExistsException;
import xyz.aniksarker.fs.exception.NodeExistsException;

import java.util.*;

public class Folder extends Node {

    // TreeSet is used because it guarantees uniqueness, it's always sorted
    // and access and retrieval times are quiet fast for TreeSet
    private Set<Node> children = new TreeSet<>();

    /**
     * Constructs a new Folder object with the specified name
     * @param name the name of the folder
     */
    public Folder(String name) {
        super(name);
    }

    public Set<Node> getChildren() {
        return children;
    }

    /**
     * Returns the size of the folder which is the total size
     * of all files that it and its sub-folders (recursively) have
     * @return the size of the folder
     */
    @Override
    public int size() {
        int size = 0;
        for(Node node: children) {
            size += node.size();
        }
        return size;
    }

    /**
     * Adds a Node as a child
     * @param node the node to be added
     */
    public void add(Node node) {
        if (this == node) {
            throw new InvalidOperationException("A Node can't be placed inside itself");
        }
        if (children.contains(node)) {
            throw new NodeExistsException("Node with the same name already exists in the folder");
        }
        if(node.getParent() != null) {
            Folder currentParent = ((Folder)node.getParent());
            currentParent.getChildren().remove(node);
        }
        children.add(node);
        node.setParent(this);
    }

    /**
     * Prints the names of all children Nodes (Folders and Files) on Stdout (as per requirement)
     * sorted alphabetically
     * This excludes sub-folders
     */
    public void list() {
        List<Node> nodes = new ArrayList<Node>(children);
        Collections.sort(nodes);
        StringBuilder stringBuilder = new StringBuilder();
        for (Node node : nodes) {
            stringBuilder.append(node.getName()).append("\n");
        }
        System.out.println(stringBuilder.toString().trim());
    }

    /**
     * Prints the names of all Nodes (Folders and Files) on Stdout (as per requirement)
     * sorted alphabetically in a tree structure the folder contains.
     * This includes sub-folders recursively
     */
    public void tree() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Node node:children) {
            buildTree(node, 0, stringBuilder);
        }
        System.out.println(stringBuilder.toString().trim());
    }

    /**
     * Builds the tree structured string for a particular node
     * @param node the node for which the tree structured string should be built
     * @param indent amount of indentation required
     * @param stringBuilder the string builder in which the tree and built
     */
    private void buildTree(Node node, int indent, StringBuilder stringBuilder) {
        stringBuilder.append(getIndentString(indent)).append("|__").append(node.getName()).append("\n");
        if ((node instanceof Folder)) {
            for (Node n: ((Folder) node).getChildren()) {
                buildTree(n, indent + 1, stringBuilder);
            }
        }
    }

    /**
     * Returns the indent string based on the the number of indent required
     * @param indent the number of indentation required to print tree structure
     * @return the indent string
     */
    private static String getIndentString(int indent) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            stringBuilder.append("|  ");
        }
        return stringBuilder.toString();
    }

}
