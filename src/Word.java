
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * A Word class to manage single words. Each word should at least have a name, meaning, 
 * and example usage in sentences. A user can type in the meaning and example sentences, 
 * or alternatively, important them from the Merriam-Webster's Dictionary. 
 * @author Xingmin Zhang
 *
 */
public class Word implements Comparable<Word> {
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

	public void printExample() {
		for (int i = 0; i < this.getExample().length; i++) {
			if(this.getExample()[i] != "" & !this.getExample()[i].equals("null")) {
				System.out.println(this.getExample()[i]);
			}
		}
	}
	
	//My API key for Merriam-Webster's Dictionary
	private final String DICTIONARY_KEY = "73b58ad7-1efd-43b8-944f-70f801e2c801";
	private final String THESAURUS_KEY = "ec834769-f0b2-4ab9-a1cf-98ae44838ab5";
	
	/**
	 * A method to generate a URL for a word.
	 * @return
	 * @throws MalformedURLException
	 */
	public URL getDictionaryURL() throws MalformedURLException {
		URL url = new URL("http://www.dictionaryapi.com/api/v1/references/collegiate/xml/" + 
	 this.getName() + "?key=" + DICTIONARY_KEY);
		return url;
	}
	
	public URL getThesaurusURL() throws MalformedURLException {
		URL url = new URL("http://www.dictionaryapi.com/api/v1/references/thesaurus/xml/" + 
				 this.getName() + "?key=" + THESAURUS_KEY);
		return url;
	}
	
	/**
	 * A method to query a word to the Marriam-Webster's Dictionary
	 */
	public String getMerriamWebster() {
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
	
	public String getMerriamWebsterMeaning() {
		String meaning = "";
		String xml = this.getMerriamWebster();
		MerriamWebsterXMLParser merriamWebsterXMLParser = new MerriamWebsterXMLParser(xml);
		merriamWebsterXMLParser.getMeaning();
		return meaning;
	}
	
	@Override
	public int compareTo(Word o) {
		return this.getName().compareTo(o.getName());
	}
	

}
