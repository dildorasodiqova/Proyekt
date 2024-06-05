package uz.pdp.proyekt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.proyekt.entities.ProductLikeEntity;

import java.util.UUID;

public interface ProductLikeRepository extends JpaRepository<ProductLikeEntity, UUID> {
}
