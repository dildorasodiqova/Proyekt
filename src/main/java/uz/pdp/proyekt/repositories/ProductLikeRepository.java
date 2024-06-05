package uz.pdp.proyekt.repositories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.proyekt.entities.ProductLikeEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductLikeRepository extends JpaRepository<ProductLikeEntity, UUID> {
    Optional<ProductLikeEntity> getAllByUserIdAndProductId(UUID user_id, UUID product_id);
    List<ProductLikeEntity> getAllByUserId(UUID user_id, PageRequest pageRequest);
}
