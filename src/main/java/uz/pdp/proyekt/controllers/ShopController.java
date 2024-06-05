package uz.pdp.proyekt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.proyekt.dtos.createDtos.ShopCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.ShopResponseDto;
import uz.pdp.proyekt.service.shopService.ShopService;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @Operation(
            description = "This method is used to add shop",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<BaseResponse<ShopResponseDto>> create(@Valid @RequestBody ShopCreateDto dto){
        return ResponseEntity.ok(shopService.create(dto));
    }


    @Operation(
            description = "This method get all shops",
            method = "Get method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse<PageImpl<ShopResponseDto>>> getAll(
            @RequestParam(required = false, defaultValue = "1") int size,
            @RequestParam(required = false, defaultValue = "10") int page
    ) {
        return ResponseEntity.ok(shopService.getAll(size, page));
    }


    @Operation(
            description = "This method delete shop",
            method = "Delete method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{shopId}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable UUID shopId) {
        return ResponseEntity.ok(shopService.delete(shopId));
    }


    @Operation(
            description = "This method used to get one shop information",
            method = "Get method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping("/getById/{shopId}")
    public ResponseEntity<BaseResponse<ShopResponseDto>> getById(@PathVariable UUID shopId) {
        return ResponseEntity.ok(shopService.getById(shopId));
    }
}
