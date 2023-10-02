package graph.junitTests;

import graph.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

// test class for Junit tests
public class JunitTest {

        // create graph
        Graph<String, String> graph1 = new Graph<>();

        // create nodes
        Graph<String, String>.GraphNode n1 = graph1.new GraphNode("n1");
        Graph<String, String>.GraphNode n2 = graph1.new GraphNode("n2");
        Graph<String, String>.GraphNode n3 = graph1.new GraphNode("n3");


        // create edges
        Graph<String, String>.GraphEdge e1 = graph1.new GraphEdge(n1, n2, "e1");
        Graph<String, String>.GraphEdge e2 = graph1.new GraphEdge(n2, n3, "e2");
        Graph<String, String>.GraphEdge e3 = graph1.new GraphEdge(n1, n3, "e3");
        Graph<String, String>.GraphEdge e4 = graph1.new GraphEdge(n1, n3, "e3");
        Graph<String, String>.GraphEdge e5 = graph1.new GraphEdge(n1, n2, "e5");





    // asserting that the edge returned by getEdge is equal to the inputted start and end nodes with the name
    @Test
    public void getEdge() {

        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        graph1.addEdge(e1);
        graph1.addEdge(e2);
        graph1.addEdge(e3);
        graph1.addEdge(e4);
        graph1.addEdge(e5);

        assertEquals(e1, graph1.getEdge(n1, n2, "e1"));
        assertEquals(e2, graph1.getEdge(n2, n3, "e2"));
        assertEquals(e3, graph1.getEdge(n1, n3, "e3"));
    }
    // asserts that the name of the edge correlates to itself
    @Test
    public void getName() {

        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        graph1.addEdge(e1);
        graph1.addEdge(e2);
        graph1.addEdge(e3);
        graph1.addEdge(e4);
        graph1.addEdge(e5);

        assertEquals(e1.getName(),"e1");
        assertEquals(e2.getName(),"e2");
        assertEquals(e3.getName(),"e3");
    }

    // test if the new .equals methods return the right booleans
    @Test
    public void testEquals() {

        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        graph1.addEdge(e1);
        graph1.addEdge(e2);
        graph1.addEdge(e3);
        graph1.addEdge(e4);
        graph1.addEdge(e5);

        assertTrue(e3.equals(e4));
        assertNotEquals(e1, e5);
    }

    // tests that the
    @Test
    public void testHashCode() {

        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        graph1.addEdge(e1);
        graph1.addEdge(e2);
        graph1.addEdge(e3);
        graph1.addEdge(e4);
        graph1.addEdge(e5);

        assertEquals(e1.hashCode(),"e1".hashCode()^n2.hashCode());
        assertEquals(e3.hashCode(),"e3".hashCode()^n3.hashCode());
    }
}

