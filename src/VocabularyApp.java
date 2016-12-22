import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class VocabularyApp {
	public static void main(String[] args){
		final String FILE_EXTENSION = "wdl";
		final String FILE_PATH = "./";
		int numLists = 0;
		Scanner scnr = new Scanner(System.in);
		System.out.println("Welcome to use VocabularyApp!\n\n");
	
		numLists = fileList(FILE_EXTENSION, FILE_PATH);
		if (numLists == 0) {
			createList(FILE_PATH, scnr);
		} else {
			System.out.println("\nChoose: O)pen existing list C)reat a new list");
			char choice = scnr.nextLine().toLowerCase().charAt(0);
			if (choice == 'o') {
				System.out.println("Which file to open");
				String fileToOpen = scnr.nextLine();
				try {
					FileInputStream list = new FileInputStream(fileToOpen);
					//TODO
					//parseWords(list);
				} catch (FileNotFoundException e) {
					System.out.println("Cannot open file");
				}
				
			} else {
				createList(FILE_PATH, scnr);
			}
		}

		/*
		WordList GREList = new WordList();
		GREList.printList();
		add(GREList, scnr);
		GREList.printList();
		*/
	}
	
	public static void createList(String file_path, Scanner scnr) {
		System.out.println("What's the name of the list? (end with .wdl)");
		String listName = scnr.nextLine();
		File dir = new File(file_path);
		dir.mkdir();
		File newFile = new File(dir, listName);
		try {
			newFile.createNewFile();
		} catch (IOException e) {
			System.out.println(listName + " cannot be be created");
		}
	}

	public static void add(WordList wordlist, Scanner scnr) {
		
		String[] examples = new String[5];
		System.out.println("Type in word: ");
		String name = scnr.nextLine();
		System.out.println("Type in meaning in one line: ");
		String meaning = scnr.nextLine();
		System.out.println("Type in an example sentence: ");
		examples[0] = scnr.nextLine();
		
		Word newWord = new Word(name, meaning, examples);
		
		wordlist.addWord(newWord);
	}
	
	public static int fileList(String file_extension, String file_path) {
		File files = new File("./");
		System.out.println("My Word Lists: ");
		int count = 0;
		for (File file: files.listFiles()) { //print out all files
			if (file.getName().endsWith(file_extension)) {
				System.out.println("    " + file.getName());
				count++;
			}
		}
		if (count == 0) {
			System.out.println("No word list. Create your first one!");
		}
		return count;
	}
	
}
