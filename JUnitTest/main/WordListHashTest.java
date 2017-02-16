package main;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.PriorityQueue;


import org.junit.Test;

public class WordListHashTest {

	

	@Test
	public void testAddWord() {
		WordListHash newlist = new WordListHash();
		assertEquals(0, newlist.size());
		try{
			Word newWord = new Word("vocabulary");
			newlist.addWord(newWord);
			assertEquals(1, newlist.size());
			newWord = new Word("implementation");
			newlist.addWord(newWord);
			assertEquals(2, newlist.size());
		} catch (WordDuplicateException e) {
			System.out.println("word duplication! Add a different one");
		}
	}


	@Test
	public void testDelete() {
		WordListHash newlist = new WordListHash();
		try{
			Word newWord = new Word("vocabulary");
			newlist.addWord(newWord);
			newWord = new Word("implementation");
			newlist.addWord(newWord);
			assertEquals(2, newlist.size());
		} catch (WordDuplicateException e) {
			System.out.println("word duplication! Add a different one");
		}
		newlist.delete("implementation");
		assertEquals(1, newlist.size());
		
	
	}

	@Test
	public void testContains() {
		WordListHash newlist = new WordListHash();
		try{
			Word newWord = new Word("vocabulary");
			newlist.addWord(newWord);
			assertEquals(false, newlist.contains("implementation"));
			newWord = new Word("implementation");
			newlist.addWord(newWord);
			assertEquals(true, newlist.contains("implementation"));
		} catch (WordDuplicateException e) {
			System.out.println("word duplication! Add a different one");
		}
	}



	@Test
	public void testIsChanged() throws CloneNotSupportedException {
		WordListHash newlist = new WordListHash();
		try{
			Word newWord = new Word("vocabulary");
			newlist.addWord(newWord);
			assertEquals(false, newlist.contains("implementation"));
			newWord = new Word("implementation");
			newlist.addWord( newWord);
			assertEquals(true, newlist.contains("implementation"));
		} catch (WordDuplicateException e) {
			System.out.println("word duplication! Add a different one");
		}
		
		WordListHash cloned = newlist.clone();
		assertEquals(false, newlist.isChanged(cloned));
		newlist.delete("implementation");
		try{
			Word newWord = new Word("change");
			newlist.addWord(newWord);
		} catch (WordDuplicateException e) {
			System.out.println("word duplication! Add a different one");
		}
		assertEquals(true, newlist.isChanged(cloned));
		
	}

	@Test
	public void testPrintList() {
		WordListHash newlist = new WordListHash();
		try{
			Word newWord = new Word("vocabulary");
			newlist.addWord(newWord);
			assertEquals(false, newlist.contains("implementation"));
			newWord = new Word("implementation");
			newlist.addWord(newWord);
			assertEquals(true, newlist.contains("implementation"));
		} catch (WordDuplicateException e) {
			System.out.println("word duplication! Add a different one");
		}
		newlist.printList();
	}
	
	@Test
	public void testPriorityWords() {
		WordListHash newlist = new WordListHash();
		try{
			Word newWord = new Word("wisc");
			newWord.adjPriority(50);
			newlist.addWord(newWord);
			
			newWord = new Word("minn");
			newWord.adjPriority(60);
			newlist.addWord(newWord);
			
			newWord = new Word("ny");
			newWord.adjPriority(80);
			newlist.addWord(newWord);
			
			newWord = new Word("oi");
			newWord.adjPriority(-20);
			newlist.addWord(newWord);
			
			newWord = new Word("ct");
			newWord.adjPriority(-40);
			newlist.addWord(newWord);
			
			newWord = new Word("tx");
			newWord.adjPriority(-90);
			newlist.addWord(newWord);
			
		} catch (WordDuplicateException e) {
			System.out.println("word duplication! Add a different one");
		}
		PriorityQueue<Word> wordsInQueue = newlist.priorityWord();
		assertEquals(6, newlist.size());
		assertEquals("ny", wordsInQueue.remove().getName());
		assertEquals("minn", wordsInQueue.remove().getName());
		assertEquals("wisc", wordsInQueue.remove().getName());
		
	}
	
	@Test
	public void testTopWords() {
		WordListHash newlist = new WordListHash();
		try{
			Word newWord = new Word("wisc");
			newWord.adjPriority(50);
			newlist.addWord(newWord);
			
			newWord = new Word("minn");
			newWord.adjPriority(60);
			newlist.addWord(newWord);
			
			newWord = new Word("ny");
			newWord.adjPriority(80);
			newlist.addWord(newWord);
			
			newWord = new Word("oi");
			newWord.adjPriority(-20);
			newlist.addWord(newWord);
			
			newWord = new Word("ct");
			newWord.adjPriority(-40);
			newlist.addWord(newWord);
			
			newWord = new Word("tx");
			newWord.adjPriority(-90);
			newlist.addWord(newWord);
			
		} catch (WordDuplicateException e) {
			System.out.println("word duplication! Add a different one");
		}
		LinkedList<Word> wordsInQueue = newlist.topWords(4);
		assertEquals("ny", wordsInQueue.remove().getName());
		assertEquals("minn", wordsInQueue.remove().getName());
		assertEquals("wisc", wordsInQueue.remove().getName());
		
	}

}
