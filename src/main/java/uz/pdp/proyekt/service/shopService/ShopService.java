package uz.pdp.proyekt.service.shopService;

import org.springframework.data.domain.PageImpl;
import uz.pdp.proyekt.dtos.createDtos.ShopCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.ShopResponseDto;

import java.util.UUID;

public interface ShopService {
    BaseResponse<String> delete(UUID shopId);

    BaseResponse<PageImpl<ShopResponseDto>> getAll(int size, int page);

    BaseResponse<ShopResponseDto> create(ShopCreateDto dto);

    BaseResponse<ShopResponseDto> getById(UUID shopId);
}
