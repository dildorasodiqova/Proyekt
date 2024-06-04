package uz.pdp.proyekt.service.orderProductService;

import uz.pdp.uzummarket.Dto.requestSTO.OrderProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.OrderProductResponseDTO;
import uz.pdp.uzummarket.entity.Order;
import uz.pdp.uzummarket.entity.OrderProduct;

import java.util.List;

public interface OrderProductService {
    List<OrderProductResponseDTO> save(Order order, List<OrderProductCreateDTO> products);
    List<OrderProductResponseDTO> parse(List<OrderProduct> products);
    List<OrderProductResponseDTO> update(List<OrderProductCreateDTO> products, Order order);
}
