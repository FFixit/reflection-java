package de.ffixit;

public class Edge {
    public String source;
    public String target;


    public Edge(String source, String target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public String toString() {
        return "{id:\"%s-%s\", source:\"%s\", target:\"%s\", animated: false}".formatted(source, target, source, target);
    }
}
