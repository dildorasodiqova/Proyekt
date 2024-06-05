package uz.pdp.proyekt.dtos.createDtos;

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
    private UUID userId;
    private UUID productId;
}
