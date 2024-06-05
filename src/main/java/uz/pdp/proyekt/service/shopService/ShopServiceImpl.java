package uz.pdp.proyekt.service.shopService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.dtos.createDtos.ShopCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.ShopResponseDto;
import uz.pdp.proyekt.entities.ShopEntity;
import uz.pdp.proyekt.exception.DataAlreadyExistsException;
import uz.pdp.proyekt.exception.DataNotFoundException;
import uz.pdp.proyekt.repositories.ShopRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService{
    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;

    @Override
    public BaseResponse<ShopResponseDto> create(ShopCreateDto dto) {
        Optional<ShopEntity> shop = shopRepository.findByName(dto.getName());
        if (shop.isPresent()){
            throw new DataAlreadyExistsException("This shop's name already exists !");
        }
        ShopEntity save = shopRepository.save(new ShopEntity(dto.getName(), dto.getAboutUs()));
        return BaseResponse.<ShopResponseDto>builder()
                .data(modelMapper.map(save, ShopResponseDto.class))
                .success(true)
                .message("success")
                .code(200)
                .build();
    }

    @Override
    public BaseResponse<ShopResponseDto> getById(UUID shopId) {
        ShopEntity entity = shopRepository.findById(shopId).orElseThrow(() -> new DataNotFoundException("Shop not found"));
        return BaseResponse.<ShopResponseDto>builder()
                .data(modelMapper.map(entity, ShopResponseDto.class))
                .success(true)
                .message("success")
                .code(200)
                .build();
    }



    @Override
    public BaseResponse<String> delete(UUID shopId) {
        int updatedRows = shopRepository.deactivateShop(shopId);
        if (updatedRows == 0) {
            throw new DataNotFoundException("Shop not found with id: " + shopId);
        }
        return BaseResponse.<String>builder()
                .data("Shop deactivated")
                .success(true)
                .message("success")
                .code(200)
                .build();
    }

    @Override
    public BaseResponse<PageImpl<ShopResponseDto>> getAll(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ShopEntity> shopPage = shopRepository.findAllByIsActiveTrue(pageable);

        List<ShopResponseDto> shopResponseDtoList = shopPage.getContent().stream()
                .map(shop -> modelMapper.map(shop, ShopResponseDto.class))
                .collect(Collectors.toList());

        PageImpl<ShopResponseDto> shopResponseDtoPage = new PageImpl<>(shopResponseDtoList, pageable, shopPage.getTotalElements());

        return BaseResponse.<PageImpl<ShopResponseDto>>builder()
                .data(shopResponseDtoPage)
                .success(true)
                .message("success")
                .code(200)
                .build();
    }




}
