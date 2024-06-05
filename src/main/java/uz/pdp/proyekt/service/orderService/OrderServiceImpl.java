package uz.pdp.proyekt.service.orderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.dtos.createDtos.OrderCreateDto;
import uz.pdp.proyekt.dtos.createDtos.OrderProductCreateDTO;
import uz.pdp.proyekt.dtos.responseDto.OrderProductResponseDTO;
import uz.pdp.proyekt.dtos.responseDto.OrderResponseDto;
import uz.pdp.proyekt.entities.OrderEntity;
import uz.pdp.proyekt.entities.OrderProductEntity;
import uz.pdp.proyekt.entities.UserEntity;
import uz.pdp.proyekt.exception.DataNotFoundException;
import uz.pdp.proyekt.repositories.OrderProductRepository;
import uz.pdp.proyekt.repositories.OrderRepository;
import uz.pdp.proyekt.repositories.UserRepository;
import uz.pdp.proyekt.service.orderProductService.OrderProductService;

import java.util.List;
import java.util.UUID;

import static uz.pdp.proyekt.enums.OrderStatus.NEW;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;
    private final OrderProductService orderProductService;

    @Override
    public OrderResponseDto add(OrderCreateDto dto) {
        UUID userId = dto.getUserId();
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
        double price = 0;
        for (OrderProductCreateDTO product : dto.getProducts()) {
            price += product.getPrice();
        }
        OrderEntity order = new OrderEntity(user, price, NEW, false);
        orderRepository.save(order);
        List<OrderProductResponseDTO> save = orderProductService.save(order, dto.getProducts());
        return parse(order, save);
    }

    @Override
    public OrderResponseDto getById(UUID orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order not found "));
        List<OrderProductEntity> orderProducts = order.getOrderProducts();

        List<OrderProductResponseDTO> parse = orderProductService.parse(orderProducts);
        return new OrderResponseDto(order.getUser().getId(), order.getPrice(), parse);
    }

    @Override
    public OrderResponseDto cancel(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order not found"));
        orderRepository.updateStatus(order.getId(), CANCELLED);
        List<OrderProductResponseDTO> parse = orderProductService.parse(order.getOrderProducts());
        return parse(order, parse);
    }

    @Override
    public OrderResponseDto update(UUID orderId, OrderCreateDto dto) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order not found"));
        double price = 0;
        for (OrderProductCreateDTO product : dto.getProducts()) {
            price += product.getPrice();
        }
        order.setPrice(price);
        orderRepository.save(order);
        List<OrderProductResponseDTO> update = orderProductService.update(dto.getProducts(), order);
        return parse(order, update);
    }

    private OrderResponseDto parse(OrderEntity order, List<OrderProductResponseDTO> save) {
        return new OrderResponseDto(order.getUser().getId(), order.getPrice(), save);
    }
}
