package vnua.kltn.herb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.response.MediaResponseDto;
import vnua.kltn.herb.entity.Media;
import vnua.kltn.herb.entity.PlantMedia;
import vnua.kltn.herb.entity.PlantMediaId;
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
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
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

    @Override
    @Transactional
    public MediaResponseDto upload(MediaRequestDto requestDto) throws HerbException, IOException {
        // Lấy thông tin người dùng
        var currentUser = userService.getCurrentUser();

        var file = requestDto.getFile();
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File ko duoc de trong");
        }

        // Kiểm tra định dạng file hợp lệ
        var originalFilename = file.getOriginalFilename();
        var fileExtension = FileUtils.getFileExtension(originalFilename).orElse("bin");

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

        // Kiểm tra xem đây có phải file đầu tiên của plant không
        boolean isFirstFile = plantMediaRepo.countById_PlantId(requestDto.getPlantId()) == 0;
        boolean isFeatured = requestDto.getIsFeatured() != null ? requestDto.getIsFeatured() : isFirstFile;

        var plantMedia = PlantMedia.builder()
                .id(new PlantMediaId(requestDto.getPlantId(), media.getId()))
                .isFeatured(isFeatured)
                .build();
        plantMediaRepo.save(plantMedia);

        return mediaMapper.entityToResponse(media);
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
            var filePath = media.getFilePath();
            var file = new File(filePath);

            if (file.exists()) {
                if (!file.delete()) {
                    System.out.println("Không thể xóa file: " + filePath);
                    // Vẫn tiếp tục thực hiện, không throw exception để tránh lỗi transaction
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa file vật lý: " + e.getMessage());
        }

        mediaRepo.deleteById(id);
        return true;
    }
}
