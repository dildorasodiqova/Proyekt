package uz.pdp.proyekt.dtos.createDtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductLikeCreateDto {
    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID productId;
}
