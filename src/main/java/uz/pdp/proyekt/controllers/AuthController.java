package uz.pdp.proyekt.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.proyekt.dtos.createDtos.SignInDto;
import uz.pdp.proyekt.dtos.createDtos.UserCreateDto;
import uz.pdp.proyekt.dtos.responseDto.UserResponseDto;
import uz.pdp.proyekt.service.userService.UserService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag( name = "Auth Management")
@SecurityRequirement( name = "Bearer Authentication")
public class AuthController {
    private final UserService userService;

    @PostMapping("/access-token")
    public String getAccessToken(@RequestBody String refreshToken, Principal principal) {
        return userService.getAccessToken(refreshToken, UUID.fromString(principal.getName()));
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgetDto forgetDto) {
        return ResponseEntity.ok(userService.forgetPassword(forgetDto));
    }

    @PermitAll
    @PostMapping("/sign-up")
    public UserResponseDto signUp(@Valid @RequestBody UserCreateDto userCreateDto) {
        return userService.signUp(userCreateDto);
    }

    @PermitAll
    @PostMapping("/sign-in")
    public JwtResponse signIn(@RequestBody SignInDto signInDto) {
        return userService.signIn(signInDto);
    }

}
