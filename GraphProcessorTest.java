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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GraphProcessorTest {
	private GraphProcessor graphProcessor;
	private String fileName;
	private Integer numOfVertices;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		this.graphProcessor = new GraphProcessor();
		this.fileName = "word_list.txt";
		numOfVertices = graphProcessor.populateGraph(fileName);
	}

	@After
	public void tearDown() throws Exception {
		this.graphProcessor = null;
	}
	
	@Test
	public final void checkWordProcessorIsAdjacent() {
		// this method checks WordProcessor.isAdjacent method
		// and see whether it returns the expected boolean value
		assertEquals(WordProcessor.isAdjacent("wheat","heat"), true);
		assertEquals(WordProcessor.isAdjacent("heat","hat"), false);
		assertEquals(WordProcessor.isAdjacent("hot","hit"), true);
		assertEquals(WordProcessor.isAdjacent("hat","at"), true);
		assertEquals(WordProcessor.isAdjacent("at","bat"), false);
		assertEquals(WordProcessor.isAdjacent("bit","it"), true);
		assertEquals(WordProcessor.isAdjacent("it","kit"), false);
		assertEquals(WordProcessor.isAdjacent("seat","eat"), true);
	}
	
	@Test
	public final void checkPopulatGraphReturnValue() {
		// this test checks whether populateGraph method returns
		// number of vertices within the graph
		assertEquals(numOfVertices, new Integer(400));
	}
	
	@Test
	public final void checkTwoSameWordPathIsEmpty() {
		// this test checks whether passing two same words into 
		// getShortestPath method would return an empty arraylist
		List<String> list = new ArrayList<String>();
		graphProcessor.shortestPathPrecomputation();
		for (String elem: graphProcessor.getShortestPath("RAPINE", "RAPINE")) {
			list.add(elem);
		}
		assertEquals(list.isEmpty(), true);
	}
	
	@Test
	public final void checkTwoDifferentWordShortestPathCase1() {
		// this test checks whether the shortest path between two different words
		// is correct after passing into getShortestPath method
		List<String> list = new ArrayList<String>();
		graphProcessor.shortestPathPrecomputation();
		for (String elem: graphProcessor.getShortestPath("BELLIES", "JOLLIES")) {
			list.add(elem);
		}
		assertEquals(list.get(0),"BELLIES");
		assertEquals(list.get(1),"JELLIES");
		assertEquals(list.get(2),"JOLLIES");
	}
	
	@Test
	public final void checkTwoDifferentWordShortestPathCase2() {
		// this test checks whether the shortest path between two different words
		// is correct after passing into getShortestPath method
		List<String> list = new ArrayList<String>();
		graphProcessor.shortestPathPrecomputation();
		for (String elem: graphProcessor.getShortestPath("RAPINE", "HOMINY")) {
			list.add(elem);
		}
		assertEquals(list.get(0),"RAPINE");
		assertEquals(list.get(1),"RAVINE");
		assertEquals(list.get(2),"RAVING");
		assertEquals(list.get(3),"ROVING");
		assertEquals(list.get(4),"ROPING");
		assertEquals(list.get(5),"COPING");
		assertEquals(list.get(6),"COMING");
		assertEquals(list.get(7),"HOMING");
		assertEquals(list.get(8),"HOMINY");

	}
	
	@Test
	public final void checkTwoSameWordShortestDistance() {
		// this test checks whether the method getShortestDistance method
		// returns -1 after passing in two same words
		// select two same words randomly from word_list.txt
		graphProcessor.shortestPathPrecomputation();
		assertEquals(graphProcessor.getShortestDistance("BELLIES", "BELLIES"), new Integer(-1));
		assertEquals(graphProcessor.getShortestDistance("JELLIES", "JELLIES"), new Integer(-1));
		assertEquals(graphProcessor.getShortestDistance("JOLLIES", "JOLLIES"), new Integer(-1));
		assertEquals(graphProcessor.getShortestDistance("RAPINE", "RAPINE"), new Integer(-1));
		assertEquals(graphProcessor.getShortestDistance("ALIKE", "ALIKE"), new Integer(-1));
	}
