package uz.pdp.proyekt.dtos.createDtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryCreateDto {
    @Column(nullable = false)
    private String name;

    private UUID parentId;
}
