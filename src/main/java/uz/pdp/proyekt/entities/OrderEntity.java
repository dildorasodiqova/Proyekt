package uz.pdp.proyekt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.proyekt.enums.OrderStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "order")
@Table(name = "order")
public class OrderEntity extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity user;

    @Column(nullable = false)
    private double price; ///total price

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProductEntity> orderProducts;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;

    private boolean delivery; /// true bo'lsa uyiga yetkazgan bo'ladi, false bo'lsa punkitga

    public OrderEntity(UserEntity user, double price, OrderStatus orderStatus, boolean delivery) {
        this.user = user;
        this.price = price;
        this.status = orderStatus;
        this.delivery = delivery;
    }
}
