package Orange.Eshop.UserService.Controllers;

import Orange.Eshop.UserService.DTOs.UpdateUserRequest;
import Orange.Eshop.UserService.DTOs.UserResponse;
import Orange.Eshop.UserService.Security.CustomUserDetails;
import Orange.Eshop.UserService.Services.AuthService;
//import jakarta.annotation.Resource;
//import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final AuthService authService;

    @GetMapping
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal UserDetails userDetails) {

        log.info("controller reached");
        UserResponse userResponse = authService.getUser(userDetails.getUsername());

        return ResponseEntity.ok(userResponse);

    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                   @Valid @ModelAttribute UpdateUserRequest updateUserRequest)
    {
        return ResponseEntity.ok(authService.updateUser(userDetails.getId(), updateUserRequest));
    }



    @GetMapping("/pic")
    public ResponseEntity<Resource> getProfilePicture(@AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        Path file = Paths.get("uploads/").resolve(userDetails.getProfilePicture());
        Resource resource = new UrlResource(file.toUri());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource);
    }
}
