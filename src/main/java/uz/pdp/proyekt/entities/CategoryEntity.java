package uz.pdp.proyekt.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private String name;
    private UUID userId;
}
