import java.util.ArrayList;
import java.util.Collections;
/**
 * A WordList class to manager word lists. A user should be able to add, delete or show
 * words. 
 * @author Xingmin Zhang
 *
 */
public class WordList implements Cloneable {
	
	private ArrayList<Word> wordList; 
	
	public WordList() {
		
		this.wordList = new ArrayList<>();
	}
	
	public void addWord(Word word) {
		wordList.add(word);
	}
	
	public Word getWord(int index) {
		return wordList.get(index);
	}
	
	public void delete(int index) {
		wordList.remove(index);
	}
	
	/**
	 * A method to delete a word from a list
	 * @param word
	 * @throws Exception
	 */
	public void delete(String word) throws Exception{
		boolean wordFound = false;
		for (Word e: wordList) {
			if (e.getName().equals(word)) {
				wordList.remove(e);
				wordFound = true;
				break;
			}
		}
		if (!wordFound) {
			throw new Exception();
		}
	}
	
	/**
	 * A method to determine whether the list contains a word.
	 * The method only compares the name field, without considering meaning and example sentences. 
	 * @param newWord
	 * @return
	 */
	public boolean contains(String newWord) {
		boolean contains = false;
		for (Word word : wordList) {
			if(word.getName().equals(newWord)) {
				contains = true;
				break;
			}
		}
		return contains;
	}
	
	public int size() {
		return wordList.size();
	}
	
	/**
	 * This method determines whether one word list is changed
	 * @param otherWordList
	 * @return
	 */
	
	/*
	public boolean isChanged(WordList otherWordList) {
		boolean equalList = false;
		if (this == null && otherWordList == null) {
			equalList = true;
		} else if (this == null && otherWordList != null 
				|| this != null && otherWordList == null 
				|| this.size() != otherWordList.size()) {
			equalList = false;
		} else {
			this.sort();
			otherWordList.sort();
			for (int i = 0; i < this.size(); i++) {
				if (!this.getWord(i).equals(otherWordList.getWord(i))) {
					break;
				} else {
					equalList = true;
				}
			}
		}
		return !equalList;
	}
	*/
	public boolean isChanged(WordList otherWordList) {
		boolean equalList = false;
		if (this.size() == 0 && otherWordList.size() == 0) {
			equalList = true;
		} else if (this.size() != otherWordList.size()) {
			equalList = false;
		} else {
			this.sort();
			otherWordList.sort();
			for (int i = 0; i < this.size(); i++) {
				if (!this.getWord(i).equals(otherWordList.getWord(i))) {
					break;
				} else {
					equalList = true;
				}
			}
		}
		return !equalList;
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
			this.sort();
			int i = 1;
			for (Word e: wordList) {
				System.out.println(i + ":" +  e.getName());
				System.out.println(e.getMeaning());
				e.printExample();
				System.out.println("");
				i++;
			}
		}
		System.out.println("---------------------------------------------\n");
	
	}
	
	/**
	 * A method to sort places in the place list. It relies on the compareTo 
	 * method in Place class.
	 */
	public void sort() {
		Collections.sort(wordList);
	}
	
	
	/**
	 * A method to clone a word list.
	 * The cloned copy has its own copy of all words. 
	 */
	public WordList clone() throws CloneNotSupportedException {
		WordList copy = new WordList();
		for (Word word : wordList) {
			copy.addWord(word.clone());
		}
		return copy;
	}

}
