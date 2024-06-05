package uz.pdp.proyekt.dtos.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderProductResponseDTO {
    private UUID id;
    private UUID orderId;
    private String productName;
    private int count;
    private Double price;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
