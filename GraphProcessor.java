////////////////////////////////////////////////////////////////////////////
// Semester:         CS400 Spring 2018
// PROJECT:          P4_DictionaryGraph
// FILES:            Graph.java
// 		     GraphProcessor.java
//		     GraphTest.java
// 		     WordProcessor.java
//                   GraphProcessor.java
// USER:             sinde@wisc.edu
//                   xshao36@wisc.edu
//                   rshih2@wisc.edu
//                   escott7@wisc.edu
//                 
//
// Instructor:       Deb Deppeler (deppeler@cs.wisc.edu)
// Bugs:             no known bugs
//
// 2018 Apr 16, 2018 WordProcessor.java 
////////////////////////////80 columns wide //////////////////////////////////

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.List;

/**
 * This class adds additional functionality to the graph as a whole.
 * 
 * Contains an instance variable, {@link #graph}, which stores information for all the vertices and edges.
 * 
 * @see #populateGraph(String)
 *      - loads a dictionary of words as vertices in the graph.
 *      - finds possible edges between all pairs of vertices and adds these edges in the graph.
 *      - returns number of vertices added as Integer.
 *      - every call to this method will add to the existing graph.
 *      - this method needs to be invoked first for other methods on shortest path computation to work.
 * @see #shortestPathPrecomputation()
 *      - applies a shortest path algorithm to precompute data structures (that store shortest path data)
 *      - the shortest path data structures are used later to
 *      to quickly find the shortest path and distance between two vertices.
 *      - this method is called after any call to populateGraph.
 *      - It is not called again unless new graph information is added via populateGraph().
 * @see #getShortestPath(String, String)
 *      - returns a list of vertices that constitute the shortest path between two given vertices,
 *      computed using the precomputed data structures computed as part of {@link #shortestPathPrecomputation()}.
 *      - {@link #shortestPathPrecomputation()} must have been invoked once before invoking this method.
 * @see #getShortestDistance(String, String)
 *      - returns distance (number of edges) as an Integer for the shortest path between two given vertices
 *      - this is computed using the precomputed data structures computed as part of {@link #shortestPathPrecomputation()}.
 *      - {@link #shortestPathPrecomputation()} must have been invoked once before invoking this method.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class GraphProcessor {
    
    /**
     * Graph which stores the dictionary words and their associated connections
     */
    private GraphADT<String> graph;
    // index i = beginning of word, j = end of word
    private List<String>[][] shortestPath;
    
    /**
     * Constructor for this class. Initializes instances variables to set the starting state of the object
     */
    public GraphProcessor() {
        this.graph = new Graph<>();
    }
    
    /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the dictionary as vertices
     * and finding and adding the corresponding connections (edges) between
     * existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph.
     * Repeat for all words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent {@link WordProcessor#isAdjacent(String, String)}
     * If a pair is adjacent, adds an undirected and unweighted edge between the pair of vertices in the graph.
     * 
     * @param filepath
     *            file path to the dictionary
     * @return Integer the number of vertices (words) added
     */
    @SuppressWarnings("unchecked")
    public Integer populateGraph(String filepath) {
        int size = 0;
        try {
            Iterator<String> words = WordProcessor.getWordStream(filepath).iterator();
            while (words.hasNext()) {
                String word1 = words.next();
                graph.addVertex(word1);
                for (String word2 : graph.getAllVertices()) {
                    if (WordProcessor.isAdjacent(word1, word2))
                        graph.addEdge(word1, word2);
                }
                size++;
            }
            shortestPaths = (List<String>[][]) (new List<?>[size][size]);
            shortestPathPrecomputation();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return size;
        
    }
    
    /**
     * Gets the list of words that create the shortest path between word1 and word2
     * 
     * Example: Given a dictionary,
     * cat
     * rat
     * hat
     * neat
     * wheat
     * kit
     * shortest path between cat and wheat is the following list of words:
     * [cat, hat, heat, wheat]
     * 
     * @param word1
     *            first word
     * @param word2
     *            second word
     * @return List<String> list of the words
     */
    public List<String> getShortestPath(String word1, String word2) {
        ArrayList<String> vertices = ((ArrayList<String>) graph.getAllVertices());
        // uppercase all input regardless of case
        int start = vertices.indexOf(word1.toUpperCase());
        int end = vertices.indexOf(word2.toUpperCase());
        if (start == -1 || end == -1)
            return null;
        return shortestPaths[start][end];
    }
    
    /**
     * Gets the distance of the shortest path between word1 and word2
     * 
     * Example: Given a dictionary,
     * cat
     * rat
     * hat
     * heat
     * wheat
     * kit
     * distance of the shortest path between cat and wheat, [cat, hat, heat, wheat]
     * = 3 (the number of edges in the shortest path)
     * 
     * @param word1
     *            first word
     * @param word2
     *            second word
     * @return Integer distance
     */
    public Integer getShortestDistance(String word1, String word2) {
        List<String> shortestPath = getShortestPath(word1, word2);
        if (shortestPath == null) // If there is no path, return -1
            return -1;
        return shortestPath.size() - 1;
    }
    
    /**
     * Computes shortest paths and distances between all possible pairs of vertices.
     * This method is called after every set of updates in the graph to recompute the path information.
     * Any shortest path algorithm can be used (Djikstra's or Floyd-Warshall recommended).
     */
    public void shortestPathPrecomputation() {
        ArrayList<String> vertices = ((ArrayList<String>) graph.getAllVertices());
        // Calculate the shortest distance between a vertex and its neighboring vertices
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                // If different vertex
                if (!vertices.get(i).equals(vertices.get(j)))
                    shortestPaths[i][j] = shortestPathComp(vertices.get(i), vertices.get(j), vertices);
            }
        }
    }
    
    /**
     * Private method that uses Dijkstra's Algorithm
     * To calculate the shortest path from A to B
     * Will be calculating for shortestPathPrecomputation()
     * 
     * @param word1
     *            Starting point
     * @param word2
     *            Ending Point
     * @param vertices
     *            To use to check against if it's been visited or not
     * @return
     */
    private List<String> shortestPathComp(String word1, String word2, ArrayList<String> vertices) {
        Node current;
        int Weightofpath = 0; // Starting path weight
        // Checks if already visited
        boolean[] inPosition = new boolean[vertices.size()]; // Integer Position
        // Gets the node with the highest priority
        PriorityQueue<Node> lessCostPath = new PriorityQueue<Node>();
        lowCostPath.add(new Node(word1, 0)); // Just so we run through at least once
        
        while (!lessCostPath.isEmpty()) {
            // Get next best node
            current = lessCostPath.poll();
            // If the destination was added to the list
            if (current.node.equals(word2))
                return generatePath(current); // Generates path starting with last node
            // Add all unchecked neighbors
            for (String neighbor : (ArrayList<String>) graph.getNeighbors(curr.node)) {
                int index = vertices.indexOf(neighbor);
                if (!inPosition[index]) {
                    inPosition[index] = true; // Add to checked
                    Node newNode = new Node(neighbor, curr, pathWeight + 1);
                    lowCostPath.add(newNode); // Add to priority queue
                }
            }
            // Increment path weight 
            Weightofpath++;
        }
        return null; // If possible
    }
    
    /**
     * Generates the list of nodes inside the shortest path
     * 
     * @param visited
     *            The destination node
     * @return
     *         The shortest path
     */
    private List<String> generatePath(Node visited) {
        List<String> path = new ArrayList<String>();
        addNodeToPath(path, visited); // Start Recursion
        return path;
    }
    
    /**
     * Recursive method to add nodes to the path
     * 
     * @param path
     *            The shortest path from A to B
     * @param visited
     *            The current node
     */
    private void addNodeToPath(List<String> path, Node visited) {
        if (visited != null) {
            // Work "bottom" up to build path correctly
            addNodeToPath(path, visited.parent);
            // Then add the node
            path.add(visited.node);
        }
    }
    
    /**
     * Private class for Dijkstra's Algorithm
     * 
     * @author nagsinde
     *
     */
    private class Node implements Comparable<Node> {
        String node; // Vertex name
        Node parentnode; // Vertex's parent
        int weight; // The weight to reach this node
        
        public Node(String node, int weight) {
            this.node = node;
            parentnode = null;
            this.weight = weight;
        }
        
        public Node(String node, Node parent, int weight) {
            this.node = node;
            this.parentnode = parentnode;
            this.weight = weight;
        }
        
        /**
         * Compares two nodes based on their weights
         */
        @Override
        public int compareTo(Node o) {
            return weight - o.weight;
        }
        
    }
}
