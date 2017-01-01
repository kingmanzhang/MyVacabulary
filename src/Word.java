
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

/**
 * A Word class to manage single words. Each word should at least have a name, meaning, 
 * and example usage in sentences. A user can type in the meaning and example sentences, 
 * or alternatively, important them from the Merriam-Webster's Dictionary. 
 * @author Xingmin Zhang
 *
 */
public class Word implements Comparable<Word>, Cloneable {
	private String word_name;
	private String word_meaning;
	private String[] word_example;
	private final int MAX_EXAMPLE = 5;
	
	public Word() {
		this.word_name = null;
		this.word_meaning = null;
		this.word_example = new String[MAX_EXAMPLE];
	}
	
	public Word(String name) {
		this.word_name = name;
		this.word_meaning = null;
		this.word_example = new String[MAX_EXAMPLE];
	}
	
	public Word(String name, String word_meaning, String[] word_example) {
		this.word_name = name;
		this.word_meaning = word_meaning;
		this.word_example = word_example;
	}
	
	public void setName(String word) {
		word_name = word;
	}
	
	public String getName() {
		return word_name;
	}
	
	public void setMeaning(String meaning) {
		word_meaning = meaning;
	}
	
	public String getMeaning() {
		return word_meaning;
	}
	
	public void setExample(String[] examples) {
		word_example = examples;
	}
	
	public String[] getExample() {
		return word_example;
	}

	/**
	 * A method to print the example sentences of a word.
	 */
	public void printExample() {
		for (int i = 0; i < this.getExample().length; i++) {
			if(this.getExample() != null & this.getExample()[i] != "") {
				System.out.println(this.getExample()[i]);
			}
		}
	}
	
	//& this.getExample()[i] != "" & !this.getExample()[i].equals("null")
	
	//My API key for Merriam-Webster's Dictionary
	private final String DICTIONARY_KEY = "73b58ad7-1efd-43b8-944f-70f801e2c801";
	private final String THESAURUS_KEY = "ec834769-f0b2-4ab9-a1cf-98ae44838ab5";
	
	/**
	 * A method to generate a URL (Dictionary) for a word.
	 * @return the URL.
	 * @throws MalformedURLException
	 */
	public URL getDictionaryURL() throws MalformedURLException {
		URL url = new URL("http://www.dictionaryapi.com/api/v1/references/collegiate/xml/" + 
	 this.getName() + "?key=" + DICTIONARY_KEY);
		return url;
	}
	
	
	/**
	 * A method to generate a URL (Thesaurus) for a word.
	 * @return the URL
	 * @throws MalformedURLException
	 */
	public URL getThesaurusURL() throws MalformedURLException {
		URL url = new URL("http://www.dictionaryapi.com/api/v1/references/thesaurus/xml/" + 
				 this.getName() + "?key=" + THESAURUS_KEY);
		return url;
	}
	
	/**
	 * A method to query a word to the Marriam-Webster's Dictionary
	 * @return the XML response of a word.
	 */
	private String getMerriamWebster() {
		try {
			URLConnection connection = getDictionaryURL().openConnection();
			InputStream streamMerriamWebster = connection.getInputStream();
			BufferedReader streamBuffer = new BufferedReader(new InputStreamReader(streamMerriamWebster));
			String line = null;
			String response = "";
			while((line = streamBuffer.readLine()) != null) {
				response = response + line + "\n";
			}
			//System.out.println("Response from Merriam-Webster is: \n" + response);
			return response;
		} catch (MalformedURLException e) {
			System.out.println("URL is not formed correctly");
			return null;
		} catch (IOException e) {
			System.out.println("Cannot establish connection to Merriam-Webster");
			return null;
		}
	}
	
	
	/**
	 * A method to get the meanings of a word parsed from the XML response
	 * @return the meaning of a word
	 */
	public String getMerriamWebsterMeaning() {
		String meaning = "";
		String xml = this.getMerriamWebster();
		MerriamWebsterXMLParser merriamWebsterXMLParser = new MerriamWebsterXMLParser(xml);
		merriamWebsterXMLParser.getMeaning();
		return meaning;
	}
	
	/**
	 * A method to override the compareTo method. 
	 * Compare word alphabetically. 
	 */
	@Override
	public int compareTo(Word o) {
		return this.getName().compareTo(o.getName());
	}
	
	/**
	 * A method to determine whether two words are equal. 
	 * Two words are equal only when all their fields are identical. 
	 */
	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		if(o instanceof Word) {
			Word otherWord = (Word) o;
			if (this.getName().equals(otherWord.getName()) 
					& this.getMeaning().equals(otherWord.getMeaning())
					& Arrays.equals(this.getExample(), otherWord.getExample())) {
				equals = true;
			}
		}
		return equals;
	}
	
	
	/**
	 * A method to (deep) clone a word. 
	 * The cloned copy should have its own copy of all the fields of a word.  
	 */
	public Word clone() {
		Word copy = new Word();
		copy.word_name = new String(this.word_name);
		copy.word_meaning = new String(this.word_meaning);
		copy.word_example = new String[word_example.length];
		for (int i = 0; i < this.word_example.length; i++) {
			if(word_example != null) {
				copy.word_example[i] = new String(this.word_example[i]);
			}
			
		}
		return copy;
		
	}

}
