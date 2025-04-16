package Orange.Eshop.UserService.Controllers;

import Orange.Eshop.UserService.DTOs.UpdateUserRequest;
import Orange.Eshop.UserService.DTOs.UserResponse;
import Orange.Eshop.UserService.Security.CustomUserDetails;
import Orange.Eshop.UserService.Services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final AuthService authService;

    @GetMapping
    public ResponseEntity<UserResponse> getuser(@AuthenticationPrincipal UserDetails userDetails) {

        UserResponse userResponse = authService.getUser(userDetails.getUsername());

        return ResponseEntity.ok(userResponse);

    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal CustomUserDetails userDetails,@Valid @RequestBody UpdateUserRequest request)
    {
        return ResponseEntity.ok(authService.updateUser(userDetails.getId(), request));
    }
}
