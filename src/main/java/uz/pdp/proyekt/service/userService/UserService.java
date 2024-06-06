package uz.pdp.proyekt.service.userService;

import uz.pdp.proyekt.dtos.createDtos.*;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.JwtResponse;
import uz.pdp.proyekt.dtos.responseDto.UserResponseDto;
import uz.pdp.proyekt.entities.UserEntity;
import uz.pdp.proyekt.entities.UserPassword;

import java.util.List;
import java.util.UUID;

public interface UserService {
    BaseResponse<UserResponseDto> signUp(UserCreateDto userCreateDto);

    JwtResponse signIn(SignInDto signInDto);

    BaseResponse<String> deleteUser(UUID userId);
    BaseResponse<List<UserResponseDto>> getAll(int page, int size);
    BaseResponse<String> updateStatus(UUID userId, Boolean status, UUID uuid);

    BaseResponse<UserResponseDto> getById(UUID uuid);
    UserEntity findById(UUID userId);

    void emailSend(UserEntity userEntity);

    UserResponseDto verify(VerifyDto verifyDto);

    BaseResponse<String> forgetPassword(ForgetDto forgetDto);

    String getAccessToken(String refreshToken, UUID userId);

    String getVerificationCode(String email);

    SubjectDto verifyToken(String token);


}
