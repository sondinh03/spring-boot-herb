package vnua.kltn.herb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.*;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByArticle(Article article, Pageable pageable);

    Page<Comment> findByUser(User user, Pageable pageable);

    Page<Comment> findByArticleAndParentIsNull(Article article, Pageable pageable);

    List<Comment> findByParent(Comment parent);

    Page<Comment> findByStatus(Integer status, Pageable pageable);
}
