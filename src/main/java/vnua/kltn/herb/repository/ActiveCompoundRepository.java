package vnua.kltn.herb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.ActiveCompound;
import vnua.kltn.herb.entity.Families;

@Repository
public interface ActiveCompoundRepository extends JpaRepository<ActiveCompound, Long>, JpaSpecificationExecutor<ActiveCompound> {
    Boolean existsByName(String name);
}