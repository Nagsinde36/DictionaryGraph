////////////////////////////////////////////////////////////////////////////
// Semester:         CS400 Spring 2018
// PROJECT:          P4_DictionaryGraph
// FILES:            Graph.java
// 		     GraphProcessor.java
//		     GraphTest.java
// 		     WordProcessor.java
//		     GraphProcessorTest.java
// USER:             rshih2@wisc.edu
//                   xshao36@wisc.edu
//                   sinde@wisc.edu 
//                   escott7@wisc.edu
//                 
//
// Instructor:       Deb Deppeler (deppeler@cs.wisc.edu)
// Bugs:             no known bugs
//
// 2018 Apr 16, 2018 WordProcessor.java 
////////////////////////////80 columns wide //////////////////////////////////
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This class contains some utility helper methods
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class WordProcessor {
	
	/**
	 * Gets a Stream of words from the filepath.
	 * 
	 * The Stream should only contain trimmed, non-empty and UPPERCASE words.
	 * 
	 * @see <a href="http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html">java8 stream blog</a>
	 * 
	 * @param filepath file path to the dictionary file
	 * @return Stream<String> stream of words read from the filepath
	 * @throws IOException exception resulting from accessing the filepath
	 */
	public static Stream<String> getWordStream(String filepath) throws IOException {
		/**
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html">java.nio.file.Files</a>
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Paths.html">java.nio.file.Paths</a>
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html">java.nio.file.Path</a>
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html">java.util.stream.Stream</a>
		 * 
		 * class Files has a method lines() which accepts an interface Path object and 
		 * produces a Stream<String> object via which one can read all the lines from a file as a Stream.
		 * 
		 * class Paths has a method get() which accepts one or more strings (filepath),  
		 * joins them if required and produces a interface Path object
		 * 
		 * Combining these two methods:
		 *     Files.lines(Paths.get(<string filepath>))
		 *     produces
		 *         a Stream of lines read from the filepath
		 * 
		 * Once this Stream of lines is available, you can use the powerful operations available for Stream objects to combine 
		 * multiple pre-processing operations of each line in a single statement.
		 * 
		 * Few of these features:
		 * 		1. map( )      [changes a line to the result of the applied function. Mathematically, line = operation(line)]
		 * 			-  trim all the lines
		 * 			-  convert all the lines to UpperCase
		 * 			-  example takes each of the lines one by one and apply the function toString on them as line.toString() 
		 * 			   and returns the Stream:
		 * 			        streamOfLines = streamOfLines.map(String::toString) 
		 * 
		 * 		2. filter( )   [keeps only lines which satisfy the provided condition]  
		 *      	-  can be used to only keep non-empty lines and drop empty lines
		 *      	-  example below removes all the lines from the Stream which do not equal the string "apple" 
		 *                 and returns the Stream:
		 *      			streamOfLines = streamOfLines.filter(x -> x != "apple");
		 *      			 
		 * 		3. collect( )  [collects all the lines into a java.util.List object]
		 * 			-  can be used in the function which will invoke this method to convert Stream<String> of lines to List<String> of lines
		 * 			-  example below collects all the elements of the Stream into a List and returns the List:
		 * 				List<String> listOfLines = streamOfLines.collect(Collectors::toList); 
		 * 
		 * Note: since map and filter return the updated Stream objects, they can chained together as:
		 * 		streamOfLines.map(...).filter(a -> ...).map(...) and so on
		 */
		Stream <String> wordStream = Files.lines(Paths.get(filepath));
		wordStream = wordStream.map(String::toString);
		wordStream = wordStream.filter(x -> x != null && !x.equals(""));
		wordStream = wordStream.map(String::toUpperCase);
		return wordStream;
	}
	
	/**
	 * Adjacency between word1 and word2 is defined by:
	 * if the difference between word1 and word2 is of
	 * 	1 char replacement
	 *  1 char addition
	 *  1 char deletion
	 * then 
	 *  word1 and word2 are adjacent
	 * else
	 *  word1 and word2 are not adjacent
	 *  
	 * Note: if word1 is equal to word2, they are not adjacent
	 * 
	 * @param word1 first word
	 * @param word2 second word
	 * @return true if word1 and word2 are adjacent else false
	 */
	 public static boolean isAdjacent(String word1, String word2) {
		    if(word1.length() == word2.length()) 
		 	{
		 		/*since the words are equal in length, they
		 	        can only be adjacent by substitution*/
	                        int subChar = 0;
	                        for(int i = 0; i < word1.length(); i++)
	                        {
	                		if(word1.charAt(i) != word2.charAt(i))
	                		{
	                			subChar++;
	                		}
	            		}
	            		//subChar must be exactly one to return true, meaning one char replacement
	            		if (subChar == 1)
	            		{
	            			return true;
	            		}
	            		else return false;
	        	}
		 	//returns false when the length difference is more than 1
	        	if(Math.abs(word1.length()-word2.length()) > 1)
	       		{
	            		return false;
	        	}
	        	//when words length differ in length, must be add or delete
	        	String bigWord;
		 	String smallWord;
	        	if(word1.length() > word2.length())
	        	{
	            		bigWord = word1;
	            		smallWord = word2;
	        	}
	        	else 
	        	{
	            		bigWord = word2;
	            		smallWord = word1;
	        	}
	        
	        	//if the addition is at the end, return true immediately
	        	if(bigWord.substring(0, bigWord.length()-1).equals(smallWord))
	        	{
	            		return true;
	        	}
	        	//compare two words when difference not at end
	        	boolean indicator = false;
	        	int b = 0;
	        	for(int s = 0; s <smallWord.length(); s++)
	        	{
	            		if(bigWord.charAt(b) != smallWord.charAt(s))
	            		{
	                		if(indicator)
	                		{
	                    			return false;
	                		} 
	                		else
	                		{
	                    			indicator = true;
	                    			s--;
	                		}
	            		}
	            		b++;
	        	}
	        	return true;
	    	}
	    
	}
