package vnua.kltn.herb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.Plant;

import java.util.Optional;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long>, JpaSpecificationExecutor<Plant> {
    Boolean existsByName(String name);

    Boolean existsByScientificName(String scientificName);

    Page<Plant> findAll(Specification<Plant> spec, Pageable pageable);

    Optional<Plant> findBySlug(String slug);

    Page<Plant> findByStatus(Integer status, Pageable pageable);

    Page<Plant> findByFeatured(Boolean featured, Pageable pageable);

    @Query("SELECT p FROM Plant p WHERE p.name LIKE %?1% OR p.scientificName LIKE %?1% OR p.description LIKE %?1%")
    Page<Plant> search(String keyword, Pageable pageable);

//    Page<Plant> findByCategoriesContaining(Category category, Pageable pageable);

//    Page<Plant> findByTagsContaining(Tag tag, Pageable pageable);
}
