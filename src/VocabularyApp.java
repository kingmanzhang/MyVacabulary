import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class VocabularyApp {
	public static void main(String[] args){
		final String FILE_EXTENSION = "wdl";
		final String FILE_PATH = "./";
		int numLists = 0;
		boolean validInput = false;
		WordList myList = new WordList();
		String filename = "";
		Scanner scnr = new Scanner(System.in);
		
		System.out.println("Welcome to use VocabularyApp!");
		System.out.println("---------------------------------------------");
		
	   //first ask user to operate on files 
		numLists = fileList(FILE_EXTENSION, FILE_PATH);
		if (numLists == 0) { //if there is no word list, ask user to create one
			filename = createList(FILE_PATH, scnr);
		} else { //otherwise, ask user to open a list or create a new one, or delete one
			char choice = ' ';
			do {
				System.out.println("\nChoose: O)pen a list C)reat a new list D)elete a list ");
				try {		
					String newline = scnr.nextLine();
					if(newline.isEmpty()) {
						throw new IOException();
					}
					
					choice = newline.toLowerCase().charAt(0);
					if(choice == 'o' || choice == 'c' || choice == 'd') {
						validInput = true;						
						switch (choice) {
						case 'o':			
							openfile(scnr, myList);						
							break;							
							
						case 'c': 
							filename = createList(FILE_PATH, scnr);
							break;
						case 'd':
							deletefile(scnr);
							break;							
						}						
					} else {
						System.out.println("cannot recognize command");
					}
				} catch (IOException e) {
					System.out.println("Invalid input.");
					validInput = false;
				}
			} while (!validInput || choice == 'd');
		}
			
		//keep a clone of current word list
		WordList originalList = null;
		try {
			originalList = myList.clone();
		} catch (CloneNotSupportedException e) {//impossible
			e.printStackTrace();
		}
		
		//start working with a word list
		//can put this loop inside the upper loop
		boolean quit = false; 
		while (!quit) {
			myList.printList();
			validInput = false;
			char choice = ' ';
			do {
				System.out.println("Choose: A)dd D)elete S)how W)rite Q)uit");
				if (!scnr.hasNextInt()) {
					choice = scnr.nextLine().toLowerCase().charAt(0);
					if (choice == 'a' || choice == 'd' || choice == 's' 
							|| choice == 'w' || choice == 'q') {
						validInput = true;
					} else {
						System.out.println("Input not valid. Type again.");
					}
				} else {
					scnr.nextLine();
					System.out.println("Input should be a character. Type again.");
				}
			} while (!validInput);
			
			switch (choice) {
			case 'a':
				add(myList, scnr);
				pressEnter(scnr);
				break;
			case 'd':
				delete(myList, scnr);
				System.out.println("Deleting word.");
				pressEnter(scnr);
				break;
			case 's':
		      show(myList, scnr);
		      pressEnter(scnr);
				break;
			case 'w': 
				System.out.println("writing to " + filename);
				pressEnter(scnr);
				write(myList, filename);
				break;
			case 'q':
				quit(originalList, myList, scnr, filename);
				System.out.println("quiting program.");
				pressEnter(scnr);
				quit = true;
				break;
			}
		}
		System.out.println("\n---------------------------------------------");
		System.out.println("Thank you for using the app");
		}

	/**
	 * A helper method to open a file 
	 * @param scnr: main scanner
	 * @param myList: import words in the file to this list
	 */
	public static void openfile(Scanner scnr, WordList myList) {
		
		boolean fileOpened = false;
		String filename = "";
		do {
			System.out.println("Which file to open");
			
			try {
				filename = scnr.nextLine();
				myList = openList(filename);
				fileOpened = true;
			} catch (FileNotFoundException e) {
				fileOpened = false;
				System.out.println("File is not found. Type again.");
			} catch (IOException e) {
				System.out.println("Invalid input. Type again.");
			}
		} while (!fileOpened);
		
	}
	
	/**
	 * A helper method to delete a file
	 * @param scnr
	 */
	public static void deletefile(Scanner scnr) {
		
		String filename = "";
		System.out.println("Which file to delete");
		filename = scnr.nextLine();
		File file = new File(filename);
		if(file.delete()){			
			System.out.println(file.getName() + " is deleted!");
		}else{
			System.out.println("Delete operation is failed.");
		}
		
	}

	/**
	 * A method to create a file for a new word list. 
	 * @param file_path
	 * @param scnr
	 * @return the name of the file. It will be used when saving the list to file. 
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
	
	/**
	 * A method to open an existing file.
	 * @param filename: name of file to open. 
	 * @return a word list parsed from the file. 
	 * @throws IOException
	 */
	public static WordList openList(String filename) throws IOException{
		WordList myList = new WordList();
		String[] str;
		String newLine;
		String word_name;
		String word_meaning;
		String[] word_sentences;
		
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
		
		return myList;
	}

	
	/**
	 * A method to add new word to a word list. 
	 * @param wordlist
	 * @param scnr
	 */
	public static void add(WordList wordlist, Scanner scnr) {
		String[] examples = new String[] {"", "", "", "", ""};
		System.out.println("Type in word: ");
		String name = scnr.nextLine();
		if (wordlist.contains(name)) {
			System.out.println("\"" + name + "\" already in list. Update? Y/N" );
		} else {
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
			System.out.println("Adding new word.");
		}
	}
	
	
	/**
	 * A method to delete a word from a word list.
	 * @param wordlist
	 * @param scnr
	 */
	public static void delete(WordList wordlist, Scanner scnr) {
		boolean wordDeleted = false;
		do {
			System.out.print("Type in word or index to delete: ");
		   if(scnr.hasNextInt()) {
		   	int index = scnr.nextInt();
		   	if (index > 0 && index <= wordlist.size()) {
		   		wordlist.delete(index);
		   		wordDeleted = true;
		   	} else {
		   		System.out.println("Input index is out of range. Enter a number between 1 and " + wordlist.size());
		   	}
		   } else {
		   	String name = scnr.nextLine();
		   	try {
		   		wordlist.delete(name);
		   		wordDeleted = true;
		   	} catch (Exception e) {
		   		System.out.println("Input word not found.");
		   	}
		   }
		} while (!wordDeleted);
	}
	
	
	/**
	 * A method to write the current word list to a file. 
	 * @param wordlist: the word list to write out.
	 * @param filename: the file that the list should be written to.
	 */
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
	
	/**
	 * A method to open a Merriam-Webster's webpage for a word.
	 * @param wordlist
	 * @param scnr
	 */
	public static void show(WordList wordlist, Scanner scnr) {
		System.out.println("\nType in the index of a word to show: ");
		if (scnr.hasNextInt()) {
			int wordToShow = scnr.nextInt();
			if (wordToShow  > 0 && wordToShow  <= wordlist.size()) {
				String URL = "https://www.merriam-webster.com/dictionary/" + wordlist.getWord(wordToShow - 1).getName();
				try {
					URI wordURL = new URI(URL);
					Desktop desktop = Desktop.getDesktop();
					desktop.browse(wordURL);
				} catch (URISyntaxException e) {
					System.out.println("URL formation error");
				} catch (IOException e) {
					System.out.println("Cannot open URL");
				}
			} else {
				System.out.println("Input not valid. Enter a number between 1 and " + wordlist.size());
			}
		} else {
			System.out.println("Input should be a number.");
		}
		scnr.nextLine();
	}
	
	
	/**
	 * A method to deal with quitting. First check whether the word list has changed; 
	 * if it has, remind user to save it before exiting. 
	 * @param originalList
	 * @param newList
	 * @param scnr
	 * @param filename
	 */
	public static void quit(WordList originalList, WordList newList, Scanner scnr, String filename) {
		if (originalList.isChanged(newList)) {
			boolean validChoice = false;
			do {
				System.out.print("The list has changed. Do you want to save? Y/N ");
				if (!scnr.hasNextInt()) {
					char choice = scnr.nextLine().toLowerCase().charAt(0);
					if (choice == 'y') {
						write(newList, filename);
						validChoice = true;
					} else if (choice == 'n') {
						System.out.println("Exit without saving");
						validChoice = true;
					} else {
						System.out.println("Input not recognized. Try again");
					}
				}
			} while (!validChoice);
		} 
	}
	

	/**
	 * A method to list files in a directory
	 * @param file_extension
	 * @param file_path
	 * @return the number of files with a specified extension.
	 */
	public static int fileList(String file_extension, String file_path) {
		File files = new File("./");
		System.out.println("\nMy Word Lists: ");
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
