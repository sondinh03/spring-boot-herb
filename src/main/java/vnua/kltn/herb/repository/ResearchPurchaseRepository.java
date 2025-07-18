package vnua.kltn.herb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.Research;
import vnua.kltn.herb.entity.ResearchPurchase;

@Repository
public interface ResearchPurchaseRepository extends JpaRepository<ResearchPurchase, Long>, JpaSpecificationExecutor<ResearchPurchase> {
    ResearchPurchase findByResearchIdAndUserId(Long researchId, Long userId);
}
