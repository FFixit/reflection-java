package de.ffixit;

public class Node implements Comparable<Node> {
    public String name;
    public String type;

    private static int counter = 0;

    public Node(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public int compareTo(Node o) {
        return name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "{id:\"%s\", name:\"%s\", type:\"%s\", position: {x:%d,y:%d}, data: {label:\"%s\",type:\"%s\"}}"
                .formatted(name, name, type, counter++ * 100, type.equals("interface") ? 0 : 100, name, type);
    }
}
