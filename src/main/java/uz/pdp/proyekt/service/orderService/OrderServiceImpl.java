package uz.pdp.proyekt.service.orderService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.dtos.createDtos.OrderCreateDto;
import uz.pdp.proyekt.dtos.createDtos.OrderProductCreateDTO;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.OrderProductResponseDTO;
import uz.pdp.proyekt.dtos.responseDto.OrderResponseDto;
import uz.pdp.proyekt.entities.OrderEntity;
import uz.pdp.proyekt.entities.OrderProductEntity;
import uz.pdp.proyekt.entities.UserEntity;
import uz.pdp.proyekt.exception.DataNotFoundException;
import uz.pdp.proyekt.repositories.OrderRepository;
import uz.pdp.proyekt.repositories.UserRepository;
import uz.pdp.proyekt.service.orderProductService.OrderProductService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static uz.pdp.proyekt.enums.OrderStatus.CANCELLED;
import static uz.pdp.proyekt.enums.OrderStatus.NEW;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderProductService orderProductService;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderResponseDto add(OrderCreateDto dto) {
        UserEntity user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new DataNotFoundException("User not found"));
        double price = 0;
        for (OrderProductCreateDTO product : dto.getProducts()) {
            price += product.getPrice();
        }
        OrderEntity order = new OrderEntity(user, price, NEW, false);
        orderRepository.save(order);
        List<OrderProductResponseDTO> save = orderProductService.save(order, dto.getProducts()).getData();
        return parse(order, save);
    }

    @Override
    public OrderResponseDto getById(UUID orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order not found "));
        List<OrderProductEntity> orderProducts = order.getOrderProducts();

        List<OrderProductResponseDTO> parse = orderProductService.parse(orderProducts);
        OrderResponseDto map = modelMapper.map(order, OrderResponseDto.class);
        map.setOrderProducts(parse);
       return map;
    }




    @Override
    public OrderResponseDto cancel(UUID orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order not found"));
        orderRepository.updateStatus(order.getId(), CANCELLED);
        List<OrderProductResponseDTO> parse = orderProductService.parse(order.getOrderProducts());
        return parse(order, parse);
    }




    @Override
    public BaseResponse<OrderResponseDto> update(UUID orderId, OrderCreateDto dto) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));

        double price = dto.getProducts().stream()
                .mapToDouble(OrderProductCreateDTO::getPrice)
                .sum();
        order.setPrice(price);
        orderRepository.save(order);

        List<OrderProductResponseDTO> update = orderProductService.update(dto.getProducts(), order).getData();

        return BaseResponse.<OrderResponseDto>builder()
                .data(parse(order, update))
                .success(true)
                .message("success")
                .code(200)
                .build();
    }


    private OrderResponseDto parse(OrderEntity order, List<OrderProductResponseDTO> save) {
        return new OrderResponseDto(order.getId(), order.getUser().getId(), order.getPrice(), save, order.getCreatedDate(), order.getUpdateDate());
    }
}
