package uz.pdp.proyekt.service.productLikeService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.repositories.ProductLikeRepository;

@Service
@RequiredArgsConstructor
public class ProductLikeServiceImpl implements ProductLikeService{
    private final ProductLikeRepository productLikeRepository;
}
