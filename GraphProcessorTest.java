////////////////////////////////////////////////////////////////////////////
// Semester:         CS400 Spring 2018
// PROJECT:          P4_DictionaryGraph
// FILES:            Graph.java
// 		     GraphProcessor.java
//		     GraphTest.java
// 		     WordProcessor.java
//		     GraphProcessorTest.java
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
	private Integer Verticesnum;

	@Before
	public void setUp() throws Exception {
		this.graphProcessor = new GraphProcessor();
		this.fileName = "word_list.txt";
		Verticesnum = graphProcessor.populateGraph(fileName);
	}

	@After
	public void tearDown() throws Exception {
		this.graphProcessor = null;
	}
	
	@Test
	public final void checkWordProcessor() {
		// Checks WordProcessor method
		// and see whether it returns the expected boolean value
		assertEquals(WordProcessor.isAdjacent("hate","hates"), true);
		assertEquals(WordProcessor.isAdjacent("eat","hat"), false);
		assertEquals(WordProcessor.isAdjacent("hot","hit"), true);
		assertEquals(WordProcessor.isAdjacent("hate","ate"), true);
		assertEquals(WordProcessor.isAdjacent("at","bat"), false);
		assertEquals(WordProcessor.isAdjacent("bit","it"), true);
		
	}
	
	@Test
	public final void checkPopulatGraphReturnValue() {
		// checks number of vertices within the graph
		assertEquals(numOfVertices, new Integer());
	}
	
	@Test
	public final void checkTwoSameWordPathIsEmpty() {
		// checks whether passing two identical words into 
		// getShortestPath method returns an empty arraylist
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
	public final void checkTwoSameWordShortestDistance() {
		// checks whether the method getShortestDistance method
		// returns -1 after using two same words
		graphProcessor.shortestPathPrecomputation();
		assertEquals(graphProcessor.getShortestDistance("DELLIES", "DELLIES"), new Integer(-1));
		assertEquals(graphProcessor.getShortestDistance("FELLIES", "FELLIES"), new Integer(-1));
		assertEquals(graphProcessor.getShortestDistance("JOLLIES", "JOLLIES"), new Integer(-1));
		assertEquals(graphProcessor.getShortestDistance("SUPINE", "SUPINE"), new Integer(-1));
		assertEquals(graphProcessor.getShortestDistance("HIIKE", "HIKE"), new Integer(-1));
	}
}
