package uz.pdp.proyekt.service.userService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceIml implements UserService{
    private final UserRepository userRepository;


}
