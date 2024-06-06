package uz.pdp.proyekt.dtos.createDtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FeedBackCreateDTO {
    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private UUID userId;

    private int rate;

    @NotBlank(message = "Text cannot be empty")
    private String text;
}
