package vn.edu.fpt.cpdm.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    void store(MultipartFile file, String storedFilename);

    Resource loadFileAsResource(String storedFilename);
}
