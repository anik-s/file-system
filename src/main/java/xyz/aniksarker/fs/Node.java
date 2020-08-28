package xyz.aniksarker.fs;


public abstract class Node implements Comparable<Node> {

    private String name;
    private Node parent;

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Abstract method for the inherited classes to override
     */
    abstract int size();

    public int compareTo(Node node) {
        return getName().compareTo(node.getName());
    }

}