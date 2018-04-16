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
//                   escott7@wisc.edu
//                 
//
// Instructor:       Deb Deppeler (deppeler@cs.wisc.edu)
// Bugs:             no known bugs
//
// 2018 Apr 16, 2018 WordProcessor.java 
////////////////////////////80 columns wide //////////////////////////////////
import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Arrays;

import com.jogamp.graph.geom.Vertex;

/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
    final int MAX_VERTEX = 20;// 最多20个顶点  
    E[] vertices;// 顶点数组  
    int[][] adjacency;// 邻接矩阵  
    int numOfVertex;// 当前图中顶点的数量  
    /*public class Vertex<T>{
        public E label;
        public Boolean wasVisited;
        public Vertex(E lab){
            label = lab;
            wasVisited = false;
        }
    }*/
    public Graph() {// 构造器  
        this.vertices = (E[]) new Object[MAX_VERTEX];  
        adjacency = new int[MAX_VERTEX][MAX_VERTEX];  
        numOfVertex = 0;  
        // 将邻接矩阵初始化 
        //A default value of 0 for arrays of int types is guaranteed by the language spec
    }
    /**
     * Instance variables and constructors
     */
    private void expandCapacity()
    {
       //note, don't need to loop through array just use array.copy();
       int [][] largerAdjacency = 
             new int[vertices.length*2][vertices.length*2];
       for(int i=0; i < this.adjacency.length;i++){
           System.arraycopy(adjacency[i],0, largerAdjacency[i], 0, adjancency.length*2);
       }    
       this.verticies = Arrays.copyOf(this.vertices, this.vertices.length*2);
       this.adjacency = largerAdjacency;
    }
    
    public int getIndex(E vertex)
    {
       for (int i = 0; i < numOfVertex; i++)
          if (vertices[i].equals(vertex))
             return i;
       return -1;
    }
    
    private Boolean containVertex(E vertex) {
        for(int i=0; i< this.numOfVertex;i++) {
            if(this.vertices[i] == vertex)
                return true;
          }
        return false;
    }
    
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
    
    private void removeVertex (int index)
    {
       
          numOfVertex--;

          for (int i = index; i < numOfVertex; i++)
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
     * {@inheritDoc}
     */
    @Override
    public E removeVertex(E vertex) {
        for (int i = 0; i < numOfVertex; i++)
        {
           if (vertex ==vertices[i])
           {
              removeVertex(i);
              return vertex;
           }
        }
        return null;
    }
    
    /*private void addEdgeHelper (int start, int end) {
        
        adjacency[start][end] = 1;  
        adjacency[end][start] = 1; 
        
    }*/
    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
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
