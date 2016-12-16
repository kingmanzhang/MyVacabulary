

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
	@Override
	public int compareTo(Word o) {
		return this.getName().compareTo(o.getName());
	}

}
