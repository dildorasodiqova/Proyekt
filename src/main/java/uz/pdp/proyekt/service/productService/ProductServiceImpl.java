package uz.pdp.proyekt.service.productService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.proyekt.dtos.createDtos.ProductCreateDto;
import uz.pdp.proyekt.dtos.responseDto.CategoryShortInfo;
import uz.pdp.proyekt.dtos.responseDto.ProductResponseDto;
import uz.pdp.proyekt.entities.AttachmentEntity;
import uz.pdp.proyekt.entities.CategoryEntity;
import uz.pdp.proyekt.entities.ProductEntity;
import uz.pdp.proyekt.entities.ProductPhotos;
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
    public ProductResponseDto save(ProductCreateDto dto) {
//        ProductResponseDto product = null;
//        try {
//            product = createProduct(dto);
//        } catch (PSQLException e) {
//            throw new DataNotFoundException("Product already exists");
//        }
//        return BaseResponse.<ProductResponseDto>builder()
//                .code(200)
//                .message("success")
//                .success(true)
//                .data(product)
//                .build();
    }


    @Override
    public List<ProductResponseDto> allOfCategory(UUID categoryId, int size, int page) {
        PageRequest pageRequest =  PageRequest.of(page, size);
        List<ProductEntity> all = productRepository.getAllByCategoryId(categoryId, pageRequest).stream().toList();
        return parse(all);

    }

    @Override
    public List<ProductResponseDto> search(String word) {
        List<ProductEntity> products = productRepository.searchByProductNameOrCategoryName(word);
        return parse(products);
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
    public ProductResponseDto getById(UUID productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found!"));
        ProductResponseDto productResponseDTO = new ProductResponseDto();
        List<ProductPhotos> photos = productPhotosService.getByProductId(product.getId());

        List<UUID> photosId = getPhotosId(photos);

        productResponseDTO.setPhotos(photosId);
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setNowCount(product.getNowCount());
        productResponseDTO.setOldCount(product.getOldCount());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setCategory(new CategoryShortInfo(product.getCategory().getId(), product.getCategory().getName()));
        productResponseDTO.setName(product.getName());
        productResponseDTO.setId(product.getId());

        return productResponseDTO;

    }



    @Transactional
    public List<UUID> getPhotosId(List<ProductPhotos> productPhotos) {
        List<UUID> list = new ArrayList<>();
        for (ProductPhotos productPhoto : productPhotos) {
            list.add(productPhoto.getPhoto().getId());
        }
        return list;
    }

    @Override
    public String delete(UUID productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found"));
        product.setIsActive(false);
        productRepository.save(product);
        return "Successfully";
    }



    @Transactional
    public ProductResponseDto createProduct(ProductCreateDto dto){
        System.out.println("dto = " + dto);
        if (productRepository.findByName(dto.getName()).isPresent()) {
            throw new DataAlreadyExistsException("Product already exists");
        }
        CategoryEntity id = categoryService.getById(dto.getCategoryId());
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

        return new ProductResponseDto(
                save.getId(),
                save.getName(),
                save.getOldCount(),
                save.getNowCount(),
                save.getPrice(),
                save.getDescription(),
                new CategoryShortInfo(save.getCategory().getId(), save.getCategory().getName()),
                photosId);
    }

    public List<ProductResponseDto> parse(List<ProductEntity> products) {
/*List<Product> all = productRepository.getAllByUserIdAndCategory_id(userId, categoryId);
        List<ProductResponseDto> responseDtos = new ArrayList<>();
        for (Product product : all) {
            ProductResponseDto map = modelMapper.map(product, ProductResponseDto.class);
            map.setId(product.getId());

            List<ProductPhotos>> photos = productPhotosService.getByProductId(product.getId());
            List<UUID> uuids = new ArrayList<>();
            for (ProductPhotos productPhotos : photos.getData()) {
                uuids.add(productPhotos.getPhoto().getId());
            }
            map.setPhotos(uuids);
            responseDtos.add(map);
        }
     **/
        List<ProductResponseDto> list = new ArrayList<>();
        for (ProductEntity product : products) {
            ProductResponseDto map = modelMapper.map(product,ProductResponseDto.class);
            map.setId(product.getId());
            List<ProductPhotos> byProductId = productPhotosService.getByProductId(product.getId());
            List<UUID> photosId = getPhotosId(byProductId.getData());
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
