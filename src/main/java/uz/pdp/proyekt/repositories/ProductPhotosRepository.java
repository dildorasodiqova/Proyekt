package uz.pdp.proyekt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.proyekt.entities.ProductPhotos;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductPhotosRepository extends JpaRepository<ProductPhotos, UUID> {
    List<ProductPhotos> getByProduct_IdAndIsActiveTrue (UUID productId);
    @Modifying
    @Query("UPDATE product_photos p SET p.isActive = false WHERE p.product.id = :productId")
    int deactivatePhoto(@Param("productId") UUID productId);

}
