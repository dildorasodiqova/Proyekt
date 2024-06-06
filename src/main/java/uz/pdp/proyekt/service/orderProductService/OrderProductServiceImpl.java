package uz.pdp.proyekt.service.orderProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.dtos.createDtos.OrderProductCreateDTO;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.OrderProductResponseDTO;
import uz.pdp.proyekt.entities.OrderEntity;
import uz.pdp.proyekt.entities.OrderProductEntity;
import uz.pdp.proyekt.entities.ProductEntity;
import uz.pdp.proyekt.exception.DataNotFoundException;
import uz.pdp.proyekt.repositories.OrderProductRepository;
import uz.pdp.proyekt.repositories.OrderRepository;
import uz.pdp.proyekt.repositories.ProductRepository;
import uz.pdp.proyekt.service.orderService.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public BaseResponse<List<OrderProductResponseDTO>> save(UUID orderId, List<OrderProductCreateDTO> products) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(()-> new DataNotFoundException("Order not found"));
        List<OrderProductEntity> pr = products.stream().map(item -> {
            ProductEntity product = productRepository.findById(item.getProductId()).orElseThrow(() -> new DataNotFoundException("Product not found"));
            product.setNowCount(product.getNowCount() - item.getCount());
            productRepository.save(product);
            return new OrderProductEntity(order, product, item.getCount(), item.getPrice());
        }).toList();
        orderProductRepository.saveAll(pr);
        return BaseResponse.<List<OrderProductResponseDTO>>builder()
                .data(parse(pr))
                .success(true)
                .message("success")
                .code(200)
                .build();
    }




    public List<OrderProductResponseDTO> parse(List<OrderProductEntity> products) {
        return products
                .stream()
                .map(item -> new OrderProductResponseDTO(item.getId(), item.getOrder().getId(), item.getProduct().getName(), item.getCount(), item.getPrice(), item.getCreatedDate(), item.getUpdateDate()))
                .toList();
    }

    @Override
    public BaseResponse<List<OrderProductResponseDTO>> update(List<OrderProductCreateDTO> products, OrderEntity order) {
        List<OrderProductEntity> orderProducts = order.getOrderProducts();
        List<UUID> oldProducts = orderProducts.stream().map(OrderProductEntity::getId).toList();
        List<UUID> newProducts = products.stream().map(OrderProductCreateDTO::getProductId).toList();
        List<OrderProductEntity> saveAll = new ArrayList<>();
        products.forEach(item -> {
            if (!oldProducts.contains(item.getProductId())){
                ProductEntity product = productRepository.findById(item.getProductId()).orElseThrow(() -> new DataNotFoundException("Product not found"));
                saveAll.add(new OrderProductEntity(order, product, item.getCount(), item.getPrice()));
            }else {
                Optional<OrderProductEntity> first = orderProducts.stream().filter(product -> product.getProduct().getId().equals(item.getProductId())).findFirst();
               if (first.isPresent()){
                   OrderProductEntity orderProduct = first.get();
                   orderProduct.setCount(item.getCount());
                   orderProduct.setPrice(item.getPrice());
                   saveAll.add(orderProduct);
               }
            }
        });
        List<OrderProductEntity> deleteProducts = orderProducts.stream().filter(item -> !newProducts.contains(item.getId())).toList();
        orderProductRepository.deleteAll(deleteProducts);
        List<OrderProductEntity> list = orderProductRepository.saveAll(saveAll);
        return BaseResponse.<List<OrderProductResponseDTO>>builder()
                .data(parse(list))
                .success(true)
                .message("success")
                .code(200)
                .build();
    }


}
