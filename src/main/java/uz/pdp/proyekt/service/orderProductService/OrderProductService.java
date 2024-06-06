package uz.pdp.proyekt.service.orderProductService;

import uz.pdp.proyekt.dtos.createDtos.OrderProductCreateDTO;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.OrderProductResponseDTO;
import uz.pdp.proyekt.entities.OrderEntity;
import uz.pdp.proyekt.entities.OrderProductEntity;


import java.util.List;
import java.util.UUID;

public interface OrderProductService {
    BaseResponse<List<OrderProductResponseDTO>> save(UUID orderId, List<OrderProductCreateDTO> products);
    List<OrderProductResponseDTO> parse(List<OrderProductEntity> products);
    BaseResponse<List<OrderProductResponseDTO>> update(List<OrderProductCreateDTO> products, OrderEntity order);
}
