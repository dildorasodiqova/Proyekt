package uz.pdp.proyekt.service.orderService;

import uz.pdp.proyekt.dtos.createDtos.OrderCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.OrderResponseDto;

import java.util.UUID;

public interface OrderService {
    OrderResponseDto add(OrderCreateDto dto);
    OrderResponseDto getById(UUID orderId);
    OrderResponseDto cancel(UUID orderId);
    BaseResponse<OrderResponseDto> update(UUID orderId, OrderCreateDto dto);
}
