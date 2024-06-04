package uz.pdp.proyekt.service.productPhotosService;


import uz.pdp.proyekt.entities.ProductPhotos;

import java.util.List;
import java.util.UUID;

public interface ProductPhotosService {
    List<ProductPhotos> getByProductId(UUID productId);

    ProductPhotos save(ProductPhotos productPhotos);
}
