package uz.pdp.proyekt.service.productLikeService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.dtos.createDtos.ProductLikeCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.ProductLikeResponseDto;
import uz.pdp.proyekt.dtos.responseDto.ProductResponseDto;
import uz.pdp.proyekt.dtos.responseDto.UserShortInfo;
import uz.pdp.proyekt.entities.ProductEntity;
import uz.pdp.proyekt.entities.ProductLikeEntity;
import uz.pdp.proyekt.exception.DataNotFoundException;
import uz.pdp.proyekt.repositories.ProductLikeRepository;
import uz.pdp.proyekt.service.productService.ProductService;
import uz.pdp.proyekt.service.userService.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductLikeServiceImpl implements ProductLikeService{
    private final ProductLikeRepository productLikeRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public ProductLikeResponseDto create(ProductLikeCreateDto dto) {
        Optional<ProductLikeEntity> all = productLikeRepository.getAllByUserIdAndProductId(dto.getUserId(), dto.getProductId());
        if (all.isPresent()){
            productLikeRepository.delete(all.get());
        }
       return parse( productLikeRepository.save(new ProductLikeEntity(userService.findById(dto.getUserId()), productService.findById(dto.getProductId()))));

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
        ProductLikeEntity likeEntity = productLikeRepository.findById(productLikeId).orElseThrow(() -> new DataNotFoundException("Product like not found !"));
        productLikeRepository.delete(likeEntity);
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

    private List<ProductLikeResponseDto> parse(List<ProductLikeEntity> list){
        List<ProductLikeResponseDto> lists = new ArrayList<>();
        for (ProductLikeEntity like : list) {
            ProductResponseDto product = productService.getById(like.getProduct().getId()).getData();
            lists.add(new ProductLikeResponseDto(like.getId(), new UserShortInfo(like.getUser().getId(), like.getUser().getName(), like.getUser().getUsername()),product, like.getCreatedDate(), like.getUpdateDate()));
        }
        return lists;
    }
}
