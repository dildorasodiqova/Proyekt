package uz.pdp.proyekt.service.productLikeService;

import uz.pdp.proyekt.dtos.createDtos.ProductLikeCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.ProductLikeResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductLikeService {
    ProductLikeResponseDto create(ProductLikeCreateDto dto);

    BaseResponse<List<ProductLikeResponseDto>> allOfUser(int size, int page, UUID userId);

    BaseResponse<String> delete(UUID productLikeId);
}
