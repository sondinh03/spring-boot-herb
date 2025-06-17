package vnua.kltn.herb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.*;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
//    Optional<Article> findBySlug(String slug);
//
//    Page<Article> findByStatus(Integer status, Pageable pageable);
//
//    Page<Article> findByIsFeatured(Boolean isFeatured, Pageable pageable);
//
//    Page<Article> findByAuthor(User author, Pageable pageable);
//
//    Page<Article> findByCategory(Diseases category, Pageable pageable);
//
////    Page<Article> findByPlantsContaining(Plant plant, Pageable pageable);
//
//    @Query("SELECT a FROM Article a WHERE a.title LIKE %?1% OR a.content LIKE %?1% OR a.excerpt LIKE %?1%")
//    Page<Article> search(String keyword, Pageable pageable);
}
