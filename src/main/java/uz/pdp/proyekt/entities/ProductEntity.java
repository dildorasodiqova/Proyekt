package uz.pdp.proyekt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CategoryEntity category;
    
}
