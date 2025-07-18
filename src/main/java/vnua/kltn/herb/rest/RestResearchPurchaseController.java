package vnua.kltn.herb.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.constant.enums.FavoritableTypeEnum;
import vnua.kltn.herb.dto.request.FavoriteRequestDto;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.request.ResearchPurchaseRequestDto;
import vnua.kltn.herb.dto.response.FavoriteResponseDto;
import vnua.kltn.herb.dto.response.MediaResponseDto;
import vnua.kltn.herb.dto.response.PlantResponseDto;
import vnua.kltn.herb.dto.response.ResearchPurchaseResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.FavoriteService;
import vnua.kltn.herb.service.PlantMediaService;
import vnua.kltn.herb.service.PlantService;
import vnua.kltn.herb.service.ResearchPurchaseService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/research-purchase")
@RequiredArgsConstructor
public class RestResearchPurchaseController {
    private final ResearchPurchaseService researchPurchaseService;

    @PostMapping()
    HerbResponse<ResearchPurchaseResponseDto> create(@RequestBody @Valid ResearchPurchaseRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(researchPurchaseService.create(requestDto));
    }

    @GetMapping("/{researchId}")
    HerbResponse<ResearchPurchaseResponseDto> getById(@PathVariable(value = "researchId") Long id) throws HerbException {
        return new HerbResponse<>(researchPurchaseService.getByResearchIdAndUserId(id));
    }
}
