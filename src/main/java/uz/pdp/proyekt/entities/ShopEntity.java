package uz.pdp.proyekt.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ShopEntity")
@Table(name = "shop_entity")
public class ShopEntity extends BaseEntity {

    private String name;
    private String aboutUs;


    @OneToMany()
    private CategoryEntity category;
}
