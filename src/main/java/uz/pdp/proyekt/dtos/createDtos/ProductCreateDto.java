package uz.pdp.proyekt.dtos.createDtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductCreateDto {
    @NotBlank(message = "Name field cannot be empty")
    private String name;

    @Column(nullable = false)
    private Integer oldCount;

    private Integer nowCount;

    @Column(nullable = false)
    private Double price;

    private String description;

    @Column(nullable = false)
    private UUID categoryId;

    @Column(nullable = false)
    private UUID shopId;

    private List<UUID> photos;
}
