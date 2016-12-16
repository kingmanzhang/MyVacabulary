import java.util.Scanner;

public class VocabularyApp {
	public static void main(String[] args){
		Scanner scnr = new Scanner(System.in);
		WordList GREList = new WordList();
		GREList.printList();
		add(GREList, scnr);
		GREList.printList();
	}

	public static void add(WordList wordlist, Scanner scnr) {
		
		String[] examples = new String[5];
		System.out.println("type in word: ");
		String name = scnr.nextLine();
		System.out.println("type in meaning in one line: ");
		String meaning = scnr.nextLine();
		System.out.println("type in an example sentence: ");
		examples[0] = scnr.nextLine();
		
		Word newWord = new Word(name, meaning, examples);
		
		wordlist.addWord(newWord);
	}
	
}
