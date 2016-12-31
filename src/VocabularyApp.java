import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class VocabularyApp {
	public static void main(String[] args){
		final String FILE_EXTENSION = "wdl";
		final String FILE_PATH = "./";
		int numLists = 0;
		WordList myList = new WordList();
		String filename = "";
		Scanner scnr = new Scanner(System.in);
		
		System.out.println("Welcome to use VocabularyApp!");
		System.out.println("---------------------------------------------");
	
		numLists = fileList(FILE_EXTENSION, FILE_PATH);
		if (numLists == 0) {
			filename = createList(FILE_PATH, scnr);
		} else {
			System.out.println("\nChoose: O)pen existing list C)reat a new list");
			char choice = scnr.nextLine().toLowerCase().charAt(0);
			if (choice == 'o') {
				System.out.println("Which file to open");
				filename = scnr.nextLine();
				String[] str;
				String newLine;
				String word_name;
				String word_meaning;
				String[] word_sentences;
				try {
					FileInputStream list = new FileInputStream(filename);
					//myList = readFromFile(list);
					Scanner wordParser = new Scanner(list);
					while(wordParser.hasNextLine()) {
						newLine = wordParser.nextLine();
						str = newLine.split(";");
						word_sentences = new String[] {"", "", "", "", ""};
						word_name = str[0];
						word_meaning = str[1];
						for (int i = 0; i < str.length - 2; i++) {
							word_sentences[i] = str[i + 2];
						}
						Word newWord = new Word(word_name, word_meaning, word_sentences);
						myList.addWord(newWord);
					}
					wordParser.close();
					list.close();
				} catch (FileNotFoundException e) {
					System.out.println("Cannot open file");
				} catch (IOException e) {
					System.out.println("InputStream cannot be closed");
					e.printStackTrace();
				}	
			} else {
				filename = createList(FILE_PATH, scnr);
			}
		}
			
		//start working with words
		boolean quit = false; 
		while (!quit) {
			myList.printList();
			System.out.println("Choose: A)dd D)elete S)how W)rite Q)uit");
			char choice = scnr.nextLine().toLowerCase().charAt(0);
			switch (choice) {
			case 'a':
				add(myList, scnr);
				System.out.println("Adding new word.");
				pressEnter(scnr);
				break;
			case 'd':
				delete(myList, scnr);
				System.out.println("Deleting word.");
				pressEnter(scnr);
				break;
			case 's':
		      System.out.println("Show a word");
				break;
			case 'w': 
				System.out.println("writing to " + filename);
				pressEnter(scnr);
				write(myList, filename);
				break;
			case 'q':
				System.out.println("quiting program. Press enter to continue.");
				quit = true;
				break;
			}
		}
		
		System.out.println("---------------------------------------------");
		System.out.println("Thank you for using the app");
		
		}

		/*
		WordList GREList = new WordList();
		GREList.printList();
		add(GREList, scnr);
		GREList.printList();
		*/
	
	public static String createList(String file_path, Scanner scnr) {
		System.out.println("What's the name of the list? (end with .wdl)");
		String listName = scnr.nextLine();
		File dir = new File(file_path);
		dir.mkdir();
		File newFile = new File(dir, listName);
		try {
			newFile.createNewFile();
			return listName;
		} catch (IOException e) {
			System.out.println(listName + " cannot be be created");
			return null;
		}
	}

	public static void add(WordList wordlist, Scanner scnr) {
		
		String[] examples = new String[] {"", "", "", "", ""};
		System.out.println("Type in word: ");
		String name = scnr.nextLine();
		Word newWord = new Word(name);
		
		System.out.println("Explaination from Merriam-Webster's Dictionary: ");
		System.out.println(newWord.getMerriamWebsterMeaning());
		System.out.println("Type in meaning in one line: ");
		String meaning = scnr.nextLine();
		System.out.println("Type in an example sentence: ");
		examples[0] = scnr.nextLine();
		newWord.setMeaning(meaning);
		newWord.setExample(examples);
		
		wordlist.addWord(newWord);
	}
	
	public static void delete(WordList wordlist, Scanner scnr) {
		System.out.print("Type in word to delete: ");
		String name = scnr.nextLine();
		wordlist.delete(name);
	}
	
	public static void write(WordList wordlist, String filename) {
	   try {
	   	//try to create a FileOutputStream to the file
			FileOutputStream fileStringStream = new FileOutputStream(filename);
			PrintWriter output = new PrintWriter(fileStringStream);
			//print the names and address of each place to the file. 
			//Separate them with ";"
			for (int i = 0; i < wordlist.size(); i++) {
				output.print(wordlist.getWord(i).getName() + ";");
				output.print(wordlist.getWord(i).getMeaning() + ";");
				String[] sentences = wordlist.getWord(i).getExample();
				int j = 0;
				for (j = 0; j < sentences.length - 1; j++) {
					output.print(sentences[j] + ";");
				}
				output.println(sentences[j]);
			}
			output.close();
			//if user types in a invalid file, deal the exception
		} catch (FileNotFoundException e) {
			System.out.println("Unable to write to file: " + filename);
		}
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
	
	/**
	 * A method to ask user to press the enter key.
	 * @param scnr: a scanner instance for user input
	 */
	public static void pressEnter(Scanner scnr) {
		System.out.print("Press Enter to continue.");
	   scnr.nextLine(); //let user type in anything (or nothing) and press enter
	}
	
}
