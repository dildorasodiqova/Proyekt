package uz.pdp.proyekt.service.orderProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.requestSTO.OrderProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.OrderProductResponseDTO;
import uz.pdp.uzummarket.entity.Order;
import uz.pdp.uzummarket.entity.OrderProduct;
import uz.pdp.uzummarket.entity.Product;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.OrderProductRepository;
import uz.pdp.uzummarket.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    @Override
    public List<OrderProductResponseDTO> save(Order order, List<OrderProductCreateDTO> products) {
        List<OrderProduct> pr = products.stream().map(item -> {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> new DataNotFoundException("Product not found"));
            product.setCount(product.getCount() - item.getCount());
            productRepository.save(product);
            return new OrderProduct(order, product, item.getCount(), item.getPrice());
        }).toList();
        orderProductRepository.saveAll(pr);
        return parse(pr);
    }

    public List<OrderProductResponseDTO> parse(List<OrderProduct> products) {
        return products
                .stream()
                .map(item -> new OrderProductResponseDTO(item.getOrder().getId(), item.getProduct().getName(), item.getCount(), item.getPrice()))
                .toList();
    }

    @Override
    public List<OrderProductResponseDTO> update(List<OrderProductCreateDTO> products, Order order) {
        List<OrderProduct> orderProducts = order.getOrderProducts();
        List<UUID> oldProducts = orderProducts.stream().map(OrderProduct::getId).toList();
        List<UUID> newProducts = products.stream().map(OrderProductCreateDTO::getProductId).toList();
        List<OrderProduct> saveAll = new ArrayList<>();
        products.forEach(item -> {
            if (!oldProducts.contains(item.getProductId())){
                Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> new DataNotFoundException("Product not found"));
                saveAll.add(new OrderProduct(order, product, item.getCount(), item.getPrice()));
            }else {
                Optional<OrderProduct> first = orderProducts.stream().filter(product -> product.getProduct().getId().equals(item.getProductId())).findFirst();
               if (first.isPresent()){
                   OrderProduct orderProduct = first.get();
                   orderProduct.setCount(item.getCount());
                   orderProduct.setPrice(item.getPrice());
                   saveAll.add(orderProduct);
               }
            }
        });
        List<OrderProduct> deleteProducts = orderProducts.stream().filter(item -> !newProducts.contains(item.getId())).toList();
        orderProductRepository.deleteAll(deleteProducts);
        List<OrderProduct> list = orderProductRepository.saveAll(saveAll);
        return parse(list);
    }


}
