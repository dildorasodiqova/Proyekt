package uz.pdp.proyekt.service.categoryService;

import uz.pdp.proyekt.dtos.createDtos.CategoryCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.CategoryResponseDto;
import uz.pdp.proyekt.entities.CategoryEntity;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDto create(CategoryCreateDto dto, UUID uuid);

    List<CategoryResponseDto> getAll(int page, int size);

    CategoryEntity findById(UUID categoryId);
    CategoryResponseDto getById(UUID categoryId);
    BaseResponse<List<CategoryResponseDto>> subCategories(UUID parentId, int page, int size );

    BaseResponse<List<CategoryResponseDto>> firstCategories();
    BaseResponse<String> update(UUID categoryId, CategoryCreateDto dto);
}
