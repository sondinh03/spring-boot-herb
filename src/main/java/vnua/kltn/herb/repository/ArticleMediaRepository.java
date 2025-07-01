package vnua.kltn.herb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.*;

import java.util.List;

@Repository
public interface ArticleMediaRepository extends JpaRepository<ArticleMedia, ArticleMediaId> {
    List<ArticleMedia> findByIdArticleIdInAndIsFeaturedTrue(List<Long> articleIds);
}
