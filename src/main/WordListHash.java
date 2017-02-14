package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class WordListHash implements Cloneable {
	
	private HashMap<String, Word> wordList;
	
	public WordListHash() {
		
		this.wordList = new HashMap<>();
		
	}
	
	public void addWord(Word word) throws WordDuplicateException {
		if(wordList.containsKey(word.getName())) {
			throw new WordDuplicateException();
		}
		wordList.put(word.getName(), word);
	}
	
	public Word getWord(String str) {
		return wordList.get(str);
	}
	
	public Set<String> getList() {
		return wordList.keySet();
	}
	
	
	/**
	 * A method to delete a word from a list
	 * @param word
	 * @throws Exception
	 */
	public void delete(String word) {
		
		wordList.remove(word);
		
	}
	
	/**
	 * A method to determine whether the list contains a word.
	 * The method only compares the name field, without considering meaning and example sentences. 
	 * @param newWord
	 * @return
	 */
	public boolean contains(String word) {
		
		return wordList.containsKey(word);
		
	}
	
	public int size() {
		return wordList.size();
	}
	
	
	public boolean isChanged(WordListHash otherWordList) {
		
		
		if (this.size() == 0 && otherWordList.size() == 0) {
			return false;
		} 
		
		if (this.size() != otherWordList.size()) {
			return true;
		}
		
		if(! (otherWordList instanceof WordListHash)) {
			return true;
		} else {
			Set<String> originalKey = getList();	
			//WordListHash<String, Word> temp = (WordListHash<String, Word>) otherWordList;
			Set<String> newKey = otherWordList.getList();
			Iterator<String> oriItr = originalKey.iterator();
			Iterator<String> newItr = newKey.iterator();
			while(oriItr.hasNext() && newItr.hasNext()) {
				String oriNext = oriItr.next();
				String newNext = newItr.next();
				if(!oriNext.equals(newNext)) {
					return true;
				} else {
					if(!getWord(oriNext).equals(otherWordList.getWord(newNext))) {
						return true;
					}
				}
			}
			return false;
		}
			
		
		
	}
	
	
	/**
	 * A method to print a list of words
	 */
	public void printList() {
		System.out.println("\nWords in current word list");
		System.out.println("\n---------------------------------------------");
		if(wordList.size() == 0) {
			System.out.println("Empty word list");
		} else {
			Set<String> words = wordList.keySet();
			ArrayList<String> sorted = new ArrayList<>();
			sorted.addAll(words);
			Collections.sort(sorted);
			Iterator<String> itr = sorted.iterator();
			int i = 1;
			while(itr.hasNext()) {
				Word nextWord = wordList.get(itr.next());
				System.out.println(i + ":" +  nextWord.getName());
				System.out.println(nextWord.getMeaning());
				nextWord.printExample();
				System.out.println("");
				i++;
			}
			
			
		}
		System.out.println("---------------------------------------------\n");
	}
	

	/**
	 * A method to clone a word list.
	 * The cloned copy has its own copy of all words. 
	 */
	public WordListHash clone() throws CloneNotSupportedException {
		WordListHash copy = new WordListHash();
		Set<String> words = wordList.keySet();
		Iterator<String> itr = words.iterator();
		while(itr.hasNext()) {			
			String nextWord = itr.next();
			try {
				copy.addWord(getWord(nextWord).clone());
			} catch (WordDuplicateException e) {
				// will not happen
				e.printStackTrace();
			}
		}
		return copy;
	}

}
