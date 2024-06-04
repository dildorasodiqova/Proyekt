package uz.pdp.proyekt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.proyekt.entities.OrderProductEntity;

import java.util.UUID;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, UUID> {
}
