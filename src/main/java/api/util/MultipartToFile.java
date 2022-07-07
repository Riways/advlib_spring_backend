package api.util;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MultipartToFile {
	
	public File convert(MultipartFile file) {
		File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+file.getOriginalFilename());
	    try {
			file.transferTo(convFile);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	    return convFile;
	}

}
