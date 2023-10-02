package pathfinder;

import java.util.*;
import graph.*;
import pathfinder.datastructures.*;

/**
 * Representation of an application of Dijkstra's algorithm, over a graph of a node with type T.
 * Finds the least costly path between two nodes
 * Supports only edges that contain non-negative Doubles
 * @param <T> Type of node to run Dijkstra's algorithm on
 */
public class Algo<T> {
    // Start, destination nodes stored in Graph<T, Double>.GraphNode obj, algorithm is stored as Graph<T, Double> obj
    // Uses priority queue to parse through least costly paths, and uses a set to store nodes where a path is found from
    // starting node

    // RI:
    //  g1 ≠ null, contains start, dest
    //  start, dest ≠ null

    // AF(this);
    //  Paths to a specific node == this.active
    //  Nodes with known minimum cost path from start node == this.finished
    private Graph<T, Double> g1;
    private Graph<T, Double>.GraphNode start;
    private Graph<T, Double>.GraphNode dest;
    private PriorityQueue<Path<T>> active;
    private Set<Graph<T, Double>.GraphNode> finished;

    /**
     * Created an Algo object (Dijkstra's) that has no elements in active, or finished
     * Stores given respective nodes in object
     * @param g1 graph the algo will run on
     * @param start starting node
     * @param dest destination node
     */
    public Algo(Graph<T, Double> g1, Graph<T, Double>.GraphNode start, Graph<T, Double>.GraphNode dest) {
        this.g1 = g1;
        this.start = start;
        this.dest = dest;
        this.active = new PriorityQueue<>(Comparator.comparingDouble(Path::getCost));
        this.finished = new HashSet<>();
    }

    /**
     * Finds, returns minimum-cost path between the respective nodes in their object
     * @spec.modifies this.active, this.finished
     * @return path that is the minimum cost between this.start, this.dest. null if a path does not exist between them.
     */
    public Path<T> getMinPath() {
        active.add(new Path<T>(start.getName()));
        Path<T> minPath;
        Graph<T, Double>.GraphNode minDest;

        while (!active.isEmpty()) {
            minPath = active.poll();
            minDest = g1.getNode(minPath.getEnd());

            if (minDest.equals(this.dest)) {
                return minPath;
            }

            for (Graph<T, Double>.GraphEdge e1 : minDest.getEdges()) {
                if (!finished.contains(e1.getEnd())) {
                    Path<T> newPath = minPath.extend(e1.getEnd().getName(), e1.getName());
                    active.add(newPath);
                }
            }

            finished.add(minDest);
        }
        return null;
    }
}
