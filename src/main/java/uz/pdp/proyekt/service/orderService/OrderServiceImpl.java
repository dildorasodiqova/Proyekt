package uz.pdp.proyekt.service.orderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.requestSTO.OrderCreateDTO;
import uz.pdp.uzummarket.Dto.requestSTO.OrderProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.OrderProductResponseDTO;
import uz.pdp.uzummarket.Dto.responceDTO.OrderResponseDTO;
import uz.pdp.uzummarket.entity.Order;
import uz.pdp.uzummarket.entity.OrderProduct;
import uz.pdp.uzummarket.entity.User;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.OrderProductRepository;
import uz.pdp.uzummarket.repository.OrderRepository;
import uz.pdp.uzummarket.repository.UserRepository;
import uz.pdp.uzummarket.service.orderProductService.OrderProductService;

import java.util.List;
import java.util.UUID;

import static uz.pdp.uzummarket.enums.OrderStatus.CANCELLED;
import static uz.pdp.uzummarket.enums.OrderStatus.NEW;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;
    private final OrderProductService orderProductService;

    @Override
    public OrderResponseDTO add(OrderCreateDTO dto) {
        UUID userId = dto.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
        double price = 0;
        for (OrderProductCreateDTO product : dto.getProducts()) {
            price += product.getPrice();
        }
        Order order = new Order(user, price, NEW, false);
        orderRepository.save(order);
        List<OrderProductResponseDTO> save = orderProductService.save(order, dto.getProducts());
        return parse(order, save);
    }

    @Override
    public OrderResponseDTO getById(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order not found "));
        List<OrderProduct> orderProducts = order.getOrderProducts();

        List<OrderProductResponseDTO> parse = orderProductService.parse(orderProducts);
        return new OrderResponseDTO(order.getUser().getId(), order.getPrice(), parse);
    }

    @Override
    public OrderResponseDTO cancel(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order not found"));
        orderRepository.updateStatus(order.getId(), CANCELLED);
        List<OrderProductResponseDTO> parse = orderProductService.parse(order.getOrderProducts());
        return parse(order, parse);
    }

    @Override
    public OrderResponseDTO update(UUID orderId, OrderCreateDTO dto) {
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

    private OrderResponseDTO parse(Order order, List<OrderProductResponseDTO> save) {
        return new OrderResponseDTO(order.getUser().getId(), order.getPrice(), save);
    }
}
