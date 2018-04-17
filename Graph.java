////////////////////////////////////////////////////////////////////////////
// Semester:         CS400 Spring 2018
// PROJECT:          P4_DictionaryGraph
// FILES:            Graph.java
// 		     GraphProcessor.java
//		     GraphTest.java
// 		     WordProcessor.java
//                   GraphProcessorTest.java
// USER:             sinde@wisc.edu
//                   xshao36@wisc.edu
//                   rshih2@wisc.edu
//                   
//                 
//
// Instructor:       Deb Deppeler (deppeler@cs.wisc.edu)
// Bugs:             no known bugs
//
// 2018 Apr 16, 2018 WordProcessor.java 
////////////////////////////80 columns wide //////////////////////////////////
import java.util.HashSet;
import java.util.Set;



/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author  (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
    int MAX_VERTEX = 20;// maximum capacity of vertex  
    E[] vertices;// vertices  
    int[][] adjacency;// adjacent matrix  
    int numOfVertex;// the number of vertexes  
    /**
     * Instance variables and constructors
     */
    public Graph() {  
        vertices = (E[]) new Object[MAX_VERTEX];  
        adjacency = new int[MAX_VERTEX][MAX_VERTEX];  
        numOfVertex = 0;  
        //initial the adjacent matrix
        for (int i = 0; i < MAX_VERTEX; i++) {  
            for (int j = 0; j < MAX_VERTEX; j++)  
                adjacency[i][j] = 0; // represent no edge between two vertxes 
        }  
    }
    /**
     * Expand the capacity of adjacent matrix and vertices array
     * 
     * Help method
     */
    private void expandCapacity()
    {
        E[] largerVertices = (E[])(new Object[vertices.length*2]);
       int [][] largerAdjacency = new int[vertices.length*2][vertices.length*2];

       for (int i = 0; i < this.numOfVertex; i++)
       {
          for (int j = 0; j < this.numOfVertex; j++)
          {
             largerAdjacency[i][j] = this.adjacency[i][j];
          }
          largerVertices[i] = this.vertices[i];
       }

       this.vertices = largerVertices;
       this.adjacency = largerAdjacency;
    }
    /**
     * Get the index of vertex in the vertices array
     * 
     * Help method
     * 
     * @return the index if vertex in the graph, else return -1
     */
    private int getIndex(E vertex)
    {
       for (int i = 0; i < numOfVertex; i++)
          if (vertices[i].equals(vertex))
             return i;
       return -1;
    }
    /**
     * Check whether the vertex in the graph
     * 
     * Help method
     * 
     * @return true if vertex in the graph, else return false
     */
    private Boolean containVertex(E vertex) {
        for(int i=0; i< this.numOfVertex;i++) {
            if(this.vertices[i].equals(vertex))
                return true;
          }
        return false;
    }
    
    /**
     * Add new vertex to the graph
     * 
     * Valid argument conditions:
     * 1. vertex should be non-null
     * 2. vertex should not already exist in the graph 
     * 
     * @param vertex the vertex to be added
     * @return vertex if vertex added, else return null if vertex can not be added (also if valid conditions are violated)
     */
    @Override
    public E addVertex(E vertex) {
        if(vertex == null)
            return null;
        if(containVertex(vertex)== true)
            return null;
        
        
        if (numOfVertex == vertices.length)
            expandCapacity();

         vertices[numOfVertex] = vertex;
         for (int i = 0; i <= numOfVertex; i++)
         {
            adjacency[numOfVertex][i] = 0;
            adjacency[i][numOfVertex] = 0;
         }      
         numOfVertex++;
         return vertex;
    }
    /**
     * Remove the vertex in the graph by given index
     * 
     * Help method
     * 
     */
    private void removeVertex (int index)
    {
       
          numOfVertex--;

          for (int i = index; i < numOfVertex; i++) //move the vertex location 
             vertices[i] = vertices[i+1];

          for (int i = index; i < numOfVertex; i++) {
             for (int j = 0; j <= numOfVertex; j++)
                adjacency[i][j] = adjacency[i+1][j]; 
             }

          for (int i = index; i < numOfVertex; i++) {
             for (int j = 0; j < numOfVertex; j++)
                 adjacency[j][i] = adjacency[j][i+1];
             }
       
    }
    
    /**
     * Remove the vertex and associated edge associations from the graph
     * 
     * Valid argument conditions:
     * 1. vertex should be non-null
     * 2. vertex should exist in the graph 
     *  
     * @param vertex the vertex to be removed
     * @return vertex if vertex removed, else return null if vertex and associated edges can not be removed (also if valid conditions are violated)
     */
    @Override
    public E removeVertex(E vertex) {
        if(vertex == null)
            return null;
        if(containVertex(vertex)== false)
            return null;
        
           
              removeVertex(getIndex(vertex));
              return vertex;
           
        
    }
    
    /*private void addEdgeHelper (int start, int end) {
        
        adjacency[start][end] = 1;  
        adjacency[end][start] = 1; 
        
    }*/
    /**
     * Add an edge between two vertices (edge is undirected and unweighted)
     * 
     * Valid argument conditions:
     * 1. both the vertices should exist in the graph
     * 2. vertex1 should not equal vertex2
     *  
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if edge added, else return false if edge can not be added (also if valid conditions are violated)
     */
    @Override
    
    public boolean addEdge(E vertex1, E vertex2) {
        if(vertex1 == vertex2)
            return false;
        if(containVertex(vertex1)== false)
            return false;
        if(containVertex(vertex2)== false)
            return false;
        adjacency[getIndex(vertex1)][getIndex(vertex2)] = 1;  
        adjacency[getIndex(vertex2)][getIndex(vertex1)] = 1;
        
        return true;
    }    
    
    
    /**
     * Remove the edge between two vertices (edge is undirected and unweighted)
     * 
     * Valid argument conditions:
     * 1. both the vertices should exist in the graph
     * 2. vertex1 should not equal vertex2
     *  
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if edge removed, else return false if edge can not be removed (also if valid conditions are violated)
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
        if(vertex1 == vertex2)
            return false;
        if(containVertex(vertex1)== false)
            return false;
        if(containVertex(vertex2)== false)
            return false;
        adjacency[getIndex(vertex1)][getIndex(vertex2)] = 0;  
        adjacency[getIndex(vertex2)][getIndex(vertex1)] = 0;
        return true;
        
    }

    /**
     * Check whether the two vertices are adjacent
     * 
     * Valid argument conditions:
     * 1. both the vertices should exist in the graph
     * 2. vertex1 should not equal vertex2
     *  
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if both the vertices have an edge with each other, else return false if vertex1 and vertex2 are not connected (also if valid conditions are violated)
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        if(vertex1 == vertex2)
            return false;
        if(containVertex(vertex1)== false)
            return false;
        if(containVertex(vertex2)== false)
            return false;
        if( adjacency[getIndex(vertex1)][getIndex(vertex2)]==1 &&
                adjacency[getIndex(vertex2)][getIndex(vertex1)] == 1) {
            return true;
        }
        return false ;
    }

    /**
     * Get all the neighbor vertices of a vertex
     * 
     * Valid argument conditions:
     * 1. vertex is not null
     * 2. vertex exists
     * 
     * @param vertex the vertex
     * @return an iterable for all the immediate connected neighbor vertices
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        if(vertex == null)
            return null;
        if(containVertex(vertex)== false)
            return null;
        Set<E> setOfNeighbor = new HashSet<E>() ;
        for(int i=0;i< numOfVertex;i++) {
            if(adjacency[getIndex(vertex)][i]==1)
                setOfNeighbor.add(vertices[i]);
        }
        return setOfNeighbor; 
    }

    /**
     * Get all the vertices in the graph
     * 
     * @return an iterable for all the vertices
     */
    @Override
    public Iterable<E> getAllVertices() {
        Set<E> setOfVertices = new HashSet<E>() ;
        for(int i=0;i< numOfVertex;i++) {
            
                setOfVertices.add(vertices[i]);
        }
        return setOfVertices;
    }
}

    
             
    
         
                
          
     
        
           
