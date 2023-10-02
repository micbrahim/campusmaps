package graph;
import java.util.*;

/**
 * Represents a mutable, directed graph, consisting of a collecting of named nodes and connecting edges.
 * Edges can link a node tp itself or to a differing node, allowing for a parent-child relationship between
 * the nodes. Graph permits edges connecting a node to itself, but not for two different edges containing
 * the same name to connect to the same edge. No nodes shall share the same name that connect from the same node and
 * end at the same node.
 * @param <T1> Type of node in graph
 * @param <T2> Type of edge in graph
 */
public class Graph<T1, T2> {
    // Nodes in graph are stored in a List of GraphNode objects, edges are stored in a List of
    // GraphEdge objects.

    // RI:
    //  this.nodes ≠ null, this.edges ≠ null,
    //  this.nodes.get(0), ..., this.node.get(x) ≠ null,
    //  this.edges.get(0), ..., this.edges.get(y) ≠ null,
    //  x = this.nodes.size(), y = this.edges.size()

    // AF(this):
    //  Graph Nodes = this.nodes
    //  Graph Edges = this.edges

    private Set<GraphNode> nodes;
    private Set<GraphEdge> edges;
    private static final boolean DEBUGGER = false;

    // Private CheckRep method to check if RI holds
    private void checkRep(){
        assert this.edges != null : "this.edges equals null";
        assert this.nodes != null : "this.nodes equals null";

        if(DEBUGGER){
                assert this.nodes.contains(null) : "this.nodes contains a null element";
                assert this.edges.contains(null) : "this.edges contains a null element";
            }
        }


    /**
     * Creates a directed labeled graph containing no nodes or edges
     */
    public Graph() {
        this.edges = new HashSet<>();
        this.nodes = new HashSet<>();
        checkRep();
    }

    /**
     * Adds a node to the graph directly
     *
     * @param node The node being added to the graph
     * @spec.requires node ≠ null, and is not in the graph
     * @spec.modifies this.nodes
     * @spec.effects list of nodes in this
     */
    public void addNode(GraphNode node) {
        checkRep();
        this.nodes.add(node);
    }

    /**
     * Adds an edge to the graph directly
     *
     * @param edge The edge to be added to the graph
     * @spec.requires edge != null, and is not in the graph
     * @spec.modifies this.edges
     * @spec.effects adds given edge to the edge list in this
     */
    public void addEdge(GraphEdge edge) {
        checkRep();
        this.edges.add(edge);
    }

    /**
     * Returns a set of nodes in the graph
     *
     * @return set containing all GraphNodes in this
     */
    public Set<GraphNode> nodes() {
        checkRep();
        Set<GraphNode> set = new HashSet<>(this.nodes);
        checkRep();
        return set;
    }

    /**
     * Returns a set of edges in the graph
     *
     * @return set containing all GraphEdges in this
     */
    public Set<GraphEdge> edges() {
        checkRep();
        Set<GraphEdge> set = new HashSet<>(this.edges);
        checkRep();
        return set;
    }


    /**
     * Returns a set of the nodes that are child nodes
     * of a specific node. Returned set also contains no repeated nodes
     *
     * @param node the node whose child nodes will be returned
     * @return set containing all children GraphNode objects of the node
     * @spec.requires given node is in the graph and != null
     */
    public Set<GraphNode> childrenOfNode(GraphNode node) {
        checkRep();
        Set<GraphNode> children = new HashSet<>();
        if(this.nodes.contains(node)){
            for(GraphEdge edge : node.con){
                children.add(edge.getEnd());
            }
        }
        checkRep();
        return children;

    }

    /**
     * Returns the GraphEdge in the graph with given starting node, ending node, and name
     *
     * @param name name of wanted edge
     * @param start starting node of the desired edge
     * @param end ending node of the desired edge
     * @return a GraphEdge object with given starting node, ending node, and name or null if there are no edges.
     * @spec.requires name, start, and end are != null, and represent a stored edge in this
     */
    public GraphEdge getEdge(GraphNode start, GraphNode end, T1 name) {
        checkRep();

        for (GraphEdge e : this.edges) {
            if (e.name.equals(name) && e.end.equals(end) && start.con.contains(e)) {
                return e;
            }
        }

        checkRep();
        return null;
    }


    /**
     * Returns the GraphNode in the graph with given label
     * @param name name of the desired node
     * @spec.requires data is not null and is a label of a node in this
     * @return a GraphNode object with given label or null when there are no nodes
     */
    public GraphNode getNode(T1 name){
        checkRep();
        for (GraphNode node : this.nodes) {
            if (node.name.equals(name)) {
                return node;
            }
        }
        checkRep();
        return null;
    }


    /**
     * Represents a node of a graph. A node can have an unlimited amount of edges originating and connecting (con) to it
     * Each node represents a name
     */
    public class GraphNode {
        // RI:
        //  this.con ≠ null,
        //  this.con(0) ≠ this.con(1) ≠ ... ≠ this.con(n)

        // AF(this)
        //  Name = this.name
        //  Outgoing edges = this.con
        private T1 name;
        private Set<GraphEdge> con;

        /**
         * Creates a node with a name
         *
         * @param name name that identifies the new node
         */
        public GraphNode(T1 name) {
            this.name = name;
            this.con = new HashSet<>();
        }

        /**
         * Returns the name of node
         *
         * @return this.name
         */
        public T1 getName() {
            return this.name;
        }

        /**
         * Returns every outgoing edge of this node
         *
         * @return set containing the outgoing edges of this
         */
        public Set<GraphEdge> getEdges() {
            return new HashSet<>(this.con);
        }

    }
    /**
     * Represents an edge connecting two nodes of a directed
     * labeled graph. Each edge has a parent node that it
     * originates from and a child node that it connects
     * the parent to. Each edge is also identifiable by
     * a name.
     */
    public class GraphEdge {
        // RI: this.end ≠ null

        // AF(this):
        //  name = this.name
        //  Resulting node = this.end
        private T2 name;
        private GraphNode end;
        /**
         * Creates an edge starting and ending at given node, identifiable by name
         *
         * @param start Node that the new edge will originate from
         * @param end   Node that the new edge will travel to
         * @param name  name of the new node
         */
        public GraphEdge(GraphNode start, GraphNode end, T2 name) {
            this.end = end;
            this.name = name;
            start.con.add(this);
        }

        /**
         * Returns name of edge
         *
         * @return this.name
         */
        public T2 getName(){
            return this.name;
        }


        /**
         * Returns the destination node of the edge
         *
         * @return this.end
         */
        public GraphNode getEnd(){
            return this.end;
        }

        /**
         * Returns a boolean of this and obj
         *
         * @param obj an object to be compared with this
         * @return true iff instance of obj is GraphEdge and object end and name are equal
         * to this
         * @spec.requires object != null
         */

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Graph.GraphEdge)) {
                return false;
            }
            Graph.GraphEdge o = (Graph.GraphEdge) obj;
            return this.end.equals(o.end) && this.name.equals(o.name);
        }

        /**
         * Returns hash code
         *
         * @return integer hash code
         */
        @Override
        public int hashCode() {
            return name.hashCode()^end.hashCode();
        }
    }
}
