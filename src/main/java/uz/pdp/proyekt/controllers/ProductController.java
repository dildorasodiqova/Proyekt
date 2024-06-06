package uz.pdp.proyekt.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.proyekt.dtos.createDtos.ProductCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.ProductResponseDto;
import uz.pdp.proyekt.service.productService.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<BaseResponse<ProductResponseDto>> create
            (
                    @RequestBody ProductCreateDto dto
            ) {
        return ResponseEntity.ok(productService.save(dto));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse<ProductResponseDto>> update
            (
                    @RequestParam UUID productId,
                    @RequestBody ProductCreateDto dto
            ) {
        return ResponseEntity.ok(productService.update(productId, dto));
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<ProductResponseDto>>> search(@RequestParam String word) {
        BaseResponse<List<ProductResponseDto>> response = productService.search(word);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/getById/{productId}")
    public ResponseEntity<BaseResponse<ProductResponseDto>> getById(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getById(productId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.delete(productId));
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/allOfCategory")
    public ResponseEntity<BaseResponse<List<ProductResponseDto>>> getAll(
                    @RequestParam UUID categoryId,
                    @RequestParam(required = false, defaultValue = "0") int size,
                    @RequestParam(required = false, defaultValue = "10") int page
            ) {
        return ResponseEntity.ok(productService.allOfCategory(categoryId, size, page));
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/all-of-shop")
    public ResponseEntity<BaseResponse<PageImpl<ProductResponseDto>>> getAllOfShop(
            @RequestParam UUID shopId,
            @RequestParam(required = false, defaultValue = "0") int size,
            @RequestParam(required = false, defaultValue = "10") int page
    ) {
        return ResponseEntity.ok(productService.productsOfShop(shopId, size, page));
    }
}
