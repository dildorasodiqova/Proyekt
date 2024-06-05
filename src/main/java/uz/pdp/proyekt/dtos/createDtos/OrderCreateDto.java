package uz.pdp.proyekt.dtos.createDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderCreateDto {
    private UUID userId;
    private List<OrderProductCreateDTO> products;
}
