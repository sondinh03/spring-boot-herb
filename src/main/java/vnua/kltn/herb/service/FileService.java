package vnua.kltn.herb.service;

import vnua.kltn.herb.dto.request.FileRequestDto;
import vnua.kltn.herb.dto.response.FileResponseDto;
import vnua.kltn.herb.entity.File;
import vnua.kltn.herb.exception.HerbException;

import java.util.Optional;

public interface FileService {
    FileResponseDto upload(FileRequestDto requestDto) throws HerbException;

    Optional<File> getById(Long id);

    boolean deleteFile(Long id);

    String getFilePath(Long id);

    Optional<byte[]> getFileContent(Long id);
}
