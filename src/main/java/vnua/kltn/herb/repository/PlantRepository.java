package vnua.kltn.herb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vnua.kltn.herb.entity.Plant;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    Plant findByVietnameseName(String name);

    @Query(value = " FROM Plant p WHERE p.id <> :id AND p.vietnameseName = :name ")
    Plant findByVietnameseNameAndNotId(String name, Long id);

    /*
    @Query(value = " FROM Plant p WHERE (:keyword IS NULL OR " +
            " LOWER(p.vietnameseName) LIKE %:keyword% OR " +
            " LOWER(p.scientificName) LIKE %:keyword%) ")
    Page<Plant> search(@Param("keyword") String keyword, Pageable pageable);

     */

    @Query(value = """
        FROM Plant p 
        WHERE (:keyword IS NULL OR
        LOWER(p.vietnameseName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
         LOWER(p.scientificName) LIKE LOWER(CONCAT('%', :keyword, '%')))
        """)
    Page<Plant> search(@Param("keyword") String keyword, Pageable pageable);



    @Query(value = " FROM Plant p WHERE (:keyword IS NULL OR " +
            " LOWER(p.vietnameseName) LIKE :keyword% )")
    Page<Plant> findByNameWithFirstLetter(@Param("keyword") String keyword, Pageable pageable);
}
