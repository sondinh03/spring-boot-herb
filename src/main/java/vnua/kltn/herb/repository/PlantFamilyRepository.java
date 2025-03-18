package vnua.kltn.herb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.PlantFamily;

import java.util.List;

@Repository
public interface PlantFamilyRepository extends JpaRepository<PlantFamily, Long> {
    PlantFamily findByName(String name);

    @Query(value = " FROM PlantFamily pf WHERE pf.id <> :id AND pf.name = :name ")
    PlantFamily findByNameAndNotId(String name, Long id);

    @Query(value = " FROM PlantFamily pf WHERE pf.name LIKE :name ORDER BY pf.name ASC ")
    List<PlantFamily> findByNameContainingIgnoreCase(@Param("name") String name);
}
