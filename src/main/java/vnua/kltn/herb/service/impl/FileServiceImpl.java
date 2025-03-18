package vnua.kltn.herb.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.FileRequestDto;
import vnua.kltn.herb.dto.response.FileResponseDto;
import vnua.kltn.herb.entity.File;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.FileRepository;
import vnua.kltn.herb.service.FileService;
import vnua.kltn.herb.service.mapper.FileMapper;
import vnua.kltn.herb.utils.FileUtils;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    private final FileRepository fileRepo;
    private final FileMapper fileMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public FileResponseDto upload(FileRequestDto requestDto) throws HerbException {
        MultipartFile multipartFile = requestDto.getFile();

        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new HerbException(ErrorCodeEnum.FILE_NOT_EMPTY);
        }

        //Tạo file entity
        File fileEntity = createFileEntity(multipartFile, requestDto);

        // Tạo thư mục nếu chưa tồn tại
        FileUtils.createDirectoryIfNotExist(uploadDir);

        // Đường dẫn đầy đủ để lưu file
        String fullPath = Paths.get(uploadDir, fileEntity.getPath()).toString();

        // Lưu file và xử lý kết quả
        boolean saved = FileUtils.saveFile(fullPath, multipartFile);
        if (!saved) {
            log.error("Failed to save file: {}", fileEntity.getName());
            throw new HerbException(ErrorCodeEnum.FILE_UPLOAD_ERROR);
        }

        // Lưu thông tin file vào database
        fileRepo.save(fileEntity);
        return fileMapper.entityToResponse(fileEntity);
    }

    private File createFileEntity(MultipartFile multipartFile, FileRequestDto requestDto) {
        // Lấy tên file gốc, đảm bảo không null
        String originalFileName = multipartFile.getOriginalFilename();
        if (originalFileName == null) {
            originalFileName = "unknown_file";
        }

        // Lấy tên file (dùng tên từ request nếu có, nếu không dùng tên gốc)
        String fileName = (requestDto.getName() != null && !requestDto.getName().isEmpty())
                ? requestDto.getName()
                : originalFileName;

        // Lấy phần mở rộng từ tên file gốc
        String fileExtension = FileUtils.getFileExtension(originalFileName)
                .orElse("");

        // Tạo tên file duy nhất để lưu trữ
        String uniqueFileName = UUID.randomUUID() +
                (fileExtension.isEmpty() ? "" : "." + fileExtension);

        // Tạo và trả về entity File
        return fileRepo.save(File.builder()
                .name(fileName)
                .description(requestDto.getDescription())
                .type(fileExtension)
                .path(uniqueFileName)
                .size(multipartFile.getSize())
                .build());
    }

    /**
     * Lấy File theo ID
     */
    @Override
    public Optional<File> getById(Long id) {
        return fileRepo.findById(id);
    }

    /**
     * Xóa File theo ID
     */
    @Override
    public boolean deleteFile(Long id) {
        Optional<File> fileOpt = fileRepo.findById(id);
        if (fileOpt.isPresent()) {
            File file = fileOpt.get();
            String fullPath = Paths.get(uploadDir, file.getPath()).toString();

            // Xóa file từ hệ thống file
            boolean deleted = FileUtils.deleteFile(fullPath);
            if (deleted) {
                // Xóa thông tin file từ database
                fileRepo.delete(file);
                return true;
            } else {
                log.error("Failed to delete file on disk: {}", fullPath);
            }
        }
        return false;
    }

    /**
     * Lấy đường dẫn đầy đủ của file
     */
    @Override
    public String getFilePath(Long id) {
        Optional<File> fileOpt = fileRepo.findById(id);
        if (fileOpt.isPresent()) {
            File file = fileOpt.get();
            return Paths.get(uploadDir, file.getPath()).toString();
        }
        return null;
    }

    /**
     * Lấy nội dung file dưới dạng byte[]
     */
    @Override
    public Optional<byte[]> getFileContent(Long id) {
        String filePath = getFilePath(id);
        if (filePath != null) {
            return FileUtils.readFile(filePath);
        }
        return Optional.empty();
    }
}
