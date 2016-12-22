import java.util.ArrayList;

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
			}
		}
	}
	
	public void printList() {
		for (Word e: wordList) {
			System.out.println(e.getName());
			System.out.println(e.getMeaning());
			e.printExample();
		}
	}

}
