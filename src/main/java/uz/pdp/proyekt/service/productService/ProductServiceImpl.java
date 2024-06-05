package uz.pdp.proyekt.service.productService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.proyekt.dtos.createDtos.ProductCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.CategoryShortInfo;
import uz.pdp.proyekt.dtos.responseDto.ProductResponseDto;
import uz.pdp.proyekt.entities.AttachmentEntity;
import uz.pdp.proyekt.entities.CategoryEntity;
import uz.pdp.proyekt.entities.ProductEntity;
import uz.pdp.proyekt.entities.ProductPhotos;
import org.postgresql.util.PSQLException;
import uz.pdp.proyekt.exception.DataAlreadyExistsException;
import uz.pdp.proyekt.exception.DataNotFoundException;
import uz.pdp.proyekt.repositories.AttachmentRepository;
import uz.pdp.proyekt.repositories.ProductRepository;
import uz.pdp.proyekt.service.categoryService.CategoryService;
import uz.pdp.proyekt.service.productPhotosService.ProductPhotosService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductPhotosService productPhotosService;
    private final AttachmentRepository attachmentRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    @Transactional
    @Override
    public ProductResponseDto save(ProductCreateDto dto){
        System.out.println("dto = " + dto);
        if (productRepository.findByName(dto.getName()).isPresent()) {
            throw new DataAlreadyExistsException("Product already exists");
        }
        CategoryEntity id = categoryService.findById(dto.getCategoryId());
        ProductEntity product = new ProductEntity(dto.getName(),
                dto.getOldCount(),
                dto.getNowCount(),
                dto.getPrice(),
                dto.getDescription(),
                id);

        ProductEntity save = productRepository.save(product);

        List<AttachmentEntity> photos = attachmentRepository.findAllById(dto.getPhotos());
        List<ProductPhotos> list = new ArrayList<>();
        for (int i = 0; i < photos.size(); i++) {
            ProductPhotos productPhotos = new ProductPhotos();
            productPhotos.setProduct(save);
            productPhotos.setPhoto(photos.get(i));
            productPhotos.setOrderIndex(i);
            productPhotosService.save(productPhotos);
            list.add(productPhotos);
        }

        List<UUID> photosId = getPhotosId(list);

        ProductResponseDto map = modelMapper.map(save, ProductResponseDto.class);
        map.setCategory(new CategoryShortInfo(save.getCategory().getId(), save.getCategory().getName()));
        map.setPhotos(photosId);
        return map;
    }





    @Transactional
    @Override
    public BaseResponse<List<ProductResponseDto>> allOfCategory(UUID categoryId, int size, int page) {
        PageRequest pageRequest =  PageRequest.of(page, size);
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
    public ProductResponseDto update(UUID productId, ProductCreateDto dto) {
        Optional<ProductEntity> byId = productRepository.findById(productId);
        if (byId.isEmpty()){
            throw new DataNotFoundException("");
        }
        ProductEntity product = byId.get();
        product.setPrice(dto.getPrice());
        product.setNowCount(dto.getNowCount());
        product.setOldCount(dto.getOldCount());
        product.setDescription(dto.getDescription());
        productRepository.save(product);
        return modelMapper.map(product, ProductResponseDto.class);

    }

    @Transactional
    @Override
    public BaseResponse<ProductResponseDto> getById(UUID productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found!"));

        List<ProductPhotos> photos = productPhotosService.getByProductId(product.getId());
        List<UUID> photosId = getPhotosId(photos);

        ProductResponseDto map = modelMapper.map(product, ProductResponseDto.class);
        map.setPhotos(photosId);
        map.setCategory(new CategoryShortInfo(product.getCategory().getId(), product.getCategory().getName()));

        return  BaseResponse.<ProductResponseDto>builder()
                .data(map)
                .message("success")
                .code(200)
                .success(true)
                .build();

    }




    public List<UUID> getPhotosId(List<ProductPhotos> productPhotos) {
        List<UUID> list = new ArrayList<>();
        for (ProductPhotos productPhoto : productPhotos) {
            list.add(productPhoto.getPhoto().getId());
        }
        return list;
    }

    @Override
    public BaseResponse<String> delete(UUID productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found"));
        product.setIsActive(false);
        productRepository.save(product);
        return BaseResponse.<String>builder()
                .data("Successfully")
                .success(true)
                .message("success")
                .code(200)
                .build();
    }




    public List<ProductResponseDto> parse(List<ProductEntity> products) {
        List<ProductResponseDto> list = new ArrayList<>();
        for (ProductEntity product : products) {
            ProductResponseDto map = modelMapper.map(product,ProductResponseDto.class);
            map.setId(product.getId());
            List<ProductPhotos> byProductId = productPhotosService.getByProductId(product.getId());
            List<UUID> photosId = getPhotosId(byProductId);
            map.setPhotos(photosId);
            list.add(map);
        }
        return list;
    }


    @Override
    public ProductEntity findById(UUID productId) {
        return productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found !"));
    }
}
