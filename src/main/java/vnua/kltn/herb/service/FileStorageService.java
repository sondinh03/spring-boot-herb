package vnua.kltn.herb.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileStorageService {

    String storeFile(MultipartFile file);

    Resource loadFileAsResource(String fileName);

    String readFileAsString(String fileName) throws IOException;

    byte[] readFileAsBytes(String fileName) throws IOException;

    List<String> readFileLines(String fileName) throws IOException;

    void writeStringToFile(String content, String fileName) throws IOException;

    void writeBytesToFile(byte[] bytes, String fileName) throws IOException;

    void appendToFile(String content, String fileName) throws IOException;

    public boolean deleteFile(String fileName) throws IOException;

    List<String> listFiles() throws IOException;

    boolean fileExists(String fileName);
}
