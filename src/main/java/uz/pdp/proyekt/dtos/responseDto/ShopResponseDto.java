package uz.pdp.proyekt.dtos.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopResponseDto {
    private UUID id;
    private String name;
    private String aboutUs;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
