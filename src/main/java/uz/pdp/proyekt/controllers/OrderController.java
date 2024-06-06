package uz.pdp.proyekt.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.proyekt.dtos.createDtos.OrderCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.OrderResponseDto;
import uz.pdp.proyekt.entities.OrderEntity;
import uz.pdp.proyekt.service.orderService.OrderService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<OrderResponseDto>> addOrder(@Valid @RequestBody OrderCreateDto dto) {

        return ResponseEntity.ok(orderService.add(dto));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<BaseResponse<OrderResponseDto>> cancelOrder(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.cancel(orderId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/update/{orderId}")
    public ResponseEntity<BaseResponse<OrderResponseDto>> updateOrder(@PathVariable UUID orderId, @RequestBody OrderCreateDto dto) {
        BaseResponse<OrderResponseDto> response = orderService.update(orderId, dto);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getById/{orderId}")
    public ResponseEntity<OrderEntity> getById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.findById(orderId));
    }
}
