package uz.pdp.proyekt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "orderProduct")
@Table(name = "orderProduct")
public class OrderProductEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ProductEntity product;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private Double price;
}
