package uz.pdp.proyekt.service.productPhotosService;


import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.entities.ProductPhotos;

import java.util.List;
import java.util.UUID;
import java.util.prefs.BackingStoreException;

public interface ProductPhotosService {
    BaseResponse<List<ProductPhotos>> getByProductId(UUID productId);

    BaseResponse<ProductPhotos> save(ProductPhotos productPhotos);

    BaseResponse<String> delete(UUID productId);


}
