package uz.pdp.proyekt.service.categoryService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.dtos.createDtos.CategoryCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.CategoryResponseDto;
import uz.pdp.proyekt.entities.CategoryEntity;
import uz.pdp.proyekt.exception.DataAlreadyExistsException;
import uz.pdp.proyekt.exception.DataNotFoundException;
import uz.pdp.proyekt.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponseDto create(CategoryCreateDto dto, UUID uuid) {
        Optional<CategoryEntity> byName = categoryRepository.findByName(dto.getName());
        if (byName.isPresent()) {
            throw new DataAlreadyExistsException("This category name already exist.");
        }
        if (dto.getParentId() == null) {
            categoryRepository.save(new CategoryEntity(dto.getName(), null));
        }
        CategoryEntity entity = categoryRepository.findById(dto.getParentId()).orElseThrow(() -> new DataNotFoundException("Category not found !"));

        return parse(categoryRepository.save(new CategoryEntity(dto.getName(), entity)));
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
            list.add(modelMapper.map(category, CategoryResponseDto.class));
        }
        return list;
    }

    private CategoryResponseDto parse(CategoryEntity entity) {
        return modelMapper.map(entity, CategoryResponseDto.class);
    }

    @Override
    public CategoryEntity findById(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new DataNotFoundException("Category not found !"));
    }

    @Override
    public CategoryResponseDto getById(UUID categoryId) {
        CategoryEntity categoryBy = categoryRepository.findById(categoryId).orElseThrow(() -> new DataNotFoundException("Category not found"));
        return modelMapper.map(categoryBy, CategoryResponseDto.class);
    }

    @Override
    public BaseResponse<String> update(UUID categoryId, CategoryCreateDto dto) {
        if (categoryRepository.existsAllBy(categoryId, dto.getName())){
            throw  new DataAlreadyExistsException("This category name already exists !");
        }
        CategoryEntity categoryBy = categoryRepository.findById(categoryId).orElseThrow(() -> new DataNotFoundException("Category not found"));
        categoryBy.setName(dto.getName());
        categoryRepository.save(categoryBy);
        return BaseResponse.<String>builder()
                .data("Successfully")
                .success(true)
                .message("success")
                .code(200)
                .build();
    }

    @Override
    public BaseResponse<List<CategoryResponseDto>> subCategories(UUID parentId, int page, int size ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<CategoryEntity> categoriesByParentId = categoryRepository.getCategoryEntitiesByParentId(parentId, pageRequest).stream().toList();
        List<CategoryResponseDto> parse = parse(categoriesByParentId);
        return BaseResponse.<List<CategoryResponseDto>>builder()
                .message("success")
                .data(parse)
                .success(true)
                .code(200)
                .build();
    }

    @Override
    public BaseResponse<List<CategoryResponseDto>> firstCategories() {
        List<CategoryEntity> categories = categoryRepository.getFirstCategory();
        List<CategoryResponseDto> list = new ArrayList<>();
        for (CategoryEntity category : categories) {
            if (category.getIsActive()) {
                list.add(modelMapper.map(category,CategoryResponseDto.class));
            }
        }
        return BaseResponse.<List<CategoryResponseDto>>builder()
                .message("success")
                .success(true)
                .code(200)
                .data(list).build();
    }
}

