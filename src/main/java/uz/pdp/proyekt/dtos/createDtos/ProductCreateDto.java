package uz.pdp.proyekt.dtos.createDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductCreateDto {
    private String name;
    private Integer oldCount;
    private Integer nowCount;
    private Double price;
    private String description;
    private UUID categoryId;
}
