package uz.pdp.proyekt.service.productPhotosService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.entities.ProductPhotos;
import uz.pdp.proyekt.repositories.ProductPhotosRepository;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ProductPhotosServiceImpl implements ProductPhotosService{
    private final ProductPhotosRepository productPhotosRepository;
    @Override
    public List<ProductPhotos> getByProductId(UUID productId) {
        return productPhotosRepository.getByProduct_Id(productId);
    }

    @Override
    public ProductPhotos save(ProductPhotos productPhotos) {
        return productPhotosRepository.save(productPhotos);
    }

}
