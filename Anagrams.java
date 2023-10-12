import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * @author Aaren Patel
 * I pledge my honor that I have abided by the Stevens Honor System
 * CS 284 B - HW 5
 * 4/27/2023
 */
public class Anagrams {
	
	final Integer[] primes =  
			{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 
			31, 37, 41, 43, 47, 53, 59, 61, 67, 
			71, 73, 79, 83, 89, 97, 101};

    final Character[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                                's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	Map<Character,Integer> letterTable;
	Map<Long,ArrayList<String>> anagramTable;

	
	public void buildLetterTable() {
		/* Sets up the letterTable HashMap assigning letters to their respective prime */
	    letterTable = new HashMap<Character, Integer>();
        for (int i = 0; i < primes.length; i++)
            letterTable.put(letters[i], primes[i]);
    }

	Anagrams() {
		/* Constructor for Anagrams */
		buildLetterTable();
		anagramTable = new HashMap<Long,ArrayList<String>>();
	}

	
	public void addWord(String s) {
		/* Adds input word s to the anagramTable HashMap */
		long hashCode = myHashCode(s);
        
        if (anagramTable.containsKey(hashCode))
        	anagramTable.get(hashCode).add(s);
        else {
        	ArrayList<String> list = new ArrayList<>();
        	list.add(s);
        	anagramTable.put(hashCode, list);
        }
	}
	
	public long myHashCode(String s) {
		/* Generates the HashCode for the input String s */
	    if (s == "")
	        throw new IllegalArgumentException("s cannot be empty");
        long hashCode = 1;
        for (int i = 0; i < s.length(); i++)
            hashCode *= letterTable.get(s.charAt(i));
        return hashCode;
	}
	
	public void processFile(String s) throws IOException {
		/* Processes the input file and sends each word to addWord*/
		FileInputStream fstream = new FileInputStream(s);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		while ((strLine = br.readLine()) != null)   {
		  this.addWord(strLine);
		}
		br.close();
	}
	
	public ArrayList<Map.Entry<Long,ArrayList<String>>> getMaxEntries() {
		/* Returns the list of the longest set of anagrams */
		int max = 0;
		ArrayList<Map.Entry<Long,ArrayList<String>>> maxList = new ArrayList<>();
		for (Map.Entry<Long,ArrayList<String>> key : anagramTable.entrySet()) {
			ArrayList<String> list = key.getValue();
			if (list.size() > max) {
				max = list.size();
				maxList.clear();
				maxList.add(key);
			}
			else if (list.size() == max)
				maxList.add(key);
		}
		return maxList;
	}
	
	public static void main(String[] args) {
		Anagrams a = new Anagrams();

		final long startTime = System.nanoTime();    
		try {
			a.processFile("words_alpha.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<Map.Entry<Long,ArrayList<String>>> maxEntries = a.getMaxEntries();
		final long estimatedTime = System.nanoTime() - startTime;
		final double seconds = ((double) estimatedTime/1000000000);
		System.out.println("Time: "+ seconds);
		
		System.out.println("List of max anagrams: "+ maxEntries);
	}
}
