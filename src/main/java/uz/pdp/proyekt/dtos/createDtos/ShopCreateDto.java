package uz.pdp.proyekt.dtos.createDtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopCreateDto {
    @NotBlank(message = "Name field cannot be empty")
    private String name;

    private String aboutUs;
}
