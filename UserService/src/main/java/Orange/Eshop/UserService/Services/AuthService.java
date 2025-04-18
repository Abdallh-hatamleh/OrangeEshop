package Orange.Eshop.UserService.Services;

import Orange.Eshop.UserService.DTOs.RegisterRequest;
import Orange.Eshop.UserService.DTOs.UpdateUserRequest;
import Orange.Eshop.UserService.DTOs.UserResponse;
import Orange.Eshop.UserService.Entities.User;
import Orange.Eshop.UserService.Mapper.UserMapper;
import Orange.Eshop.UserService.Repositories.UserRepository;
import Orange.Eshop.UserService.Utils.FileHandling;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService {
    private final FileHandling fileHandling;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    public void register(RegisterRequest request)
    {
        log.info(request.getName() + " service");
        if(userRepository.existsByEmail(request.getEmail()))
        {
            throw new RuntimeException("Email Already Registered!!");
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmailVerified(false);
        user.setAdmin(false);
        userRepository.save(user);
        log.info(user.getName() + " service done");
    }

    public UserResponse getUser(String email) {
        UserResponse userResponse;
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email" + email));

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        log.info(request);
        if(request.getName() != null) {
            user.setName(request.getName());
        }

        if(request.getPassword() != null) {
            String pass = passwordEncoder.encode(request.getPassword());
            user.setPassword(pass);
        }

        if(request.getImage() != null && !request.getImage().isEmpty()) {
            String filename = fileHandling.saveFile(request.getImage());
            user.setProfilePicture(filename);
        }
        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }
}
