package vnua.kltn.herb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.DiseaseGroup;
import vnua.kltn.herb.entity.PlantFamily;

import java.util.List;

@Repository
public interface DiseaseGroupRepository extends JpaRepository<DiseaseGroup, Long> {
    DiseaseGroup findByName(String name);

    @Query(value = " FROM DiseaseGroup dg WHERE dg.id <> :id AND dg.name = :name ")
    DiseaseGroup findByNameAndNotId(String name, Long id);

    @Query(value = " FROM DiseaseGroup dg WHERE dg.name LIKE :name ORDER BY dg.name ASC ")
    List<DiseaseGroup> findByNameContainingIgnoreCase(@Param("name") String name);
}
