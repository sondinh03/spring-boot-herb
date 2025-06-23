package vnua.kltn.herb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.*;

@Repository
public interface ResearchRepository extends JpaRepository<Research, Long>, JpaSpecificationExecutor<Research> {
    Boolean existsByTitle(String title);
}
