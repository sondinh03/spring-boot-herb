package vnua.kltn.herb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.Category;
import vnua.kltn.herb.entity.Media;
import vnua.kltn.herb.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByUploadedBy(User uploadedBy);
    List<Media> findByFileType(Integer fileType);
}
