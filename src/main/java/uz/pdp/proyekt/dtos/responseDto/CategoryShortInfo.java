package uz.pdp.proyekt.dtos.responseDto;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryShortInfo {
    private UUID id;
    private String name;
}
