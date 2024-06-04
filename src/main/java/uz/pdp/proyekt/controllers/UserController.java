package uz.pdp.proyekt.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping("/getById")
    public UserResponseDto getById(Principal principal) {
        return userService.getById(UUID.fromString(principal.getName()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-all")
    public List<UserResponseDto> getAll(@RequestParam(value = "page", defaultValue = "0")
                                            int page,
                                        @RequestParam(value = "size", defaultValue = "5")
                                            int size) {
        return userService.getAll(page, size);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public String delete(@PathVariable UUID userId) {
        return userService.deleteUser(userId);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateStatus/{userId}")
    public String updateStatus(@PathVariable UUID userId,
                               @RequestParam Boolean status,
                               Principal principal) {
        return userService.updateStatus(userId, status, UUID.fromString(principal.getName()));

    }
}
