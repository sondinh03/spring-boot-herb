package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.ResearchPurchaseRequestDto;
import vnua.kltn.herb.dto.response.ResearchPurchaseResponseDto;
import vnua.kltn.herb.entity.ResearchPurchase;

@Mapper(componentModel = "spring")
public interface ResearchPurchaseMapper {
    ResearchPurchaseResponseDto entityToResponse(ResearchPurchase entity);

    ResearchPurchase requestToEntity(ResearchPurchaseRequestDto requestDto);

    void setValue(ResearchPurchaseRequestDto requestDto,@MappingTarget ResearchPurchase entity);
}
