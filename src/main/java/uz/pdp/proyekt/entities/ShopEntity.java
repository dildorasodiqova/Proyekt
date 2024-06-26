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
@Entity(name = "ShopEntity")
@Table(name = "shop_entity")
public class ShopEntity extends BaseEntity {
    @Column(unique = true)
    private String name;

    private String aboutUs;



}
