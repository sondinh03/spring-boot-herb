package vnua.kltn.herb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.Genera;

import java.util.List;

@Repository
public interface GeneraRepository extends JpaRepository<Genera, Long>, JpaSpecificationExecutor<Genera> {
    Boolean existsByName(String name);
    List<Genera> findByFamilyId(Long familyId);
}