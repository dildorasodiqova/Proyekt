package uz.pdp.proyekt.service.orderService;

import uz.pdp.uzummarket.Dto.requestSTO.OrderCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.OrderResponseDTO;

import java.util.UUID;

public interface OrderService {
    OrderResponseDTO add(OrderCreateDTO dto);
    OrderResponseDTO getById(UUID orderId);
    OrderResponseDTO cancel(UUID orderId);
    OrderResponseDTO update(UUID orderId, OrderCreateDTO dto);
}
