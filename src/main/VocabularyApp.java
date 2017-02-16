package main;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class VocabularyApp {
	public static void main(String[] args){
		final String FILE_EXTENSION = "wdl";
		final String FILE_PATH = "./";
		int numLists = 0;
		boolean validInput = false;
		WordListHash myList = new WordListHash();
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
							System.out.println("Which file to open");
							try {
								filename = scnr.nextLine();
								myList = openList(filename);	
							} catch (FileNotFoundException e) {
								System.out.println("File is not found. Type again.");
							}					
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
		WordListHash originalList = null;
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
				System.out.println("Choose: A)dd D)elete R)eview S)how W)rite Q)uit");
				if (!scnr.hasNextInt()) {
					choice = scnr.nextLine().toLowerCase().charAt(0);
					if (choice == 'a' || choice == 'd' || choice == 's' 
							|| choice == 'w' || choice == 'q' || choice == 'r') {
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
			case 'r': //review word
				review(myList, scnr, filename);
				pressEnter(scnr);
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
	public static String openfile(Scanner scnr, WordListHash myList) {
		
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
		
		return filename;
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
	public static WordListHash openList(String filename) throws IOException{
		WordListHash myList = new WordListHash();
		String[] str;
		String newLine;
		int priority;
		String word_name;
		String word_meaning;
		LinkedList<String> word_sentences;
		
		FileInputStream list = new FileInputStream(filename);
		//myList = readFromFile(list);
		Scanner wordParser = new Scanner(list);
		while(wordParser.hasNextLine()) {
			newLine = wordParser.nextLine();
			str = newLine.split(";");
			word_sentences = new LinkedList<String>();
			priority = Integer.parseInt(str[0]);
			word_name = str[1];
			word_meaning = str[2];
			for (int i = 0; i < str.length - 3; i++) {
				word_sentences.add(str[i + 3]);
			}
			Word newWord = new Word(word_name, word_meaning, word_sentences);
			newWord.setPriority(priority);
			try {
				myList.addWord(newWord);
			} catch (WordDuplicateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	public static void add(WordListHash wordlist, Scanner scnr) {
		LinkedList<String> examples = new LinkedList<String>();
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
			System.out.println("Type in an example sentence or END: ");
			String newSentence = "";
			do {
				newSentence = scnr.nextLine();
				if(!newSentence.isEmpty() && !newSentence.equals("END")){
					examples.add(newSentence);
				}
			} while (!newSentence.equals("END"));
			
			newWord.setMeaning(meaning);
			newWord.setExample(examples);
			try {
				wordlist.addWord(newWord);
			} catch (WordDuplicateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Adding new word.");
		}
	}
	
	
	/**
	 * A method to delete a word from a word list.
	 * @param wordlist
	 * @param scnr
	 */
	public static void delete(WordListHash wordlist, Scanner scnr) {
		boolean wordDeleted = false;
		do {
			System.out.print("Type in word to delete: ");
			/*
		   if(scnr.hasNextInt()) {
		   	int index = scnr.nextInt();
		   	if (index > 0 && index <= wordlist.size()) {
		   		wordlist.delete(index);
		   		wordDeleted = true;
		   	} else {
		   		System.out.println("Input index is out of range. Enter a number between 1 and " + wordlist.size());
		   	}
		   	*/
		   
		   	String name = scnr.nextLine();
		   	try {
		   		wordlist.delete(name);
		   		wordDeleted = true;
		   	} catch (Exception e) {
		   		System.out.println("Input word not found.");
		   	}
		   
		} while (!wordDeleted);
	}
	
	/**
	 * The review function allows user to review words. 
	 * The words are presented to user by their priority. 
	 * Every word start with a priority of 100; if a user recognizes the word, decrease
	 * priority by 7; if a user failed to recognize the word, increase by 7. Priority
	 * does not go above 200 or below 1 
	 * @param wordlist
	 * @param scnr
	 */
	public static void review(WordListHash wordlist, Scanner scnr, String filename) {
		
		wordlist.resetReviewedNum();
		
		System.out.println("Begin reviewing the most unfamiliar words");
		boolean terminate = false;
		do {
			LinkedList<Word> topWords = wordlist.topWords(wordlist.size()/3);
			if(topWords.size() == 0) {
				System.out.println("Congratulations! No more words to review currently.");
				break;
			}
			while(!topWords.isEmpty()) {
				Word currentWord = topWords.remove();
				wordlist.getWord(currentWord.getName()).increaseReviewedNum();
				System.out.println("\nnext word:\n");
				System.out.println("--------------------------");
				System.out.println(currentWord.getName());
				System.out.println("--------------------------");
				System.out.println("\nremember the word? Y/N. type END to terminate session");
				String inputLine = scnr.nextLine();
				if(!inputLine.isEmpty()) {
					if(inputLine.equals("END")) {
						terminate = true;
						break;
					}else if(inputLine.toUpperCase().charAt(0) == 'Y') {
						System.out.println("Well done");
						wordlist.getWord(currentWord.getName()).adjPriority(-7);
					}else if(inputLine.toUpperCase().charAt(0) == 'N') {
						wordlist.getWord(currentWord.getName()).adjPriority(7);
						currentWord.print();
					} else {
						System.out.println("invalid input");
					}
				}
			}
		} while(!terminate);
		
		write(wordlist, filename);
	}
	
	/**
	 * A method to write the current word list to a file. 
	 * @param wordlist: the word list to write out.
	 * @param filename: the file that the list should be written to.
	 */
	public static void write(WordListHash wordlist, String filename) {
	   try {
	   	//try to create a FileOutputStream to the file
			FileOutputStream fileStringStream = new FileOutputStream(filename);
			PrintWriter output = new PrintWriter(fileStringStream);
			//print the names and address of each place to the file. 
			//Separate them with ";"
			Set<String> allwords = wordlist.getList();
			Iterator<String> itrWord = allwords.iterator();
			while(itrWord.hasNext()) {
				Word nextWord = wordlist.getWord(itrWord.next());
				output.print(nextWord.getPriority() + ";");
				output.print(nextWord.getName() + ";");
				output.print(nextWord.getMeaning() + ";");
				LinkedList<String> sentences = nextWord.getExample();
				Iterator<String> itr = sentences.iterator();
				int j = 1;
				while(itr.hasNext()) {
					if(j == sentences.size()){
						output.println(itr.next());
					} else {
						output.print(itr.next() + ";");
						j++;
					}		
				}
				output.print("\n");
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
	public static void show(WordListHash wordlist, Scanner scnr) {
		System.out.println("\nType in the word to show on Merriam-webster's website: ");
		String query = scnr.nextLine().trim().toLowerCase();
		if(!query.isEmpty() && wordlist.contains(query)) {
				String URL = "https://www.merriam-webster.com/dictionary/" + query;
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
			System.out.println("Query word is not in the list.");
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
	public static void quit(WordListHash originalList, WordListHash newList, Scanner scnr, String filename) {
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
