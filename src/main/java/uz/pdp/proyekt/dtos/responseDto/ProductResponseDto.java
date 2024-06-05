package uz.pdp.proyekt.dtos.responseDto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.proyekt.entities.CategoryEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private UUID id;
    private String name;

    private Integer oldCount;
    private Integer nowCount;

    private Double price;

    private String description;
    private CategoryShortInfo category;
    private List<UUID> photos;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
