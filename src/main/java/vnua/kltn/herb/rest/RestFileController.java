package vnua.kltn.herb.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.request.FileRequestDto;
import vnua.kltn.herb.dto.request.PlantFamilyRequestDto;
import vnua.kltn.herb.dto.response.FileResponseDto;
import vnua.kltn.herb.dto.response.PlantFamilyResponseDto;
import vnua.kltn.herb.entity.File;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.FileService;
import vnua.kltn.herb.service.PlantFamilyService;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class RestFileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public HerbResponse<FileResponseDto> upload(@ModelAttribute FileRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(fileService.upload(requestDto));
    }
}
