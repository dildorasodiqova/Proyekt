package uz.pdp.proyekt.dtos.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FeedbackResponseDTO {
    private UUID id;
    private UUID productId;
    private String userName;
    private int rate; /// yulduzchalari
    private String text;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
