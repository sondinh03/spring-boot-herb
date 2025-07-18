package vnua.kltn.herb.service;

import org.springframework.data.domain.Page;
import vnua.kltn.herb.dto.request.ResearchPurchaseRequestDto;
import vnua.kltn.herb.dto.request.ResearchRequestDto;
import vnua.kltn.herb.dto.response.ResearchPurchaseResponseDto;
import vnua.kltn.herb.dto.response.ResearchResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;

public interface ResearchPurchaseService {
   ResearchPurchaseResponseDto create(ResearchPurchaseRequestDto dto);

//   ResearchPurchaseResponseDto update(ResearchRequestDto dto) throws HerbException;
//
//   ResearchPurchaseResponseDto delete(ResearchRequestDto dto) throws HerbException;

   ResearchPurchaseResponseDto getByResearchIdAndUserId(Long researchId) throws HerbException;
}
