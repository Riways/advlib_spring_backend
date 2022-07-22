package api.service.storage_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileSystemStorageService implements StorageService {

	Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

	@Value("${storage}")
	private String location;

	private Path rootLocation;

	public FileSystemStorageService() {
	}

	@PostConstruct
	public void setRootLocation() {
		this.rootLocation = Paths.get(System.getProperty("user.dir") + File.separator + location);
		File directory = rootLocation.toFile();
		if (!directory.exists()) {
			directory.mkdir();
		}
	}

	@Override
	public void storeMultipartFile(MultipartFile file) {
		String fileName = file.getOriginalFilename();

		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + fileName);
			} else if (!fileName.endsWith(".txt")) {
				logger.debug("Not txt");
				throw new StorageException("File must be of type txt");
			}
			logger.debug("Saving...");
			Path pathToCreatingFile = this.rootLocation.resolve(fileName);
			Files.copy(file.getInputStream(), pathToCreatingFile);
			logger.debug("File was saved!");
		} catch (FileAlreadyExistsException e) {
			throw new StorageException("File " + fileName + " already exist in storage");
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + fileName);
		}
	}

	@Override
	public String getLocation() {
		return rootLocation.toString();
	}

	@Override
	public File generatePathToFile(String filename) {
		return rootLocation.resolve(filename).toFile();
	}

	@Override
	public void deleteBook(String pathToFile) throws IOException {
		Path path = Paths.get(pathToFile);
		Files.delete(path);
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public void store(File file) {
		String fileName = file.getName();
		Path pathToCreatingFile = this.rootLocation.resolve(fileName);
		if (Files.exists(pathToCreatingFile)) {
			throw new StorageException("File " + fileName + " already exist in storage");
		}
		if (!fileName.endsWith(".txt")) {
			logger.debug("Not txt");
			throw new StorageException("File must be of type txt");
		}
		try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter(generatePathToFile(fileName)));
				BufferedReader bufReader = new BufferedReader(new FileReader(file))) {

			logger.debug("Saving...");
			String line;

			while ((line = bufReader.readLine()) != null) {
				bufWriter.write(line);
			}
			logger.debug("File was saved!");
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + fileName);
		}

	}

	@Override
	public File store(InputStream is, String fileName) {
		File pathToNewFile = generatePathToFile(fileName);
		if (pathToNewFile.exists()) {
			throw new StorageException("File " + fileName + " already exist in storage");
		}
		if (!fileName.endsWith(".txt")) {
			logger.debug("Not txt");
			throw new StorageException("File must be of type txt");
		}
		
		try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter(pathToNewFile));
				BufferedReader bufReader = new BufferedReader(new InputStreamReader(is))) {
			
			logger.debug("Saving...");
			String line;

			while ((line = bufReader.readLine()) != null) {
				bufWriter.write(line);
			}
			logger.debug("File was saved!");
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + fileName);
		}
		
		return pathToNewFile;

	}

}