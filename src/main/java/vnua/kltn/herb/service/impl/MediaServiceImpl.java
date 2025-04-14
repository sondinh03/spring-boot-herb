package vnua.kltn.herb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.response.MediaResponseDto;
import vnua.kltn.herb.entity.Media;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.MediaRepository;
import vnua.kltn.herb.repository.UserRepository;
import vnua.kltn.herb.service.MediaService;
import vnua.kltn.herb.service.UserService;
import vnua.kltn.herb.service.mapper.MediaMapper;
import vnua.kltn.herb.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import static vnua.kltn.herb.constant.enums.FileTypeEnum.*;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final UserRepository userRepo;
    private final UserService userService;
    private final MediaRepository mediaRepo;
    private final MediaMapper mediaMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    @Transactional
    public MediaResponseDto upload(MediaRequestDto requestDto) throws HerbException, IOException {
        // Lấy thông tin người dùng
        var currentUser = userService.getCurrentUser();

        var file = requestDto.getFile();
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File ko duoc de trong");
        }

        var originalFilename = file.getOriginalFilename();
        var fileExtension = FileUtils.getFileExtension(originalFilename).orElse("bin");
        var newFileName = UUID.randomUUID().toString() + "." + fileExtension;

        var yearMonth = LocalDateTime.now().getYear() + "_" + LocalDateTime.now().getMonthValue();
        var relativePath = Paths.get(yearMonth).toString();
        FileUtils.createDirectoryIfNotExist(Paths.get(uploadDir, yearMonth).toString());

        var dbFilePath = Paths.get(yearMonth, newFileName).toString();
        var fullPath = Paths.get(uploadDir, yearMonth, newFileName).toString();

        //Luu file
        if (!FileUtils.saveFile(fullPath, file)) {
            throw new IOException("Không thể lưu file");
        }

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
        // Tao media
        var media = Media.builder()
                .fileName(newFileName)
                .filePath(dbFilePath)
                .fileType(fileType)
                .fileSize((int) file.getSize())
                .altText(requestDto.getAltText())
                .uploadedBy(currentUser.getId())
                .build();
        mediaRepo.save(media);
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
}
