package uz.pdp.proyekt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProductEntity")
@Table(name = "product")
public class ProductEntity extends BaseEntity{
    @Column(unique = true)
    private String name;

    private Integer oldCount;
    private Integer nowCount;

    @Column(nullable = false)
    private Double price;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private ShopEntity shops;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;





}
