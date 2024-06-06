package uz.pdp.proyekt.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.UserResponseDto;
import uz.pdp.proyekt.service.userService.UserService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getById/{userId}")
    public ResponseEntity<BaseResponse<UserResponseDto>> getById(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getById(userId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<UserResponseDto>>> getAll(@RequestParam(value = "page", defaultValue = "0")
                                            int page,
                                                                    @RequestParam(value = "size", defaultValue = "5")
                                            int size) {
        return ResponseEntity.ok(userService.getAll(page, size));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateStatus/{userId}")
    public ResponseEntity<BaseResponse<String>> updateStatus(@PathVariable UUID userId,
                               @RequestParam Boolean status,
                               Principal principal) {
        return ResponseEntity.ok(userService.updateStatus(userId, status, UUID.fromString(principal.getName())));

    }
}
