import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class is responsible for parsing xml responses from Merriam-Webster's dictionary
 * @author Aaron
 *
 */

public class MerriamWebsterXMLParser {
	private String xml;
	
	
	//private String[][] meaning; //first dimension is entry
	                            //second dimension is meaning
	
	public MerriamWebsterXMLParser(String xml) {
		this.xml = xml;
	}
	
	public Document parse() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		StringBuilder xmlStringBuilder = new StringBuilder();
		xmlStringBuilder.append(xml);
		ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
		Document doc = builder.parse(input);
		return doc;
	}
	
	public String[] getMeaning() {
		
		try {
			Document doc = this.parse();
			NodeList nMeaningList = doc.getElementsByTagName("dt");
			String[] meaning = new String[nMeaningList.getLength()];
			String aMeaning;
			for (int j = 0; j < nMeaningList.getLength(); j++) {
				Node nMeaningNode = nMeaningList.item(j);
				aMeaning = nMeaningNode.getTextContent().trim() + ";";
				if(!aMeaning.equals(":") && !aMeaning.equals("")) {
					meaning[j] = aMeaning;	
					System.out.println(j + meaning[j]);
				}	
			}

			
	/*		
System.out.println("get root element: \n" + doc.getDocumentElement().getNodeName());
NodeList nList = doc.getElementsByTagName("entry");
for (int i = 0; i < nList.getLength(); i++ ) {
	Node nNode = nList.item(i);
	System.out.println("Current Element: " + nNode.getNodeName());
	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		Element eElement = (Element) nNode;
		System.out.println("entry " + eElement.getAttribute("id"));
		//get meaning list for each entry
		NodeList nMeaningList = doc.getElementsByTagName("dt");
		for (int j = 0; j < nMeaningList.getLength(); j++) {
			Node nMeaningNode = nMeaningList.item(j);
			Element meaningElement = (Element) nMeaningNode;
			NodeList childList = meaningElement.getChildNodes();
			String[] meaning = new String[childList.getLength()];
			for (int k = 0; k < childList.getLength(); k++) {
				Node childNode = childList.item(k);
				meaning[k] = childNode.getNodeName() + "\n" + childNode.getTextContent();
				System.out.println(meaning[k]);
			}
		}
		//System.out.println("meaning: " + eElement.getElementsByTagName("dt").item(0).getTextContent());
		
	}
	
}
*/

			return meaning;
		} catch (Exception e) {
			System.out.println("failed to parse xml");
			return null;
		}
	}
	


}
