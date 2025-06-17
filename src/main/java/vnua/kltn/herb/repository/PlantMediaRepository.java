package vnua.kltn.herb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.Media;
import vnua.kltn.herb.entity.PlantMedia;
import vnua.kltn.herb.entity.PlantMediaId;

import java.util.List;

@Repository
public interface PlantMediaRepository extends JpaRepository<PlantMedia, PlantMediaId> {
    Long countById_PlantId(Long id);

    Boolean existsById_PlantId(Long id);

    @Query("SELECT pm.id.mediaId FROM PlantMedia pm WHERE pm.id.plantId = :plantId")
    List<Long> findMediaIdsByPlantId(@Param("plantId") Long plantId);

    List<Media> findMediaById_PlantId(Long plantId);

    @Query("SELECT pm.id.mediaId FROM PlantMedia pm WHERE pm.id.plantId = :plantId AND pm.isFeatured = true ")
    Long findMediaIdFeaturedByPlantId(@Param("plantId") Long plantId);

    @Modifying
    @Query("DELETE FROM PlantMedia pm WHERE pm.id.mediaId = :mediaId")
    void deleteAllById_MediaId(@Param("mediaId") Long mediaId);

    List<PlantMedia> findByIdPlantIdInAndIsFeaturedTrue(List<Long> plantIds);
}
