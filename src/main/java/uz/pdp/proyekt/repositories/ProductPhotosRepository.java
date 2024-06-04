package uz.pdp.proyekt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.proyekt.entities.ProductPhotos;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductPhotosRepository extends JpaRepository<ProductPhotos, UUID> {
    List<ProductPhotos> getByProduct_Id (UUID productId);
}
