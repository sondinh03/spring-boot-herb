package vnua.kltn.herb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.Families;

@Repository
public interface FamiliesRepository extends JpaRepository<Families, Long>, JpaSpecificationExecutor<Families> {
    Boolean existsByName(String name);
}