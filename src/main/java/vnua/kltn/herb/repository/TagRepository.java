package vnua.kltn.herb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.Media;
import vnua.kltn.herb.entity.Tag;
import vnua.kltn.herb.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findBySlug(String slug);
    Optional<Tag> findByName(String name);
}
