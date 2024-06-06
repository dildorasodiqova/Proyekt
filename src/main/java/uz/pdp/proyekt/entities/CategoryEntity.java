package uz.pdp.proyekt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "CategoryEntity")
@Table(name = "category_entity")
public class CategoryEntity extends BaseEntity {
    @Column(unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private CategoryEntity parentId;


}
