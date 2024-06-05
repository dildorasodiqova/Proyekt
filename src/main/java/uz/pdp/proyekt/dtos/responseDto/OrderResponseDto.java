package uz.pdp.proyekt.dtos.responseDto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderResponseDto {
    private UUID id;
    private UUID userId;
    private double price;
    private List<OrderProductResponseDTO> orderProducts;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
