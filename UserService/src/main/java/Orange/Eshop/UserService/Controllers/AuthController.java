package Orange.Eshop.UserService.Controllers;

import Orange.Eshop.UserService.DTOs.RegisterRequest;
import Orange.Eshop.UserService.Services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
     private final AuthService authService;

     @PostMapping("/register")
     public ResponseEntity<String> register(@RequestBody RegisterRequest request)
     {
         authService.register(request);
         return ResponseEntity.ok("User Registerd Successfully!");
     }
}
