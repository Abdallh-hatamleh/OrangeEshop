package Orange.Eshop.UserService.Services;

import Orange.Eshop.UserService.DTOs.RegisterRequest;
import Orange.Eshop.UserService.Entities.User;
import Orange.Eshop.UserService.Mapper.UserMapper;
import Orange.Eshop.UserService.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    public void register(RegisterRequest request)
    {
        if(userRepository.existsByEmail(request.getEmail()))
        {
            throw new RuntimeException("Email Already Registered!!");
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmailVerified(false);
        user.setAdmin(false);
        userRepository.save(user);
    }
}
