package uz.pdp.proyekt.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.proyekt.entities.ShopEntity;

import java.util.Optional;
import java.util.UUID;

public interface ShopRepository extends JpaRepository<ShopEntity, UUID> {
    Optional<ShopEntity> findByName(String name);
    @Modifying
    @Query("UPDATE ShopEntity s SET s.isActive = false WHERE s.id = :shopId")
    int deactivateShop(@Param("shopId") UUID shopId);

    Page<ShopEntity> findAllByIsActiveTrue(Pageable pageable);
}
