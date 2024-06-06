package uz.pdp.proyekt.service.productPhotosService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.entities.ProductPhotos;
import uz.pdp.proyekt.exception.DataNotFoundException;
import uz.pdp.proyekt.repositories.ProductPhotosRepository;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ProductPhotosServiceImpl implements ProductPhotosService{
    private final ProductPhotosRepository productPhotosRepository;
    @Override
    public BaseResponse<List<ProductPhotos>> getByProductId(UUID productId) {
        return BaseResponse.<List<ProductPhotos>>builder()
                .data(productPhotosRepository.getByProduct_IdAndIsActiveTrue(productId))
                .message("success")
                .code(200)
                .success(true)
                .build();


    }

    @Override
    public BaseResponse<ProductPhotos> save(ProductPhotos productPhotos) {
        return BaseResponse.<ProductPhotos>builder()
                .data(productPhotosRepository.save(productPhotos))
                .message("success")
                .code(200)
                .success(true)
                .build();

    }

    @Override
    public BaseResponse<String> delete(UUID photoId) {
        int updatedRows = productPhotosRepository.deactivatePhoto(photoId);
        if (updatedRows == 0) {
            throw new DataNotFoundException("Photo not found.");
        }
        return BaseResponse.<String>builder()
                .data("Photo deactivated")
                .success(true)
                .message("success")
                .code(200)
                .build();
    }

}
