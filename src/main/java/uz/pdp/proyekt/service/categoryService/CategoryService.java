package uz.pdp.proyekt.service.categoryService;

import uz.pdp.proyekt.dtos.createDtos.CategoryCreateDto;
import uz.pdp.proyekt.dtos.responseDto.CategoryResponseDto;
import uz.pdp.proyekt.entities.CategoryEntity;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDto create(CategoryCreateDto dto, UUID uuid);

    List<CategoryResponseDto> getAll(int page, int size);

    CategoryEntity getById(UUID categoryId);
}
