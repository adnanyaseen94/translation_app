package javaTranslator;
import javaTranslator.Translate;
import java.io.IOException;


public class MainTranslate {


	public static void main(String[] args) throws IOException {
		//System.setProperty("https.proxyHost", "127.0.0.1");
		//System.setProperty("https.proxyPort", "3128");
		System.out.println("---- Welcome to English to French translator ----");
		
		// Set translation api Url
		String apiUrl = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=";
		// Settings to translate from English to French
		String sourceLang = "en";
		String targetLang="fr";
		
		// Create object of the class Translate
		Translate myObj1 = new Translate(apiUrl, sourceLang, targetLang);
		
		// Ask user to type the sentence to be converted
		myObj1.userInput();
		
		// Get the translated sentence
		String response = myObj1.sendRequest();
		
		System.out.println(response);
		System.exit(0);
	}
}
