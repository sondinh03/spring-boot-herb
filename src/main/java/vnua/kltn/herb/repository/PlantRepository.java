package vnua.kltn.herb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.dto.response.PlantWithMedia;
import vnua.kltn.herb.entity.Plant;
import vnua.kltn.herb.repository.projection.PlantDetailsProjection;

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

//    @Query(" SELECT p as plant, pm as media FROM Plant p LEFT JOIN PlantMedia pm ON pm.id.plantId = p.id AND pm.isFeatured = true ")
//    Page<PlantWithMedia> searchWithFeaturedMedia(Specification<Plant> spec, Pageable pageable);

    @Query("""
    SELECT p as plant,
    ds as dataSource,
    ac as activeCompound
    FROM Plant p
    LEFT JOIN DataSource ds ON p.dataSourceId = ds.id
    LEFT JOIN ActiveCompound ac ON p.activeCompoundId = ac.id
    WHERE p.id = :plantId
""")
    Optional<PlantDetailsProjection> findByIdWithDetails(@Param("plantId") Long plantId);
}
