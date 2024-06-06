package uz.pdp.proyekt.dtos.createDtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderProductCreateDTO {
    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private int count;


    private Double price;
}




