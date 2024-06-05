package uz.pdp.proyekt.service.categoryService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        if (byName.isPresent()) throw new DataAlreadyExistsException("This category name already exist.");
        CategoryEntity parent = null;
        if (dto.getParentId() != null)
            parent = categoryRepository.findById(dto.getParentId()).orElseThrow(() -> new DataNotFoundException("Category not found !"));
        return parse(categoryRepository.save(new CategoryEntity(dto.getName(), parent)));
    }

    @Override
    public List<CategoryResponseDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<CategoryEntity> all = categoryRepository.findAllByIsActiveTrue(pageRequest).get().toList();
        return parse(all);
    }

    private List<CategoryResponseDto> parse(List<CategoryEntity> all) {
        return all.stream().map(item->modelMapper.map(item,CategoryResponseDto.class)).toList();
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
        return parse(categoryRepository.findById(categoryId).orElseThrow(() -> new DataNotFoundException("Category not found")));
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
    public BaseResponse<PageImpl<CategoryResponseDto>> subCategories(UUID parentId, int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<CategoryEntity> categoriesByParentId = categoryRepository.getCategoryEntitiesByParentId_Id(parentId, pageRequest);
        List<CategoryResponseDto> parse = parse(categoriesByParentId.getContent());
        return BaseResponse.<PageImpl<CategoryResponseDto>>builder()
                .message("success")
                .data(new PageImpl<>(parse, pageRequest, categoriesByParentId.getTotalElements()))
                .success(true)
                .code(200)
                .build();
    }


    @Override
    public BaseResponse<List<CategoryResponseDto>> firstCategories() {
        return BaseResponse.<List<CategoryResponseDto>>builder()
                .message("success")
                .success(true)
                .code(200)
                .data(categoryRepository.getFirstCategory().stream().filter(CategoryEntity::getIsActive).map(this::parse).toList())
                .build();
    }

}

