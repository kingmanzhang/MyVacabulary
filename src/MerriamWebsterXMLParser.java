/**
 * This class is responsible for parsing xml responses from Merriam-Webster's dictionary
 * @author Aaron
 *
 */

public class MerriamWebsterXMLParser {
	private String xml;
	private String[][] meaning; //first dimension is entry
	                            //second dimension is meaning 
	
	
	public MerriamWebsterXMLParser(String xml) {
		this.xml = xml;
	}
	
	static void main(String[] args) {
	//TODO add testing lines
	}

}
