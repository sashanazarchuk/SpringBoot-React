package shop.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
    void init();
    Resource loasAsResource(String fileName);
    String save(String base64);
    String saveMultipartFile(MultipartFile file);
    void  removeFile(String removeFile);
    Path load(String filename);
}