package uz.pdp.proyekt.service.orderService;

import uz.pdp.proyekt.dtos.createDtos.OrderCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.OrderResponseDto;
import uz.pdp.proyekt.entities.OrderEntity;

import java.util.UUID;

public interface OrderService {
    BaseResponse<OrderResponseDto> add(OrderCreateDto dto);

    BaseResponse<OrderResponseDto> getById(UUID orderId);

    BaseResponse<OrderResponseDto> cancel(UUID orderId);

    BaseResponse<OrderResponseDto> update(UUID orderId, OrderCreateDto dto);

    OrderEntity findById(UUID orderId);


}
