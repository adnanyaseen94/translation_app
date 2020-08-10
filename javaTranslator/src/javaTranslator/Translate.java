package javaTranslator;

// Import dependencies

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class Translate {
	String apiUrl;
	String completeUrl;
	String sourceLang;
	String targetLang;


	// Method the sentence from command line
	protected void userInput() {
		Scanner scan = new Scanner(System.in);	
		System.out.println("Please type the sentence that you want to translate:");
		String userInput = scan.nextLine();	
		scan.close();

		// Check if the user entered only spaces 
		if (userInput.trim().isEmpty()) {
			System.out.println("You have't typed a sentence!");		
			System.out.println("Program terminating");	
			System.exit(-1);
		}

		generateCompleteUrl(userInput);
	}

	// Generate the complete url for translation
	private void generateCompleteUrl(String textToTranslate) {
		textToTranslate = textToTranslate.replace("\"", "");
		try {
			textToTranslate = java.net.URLEncoder.encode(textToTranslate, "UTF-8").replace("+", "%20");
		}
		catch(UnsupportedEncodingException ex) {
			System.out.println(ex);
			System.exit(-1);

		}
		this.completeUrl = apiUrl + this.sourceLang + "&tl=" + this.targetLang + "&dt=t&q=" + textToTranslate;
	}


	protected String sendRequest() {
		//
		try {
			URL url = new URL(this.completeUrl);
			HttpURLConnection c = (HttpURLConnection) url.openConnection();	// Connect to the api
			int responseCode = c.getResponseCode();		// Check if the request is successful
			if (responseCode == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					String decodedToISO15 = new String(inputLine.getBytes("ISO-8859-1"), "ISO-8859-15");
					response.append(decodedToISO15);
				}
				in.close();
				// Filter the request response to extract only the translation 
				String[] tempNames = new String(response).split("\"");
				String translatedString= new String();


				for(int i=1; i<=tempNames.length-4;i=i+4) {
					// when there is no translation for the sentence
					if (!(tempNames[i].equals("124705b4b7a55ca503ac35d07bb227ff"))) {
						translatedString = translatedString + tempNames[i];
					}
				}
				return translatedString;

			}
			else return "Connection Error";
		}	catch (MalformedURLException e) {
			e.printStackTrace();
			return "Exception occured";
		}
		catch (IOException e) {
			e.printStackTrace();
			return "Exception occured";		
		}
	}

	
	protected Translate(String apiUrl, String sourceLang, String targetLang) {
		this.apiUrl = apiUrl;
		this.sourceLang = sourceLang;
		this.targetLang = targetLang;
	}
}
