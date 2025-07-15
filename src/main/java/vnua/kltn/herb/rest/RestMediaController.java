package vnua.kltn.herb.rest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.response.MediaResponseDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.MediaService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
@Slf4j
public class RestMediaController {
    private final MediaService mediaService;

    @PostMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public HerbResponse<MediaResponseDto> upload(@ModelAttribute MediaRequestDto requestDto) throws HerbException, IOException {
        return new HerbResponse<>(mediaService.upload(requestDto));
    }

    @GetMapping("/{id}")
    public HerbResponse<MediaResponseDto> getById(@PathVariable("id") Long id) throws HerbException {
        return new HerbResponse<>(mediaService.getById(id));
    }

    /**
     * Xem media (không download, dùng cho các loại có thể hiển thị trực tiếp như ảnh, video)
     */
    @GetMapping("/view/{id}")
    public HerbResponse<Resource> view(@PathVariable("id") Long id) throws HerbException, IOException {
        var data = mediaService.getData(id);

        var resource = new ByteArrayResource(data);
        return new HerbResponse<>(resource);
    }

    @GetMapping("/preview-pdf/{id}")
    public HerbResponse<byte[]> preview(@PathVariable("id") Long id) throws HerbException, IOException {
        return new HerbResponse<>(mediaService.previewPdf(id));
    }

    @DeleteMapping("/{id}")
    public HerbResponse<Boolean> delete(@PathVariable("id") Long id) throws HerbException {
        return new HerbResponse<>(mediaService.delete(id));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable("id") Long id) throws HerbException, IOException {
        var fileData = mediaService.downloadFile(id);
        var media = mediaService.getById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + media.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(fileData);
    }
}
