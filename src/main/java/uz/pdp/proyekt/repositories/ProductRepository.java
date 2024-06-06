package uz.pdp.proyekt.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.proyekt.entities.ProductEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Query(value = """
                SELECT p FROM ProductEntity p where p.category.name ilike '%' || :keyword || '%' or p.name ilike '%' || :keyword || '%'
                """
    )
    List<ProductEntity> searchByProductNameOrCategoryName(@Param("keyword") String keyword);

    Optional<Object> findByName(String name);

    Page<ProductEntity> getAllByCategoryId(UUID category_id, Pageable pageable);

    Page<ProductEntity> getAllByShops_Id(UUID shops_id, Pageable pageable);

    @Modifying
    @Query("UPDATE ProductEntity p SET p.isActive = false WHERE p.id = :productId")
    int deactivateProduct(@Param("productId") UUID productId);

}
