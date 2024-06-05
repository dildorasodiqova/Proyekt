package uz.pdp.proyekt.dtos.createDtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopCreateDto {
    @Column(nullable = false)
    private String name;

    private String aboutUs;
}
