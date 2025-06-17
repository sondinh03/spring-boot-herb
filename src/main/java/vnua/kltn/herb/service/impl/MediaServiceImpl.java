package vnua.kltn.herb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.response.MediaResponseDto;
import vnua.kltn.herb.entity.Media;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.MediaRepository;
import vnua.kltn.herb.repository.PlantMediaRepository;
import vnua.kltn.herb.repository.UserRepository;
import vnua.kltn.herb.service.MediaService;
import vnua.kltn.herb.service.UserService;
import vnua.kltn.herb.service.mapper.MediaMapper;
import vnua.kltn.herb.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

import static vnua.kltn.herb.constant.enums.FileTypeEnum.*;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final UserRepository userRepo;
    private final UserService userService;
    private final MediaRepository mediaRepo;
    private final MediaMapper mediaMapper;
    private final PlantMediaRepository plantMediaRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.base-url}")
    private String baseFileUrl;

    /*
    @Override
    @Transactional
    public MediaResponseDto upload(MediaRequestDto requestDto) throws HerbException, IOException {
        var currentUser = userService.getCurrentUser();
        var file = requestDto.getFile();

        // Kiểm tra file hợp lệ
        if (file == null || file.isEmpty()) {
            throw new HerbException(ErrorCodeEnum.EMPTY_FILE);
        }

        // Kiểm tra định dạng file hợp lệ
        var originalFilename = file.getOriginalFilename();
        var fileExtension = FileUtils.getFileExtension(originalFilename)
                .orElseThrow(() -> new HerbException(ErrorCodeEnum.INVALID_FILE));

        // Danh sách định dạng được phép (có thể điều chỉnh theo yêu cầu)
        Set<String> allowedExtensions = new HashSet<>(Arrays.asList(
                "jpg", "jpeg", "png", "gif", "pdf", "doc", "docx", "xls", "xlsx", "mp4", "mp3"
        ));

        if (!allowedExtensions.contains(fileExtension.toLowerCase())) {
            throw new HerbException(ErrorCodeEnum.NOT_SUPPORT);
        }

        // Tạo tên file mới để tránh trùng lặp
        var newFileName = UUID.randomUUID().toString() + "." + fileExtension;

        // Tạo đường dẫn lưu file theo cấu trúc năm_tháng
        var yearMonth = LocalDateTime.now().getYear() + "_" + LocalDateTime.now().getMonthValue();
        var relativePath = Paths.get(yearMonth).toString();
        FileUtils.createDirectoryIfNotExist(Paths.get(uploadDir, yearMonth).toString());

        var dbFilePath = Paths.get(yearMonth, newFileName).toString().replace("\\", "/");
        var fullPath = Paths.get(uploadDir, yearMonth, newFileName).toString();

        //Luu file
        if (!FileUtils.saveFile(fullPath, file)) {
            throw new IOException("Không thể lưu file");
        }

        // Xác định loại file
        var fileType = requestDto.getFileType();
        if (fileType == null) {
            var contentType = file.getContentType();
            if (contentType != null) {
                if (contentType.startsWith("image/")) {
                    fileType = IMAGE.getType();
                } else if (contentType.startsWith("video/")) {
                    fileType = VIDEO.getType();
                } else {
                    fileType = DOCUMENT.getType();
                }
            } else {
                fileType = DOCUMENT.getType();
            }
        }

        // Tạo URL truy cập file
        String fileUrl = baseFileUrl + "/" + dbFilePath;

        /// Tạo và lưu thông tin media
        var media = Media.builder()
                .fileName(originalFilename) // Lưu tên gốc của file
                .filePath(dbFilePath)
                .urlFile(fileUrl) // Thêm URL file
                .fileType(fileType)
                .fileSize((int) file.getSize())
                .altText(requestDto.getAltText())
                .build();

        mediaRepo.save(media);
        return mediaMapper.entityToResponse(media);
    }
    */

    @Override
    @Transactional
    public MediaResponseDto upload(MediaRequestDto requestDto) throws HerbException, IOException {
        var currentUser = userService.getCurrentUser();
        var file = requestDto.getFile();

        // Kiểm tra file hợp lệ
        validateFile(file);

        // Kiểm tra định dạng file hợp lệ
        var originalFilename = file.getOriginalFilename();
        var fileExtension = FileUtils.getFileExtension(originalFilename)
                .orElseThrow(() -> new HerbException(ErrorCodeEnum.INVALID_FILE));
        validateFileExtension(fileExtension);
        validateFileSize(file);

        // Tạo đường dẫn lưu file
        var yearMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM"));
        var newFileName = UUID.randomUUID() + "." + fileExtension;
        var relativePath = Paths.get(yearMonth, newFileName).toString().replace("\\", "/");
        var fullPath = Paths.get(uploadDir, yearMonth, newFileName).toString();

        // Tạo thư mục
        FileUtils.createDirectoryIfNotExists(Paths.get(uploadDir, yearMonth).toString());

        // Lưu file
        Path savedFilePath = Paths.get(fullPath);
        FileUtils.saveFile(fullPath, file);

        // Xác định loại file
        var fileType = determineFileType(file, requestDto.getFileType());

        // Tạo URL truy cập file
        String fileUrl = baseFileUrl + "/" + relativePath;

        // Lưu Media
        try {
            var media = Media.builder()
                    .fileName(originalFilename) // Lưu tên gốc của file
                    .filePath(relativePath)
                    .urlFile(fileUrl) // Thêm URL file
                    .fileType(fileType)
                    .fileSize((int) file.getSize())
                    .altText(requestDto.getAltText())
                    .createdBy(currentUser.getUsername())
                    .build();
            mediaRepo.save(media);
            return mediaMapper.entityToResponse(media);
        } catch (Exception e) {
            // Xóa file nếu lưu database thất bại
            Files.deleteIfExists(savedFilePath);
            throw new HerbException(ErrorCodeEnum.FILE_DATABASE_ERROR, e);
        }
    }

    private Integer determineFileType(MultipartFile file, Integer providedType) {
        if (providedType != null) {
            return providedType;
        }

        var contentType = file.getContentType();
        if (contentType != null) {
            if (contentType.startsWith("image/")) {
                return IMAGE.getType();
            } else if (contentType.startsWith("video/")) {
                return VIDEO.getType();
            }
        }
        return DOCUMENT.getType();
    }

    // Helper methods
    private void validateFile(MultipartFile file) throws HerbException {
        if (file == null || file.isEmpty()) {
            throw new HerbException(ErrorCodeEnum.EMPTY_FILE);
        }
    }

    private void validateFileExtension(String fileExtension) throws HerbException {
        var allowedExtensions = Set.of("jpg", "jpeg", "png", "gif", "pdf", "doc", "docx", "xls", "xlsx", "mp4", "mp3");
        if (!allowedExtensions.contains(fileExtension.toLowerCase())) {
            throw new HerbException(ErrorCodeEnum.UNSUPPORTED_FILE_FORMAT);
        }
    }

    private void validateFileSize(MultipartFile file) throws HerbException {
        // Add file size validation (e.g., max 10MB)
        long maxSize = 10 * 1024 * 1024; // 10MB
        if (file.getSize() > maxSize) {
            throw new HerbException(ErrorCodeEnum.FILE_TOO_LARGE, "File quá lớn, tối đa 10MB");
        }
    }

    @Override
    public MediaResponseDto getById(Long id) throws HerbException {
        var media = mediaRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        return mediaMapper.entityToResponse(media);
    }

    @Override
    public byte[] getData(Long id) throws HerbException, IOException {
        var media = mediaRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));

        var filePath = Paths.get(uploadDir, media.getFilePath());
        return FileUtils.readFile(filePath.toString()).orElseThrow(() -> new IOException("Khong the doc file" + filePath));
    }

    @Override
    @Transactional
    public Boolean delete(Long id) throws HerbException {
        var media = mediaRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));

        // Xóa file
        try {
            var dbFilePath = media.getFilePath();

            if (dbFilePath != null && !dbFilePath.isEmpty()) {
                // Tạo đường dẫn đầy đủ để xóa file
                var fullPath = Paths.get(uploadDir, dbFilePath).toString();
                var file = new File(fullPath);

                if (file.exists()) {
                    if (file.delete()) {
                        System.out.println("Đã xóa file thành công: " + fullPath);
                    } else {
                        System.out.println("Không thể xóa file: " + fullPath);
                        // Log chi tiết hơn để debug
                        System.out.println("File exists: " + file.exists());
                        System.out.println("File canRead: " + file.canRead());
                        System.out.println("File canWrite: " + file.canWrite());
                    }
                } else {
                    System.out.println("File không tồn tại: " + fullPath);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa file vật lý: " + e.getMessage());
        }

        mediaRepo.deleteById(id);
        return true;
    }
}
