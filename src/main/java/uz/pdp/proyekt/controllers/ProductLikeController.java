package uz.pdp.proyekt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.proyekt.dtos.createDtos.CategoryCreateDto;
import uz.pdp.proyekt.dtos.createDtos.ProductLikeCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.CategoryResponseDto;
import uz.pdp.proyekt.dtos.responseDto.ProductLikeResponseDto;
import uz.pdp.proyekt.dtos.responseDto.ProductResponseDto;
import uz.pdp.proyekt.service.productLikeService.ProductLikeService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/productLike")
@RequiredArgsConstructor
public class ProductLikeController {
    private final ProductLikeService productLikeService;

    @Operation(
            description = "This method is used to add Product like",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"USER", "ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('USER') or hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<ProductLikeResponseDto> create(@Valid @RequestBody ProductLikeCreateDto dto){
        return ResponseEntity.ok(productLikeService.create(dto));
    }


    @Operation(
            description = "This method get products which user liked",
            method = "Get method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"USER"})
    )
    @PreAuthorize(value = "hasAuthority('USER')")
    @GetMapping("/product-like-of-user")
    public ResponseEntity<BaseResponse<List<ProductLikeResponseDto>>> getAll(
                    @RequestParam(required = false, defaultValue = "1") int size,
                    @RequestParam(required = false, defaultValue = "10") int page,
                    Principal principal
            ) {
        return ResponseEntity.ok(productLikeService.allOfUser(size, page, UUID.fromString(principal.getName())));
    }


    @Operation(
            description = "This method delete product which user liked",
            method = "Delete method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"USER"})
    )
    @PreAuthorize(value = "hasAuthority('USER')")
    @DeleteMapping("/delete/{productLikeId}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable UUID productLikeId) {
        return ResponseEntity.ok(productLikeService.delete(productLikeId));
    }
}
