package uz.pdp.proyekt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.proyekt.entities.OrderEntity;
import uz.pdp.proyekt.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findAllByUserId(UUID user_id) ;
    @Query(value = "update orders o set o.status = ?2 where o.id = ?1")
    @Transactional ////ikkalasi ham bitta orderni update qilishni bosganda ikkinchisini bolishini kutib turadi
    @Modifying //// agar biror hatolik chiqsa ozgarishni oldingi holatiga qaytarib qoyadi
    int updateStatus(UUID orderId, OrderStatus status);
}
