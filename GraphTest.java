// Semester:         CS400 Spring 2018
// PROJECT:          P4_DictionaryGraph
// FILES:            Graph.java
//           GraphProcessor.java
//           GraphTest.java
//           WordProcessor.java
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
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Test class
 * 
 * Test the work of BalancedSearchTree
 */
public class TestSearchTree {
    SearchTreeADT<Integer> iTree = null;
	SearchTreeADT<String> strTree = null;
	String expected = null;
	String actual = null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		strTree = new BalancedSearchTree<String>();
		iTree = new BalancedSearchTree<Integer>();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	     
	}

	
	/**
     * Test the ismEpty() method on empty tree
     *  
     * Fails if ismEpty() returns false for a newly construct search tree
     */
	@Test
	public void test01_isEmpty_on_empty_tree() {
		expected = "true";
		actual = "" + strTree.isEmpty();
		if (! expected.equals(actual))
			fail("expected: "+expected+ " actual: "+actual);
	}
	/**
     * Test the inAscendingOrder() method on empty tree
     *  
     * Fails if ismEpty() does not return "" 
     * for a newly construct search tree
     */
	@Test
	public void test02_ascending_order_on_empty_tree() {
		expected = "";
		actual = strTree.inAscendingOrder();
		if (! expected.equals(actual))
			fail("expected: "+expected+ " actual: "+actual);
	}

	/** tests that the height of an empty tree is 0 */
	@Test
	public void test03_height_on_empty_tree() {
		expected = "0";
		actual = "" + strTree.height();
		if (! expected.equals(actual))
			fail("expected: "+expected+ " actual: "+actual);
	}

	@Test
	/** tests that the height of one insert item is 1 */
	public void test04_isEmpty_after_one_insert() {
		strTree.insert("1");
		expected = "false";
		actual = "" + strTree.isEmpty();
		if (! expected.equals(actual))
			fail("expected: "+expected+ " actual: "+actual);
	}

	@Test
	/** tests that the ascending order after inserting A  is "A," */
	public void test05_ascending_order_after_one_insert() {
		strTree.insert("A");
		expected = "A,";
		actual = strTree.inAscendingOrder();
		if (! expected.equals(actual))
			fail("expected: "+expected+ " actual: "+actual);
	}

	@Test
	/** tests that the height after inserting A is 1 */
	public void test06_height_after_one_insert() {
		strTree.insert("A");
		expected = "1";
		actual = "" + strTree.height();
		if (! expected.equals(actual))
			fail("expected: "+expected+ " actual: "+actual);
	}

	@Test
	/** tests that the height after inserting A and deleting it is 0 */
	public void test07_height_after_one_insert_and_delete() {
		strTree.insert("A");
		strTree.delete("A");
		expected = "0";
		actual = "" + strTree.height();
		if (! expected.equals(actual))
			fail("expected: "+expected+ " actual: "+actual);
	}
	@Test
	/** tests that the ascending order after inserting many items are ascending */
	public void test08_ascending_order_after_many_insert() {
	    String [] insertItem = {"A","B","C","G","E","F","D","H","I","J"}; 
	    
	    for (String u : insertItem) 
            strTree.insert(new String(u));
        expected = "A,B,C,D,E,F,G,H,I,J,";
        actual = strTree.inAscendingOrder();
        if (! expected.equals(actual))
            fail("expected: "+expected+ " actual: "+actual);
	}
	/** tests that the exception thrown by lookup method if null as input */
	@Test
	public void test09_lookup_after_null_insert() {
	    try {
            strTree.lookup(null); // Insert a null array
            fail("Cannot throw the exception when delete item is null");
        
       }
       catch(IllegalArgumentException e) { // Catch the IllegalArgumentException
       }    
	}

	/** tests that the exception thrown by delete method 
	 * if the input item not exist in the balance tree */
	@Test
	public void test10delete_not_existItem() {
	    try {
            strTree.delete(null); // Insert a null array
            fail("Cannot throw the exception when delete item is null");
        
       }
       catch(IllegalArgumentException e) { // Catch the IllegalArgumentException
       }  
	}

	/** tests that the exception thrown by insert method if null as input */
	@Test
	public void test11_insert_null_input() {
	    try {
            strTree.insert(null); // Insert a null array
            fail("Cannot throw the exception when insert item is null");
        
       }
       catch(IllegalArgumentException e) { // Catch the IllegalArgumentException
       }
	}

	/** tests that the exception thrown by delete method if the balance tree is null */
	@Test
	public void test12_delete_of_emptyTree() {
	    try {
            strTree.delete("A");
            //Test whether delete cause strTree change
            if(strTree.height()!=0)
                fail("Cannot matain the tree when delete item on an empty tree") ;
        
       }
       catch(Exception e) { // Catch the Exception can be caused by the delete
           fail("Cannot matain the tree when delete item on an empty tree");
       } 
	}

	
	/** tests that the duplicate insert will not change the status of the tree */
	@Test
	public void test13duplicate_insert_of_tree() {
	    try {
            strTree.insert("A");
            strTree.insert("A");// Insert a duplicate item
            fail("Cannot throw the exception when insert items are duplicate");
        
       }
       catch(DuplicateException e) { // Catch the DuplicateException
           
       }
    }
	
	/**Inserts a series of items into a tree, and then deletes several 
	 * of those items.
	 *  
	 *  Fails if the item is not properly returned.
	 */
	@Test
    public void test14delete_is_in_tree() {
	    String [] insertItem = {"A","B","C","G","E","F","D","H","I","J"}; 
        
        for (String u : insertItem) 
            strTree.insert(new String(u));
        strTree.delete("C");
        strTree.delete("E");
        strTree.delete("J");
        expected = "A,B,D,F,G,H,I,";
        actual = strTree.inAscendingOrder();
        if (! expected.equals(actual))
            fail("expected: "+expected+ " actual: "+actual); 
	}
	@Test
	/**Inserts a series of items into a tree, and then deletes item not in the 
     * tree.
     *  
     *  Fails if returns error or cause side-effect.
     */
	public void test15delete_is_not_in_tree() {
        String [] insertItem = {"A","B","C","D","E","F"}; 
        
        for (String u : insertItem) 
            strTree.insert(new String(u));
        strTree.delete("J");// Delete item not in the tree
        expected = "A,B,C,D,E,F,";
        actual = strTree.inAscendingOrder();
        if (! expected.equals(actual))
            fail("expected: "+expected+ " actual: "+actual); 
    }
	@Test
	/**Inserts a series of items into a tree, and then find item  in the tree.
     *  
     *  Fails if returns false
     */
	public void test16_lookup_finds_item_exsit() {
	    strTree.insert("A");
	    strTree.insert("D");
	    strTree.insert("E");
	    strTree.insert("H");
	    expected = "true";
        actual = "" + strTree.lookup("H");
        if (! expected.equals(actual))
            fail("expected: "+expected+ " actual: "+actual);

	}
	@Test
	/**Inserts a series of items into a tree, and then find item not in the tree.
     *  
     *  Fails if returns true
     */
	public void test17_lookup_finds_item_not_exsit() {
        strTree.insert("A");
        strTree.insert("C");
        strTree.insert("D");
        expected = "false";
        actual = "" + strTree.lookup("B");
        if (! expected.equals(actual))
            fail("expected: "+expected+ " actual: "+actual);

    }
	@Test
	/**Find item in an empty tree.
     *  
     *  Fails if returns true
     */
	public void test18_lookup_finds_item_in_emptyTree() {
        expected = "false";
        actual = "" + strTree.lookup("A");
        if (! expected.equals(actual))
            fail("expected: "+expected+ " actual: "+actual);

    }
	
	@Test
    /**Check the balance of the tree after many items inserted.
     *  
     *  Fails if the height of the tree not between 0 an a*log2N
     */
	public void  test19_keep_balance_after_many_inserts() {
	    String [] insertItem = {"A","B","C","D","E","F","G","H","I","K"}; 
        double n= 10;
        for (String u : insertItem) 
            strTree.insert(new String(u));
        int height = strTree.height();
        if(height>2*Math.log(n)/Math.log(2) && height < 0) {
            fail("The tree is not balanced");
        }
	}
	
	@Test
	/**Check the ascending order after large number of Integers are inserted.
     *  
     *  Fails if the result not in ascending order
     */
	public void  test20_ascending_after_large_number_of_Integers() {
	    Integer [] unsorted = new Integer[50]; 
        //Generate an unsorted array with random numbers
        for (int i = 0; i < 50; i++) {
            unsorted[i]= new Integer(i+1) ;
        }
        Integer[] sorted = unsorted.clone(); 
        Arrays.sort(sorted); // Sort the unsorted array
        
        for (int u : unsorted) 
            iTree.insert(new Integer(u));
        //Compare the actual item output and the expected item   
        String expected = "";
        for (int k = 0; k <= sorted.length-1; k++) { 
                expected += Integer.toString(sorted[k])+","; }
                String actual = iTree.inAscendingOrder(); 
                if (expected.equals(actual)==false) 
                    fail("expected: "+expected+ " actual: "+actual);
        
    }
	
	@Test
	/**Check the balance of the tree after many items are deleted.
     *  
     *  Fails if the height of the tree not between 0 an a*log2N
     */
	public void  test21_keep_balance_after_delete_many_items() {
        String [] insertItem = {"A","B","C","D","E","F","G","H","I","K"}; 
        double n= 10;
        for (String u : insertItem) 
            strTree.insert(new String(u));
        strTree.delete("C");
        strTree.delete("G");
        strTree.delete("B");
        int height = strTree.height();
        //Compare the height of the tree and the limit
        if(height>2*Math.log(n)/Math.log(2) && height < 0) { 
            fail("The tree is not balanced");
        }
    }
}

