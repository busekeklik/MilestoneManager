package com.etiya.milestonemanager.controller.api;

import com.etiya.milestonemanager.business.services.IUserServices;
import com.etiya.milestonemanager.data.entity.UserEntity;
import com.etiya.milestonemanager.data.repository.IUserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/1.0")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private final IUserServices userService;
    private final IUserRepository userRepository;

    @Autowired
    public AuthController(IUserServices userService, IUserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        Optional<UserEntity> userOptional = userRepository.findByUserName(request.getUsername());
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (user.getPassword().equals(request.getPassword())) {
                return ResponseEntity.ok(new AuthResponse("success", "User authenticated successfully", user));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("error", "Invalid username or password", null));
    }


    @Setter
    @Getter
    public static class AuthRequest {
        private String username;
        private String password;
    }

    @Setter
    @Getter
    public static class AuthResponse {
        private String status;
        private String message;
        private UserEntity user;

        public AuthResponse(String status, String message, UserEntity user) {
            this.status = status;
            this.message = message;
            this.user = user;
        }
    }
}