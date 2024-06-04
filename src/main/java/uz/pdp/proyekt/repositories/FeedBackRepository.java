package uz.pdp.proyekt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.proyekt.entities.FeedbackEntity;

import java.util.List;
import java.util.UUID;

public interface FeedBackRepository extends JpaRepository<FeedbackEntity, UUID> {
    List<FeedbackEntity> findAllByProductId(UUID product_id);
}
