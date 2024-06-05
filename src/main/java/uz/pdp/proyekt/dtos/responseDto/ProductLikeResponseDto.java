package uz.pdp.proyekt.dtos.responseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.proyekt.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductLikeResponseDto {
    private UUID id;

    private UserShortInfo user;

    private ProductResponseDto product;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
