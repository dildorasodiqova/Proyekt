package uz.pdp.proyekt.service.productService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.proyekt.dtos.createDtos.ProductCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.CategoryShortInfo;
import uz.pdp.proyekt.dtos.responseDto.ProductResponseDto;
import uz.pdp.proyekt.entities.*;
import uz.pdp.proyekt.exception.DataAlreadyExistsException;
import uz.pdp.proyekt.exception.DataNotFoundException;
import uz.pdp.proyekt.repositories.AttachmentRepository;
import uz.pdp.proyekt.repositories.ProductRepository;
import uz.pdp.proyekt.service.categoryService.CategoryService;
import uz.pdp.proyekt.service.productPhotosService.ProductPhotosService;
import uz.pdp.proyekt.service.shopService.ShopService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductPhotosService productPhotosService;
    private final AttachmentRepository attachmentRepository;
    private final CategoryService categoryService;
    private final ShopService shopService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public BaseResponse<ProductResponseDto> save(ProductCreateDto dto) {
        if (productRepository.findByName(dto.getName()).isPresent()) {
            throw new DataAlreadyExistsException("Product already exists");
        }

        CategoryEntity category = categoryService.findById(dto.getCategoryId());

        ProductEntity product = new ProductEntity(
                dto.getName(),
                dto.getOldCount(),
                dto.getNowCount(),
                dto.getPrice(),
                dto.getDescription(),
                shopService.findById(dto.getShopId()),
                category
        );

        ProductEntity savedProduct = productRepository.save(product);

        List<AttachmentEntity> photos = attachmentRepository.findAllById(dto.getPhotos());
        List<ProductPhotos> productPhotosList = photos.stream()
                .map(photo -> {
                    ProductPhotos productPhotos = new ProductPhotos();
                    productPhotos.setProduct(savedProduct);
                    productPhotos.setPhoto(photo);
                    productPhotos.setOrderIndex(photos.indexOf(photo));
                    productPhotosService.save(productPhotos);
                    return productPhotos;
                })
                .collect(Collectors.toList());

        List<UUID> photosId = getPhotosId(productPhotosList);

        ProductResponseDto responseDto = modelMapper.map(savedProduct, ProductResponseDto.class);
        responseDto.setCategory(new CategoryShortInfo(category.getId(), category.getName()));
        responseDto.setPhotos(photosId);

        return  BaseResponse.<ProductResponseDto>builder()
                .data(responseDto)
                .message("success")
                .code(200)
                .success(true)
                .build();
    }


    @Transactional
    @Override
    public BaseResponse<List<ProductResponseDto>> allOfCategory(UUID categoryId, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<ProductEntity> all = productRepository.getAllByCategoryId(categoryId, pageRequest).stream().toList();

        return BaseResponse.<List<ProductResponseDto>>builder()
                .data(parse(all))
                .message("success")
                .success(true)
                .code(200)
                .build();

    }

    @Transactional
    @Override
    public BaseResponse<List<ProductResponseDto>> search(String word) {
        List<ProductEntity> products = productRepository.searchByProductNameOrCategoryName(word);
        return BaseResponse.<List<ProductResponseDto>>builder()
                .data(parse(products))
                .success(true)
                .message("success")
                .code(200)
                .build();
    }

    @Transactional
    @Override
    public BaseResponse<ProductResponseDto> update(UUID productId, ProductCreateDto dto) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + productId));

        product.setPrice(dto.getPrice());
        product.setNowCount(dto.getNowCount());
        product.setOldCount(dto.getOldCount());
        product.setDescription(dto.getDescription());

        List<AttachmentEntity> photos = attachmentRepository.findAllById(dto.getPhotos());
        List<ProductPhotos> list = new ArrayList<>();
        for (int i = 0; i < photos.size(); i++) {
            ProductPhotos productPhotos = new ProductPhotos();
            productPhotos.setProduct(product);
            productPhotos.setPhoto(photos.get(i));
            productPhotos.setOrderIndex(i);
            productPhotosService.save(productPhotos);
            list.add(productPhotos);
        }

        productRepository.save(product);
        List<UUID> photosId = list.stream()
                .map(photo -> photo.getPhoto().getId())
                .collect(Collectors.toList());

        ProductResponseDto responseDto = modelMapper.map(product, ProductResponseDto.class);
        responseDto.setPhotos(photosId);

        return  BaseResponse.<ProductResponseDto>builder()
                .data(responseDto)
                .message("success")
                .code(200)
                .success(true)
                .build();
    }

    @Transactional
    @Override
    public BaseResponse<ProductResponseDto> getById(UUID productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found!"));

        List<ProductPhotos> photos = productPhotosService.getByProductId(product.getId()).getData();
        List<UUID> photosId = getPhotosId(photos);

        ProductResponseDto map = modelMapper.map(product, ProductResponseDto.class);
        map.setPhotos(photosId);
        map.setCategory(new CategoryShortInfo(product.getCategory().getId(), product.getCategory().getName()));

        return BaseResponse.<ProductResponseDto>builder()
                .data(map)
                .message("success")
                .code(200)
                .success(true)
                .build();

    }


    public List<UUID> getPhotosId(List<ProductPhotos> productPhotos) {
        return productPhotos.stream()
                .map(productPhoto -> productPhoto.getPhoto().getId())
                .collect(Collectors.toList());
    }


    @Override
    public BaseResponse<String> delete(UUID productId) {
        int updatedRows = productRepository.deactivateProduct(productId);
        if (updatedRows == 0) {
            throw new DataNotFoundException("Product not found.");
        }
        productPhotosService.delete(productId);
        return BaseResponse.<String>builder()
                .data("Successfully")
                .success(true)
                .message("success")
                .code(200)
                .build();
    }


    private List<ProductResponseDto> parse(List<ProductEntity> products) {
        return products.stream().map(product -> {
            ProductResponseDto map = modelMapper.map(product, ProductResponseDto.class);
            map.setId(product.getId());
            List<ProductPhotos> byProductId = productPhotosService.getByProductId(product.getId()).getData();
            List<UUID> photosId = getPhotosId(byProductId);
            map.setPhotos(photosId);
            return map;
        }).collect(Collectors.toList());
    }


    @Override
    public ProductEntity findById(UUID productId) {
        return productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found !"));
    }

    @Override
    public BaseResponse<PageImpl<ProductResponseDto>> productsOfShop(UUID shopId, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductEntity> productPage = productRepository.getAllByShops_Id(shopId, pageRequest);

        List<ProductResponseDto> productResponseDtoList = productPage.getContent().stream()
                .map(product -> {
                    ProductResponseDto dto = modelMapper.map(product, ProductResponseDto.class);
                    dto.setCategory(new CategoryShortInfo(product.getCategory().getId(), product.getCategory().getName()));
                    dto.setPhotos(productPhotosService.getByProductId(product.getId()).getData().stream().map(photo -> photo.getPhoto().getId()).collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());

        PageImpl<ProductResponseDto> productResponseDtoPage = new PageImpl<>(productResponseDtoList, pageRequest, productPage.getTotalElements());

        return BaseResponse.<PageImpl<ProductResponseDto>>builder()
                .data(productResponseDtoPage)
                .message("success")
                .success(true)
                .code(200)
                .build();
    }
}

