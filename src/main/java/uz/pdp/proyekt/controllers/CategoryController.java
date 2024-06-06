package uz.pdp.proyekt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.proyekt.dtos.createDtos.CategoryCreateDto;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.CategoryResponseDto;
import uz.pdp.proyekt.service.categoryService.CategoryService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            description = "This method is used to add category",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<BaseResponse<CategoryResponseDto>> create(@Valid @RequestBody CategoryCreateDto dto, Principal principal){
        return ResponseEntity.ok(categoryService.create(dto, UUID.fromString(principal.getName())));
    }


    @Operation(
            description = "This method returns a single Category",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping("/getById/{categoryId}")
    public ResponseEntity<BaseResponse<CategoryResponseDto>> getById(@PathVariable UUID categoryId){
        return ResponseEntity.ok(categoryService.getById(categoryId));
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/first")
    private ResponseEntity<BaseResponse<List<CategoryResponseDto>>> firstCategory(){
        return ResponseEntity.ok(categoryService.firstCategories());
    }


    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/sub/{categoryId}")
    public ResponseEntity<BaseResponse<PageImpl<CategoryResponseDto>>> getSubCategories(@PathVariable UUID categoryId,
                                                                                        @RequestParam(value = "page", defaultValue = "0")
                                                                                        int page,
                                                                                        @RequestParam(value = "size", defaultValue = "10")
                                                                                        int size){
        return ResponseEntity.ok(categoryService.subCategories(categoryId, page, size));
    }

    @Operation(
            description = "This method return all Categories",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<CategoryResponseDto>>> getAll(@RequestParam(value = "page", defaultValue = "0")
                                                              int page,
                                                            @RequestParam(value = "size", defaultValue = "10")
                                                              int size) {
        return ResponseEntity.ok(categoryService.getAll(page, size));
    }

    @Operation(
            description = "This method updates the data of one Category",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<BaseResponse<String>> update(@Valid @RequestBody CategoryCreateDto dto, @PathVariable UUID categoryId) {
        return ResponseEntity.ok(categoryService.update(categoryId, dto));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(categoryService.delete(categoryId));
    }

}
