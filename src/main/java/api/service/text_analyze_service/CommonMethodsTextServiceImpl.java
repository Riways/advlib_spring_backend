package api.service.text_analyze_service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class CommonMethodsTextServiceImpl implements CommonMethodsTextService {

	@Override
	public String getTextInStringFromFile(File file) {
		String str;
		String result;
		StringBuilder text = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while ((str = reader.readLine()) != null) {
				text.append(str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		result = text.toString();
		result = simplifyText(result);

		return result;
	}

	@Override
	public String getText(String text) {
		return simplifyText(text);
	}

	private String simplifyText(String text) {
		text = text.toString();
		text = " " + text + " ";
		// removes every non-ascii symbol
		text = text.replaceAll("[^\\x00-\\x7F]", " ");
		// removes double tabs, and control characters
		text = text.replaceAll("[\\s]+", " ");
		text = text.replaceAll("[-]+[ ]+", "");
		return text;
	}


	
}
