package uz.pdp.proyekt.service.userService;

import uz.pdp.proyekt.dtos.createDtos.ForgetDto;
import uz.pdp.proyekt.dtos.createDtos.SignInDto;
import uz.pdp.proyekt.dtos.createDtos.UserCreateDto;
import uz.pdp.proyekt.dtos.createDtos.VerifyDto;
import uz.pdp.proyekt.dtos.responseDto.JwtResponse;
import uz.pdp.proyekt.dtos.responseDto.UserResponseDto;
import uz.pdp.proyekt.entities.UserEntity;
import uz.pdp.proyekt.entities.UserPassword;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserEntity findById(UUID userId);

    UserResponseDto signUp(UserCreateDto userCreateDto);

    String deleteUser(UUID userId);
    void emailSend(UserEntity userEntity);
    UserResponseDto verify(VerifyDto verifyDto);

    String forgetPassword(ForgetDto forgetDto);

    JwtResponse signIn(SignInDto signInDto);

    List<UserResponseDto> getAll(int page, int size);

    UserResponseDto getById(UUID uuid);

    String updateStatus(UUID userId, Boolean status, UUID uuid);

    String forgetPassword(ForgetDto forgetDto);
}
