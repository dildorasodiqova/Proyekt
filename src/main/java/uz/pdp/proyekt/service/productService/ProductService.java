package uz.pdp.proyekt.service.productService;

import uz.pdp.proyekt.dtos.createDtos.ProductCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.ProductResponseDto;
import uz.pdp.proyekt.entities.ProductEntity;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductResponseDto save(ProductCreateDto dto);

    BaseResponse<List<ProductResponseDto>> allOfCategory(UUID categoryId, int size, int page);


    BaseResponse<List<ProductResponseDto>> search(String word);

    ProductResponseDto update(UUID productId, ProductCreateDto dto);
    BaseResponse<ProductResponseDto> getById(UUID productId);
    BaseResponse<String> delete (UUID productId);

    ProductEntity findById(UUID productId);
}
