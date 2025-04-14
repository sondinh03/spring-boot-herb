package vnua.kltn.herb.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.response.MediaResponseDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.MediaService;

import java.io.IOException;

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
        var mediaInfo = mediaService.getById(id);
        var data = mediaService.getData(id);

        var resource = new ByteArrayResource(data);
        return new HerbResponse<>(resource);
    }
}
