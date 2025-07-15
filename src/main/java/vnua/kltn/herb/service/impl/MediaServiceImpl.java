package vnua.kltn.herb.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.response.MediaResponseDto;
import vnua.kltn.herb.entity.*;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.*;
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
    private final UserService userService;
    private final MediaRepository mediaRepo;
    private final MediaMapper mediaMapper;
    private final ArticleMediaRepository articleMediaRepo;
    private final ArticleRepository articleRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.base-url}")
    private String baseFileUrl;

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

        var media = Media.builder()
                .fileName(originalFilename) // Lưu tên gốc của file
                .filePath(relativePath)
                .urlFile(fileUrl) // Thêm URL file
                .fileType(fileType)
                .fileSize((int) file.getSize())
                .altText(requestDto.getAltText())
                .createdBy(currentUser.getUsername())
                .build();
        try {
            var savedMedia = mediaRepo.save(media);
            return mediaMapper.entityToResponse(savedMedia);
        } catch (Exception e) {
            // Xóa file nếu lưu database thất bại
            Files.deleteIfExists(savedFilePath);
            throw new HerbException(ErrorCodeEnum.FILE_DATABASE_ERROR, e);
        }
    }

    @Override
    @Transactional
    public void linkMediaToArticle(Long mediaId, Long articleId, Boolean isFeatured) throws HerbException {
        var articleMedia = ArticleMedia.builder()
                .id(new ArticleMediaId(articleId, mediaId))
                .isFeatured(isFeatured)
                .build();

        articleMediaRepo.save(articleMedia);

        if (isFeatured) {
            var article = articleRepo.findById(articleId)
                    .orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
            article.setFeaturedImage(mediaId);
            articleRepo.save(article);
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
            } else if (contentType.equals("application/pdf")) {
                return DOCUMENT.getType(); // Hoặc tạo PDF.getType() nếu có
            }
        }

        // Fallback dựa trên extension
        var originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            var extension = FileUtils.getFileExtension(originalFilename)
                    .orElse("").toLowerCase();
            if (extension.equals("pdf")) {
                return DOCUMENT.getType();
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
    public byte[] previewPdf(Long id) throws HerbException, IOException {
        var media = mediaRepo.findById(id)
                .orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));

        // Kiểm tra xem có phải file PDF không
        if (!media.getFileType().equals(DOCUMENT.getType()) ||
                !media.getFileName().toLowerCase().endsWith(".pdf")) {
            throw new HerbException(ErrorCodeEnum.UNSUPPORTED_FILE_FORMAT);
        }

        return getData(id);
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

    @Override
    public byte[] downloadFile(Long id) throws HerbException, IOException {
        var media = mediaRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));

        if (!media.getFileType().equals(DOCUMENT.getType()) ||
                !media.getFileName().toLowerCase().endsWith(".pdf")) {
            throw new HerbException(ErrorCodeEnum.UNSUPPORTED_FILE_FORMAT, "Chỉ hỗ trợ tải xuống file PDF");
        }

        var filePath = Paths.get(uploadDir, media.getFilePath()).toString();
        return FileUtils.readFile(filePath).orElseThrow(() -> new IOException("Không thể đọc file: " + filePath));
    }
}
