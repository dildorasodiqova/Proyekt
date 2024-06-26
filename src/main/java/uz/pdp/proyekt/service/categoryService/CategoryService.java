package uz.pdp.proyekt.service.categoryService;

import org.springframework.data.domain.PageImpl;
import uz.pdp.proyekt.dtos.createDtos.CategoryCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.CategoryResponseDto;
import uz.pdp.proyekt.entities.CategoryEntity;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    BaseResponse<CategoryResponseDto> create(CategoryCreateDto dto, UUID uuid);

    BaseResponse<List<CategoryResponseDto>> getAll(int page, int size);

    CategoryEntity findById(UUID categoryId);
    BaseResponse<CategoryResponseDto> getById(UUID categoryId);
    BaseResponse<PageImpl<CategoryResponseDto>> subCategories(UUID parentId, int page, int size );

    BaseResponse<List<CategoryResponseDto>> firstCategories();
    BaseResponse<String> update(UUID categoryId, CategoryCreateDto dto);

    BaseResponse<String> delete(UUID categoryId);

}
