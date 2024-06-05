package uz.pdp.proyekt.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<ProductResponseDto>>> search(@RequestParam String word) {
        BaseResponse<List<ProductResponseDto>> response = productService.search(word);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getById/{productId}")
    public ResponseEntity<BaseResponse<ProductResponseDto>> getById(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getById(productId));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.delete(productId));
    }


    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ProductResponseDto>>> getAll(
                    @RequestParam UUID categoryId,
                    @RequestParam(required = false, defaultValue = "0") int size,
                    @RequestParam(required = false, defaultValue = "10") int page
            ) {
        return ResponseEntity.ok(productService.allOfCategory(categoryId, size, page));
    }
}
