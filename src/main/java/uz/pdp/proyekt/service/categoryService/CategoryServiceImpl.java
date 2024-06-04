package uz.pdp.proyekt.service.categoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.dtos.createDtos.CategoryCreateDto;
import uz.pdp.proyekt.dtos.responseDto.CategoryResponseDto;
import uz.pdp.proyekt.entities.CategoryEntity;
import uz.pdp.proyekt.exception.DataNotFoundException;
import uz.pdp.proyekt.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDto create(CategoryCreateDto dto, UUID uuid) {
        return null;
    }

    @Override
    public List<CategoryResponseDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<CategoryEntity> all = categoryRepository.findAllByIsActiveTrue(pageRequest).get().toList();
        return parse(all);
    }

    private List<CategoryResponseDto> parse(List<CategoryEntity> all) {
        List<CategoryResponseDto> list = new ArrayList<>();
        for (CategoryEntity category : all) {

        }
    }

    @Override
    public CategoryEntity getById(UUID categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(() -> new DataNotFoundException(""));
    }
}
