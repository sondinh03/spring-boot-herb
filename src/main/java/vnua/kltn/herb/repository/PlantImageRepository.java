package vnua.kltn.herb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.Plant;
import vnua.kltn.herb.entity.PlantImage;

@Repository
public interface PlantImageRepository extends JpaRepository<PlantImage, Long> {

}
