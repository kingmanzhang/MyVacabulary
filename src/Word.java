import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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
			if(this.getExample()[i] != null) {
				System.out.println(this.getExample()[i]);
			}
		}
	}
	
	private final String DICTIONARY_KEY = "73b58ad7-1efd-43b8-944f-70f801e2c801";
	private final String THESAURUS_KEY = "ec834769-f0b2-4ab9-a1cf-98ae44838ab5";
	
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
	
	public void getMerriamWebster() {
		try {
			URLConnection connection = getDictionaryURL().openConnection();
			InputStream streamMerriamWebster = connection.getInputStream();
			BufferedReader streamBuffer = new BufferedReader(new InputStreamReader(streamMerriamWebster));
			String line = null;
			while((line = streamBuffer.readLine()) != null) {
				System.out.println(line);
			}
		} catch (MalformedURLException e) {
			System.out.println("URL is not formed correctly");
			
		} catch (IOException e) {
			System.out.println("Cannot establish connection to Merriam-Webster");
		}
	}
	
	@Override
	public int compareTo(Word o) {
		return this.getName().compareTo(o.getName());
	}
	

}
