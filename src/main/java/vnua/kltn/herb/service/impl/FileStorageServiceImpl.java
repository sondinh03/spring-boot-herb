package vnua.kltn.herb.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vnua.kltn.herb.service.FileStorageService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path fileStorageLocation;

    public FileStorageServiceImpl(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Không thể tạo thư mục để lưu các file upload.", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException("Xin lỗi! Tên file chứa đường dẫn không hợp lệ " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Không thể lưu file " + fileName + ". Vui lòng thử lại!", ex);
        }
    }

    /**
     * Đọc file từ storage location và trả về Resource
     */
    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File không tìm thấy " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File không tìm thấy " + fileName, ex);
        }
    }

    /**
     * Đọc nội dung file dưới dạng String
     */
    @Override
    public String readFileAsString(String fileName) throws IOException {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        return Files.readString(filePath, StandardCharsets.UTF_8);
    }

    /**
     * Đọc nội dung file dưới dạng byte array
     */
    @Override
    public byte[] readFileAsBytes(String fileName) throws IOException {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        return Files.readAllBytes(filePath);
    }

    /**
     * Đọc file theo từng dòng
     */
    @Override
    public List<String> readFileLines(String fileName) throws IOException {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        return Files.readAllLines(filePath, StandardCharsets.UTF_8);
    }

    /**
     * Ghi String vào file
     **/
    @Override
    public void writeStringToFile(String content, String fileName) throws IOException {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Files.writeString(filePath, content, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Ghi byte array vào file
     */
    @Override
    public void writeBytesToFile(byte[] bytes, String fileName) throws IOException {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Files.write(filePath, bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Thêm nội dung vào cuối file (append)
     */
    @Override
    public void appendToFile(String content, String fileName) throws IOException {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Files.writeString(filePath, content, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    /**
     * Xóa file
     */
    @Override
    public boolean deleteFile(String fileName) throws IOException {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        return Files.deleteIfExists(filePath);
    }

    /**
     * Liệt kê tất cả file trong thư mục
     */
    @Override
    public List<String> listFiles() throws IOException {
        try (var paths = Files.walk(this.fileStorageLocation, 1)) {
            return paths
                    .filter(path -> !path.equals(this.fileStorageLocation))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Kiểm tra xem file có tồn tại không
     */
    @Override
    public boolean fileExists(String fileName) {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        return Files.exists(filePath);
    }
}
