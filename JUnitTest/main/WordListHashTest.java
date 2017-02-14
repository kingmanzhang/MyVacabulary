package main;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

}
