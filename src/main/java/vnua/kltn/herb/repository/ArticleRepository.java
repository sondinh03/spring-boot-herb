package vnua.kltn.herb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.*;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    @Modifying
    @Query("UPDATE Article a SET a.favoritesCount = a.favoritesCount + 1 WHERE a.id = :id")
    void incrementFavoritesCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Article a SET a.favoritesCount = a.favoritesCount - 1 WHERE a.id = :id")
    void decrementFavoritesCount(@Param("id") Long id);
}
