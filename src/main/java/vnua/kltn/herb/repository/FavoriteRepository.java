package vnua.kltn.herb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.Diseases;
import vnua.kltn.herb.entity.Favorite;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long>, JpaSpecificationExecutor<Favorite> {
    Optional<Favorite> findByUserIdAndFavoritableTypeAndFavoritableId(Long userId, Integer favoritableType, Long favoritableId);

    Optional<Favorite> findByuserIdAndFavoritableType(Long userId, Integer favoritableType);

    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.favoritableType = :type AND f.favoritableId = :id")
    int countByFavoritableTypeAndFavoritableId(@Param("type") Integer type,@Param("id") Long id);

    void deleteByUserIdAndFavoritableTypeAndFavoritableId(
            Long userId, Integer favoritableType, Long favoritableId);
}
