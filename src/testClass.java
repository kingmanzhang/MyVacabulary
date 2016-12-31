
public class testClass {
	public static void main(String[] args) {
		/*
		String testWord = "pattern";
		Word newWord = new Word(testWord);
		String xml = newWord.getMerriamWebster();
		MerriamWebsterXMLParser merriamWebsterXMLParser = new MerriamWebsterXMLParser(xml);
		merriamWebsterXMLParser.getMeaning();
		//System.out.println(merriamWebsterXMLParser.getRoot().getAttribute("sn").toString());
		 * 
		 */
		String newLine = "abandon|give up something|The cat was abandoned.;null;null;null;null";
		String[] str = newLine.split(";");
		for (int i = 0; i < str.length; i++) {
			System.out.println(str[i]);
		}
		
	}

}
