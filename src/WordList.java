import java.util.ArrayList;
/**
 * A WordList class to manager word lists. A user should be able to add, delete or show
 * words. 
 * @author Xingmin Zhang
 *
 */
public class WordList {
	
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
	
	public void delete(String word) {
		for (Word e: wordList) {
			if (e.getName().equals(word)) {
				wordList.remove(e);
				break;
			}
		}
	}
	
	public int size() {
		return wordList.size();
	}
	
	public void printList() {
		System.out.println("Words in current word list");
		System.out.println("\n---------------------------------------------");
		if(wordList.size() == 0) {
			System.out.println("Empty word list");
		} else {
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

}
