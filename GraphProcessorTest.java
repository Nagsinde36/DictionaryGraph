////////////////////////////////////////////////////////////////////////////
// Semester:         CS400 Spring 2018
// PROJECT:          DictionaryGraph
// FILES:            Graph.java
//                   GraphProcessor.java
//                   GraphTest.java
//                   WordProcessor.java
//
// USERS:            sinde@wisc.edu
//                  
//                 
//
// Instructor:       Deb Deppeler (deppeler@cs.wisc.edu)
// Bugs:             no known bugs, but not complete either
//
// 2018 Apr 16, 2018 GraphProcessorTest.java 
////////////////////////////80 columns wide //////////////////////////////////

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Junit test class to test class @see Graph that implements @see GraphADT interface
 *
 * @author sapan (sapan@cs.wisc.edu)
 */
public class GraphProcessorTest {
    
    private GraphProcessor gProc;
    private WordProcessor wdProc;
    
    private static List<String> vertices;
    
    private static int Verticesnum = 0;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        vertices = new ArrayList<>();
        vertices.add("at");
        vertices.add("it");
        vertices.add("cat");
        vertices.add("hat");
        vertices.add("hot");
        vertices.add("rat");
        vertices.add("heat");
        vertices.add("beat");
        vertices.add("tragedy");
        vertices.add("wheat");
        vertices.add("streak");
        vertices.add("husband");
        for (String vertex : vertices)
            Verticesnum++;
    }
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        vertices = null;
        Verticesnum = 0;
    }
    
    @Before
    public void setUp() throws Exception {
        gProc = new GraphProcessor();
        wdProc = new WordProcessor();
    }
    
    @After
    public void tearDown() throws Exception {
        gProc = null;
        wdProc = null;
    }
    
    // INSERT TESTS HERE:
    /*
     * Lists of tests to be conducted (Please add your own stuff to it!):
     * 
     */
    @Test
    public final void test1ToBeNamed() {
        assertEquals("", 0, 0);
    }
}
