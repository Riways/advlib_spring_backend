package api.service.storage_service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface StorageService {

	void init();
	
	void storeMultipartFile(MultipartFile file);

	void store(File file);

	String getLocation();

	File generatePathToFile(String filename);

	void deleteBook(String pathToFile) throws IOException;

	void deleteAll();

}
