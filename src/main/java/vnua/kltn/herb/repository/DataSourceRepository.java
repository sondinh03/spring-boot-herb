package vnua.kltn.herb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.dto.response.PlantWithMedia;
import vnua.kltn.herb.entity.DataSource;
import vnua.kltn.herb.entity.Plant;

import java.util.Optional;

@Repository
public interface DataSourceRepository extends JpaRepository<DataSource, Long>, JpaSpecificationExecutor<DataSource> {
    Boolean existsByName(String name);
}
