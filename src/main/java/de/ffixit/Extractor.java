package de.ffixit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


public class Extractor {
    private static final String[] classes = {"java.util.AbstractCollection", "java.util.AbstractList", "java.util.AbstractQueue", "java.util.AbstractSequentialList", "java.util.AbstractSet", "java.util.concurrent.ArrayBlockingQueue", "java.util.ArrayDeque", "java.util.ArrayList", "java.util.concurrent.ConcurrentLinkedDeque", "java.util.concurrent.ConcurrentLinkedQueue", "java.util.concurrent.ConcurrentSkipListSet", "java.util.concurrent.CopyOnWriteArrayList", "java.util.concurrent.CopyOnWriteArraySet", "java.util.concurrent.DelayQueue", "java.util.EnumSet", "java.util.HashSet", "java.util.concurrent.LinkedBlockingDeque", "java.util.concurrent.LinkedBlockingQueue", "java.util.LinkedHashSet", "java.util.LinkedList", "java.util.concurrent.LinkedTransferQueue", "java.util.concurrent.PriorityBlockingQueue", "java.util.PriorityQueue", "java.util.Stack", "java.util.concurrent.SynchronousQueue", "java.util.TreeSet", "java.util.Vector"};
    private static final List<String> blacklist = Arrays.asList("java.io.Serializable", "java.lang.Cloneable", "java.util.RandomAccess");
    private final Set<String> classNames = new HashSet<>();
    private final Set<Edge> edges = new HashSet<>();

    public void extract() {
        for (String c : classes) {
            findParents(c);
        }

        List<Node> nodes = createNodes();
        AtomicInteger counter = new AtomicInteger();
        System.out.println(nodes.stream().sorted().map(Node::toString).toList());
        System.out.println(edges.stream().map(Edge::toString).toList());
    }

    private void findParents(String className) {
        if (classNames.contains(className) || blacklist.contains(className)) {
            return;
        }

        try {
            Class<?> c = Class.forName(className);
            System.out.println("Processing:" + className);
            classNames.add(className);

            Class superClass = c.getSuperclass();
            if (superClass != null && superClass != Object.class) {
                edges.add(new Edge(superClass.getName(), className));
                System.out.println("Found parent:" + superClass.getName());
                findParents(superClass.getName());
            }

            for (Class<?> i : c.getInterfaces()) {
                String name = i.getName();
                edges.add(new Edge(i.getName(), className));
                System.out.println("Found interface:" + name);
                findParents(name);
            }
        } catch (Throwable e) {
            System.err.println(e);
        }
    }

    private List<Node> createNodes() {
        return classNames.stream().map(name -> {
            try {
                Class<?> c = Class.forName(name);
                String type;
                if (c.isInterface()) {
                    type = "interface";
                } else {
                    type = "class";
                }
                return new Node(name, type);
            } catch (Throwable e) {
                return new Node(name, "UNKNOWN");
            }
        }).toList();
    }

}
