package vnua.kltn.herb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.*;

import java.util.Optional;

@Repository
public interface ResearchRepository extends JpaRepository<Research, Long> {
    Optional<Research> findBySlug(String slug);

    Page<Research> findByStatus(Integer status, Pageable pageable);

    Page<Research> findByCreatedBy(User createdBy, Pageable pageable);

    Page<Research> findByPlantsContaining(Plant plant, Pageable pageable);

    Page<Research> findByTagsContaining(Tag tag, Pageable pageable);

//    @Query("SELECT r FROM Research r WHERE r.title LIKE %?1% OR r.abstract LIKE %?1% OR r.content LIKE %?1%")
//    Page<Research> search(String keyword, Pageable pageable);
}
