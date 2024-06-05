package uz.pdp.proyekt.service.productLikeService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.dtos.createDtos.ProductLikeCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.ProductLikeResponseDto;
import uz.pdp.proyekt.dtos.responseDto.ProductResponseDto;
import uz.pdp.proyekt.dtos.responseDto.UserShortInfo;
import uz.pdp.proyekt.entities.ProductLikeEntity;
import uz.pdp.proyekt.exception.BadRequestException;
import uz.pdp.proyekt.repositories.ProductLikeRepository;
import uz.pdp.proyekt.service.productService.ProductService;
import uz.pdp.proyekt.service.userService.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductLikeServiceImpl implements ProductLikeService{
    private final ProductLikeRepository productLikeRepository;
    private final UserService userService;
    private final ProductService productService;


    @Override
    public ProductLikeResponseDto create(ProductLikeCreateDto dto) {
        Optional<ProductLikeEntity> existingProductLike = productLikeRepository.getAllByUserIdAndProductId(dto.getUserId(), dto.getProductId());
        if (existingProductLike.isPresent()) {
            throw new BadRequestException("You have already liked this product!");
        }

        ProductLikeEntity productLikeEntity = productLikeRepository.save(
                new ProductLikeEntity(
                        userService.findById(dto.getUserId()),
                        productService.findById(dto.getProductId())
                )
        );

        return parse(productLikeEntity);
    }


    @Override
    public BaseResponse<List<ProductLikeResponseDto>> allOfUser(int size, int page, UUID userId) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return  BaseResponse.<List<ProductLikeResponseDto>>builder()
                .data(parse(productLikeRepository.getAllByUserId(userId, pageRequest)))
                .success(true)
                .message("success")
                .code(200)
                .build();
    }

    @Override
    public BaseResponse<String> delete(UUID productLikeId) {
        productLikeRepository.deleteById(productLikeId);
        return BaseResponse.<String>builder()
                .data("Deleted")
                .success(true)
                .message("success")
                .code(200)
                .build();
    }
    private ProductLikeResponseDto parse(ProductLikeEntity entity){
        ProductResponseDto product = productService.getById(entity.getProduct().getId()).getData();
        return new ProductLikeResponseDto(entity.getId(),
                new UserShortInfo(entity.getUser().getId(), entity.getUser().getName(), entity.getUser().getUsername()),
                product, entity.getCreatedDate(),
                entity.getUpdateDate());

    }


    private List<ProductLikeResponseDto> parse(List<ProductLikeEntity> list) {
        return list.stream()
                .map(like -> {
                    ProductResponseDto product = productService.getById(like.getProduct().getId()).getData();
                    return new ProductLikeResponseDto(
                            like.getId(),
                            new UserShortInfo(
                                    like.getUser().getId(),
                                    like.getUser().getName(),
                                    like.getUser().getUsername()
                            ),
                            product,
                            like.getCreatedDate(),
                            like.getUpdateDate()
                    );
                })
                .collect(Collectors.toList());
    }

}
