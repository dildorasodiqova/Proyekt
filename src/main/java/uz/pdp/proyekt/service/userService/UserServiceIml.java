package uz.pdp.proyekt.service.userService;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.dtos.createDtos.*;
import uz.pdp.proyekt.dtos.responseDto.JwtResponse;
import uz.pdp.proyekt.dtos.responseDto.UserResponseDto;
import uz.pdp.proyekt.entities.UserEntity;
import uz.pdp.proyekt.entities.UserPassword;
import uz.pdp.proyekt.enums.UserRole;
import uz.pdp.proyekt.exception.BadRequestException;
import uz.pdp.proyekt.exception.DataAlreadyExistsException;
import uz.pdp.proyekt.exception.DataNotFoundException;
import uz.pdp.proyekt.repositories.PasswordRepository;
import uz.pdp.proyekt.repositories.UserRepository;
import uz.pdp.proyekt.service.MailService;
import uz.pdp.proyekt.service.jwt.JwtService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceIml implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MailService mailService;
    private final PasswordRepository passwordRepository;
    private final ModelMapper modelMapper;
    @Override
    public UserEntity findById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(()-> new DataNotFoundException(" User not found !"));
    }

    @Override
    public UserResponseDto signUp(UserCreateDto dto) {
        Optional<UserEntity> optionalUser = userRepository.findAllByEmailOrUsername(dto.getEmail(), dto.getUsername());
        if(optionalUser.isPresent()) {
            throw new DataAlreadyExistsException("User already exists with : " + dto.getEmail() + "or" + dto.getUsername());
        }
        UserEntity user = modelMapper.map(dto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        emailSend(user);
        return parse(user);
    }

    @Override
    public UserResponseDto verify(VerifyDto verifyDto) {
        UserEntity userEntity = userRepository.findByEmail(verifyDto.getEmail())
                .orElseThrow(() -> new DataNotFoundException("User not found with email: " + verifyDto.getEmail()));
        UserPassword passwords = passwordRepository.getUserPasswordById(userEntity.getId(),verifyDto.getCode())
                .orElseThrow(()-> new DataNotFoundException("Code is wrong !"));
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime sentDate = passwords.getSentDate();
        Duration duration = Duration.between(sentDate, currentTime);
        long minutes = duration.toMinutes();
        if(minutes <= passwords.getExpiry()) {
            userEntity.setIsAuthenticated(true);
            userRepository.save(userEntity);
            return parse(userEntity);
        }
        throw new AuthenticationCredentialsNotFoundException("Code is expired");
    }

    @Override
    public String deleteUser(UUID userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found with id: " + userId));
        userEntity.setIsActive(false);
        userRepository.save(userEntity);
        return "User deleted";
    }

    @Override
    public JwtResponse signIn(SignInDto dto) {
        UserEntity userEntity = userRepository.findAllByEmailOrUsername(dto.getUsernameOrEmail(), dto.getUsernameOrEmail())
                .orElseThrow(() -> new DataNotFoundException("User not found ! \n Please try again."));
            if(passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
                return new JwtResponse(jwtService.generateAccessToken(userEntity), jwtService.generateRefreshToken(userEntity));
            }
            throw new AuthenticationCredentialsNotFoundException("Password didn't match");

        throw new AuthenticationCredentialsNotFoundException("Not verified");

    }

    @Override
    public List<UserResponseDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userRepository.findAllByIsActiveTrue(pageRequest).stream().map(this::parse).toList();
    }

    @Override
    public void emailSend(UserEntity userEntity) {
        if(!userEntity.getIsActive()) {
            throw new AuthenticationCredentialsNotFoundException("User is not active");
        }
        String generatedString = RandomStringUtils.randomAlphanumeric(5);
        MailDto mailDto = new MailDto(generatedString, userEntity.getEmail());
        mailService.sendMail(mailDto);
         passwordRepository.save(new UserPassword(generatedString, userEntity, LocalDateTime.now(), 3));
    }

    @Override
    public UserResponseDto getById(UUID userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found with id: " + userId));
        return parse(userEntity);
    }

    @Override
    public String updateStatus(UUID userId, Boolean status, UUID currentUser) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found with id: " + userId));
        UserEntity current = userRepository.findById(currentUser)
                .orElseThrow(() -> new DataNotFoundException("User not found with id: " + userId));

        if (!current.getUserRole().equals(UserRole.ADMIN)){
            throw new BadRequestException("Only admin can update the status.");
        }
        user.setIsActive(status);
        userRepository.save(user);
        return "Successfully";
    }

    @Override
    public String forgetPassword(ForgetDto forgetDto) {
        UserEntity userEntity = userRepository.findByEmail(forgetDto.getEmail())
                .orElseThrow(() -> new DataNotFoundException("User not found with email: "));
        if(!userEntity.getIsAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("User is not verified");
        }

        UserPassword passwords = passwordRepository.getUserPasswordById(userEntity.getId(),forgetDto.getActivationCode())
                .orElseThrow(()-> new DataNotFoundException("Code is not found"));

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime sentDate = passwords.getSentDate();
        Duration duration = Duration.between(sentDate, currentTime);
        long minutes = duration.toMinutes();
        if(minutes <= passwords.getExpiry()) {
            userEntity.setPassword(passwordEncoder.encode(forgetDto.getNewPassword()));
            userRepository.save(userEntity);
            return "Password successfully updated";
        }
        throw new AuthenticationCredentialsNotFoundException("Code expired");
    }

    private UserResponseDto parse(UserEntity user) {
       return new UserResponseDto(user.getId(), user.getName(), user.getUsername(), user.getEmail(),user.getCreatedDate(), user.getUpdateDate());
    }


}